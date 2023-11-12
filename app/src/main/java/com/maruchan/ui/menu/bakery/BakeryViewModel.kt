package com.maruchan.ui.menu.bakery

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
import com.maruchan.bistro.data.room.bistroo.BistroList
import com.maruchan.bistro.data.room.bistroo.Category
import com.maruchan.bistro.data.room.bistroo.StatusCategoryTwo
import com.maruchan.bistro.data.room.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BakeryViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val baseObserver: BaseObserver,
    private val session: CoreSession



) : BaseViewModel() {

    private val _saveGetFilterCategory = MutableSharedFlow<List<Category?>>()
    val saveGetFilterCategory = _saveGetFilterCategory.asSharedFlow()

    private val _bistroList = MutableSharedFlow<List<BistroList>>()
    val bistroList = _bistroList.asSharedFlow()

    private val _responseStatusHistory = MutableSharedFlow<StatusCategoryTwo>()
    val responseStatusHistory = _responseStatusHistory.asSharedFlow()

   /* fun getBistro() = viewModelScope.launch {
        baseObserver(block = { apiService.getBistroList() },
            toast = false,
            responseListener = object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONArray(ApiCode.DATA).toList<BistroList>(gson)
                    _bistroList.emit(data)
                    Timber.d("cek api ${data.size}")

                    val dataSumAttHistory = response.getJSONObject(ApiCode.DATA).toObject<StatusCategoryTwo>(gson)
                    _responseStatusHistory.emit(dataSumAttHistory)

                    _apiResponse.emit(ApiResponse().responseSuccess())
                    Log.d("cek ss","success")

                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _apiResponse.emit(ApiResponse().responseError())
                    Log.d("cek err","error")
                }
            })
    }*/

    fun getBistroList() = viewModelScope.launch {
        baseObserver(block = { apiService.bistroCategoryOne() },
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

   /* fun getBistroList(id: String?) = viewModelScope.launch {
        baseObserver(block = { apiService.bistroCategory(id) },
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
*/
    fun getCategory() = viewModelScope.launch {
        baseObserver(block = { apiService.getCategory() },
            toast = false,
            responseListener = object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONArray(ApiCode.DATA).toList<Category>(gson)
                    _saveGetFilterCategory.emit(data)
                    _apiResponse.emit(ApiResponse().responseSuccess("Category"))
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _apiResponse.emit(ApiResponse().responseError())
                }


            })


    }

}