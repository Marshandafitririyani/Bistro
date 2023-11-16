package com.maruchan.ui.password

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.databinding.ActivityEditPasswordBinding
import com.maruchan.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditPasswordActivity :
    BaseActivity<ActivityEditPasswordBinding, EditPasswordViewModel>(R.layout.activity_edit_password) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClick()
        observe()
    }


    private fun initClick() {
        binding.btnSave.setOnClickListener {
            editPassword()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }

    }

    private fun editPassword() {
        if (
            binding.etPasswordOld.isEmptyRequired(R.string.label_must_fill) ||
            binding.etPasswordNew.isEmptyRequired(R.string.label_must_fill) ||
            binding.etConfirmasiPassword.isEmptyRequired(R.string.label_must_fill)
        ) {
            return
        }
        val oldPassword = binding.etPasswordOld.textOf()
        val newPassword = binding.etPasswordNew.textOf()
        val confirmPassword = binding.etConfirmasiPassword.textOf()

        if (newPassword != confirmPassword) {
            binding.tvPasswordNotMatch.visibility = View.VISIBLE
        } else {
            binding.tvPasswordNotMatch.visibility = View.GONE
            viewModel.editPassword(oldPassword, newPassword, confirmPassword)
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.editPassword.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Please Wait login")
                            ApiStatus.SUCCESS -> {
                                loadingDialog.dismiss()
                                openActivity<HomeActivity>()
                                finish()
                            }
                            else -> loadingDialog.setResponse(it.message ?: return@collect)
                        }
                    }
                }
            }
        }
    }

}