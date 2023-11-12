package com.maruchan.ui.profile

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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel  @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val baseObserver: BaseObserver,
    private val session: CoreSession

) : BaseViewModel() {

    val user = userDao.getUser()

    private val _responLogout = MutableSharedFlow<ApiResponse>()
    val responLogout = _responLogout.asSharedFlow()

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


    fun logout() = viewModelScope.launch {
        ApiObserver({ apiService.logout() },
            false, object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    _responLogout.emit(ApiResponse().responseSuccess("Logout Success"))
                    session.clearAll()
                    userDao.logout()
                    logoutSuccess()

                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _responLogout.emit(ApiResponse().responseError())
                }
            }
        )
    }
}