package com.jp.babyfood.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.babyfood.data.entity.User
import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun updateUser() {
//TODO GET USER DATA
//        addDisposable(
//            userRepository.getUser(true).subscribe(
//                { _user.value = it },
//                { Log.e("AB_TAG", "Throwable : ", it) }
//            )
//        )
    }

}