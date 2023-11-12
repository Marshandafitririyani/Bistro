package com.maruchan.ui.edit

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.*
import com.crocodic.core.helper.BitmapHelper
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.data.helper.ViewModelHelperAvatar.Companion.writeBitmap
import com.maruchan.bistro.databinding.ActivityEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class EditProfilrActivity :
    BaseActivity<ActivityEditProfileBinding, EditProfilViewModel>(R.layout.activity_edit_profile) {
    private var photoFile: File? = null
    private var username: String? = null
    private var phoneOrEmail: String? = null
    private var photo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClick()
        observe()

        username = intent.getStringExtra("username")
        photo = intent.getStringExtra("profile_photo_path")
        binding.edit = this
        binding.photo = photo
        binding.etUsername.setText(username)

        Glide
            .with(this)
            .load(photo)
            .placeholder(R.drawable.img_loading)
            .apply(RequestOptions.centerCropTransform())
            .error(R.drawable.img_error)
            .into(binding.imgPhotoProfile)
    }

    private fun initClick() {
        binding.imgPhotoProfile.setOnClickListener {
            gallery()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnSave.setOnClickListener {
            validateForm()
        }

    }

    private fun gallery() {
        activityLauncher.openGallery(this) { file, exception ->
            binding.imageFileEdit
            val bitmap = BitmapFactory.decodeFile(file?.absolutePath)
            val resizeBitmap = BitmapHelper.resizeBitmap(bitmap, 512f)

            photoFile = createImageFile().also { it.writeBitmap(resizeBitmap) }
            binding.imageFileEdit = photoFile
//            Log.d("foto", "$photoFile")
            file?.delete()
        }
    }


    private fun validateForm() {
        val name = binding.etUsername.textOf()

        if (name.isEmpty()) {
            binding.root.snacked(R.string.empty_user)
            return
            tos("name")
            Log.d("nama1", "$name")
        }

        if (photoFile == null) {
            if (name == username) {
                binding.root.snacked(R.string.nothing_chng)
                return
                tos("photo")
                Log.d("nama2", "$photoFile")
            }
            viewModel.updateProfile(name)
        } else {
            lifecycleScope.launch {
                binding.root.snacked(R.string.compressing)
                if (photoFile != null) {
                    viewModel.updateProfileWhite(name, photoFile ?: return@launch)
                }
            }
        }

    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.user.collect {
                        binding.user = it
                    }
                }
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show(R.string.loading_save)
                            ApiStatus.SUCCESS -> {
                                loadingDialog.dismiss()
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