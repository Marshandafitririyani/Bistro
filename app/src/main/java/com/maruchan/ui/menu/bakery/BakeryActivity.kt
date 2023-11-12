package com.maruchan.ui.menu.bakery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.crocodic.core.extension.openActivity
import com.denzcoskun.imageslider.models.SlideModel
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.const.Const
import com.maruchan.bistro.data.room.bistroo.BistroList
import com.maruchan.bistro.data.room.bistroo.Category
import com.maruchan.bistro.databinding.ActivityBakeryBinding
import com.maruchan.bistro.databinding.ActivityLoginBinding
import com.maruchan.bistro.databinding.ItemRestoBinding
import com.maruchan.ui.detail.DetailActivity
import com.maruchan.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BakeryActivity :
    BaseActivity<ActivityBakeryBinding, BakeryViewModel>(R.layout.activity_bakery) {

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


/* initClick()
 adapter()
 getData()
 observe()

 val data = intent.getParcelableExtra<BistroList>(Const.BISTRO.BISTRO)
 binding.data=data


}
private fun initClick(){
 binding.swipeRefreshLayout.setOnRefreshListener {
     swip()

 }
}

private fun swip(){
 val id = intent.getIntExtra(Const.CATEGORY.CATEGORY, 0)
 when (id) {
     1 -> {
         viewModel.getBistroList(id.toString())
     }
     2 -> {
         viewModel.getBistroList(id.toString())
     }
     3 -> {
         viewModel.getBistroList(id.toString())
     }

 }
}

private fun adapter() {
 binding.recyclerViewBakery.adapter = adapterResto
 Log.d("cek data 2", "$adapterResto")
}

private fun getData() {
 val idCategory = intent.getIntExtra(Const.CATEGORY.CATEGORY, 0)
 viewModel.getBistroList(idCategory.toString())

 binding.bakeryTitle.text = when (idCategory) {
     1 -> "Bakery"
     2 -> "Launch"
     3 -> "All"
     else -> ""
 }
}

private fun observe() {
 lifecycleScope.launch {
     repeatOnLifecycle(Lifecycle.State.STARTED) {
         launch {
             viewModel.bistroList.collect { bistroo ->
                 adapterResto.submitList(bistroo)
                 adapter()
                 binding.swipeRefreshLayout.isRefreshing = false
                 bistroAll.clear()
                 bistroAll.addAll(bistroo)
                 adapterResto.submitList(bistroo)

                 binding.recyclerViewBakery.adapter?.notifyDataSetChanged()


             }
         }
     }
 }

}}*/



