package com.maruchan.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.crocodic.core.extension.openActivity
import com.denzcoskun.imageslider.models.SlideModel
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.const.Const
import com.maruchan.bistro.data.room.bistroo.BistroList
import com.maruchan.bistro.databinding.ActivityDetailBinding
import com.maruchan.bistro.databinding.ItemRestoBinding
import com.maruchan.bistro.databinding.ItemRestoBoxBinding
import com.maruchan.ui.menu.bakery.BakeryActivity
import com.maruchan.ui.menu.fastfood.FastFoodActivity
import com.maruchan.ui.menu.lunchdining.LunchDiningActivity
import com.maruchan.ui.menu.showmenu.ShowMenuActivity
import com.maruchan.ui.table.TableActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity :  BaseActivity<ActivityDetailBinding, DetailViewModel>(R.layout.activity_detail) {
    private var bistro: BistroList? = null
    private var myUser: BistroList? = null
    private var isUser: Boolean = true
    private val produkAll = ArrayList<BistroList?>()
    private val imageList = ArrayList<SlideModel>()

    private val adapterResto by lazy {
        ReactiveListAdapter<ItemRestoBoxBinding, BistroList>(R.layout.item_resto_box).initItem { position, data ->
            val toEdit = Intent(this, DetailActivity::class.java).apply {
                Log.d("cek data 1", "$data")
                putExtra(Const.BISTRO.BISTRO, data)
            }

            startActivityForResult(toEdit, 123)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent.getParcelableExtra<BistroList>(Const.BISTRO.BISTRO)
        binding.data=data

        initClick()
        observe()
        bistroBoxList()
        adapter()

    }
    private fun initClick(){
        binding.btnBackEditProfile.setOnClickListener {
            finish()
        }
        binding.maps.setOnClickListener {
            maps()
        }
        binding.btnContactChat.setOnClickListener {
            val nomorHp = binding.contactDetail.text.toString().substring(1)
            if (nomorHp.isEmpty()) {
                Toast.makeText(
                    this@DetailActivity, getString(R.string.label_must_fill),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                whatsapp("+62$nomorHp")
            }
        }
        binding.showMenu.setOnClickListener {
            openActivity<ShowMenuActivity>()
        }
        binding.btnSend.setOnClickListener {
            shareText()
        }
        binding.orderATab.setOnClickListener {
            openActivity<TableActivity>()
        }

    }

    private fun adapter(){
        binding.rvDetail.adapter = adapterResto

    }
    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.bistroBox.collect { bistro ->
                        Log.d("cek data 3", "$bistro")
                        adapterResto.submitList(bistro)
                        produkAll.clear()
                        produkAll.addAll(bistro)
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
    private fun bistroBoxList() {
        viewModel.getBistroBoxList()
    }
    private fun maps() {
        val intentUri = Uri.parse("google.navigation:q=${bistro?.name}&mode=d")
        val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun whatsapp(number: String) {
        val intentUri = Uri.parse("https://api.whatsapp.com/send?phone=" + number)
        val waIntent = Intent(Intent.ACTION_VIEW)
        waIntent.setData(intentUri)
        startActivity(waIntent)
    }

    private fun shareText() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, binding.tvAddress.getText().toString())
        sendIntent.type = "text/plain"
        val shareIntent = Intent.createChooser(sendIntent, "chat")
        startActivity(shareIntent)

    }
}