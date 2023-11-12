package com.maruchan.ui.password

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.google.gson.Gson
import com.maruchan.bistro.api.ApiService
import com.maruchan.bistro.base.BaseObserver
import com.maruchan.bistro.base.BaseViewModel
import com.maruchan.bistro.data.room.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class EditPasswordViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val baseObserver: BaseObserver,
    private val session: CoreSession

) : BaseViewModel()  {
    private val _editPassword = MutableSharedFlow<ApiResponse>()
    val editPassword = _editPassword.asSharedFlow()

    fun editPassword(oldPassword: String?, newPassword: String?, confirmPassword: String?) = viewModelScope.launch {
        ApiObserver({ apiService.editPassword(oldPassword, newPassword,confirmPassword ) },
            false, object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    _editPassword.emit(ApiResponse().responseSuccess("Profile Updated"))
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _editPassword.emit(ApiResponse().responseError())
                }
            }
        )
    }
}