package com.maruchan.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.crocodic.core.extension.openActivity
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.const.Const
import com.maruchan.bistro.data.room.bistroo.BistroList
import com.maruchan.bistro.data.room.bistroo.Category
import com.maruchan.bistro.databinding.ActivityHomeBinding
import com.maruchan.bistro.databinding.ItemRestoBinding
import com.maruchan.ui.detail.DetailActivity
import com.maruchan.ui.menu.bakery.BakeryActivity
import com.maruchan.ui.menu.bruch.BrunchDiningActivity
import com.maruchan.ui.menu.cafe.CafeActivity
import com.maruchan.ui.menu.dining.DiningActivity
import com.maruchan.ui.menu.fastfood.FastFoodActivity
import com.maruchan.ui.menu.lunchdining.LunchDiningActivity
import com.maruchan.ui.menu.pizza.PizzeriaActivity
import com.maruchan.ui.menu.ramen.RamenDiningActivity
import com.maruchan.ui.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {

    private var isUser: Boolean = true
    private val bistroAll = ArrayList<BistroList?>()
    private val categoryList = ArrayList<Category?>()
    private var categoryId: String? = null


    private val adapterResto by lazy {
        ReactiveListAdapter<ItemRestoBinding, BistroList>(R.layout.item_resto).initItem { position, data ->
            val toEdit = Intent(this, DetailActivity::class.java).apply {
                putExtra(Const.BISTRO.BISTRO, data)
            }

            startActivityForResult(toEdit, 123)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClick()
        observe()
        search()
        searchFilter()
        adapter()
        getCategory()
        bistroList()
    }

    private fun initClick() {
        binding.btnProfile.setOnClickListener {
            openActivity<ProfileActivity>()
        }
        binding.btnFastFood.setOnClickListener {
            openActivity<FastFoodActivity>()
        }
        binding.btnBakery.setOnClickListener {
            openActivity<BakeryActivity>()
        }
        binding.btnLaunchDining.setOnClickListener {
            openActivity<LunchDiningActivity>()
        }
        binding.btnRamenDining.setOnClickListener {
            openActivity<RamenDiningActivity>()
        }
        binding.btnBrunchDining.setOnClickListener {
            openActivity<BrunchDiningActivity>()
        }
        binding.btnDining.setOnClickListener {
            openActivity<DiningActivity>()
        }
        binding.btnCaffe.setOnClickListener {
            openActivity<CafeActivity>()
        }
        binding.btnPizza.setOnClickListener {
            openActivity<PizzeriaActivity>()
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            bistroList()
        }
        binding.etSearchFilter.setOnClickListener {
            autocompleteSpinner()
        }
    }


    private fun search() {
        binding.etSearchHome.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()) {
                val filter = bistroAll.filter { it?.name?.contains("$text", true) == true }
                adapterResto.submitList(filter)

                if (filter.isEmpty()) {
                    binding.tvEmpty
                    binding.tvEmpty.visibility = View.VISIBLE
                } else {
                    binding.tvEmpty.visibility = View.GONE
                }
            } else {
                adapterResto.submitList(bistroAll)
                binding.tvEmpty.visibility = View.GONE
            }

        }
    }

    private fun searchFilter() {
        binding.etSearchFilter.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()) {
                val filter =
                    bistroAll.filter { it?.category?.name?.contains("$text", true) == true }
                adapterResto.submitList(filter)

                if (filter.isEmpty()) {
                    binding.tvEmpty
                    binding.tvEmpty.visibility = View.VISIBLE
                } else {
                    binding.tvEmpty.visibility = View.GONE
                }
            } else {
                adapterResto.submitList(bistroAll)
                binding.tvEmpty.visibility = View.GONE
            }

        }
    }

    private fun autocompleteSpinner() {
        val autoCompleteSpinner = binding.etSearchFilter
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categoryList)
        autoCompleteSpinner.setAdapter(adapter)

        autoCompleteSpinner.setOnClickListener {
            autoCompleteSpinner.showDropDown()
            autoCompleteSpinner.setDropDownVerticalOffset(autoCompleteSpinner.height)
        }
        autoCompleteSpinner.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = categoryList[position]
            categoryId = selectedItem?.id.toString()

        }
    }


    private fun adapter() {
        binding.recyclerViewHome.adapter = adapterResto
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.user.collect { data ->
                        binding.user = data
                        if (isUser) {
                            getProfile()
                            isUser = false


                        }
                    }

                }
                launch {
                    viewModel.bistroList.collect { bistro ->
                        adapterResto.submitList(bistro)
                        binding.swipeRefreshLayout.isRefreshing = false
                        bistroAll.clear()
                        bistroAll.addAll(bistro)
                        adapterResto.submitList(bistro)

                        if (bistro.isEmpty()) {
                            binding.tvEmpty.visibility = View.VISIBLE
                        } else {
                            binding.tvEmpty.visibility = View.GONE
                        }

                    }

                }
                launch {
                    viewModel.saveGetCategory.collect { category ->
                        categoryList.addAll(category)

                    }
                }


            }

        }

    }

    private fun getProfile() {
        viewModel.getProfile()
    }

    private fun bistroList() {
        viewModel.getBistroList()
    }

    private fun getCategory() {
        viewModel.getCategory()
    }
}