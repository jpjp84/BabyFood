package com.jp.babyfood.util.dispatchers

import androidx.recyclerview.widget.RecyclerView

class HomePagerScrollDispatcher {

    private var scrolledPosition = -1

    fun saveScrollPosition(scrolledPosition: Int) {
        this.scrolledPosition = scrolledPosition
    }

    fun onInsertedMonth(
        newState: Int,
        viewModel: OnFirstPage,
        callback: (insertPosition: Int) -> Unit
    ) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE || scrolledPosition != 0) {
            return
        }

        viewModel.updateMonths()?.let { callback(it) }
    }

    interface OnFirstPage {
        fun updateMonths(): Int?
    }
}