package com.jp.babyfood.ui.calendardetail

import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import javax.inject.Inject

class CalendarDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

}