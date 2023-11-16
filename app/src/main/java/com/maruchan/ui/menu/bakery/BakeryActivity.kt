package com.maruchan.ui.menu.bakery

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.const.Const
import com.maruchan.bistro.data.room.bistroo.BistroList
import com.maruchan.bistro.databinding.ActivityBakeryBinding
import com.maruchan.bistro.databinding.ItemRestoBinding
import com.maruchan.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BakeryActivity :
    BaseActivity<ActivityBakeryBinding, BakeryViewModel>(R.layout.activity_bakery) {

    private val bistroAll = ArrayList<BistroList?>()


    private val adapterResto by lazy {
        ReactiveListAdapter<ItemRestoBinding, BistroList>(R.layout.item_resto).initItem { position, data ->
            val bistro = Intent(this, DetailActivity::class.java).apply {
                putExtra(Const.BISTRO.BISTRO, data)
            }

            startActivityForResult(bistro, 123)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        adapter()
        bistroList()
        initClick()


    }

    private fun initClick() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            bistroList()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun adapter() {
        binding.recyclerViewBakery.adapter = adapterResto
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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


            }

        }

    }

    private fun bistroList() {
        viewModel.getBistroList()
    }
}




