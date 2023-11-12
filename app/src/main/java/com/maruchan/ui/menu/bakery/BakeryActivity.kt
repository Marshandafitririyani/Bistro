package com.maruchan.ui.menu.bakery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.openActivity
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.const.Const
import com.maruchan.bistro.data.room.bistroo.BistroList
import com.maruchan.bistro.databinding.ActivityBakeryBinding
import com.maruchan.bistro.databinding.ActivityLoginBinding
import com.maruchan.bistro.databinding.ItemRestoBinding
import com.maruchan.ui.detail.DetailActivity
import com.maruchan.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BakeryActivity : BaseActivity<ActivityBakeryBinding, BakeryViewModel>(R.layout.activity_bakery) {
    private var bistro = ArrayList<BistroList?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClick()

    }
    private fun initClick(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            swip()

        }
     /*   binding..setOnClickListener {
            onBackPressed()
        }*/

        binding.recyclerViewHome.adapter =
            object : CoreListAdapter<ItemRestoBinding, BistroList>(R.layout.item_resto) {
                override fun onBindViewHolder(
                    holder: ItemViewHolder<ItemRestoBinding, BistroList>,
                    position: Int
                ) {
                    val data = bistro[position]
                    holder.binding.data = data

                    holder.binding.cvItemDestination.setOnClickListener {
                        openActivity<DetailActivity> {
                            putExtra(Const.CATEGORY.CATEGORY, data)
                        }
                    }
                }
            }.initItem(bistro)

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

}

