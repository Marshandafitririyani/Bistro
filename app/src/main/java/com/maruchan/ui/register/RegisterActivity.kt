package com.maruchan.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.snacked
import com.crocodic.core.extension.textOf
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.databinding.ActivityRegisterBinding
import com.maruchan.ui.home.HomeActivity
import com.maruchan.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity :
    BaseActivity<ActivityRegisterBinding, RegisterViewModel>(R.layout.activity_register) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()
        initClick()
    }

    private fun initClick(){
        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val name = binding.etUsername.textOf()
        val phone = binding.etPhoneEmail.textOf()
        val password = binding.etPassword.textOf()
        val confirmPassword = binding.etPasswordConfim.textOf()

        if (binding.etUsername.isEmptyRequired(R.string.label_must_fill) ||
            binding.etPhoneEmail.isEmptyRequired(R.string.label_must_fill) ||
            binding.etPassword.isEmptyRequired(R.string.label_must_fill) ||
            binding.etPasswordConfim.isEmptyRequired(R.string.label_must_fill)
        ) {
            return
        }
        if (password.length < 6) {
            binding.root.snacked(R.string.password_characters)

        } else {

            if (password != confirmPassword) {
                binding.tvPasswordNotMatch.visibility = View.VISIBLE
            } else {
                binding.tvPasswordNotMatch.visibility = View.GONE
                viewModel.register(name, phone, password,confirmPassword)
            }
        }
    }
    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Please Wait login")
                            ApiStatus.SUCCESS -> {
                                loadingDialog.dismiss()
                                openActivity<LoginActivity>()
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