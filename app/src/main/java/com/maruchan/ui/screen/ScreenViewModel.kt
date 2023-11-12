package com.maruchan.ui.screen

import androidx.lifecycle.viewModelScope
import com.crocodic.core.data.CoreSession
import com.google.gson.Gson
import com.maruchan.bistro.api.ApiService
import com.maruchan.bistro.base.BaseObserver
import com.maruchan.bistro.base.BaseViewModel
import com.maruchan.bistro.data.room.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenViewModel @Inject constructor(private val userDao: UserDao) : BaseViewModel() {

    fun splash(done: (Boolean) -> Unit) = viewModelScope.launch {
        val login = userDao.isLogin()
        delay(1000)
        done(login)
    }
}