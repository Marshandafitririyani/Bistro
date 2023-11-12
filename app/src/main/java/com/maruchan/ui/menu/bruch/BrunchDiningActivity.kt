package com.maruchan.ui.menu.bruch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.const.Const
import com.maruchan.bistro.data.room.bistroo.BistroList
import com.maruchan.bistro.data.room.bistroo.Category
import com.maruchan.bistro.databinding.ActivityBrunchDiningctivityBinding
import com.maruchan.bistro.databinding.ItemRestoBinding
import com.maruchan.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrunchDiningActivity :  BaseActivity<ActivityBrunchDiningctivityBinding, BrunchDiningViewModel>(R.layout.activity_brunch_diningctivity){
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
    }
}