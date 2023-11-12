package com.maruchan.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.toList
import com.crocodic.core.extension.toObject
import com.google.gson.Gson
import com.maruchan.bistro.api.ApiService
import com.maruchan.bistro.base.BaseObserver
import com.maruchan.bistro.base.BaseViewModel
import com.maruchan.bistro.data.room.bistro.BistroDetail
import com.maruchan.bistro.data.room.bistroo.BistroList
import com.maruchan.bistro.data.room.bistroo.Category
import com.maruchan.bistro.data.room.user.User
import com.maruchan.bistro.data.room.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val baseObserver: BaseObserver,
    private val session: CoreSession

) : BaseViewModel()  {
    val user = userDao.getUser()

    private val _bistroList = MutableSharedFlow<List<BistroList>>()
    val bistroList = _bistroList.asSharedFlow()

    private val _saveGetCategory = MutableSharedFlow<List<Category?>>()
    val saveGetCategory = _saveGetCategory.asSharedFlow()

    fun getProfile() = viewModelScope.launch {
        baseObserver(
            block = { apiService.getProfile() },
            toast = false,
            responseListener = object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                    userDao.insert(data.copy(idRoom = 1))

                }
            }
        )
    }

    fun getBistroList() = viewModelScope.launch {
        baseObserver(block = { apiService.getBistroList() },
            toast = false,
            responseListener = object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONArray(ApiCode.DATA).toList<BistroList>(gson)
                    _bistroList.emit(data)
                    Timber.d("cek api ${data.size}")

                    _apiResponse.emit(ApiResponse().responseSuccess())
                    Log.d("cek ss","success")

                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _apiResponse.emit(ApiResponse().responseError())
                    Log.d("cek err","error")
                }
            })
    }

    fun getCategory() = viewModelScope.launch {
        baseObserver(block = { apiService.getCategory() },
            toast = false,
            responseListener = object : ApiObserver.ResponseListener {
            override suspend fun onSuccess(response: JSONObject) {
                val data = response.getJSONArray(ApiCode.DATA).toList<Category>(gson)
                _saveGetCategory.emit(data)
                _apiResponse.emit(ApiResponse().responseSuccess("Category"))
            }

            override suspend fun onError(response: ApiResponse) {
                super.onError(response)
                _apiResponse.emit(ApiResponse().responseError())
            }


        })


    }
}