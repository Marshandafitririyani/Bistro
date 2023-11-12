package com.maruchan.ui.login

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
import com.maruchan.bistro.const.Const
import com.maruchan.bistro.data.room.user.User
import com.maruchan.bistro.data.room.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val baseObserver: BaseObserver,
    private val session: CoreSession



) : BaseViewModel()  {

    private val _responLogin = MutableSharedFlow<ApiResponse>()
    val responLogin = _responLogin.asSharedFlow()

    fun login(phone: String, password: String) = viewModelScope.launch {
        _responLogin.emit(ApiResponse().responseLoading())
        baseObserver(
            block = { apiService.login(phone, password) },
            toast = false,
            responseListener = object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                    val token = response.getString("access_token")
                    session.setValue(Const.TOKEN.API_TOKEN, token)
                    userDao.insert(data.copy(idRoom = 1))
                    _responLogin.emit(ApiResponse().responseSuccess("Loading Success"))
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _responLogin.emit(ApiResponse().responseError())
                }
            }
        )
    }
}