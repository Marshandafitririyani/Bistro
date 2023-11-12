package com.maruchan.ui.edit

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.toObject
import com.google.gson.Gson
import com.maruchan.bistro.api.ApiService
import com.maruchan.bistro.base.BaseObserver
import com.maruchan.bistro.base.BaseViewModel
import com.maruchan.bistro.data.room.user.User
import com.maruchan.bistro.data.room.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProfilViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val baseObserver: BaseObserver,
    private val session: CoreSession

) : BaseViewModel() {
    val user = userDao.getUser()

    fun updateProfileWhite(name: String, photo: File?) =
        viewModelScope.launch {
            if (photo != null) {
                val fileBody = photo.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("photo", photo.name, fileBody)
                _apiResponse.emit(ApiResponse().responseLoading())
                baseObserver(
                    block = { apiService.updateProfileWithPhoto(name, filePart) },
                    toast = false,
                    responseListener = object : ApiObserver.ResponseListener {
                        override suspend fun onSuccess(response: JSONObject) {
                            val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                            userDao.insert(data.copy(idRoom = 1))
                            _apiResponse.emit(ApiResponse().responseSuccess("Update Photo"))

                        }

                        override suspend fun onError(response: ApiResponse) {
                            super.onError(response)
                            _apiResponse.emit(ApiResponse().responseError())
                        }
                    }
                )

            }

        }

    fun updateProfile(name: String) = viewModelScope.launch {
        _apiResponse.emit(ApiResponse().responseLoading())
        baseObserver(
            block = { apiService.updateProfil(name) },
            toast = false,
            responseListener = object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                    userDao.insert(data.copy(idRoom = 1))
                    _apiResponse.emit(ApiResponse().responseSuccess("Update Profile"))
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _apiResponse.emit(ApiResponse().responseError())
                }
            }
        )
    }
}