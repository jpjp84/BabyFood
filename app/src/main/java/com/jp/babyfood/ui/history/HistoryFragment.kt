package com.jp.babyfood.ui.history

import com.jp.babyfood.R
import com.jp.babyfood.databinding.FragmentHomeBinding
import com.jp.babyfood.ui.base.BaseFragment

class HistoryFragment : BaseFragment<HistoryViewModel, FragmentHomeBinding>() {

    override fun getViewModelClass(): Class<HistoryViewModel> = HistoryViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_history

}