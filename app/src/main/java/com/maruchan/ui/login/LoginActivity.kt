package com.maruchan.ui.login

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.databinding.ActivityLoginBinding
import com.maruchan.ui.home.HomeActivity
import com.maruchan.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()
        initClick()

    }

    private fun initClick() {
        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.tvSingUp.setOnClickListener {
            openActivity<RegisterActivity>()
        }
    }

    private fun login() {

        if (binding.etPhoneEmail.isEmptyRequired(R.string.label_must_fill) ||
            binding.etPassword.isEmptyRequired(R.string.label_must_fill)
        ) {
            return
        }
        val emailOrPhone = binding.etPhoneEmail.textOf()
        val password = binding.etPassword.textOf()


        viewModel.login(emailOrPhone, password)
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.responLogin.collect {
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