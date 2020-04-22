package com.jp.babyfood.util.dispatchers

import androidx.recyclerview.widget.RecyclerView

class HomePagerScrollDispatcher {

    private var scrolledPosition = -1

    fun saveScrollPosition(scrolledPosition: Int) {
        this.scrolledPosition = scrolledPosition
    }

    fun onScrollStateChanged(newState: Int, viewModel: OnFirstPage) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE || scrolledPosition != 0) {
            return
        }

        viewModel.onUpdate()
    }

    interface OnFirstPage {
        fun onUpdate()
    }
}