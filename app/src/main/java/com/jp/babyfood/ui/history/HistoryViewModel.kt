package com.jp.babyfood.ui.history

import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel()