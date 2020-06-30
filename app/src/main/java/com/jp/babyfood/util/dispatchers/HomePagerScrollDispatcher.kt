package com.jp.babyfood.util.dispatchers

import androidx.recyclerview.widget.RecyclerView

class HomePagerScrollDispatcher {

    private var scrolledPosition = -1

    fun saveScrollPosition(scrolledPosition: Int) {
        this.scrolledPosition = scrolledPosition
    }

    fun onScrollStateChanged(
        newState: Int,
        viewModel: OnChangePage,
        itemCount: Int
    ) {

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            viewModel.onChangePage(scrolledPosition)
        }

        if (newState == RecyclerView.SCROLL_STATE_IDLE && scrolledPosition == 0) {
            viewModel.onFirstPage()
            return
        }

        if (newState == RecyclerView.SCROLL_STATE_IDLE && scrolledPosition == itemCount - 1) {
            viewModel.onLastPage()
            return
        }
    }

    interface OnChangePage {
        fun onFirstPage() {
            willUpdateEdgePage(true)
        }

        fun onLastPage() {
            willUpdateEdgePage(false)
        }

        fun onChangePage(position: Int) {

        }

        fun willUpdateEdgePage(isFirst: Boolean) {}
    }
}