package com.maruchan.ui.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.clearNotification
import com.crocodic.core.extension.openActivity
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.databinding.ActivityProfileBinding
import com.maruchan.ui.edit.EditProfilrActivity
import com.maruchan.ui.home.HomeActivity
import com.maruchan.ui.password.EditPasswordActivity
import com.maruchan.ui.register.RegisterActivity
import com.maruchan.ui.screen.ScreenActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding, ProfileViewModel>(R.layout.activity_profile){

    private var isUser: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()
        initClick()
    }

    private fun initClick(){
        binding.btnEditProfile.setOnClickListener {
            profile()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnLogout.setOnClickListener {
            logout()
        }
        binding.btnEditPassword.setOnClickListener {
            openActivity<EditPasswordActivity> {  }
        }
    }

    private fun profile() {
        val editProfile = Intent(this, EditProfilrActivity::class.java).apply {
            putExtra("name", binding.user?.name)
            putExtra("phoneOrEmail", binding.user?.phoneOrEmail)
            putExtra("photoProfile", binding.user?.photoProfile.toString())

        }
        startActivity(editProfile)
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.user.collect { data ->
                        binding.user = data
                        if (isUser) {
                            getProfile()
                            isUser = false


                        }
                    }

                }
                launch {
                    viewModel.responLogout.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> {
                                loadingDialog.show()
                            }
                            ApiStatus.SUCCESS -> {
                                loadingDialog.dismiss()
                                clearNotification()
                                openActivity<ScreenActivity> {
                                    finishAffinity()
                                }
                            }
                            ApiStatus.ERROR -> {
                                loadingDialog.setResponse(it.message ?: return@collect)
                            }
                            else -> loadingDialog.setResponse(it.message ?: return@collect)
                        }
                    }


                }


            }

        }

    }
    private fun getProfile() {
        viewModel.getProfile()
    }
    private fun logout() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        val customLayout: View = layoutInflater.inflate(R.layout.popup_logout, null)
        builder.setView(customLayout)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnLogout = customLayout.findViewById<TextView>(R.id.btn_yes)
        btnLogout.setOnClickListener {
            viewModel.logout()
        }

        val btnCancle = customLayout.findViewById<TextView>(R.id.btn_no)
        btnCancle.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}