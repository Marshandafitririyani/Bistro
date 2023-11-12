package com.maruchan.ui.menu.cafe

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.toList
import com.google.gson.Gson
import com.maruchan.bistro.api.ApiService
import com.maruchan.bistro.base.BaseObserver
import com.maruchan.bistro.base.BaseViewModel
import com.maruchan.bistro.data.room.bistroo.BistroList
import com.maruchan.bistro.data.room.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CafeViewModel  @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val baseObserver: BaseObserver,
    private val session: CoreSession

) : BaseViewModel()   {
    private val _bistroList = MutableSharedFlow<List<BistroList>>()
    val bistroList = _bistroList.asSharedFlow()

    fun getBistroList() = viewModelScope.launch {
        baseObserver(block = { apiService.bistroCategoryTwo() },
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
}