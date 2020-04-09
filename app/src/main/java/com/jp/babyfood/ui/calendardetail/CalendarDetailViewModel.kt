package com.jp.babyfood.ui.calendardetail

import android.content.Context
import android.text.TextWatcher
import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.view.WordToChipTextWatcher
import javax.inject.Inject

class CalendarDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {


    fun getWordToChipTextWatcher(context: Context): TextWatcher {
        return WordToChipTextWatcher(context)
    }
}