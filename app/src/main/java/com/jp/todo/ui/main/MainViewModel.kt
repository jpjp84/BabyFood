package com.jp.todo.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.todo.data.entity.User
import com.jp.todo.data.repository.UserRepository
import com.jp.todo.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun updateUser() {
        addDisposable(
            userRepository.getUser(true).subscribe(
                { _user.value = it },
                { Log.e("AB_TAG", "Throwable : ", it) }
            )
        )
    }

}