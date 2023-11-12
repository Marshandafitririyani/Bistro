package com.maruchan.ui.menu.ramen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.const.Const
import com.maruchan.bistro.data.room.bistroo.BistroList
import com.maruchan.bistro.data.room.bistroo.Category
import com.maruchan.bistro.databinding.ActivityPizzeriaBinding
import com.maruchan.bistro.databinding.ActivityRamenDiningBinding
import com.maruchan.bistro.databinding.ItemRestoBinding
import com.maruchan.ui.detail.DetailActivity
import com.maruchan.ui.menu.pizza.PizzeriaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RamenDiningActivity : BaseActivity<ActivityRamenDiningBinding, RamenDiningViewModel>(R.layout.activity_ramen_dining) {
    private val bistroAll = ArrayList<BistroList?>()
    private val categoryList = ArrayList<Category?>()
    private var categoryId: String? = null


    private val adapterResto by lazy {
        ReactiveListAdapter<ItemRestoBinding, BistroList>(R.layout.item_resto).initItem { position, data ->
            val bistro = Intent(this, DetailActivity::class.java).apply {
                Log.d("cek data 1", "$data")
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

    }
    private fun adapter() {
        binding.recyclerViewBakery.adapter = adapterResto
        Log.d("cek data 2", "$adapterResto")
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.bistroList.collect { bistro ->
                        Log.d("cek data 3", "$bistro")
                        bistroAll.clear()
                        bistroAll.addAll(bistro)
                        adapterResto.submitList(bistro)

                    }

                }



            }

        }

    }
    private fun bistroList() {
        viewModel.getBistroList()
    }

}