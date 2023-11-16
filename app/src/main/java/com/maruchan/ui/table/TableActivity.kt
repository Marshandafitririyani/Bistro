package com.maruchan.ui.table

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.crocodic.core.extension.snacked
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.databinding.ActivityScreenBinding
import com.maruchan.bistro.databinding.ActivityTableBinding
import com.maruchan.ui.screen.ScreenViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TableActivity :  BaseActivity<ActivityTableBinding, TableViewModel>(R.layout.activity_table) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.orderATab.setOnClickListener {
            binding.root.snacked("Succes")

        }
    }
}