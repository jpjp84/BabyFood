package com.jp.babyfood.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jp.babyfood.R
import com.jp.babyfood.databinding.FragmentHomeBinding
import com.jp.babyfood.ui.base.BaseFragment

class HistoryFragment : BaseFragment<HistoryViewModel, FragmentHomeBinding>() {

    override fun getViewModelClass(): Class<HistoryViewModel> = HistoryViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_history

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}