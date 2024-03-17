package com.gh.mp3player.thekidszone.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gh.mp3player.thekidszone.databinding.SplashFragmentBinding
import com.gh.mp3player.thekidszone.viewmodel.CommonViewModel

class SplashFragment: BaseFragment<SplashFragmentBinding, CommonViewModel>() {
    override fun getClassVM(): Class<CommonViewModel> {
        return CommonViewModel::class.java
    }
    override fun initView() {
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): SplashFragmentBinding {
        return SplashFragmentBinding.inflate(layoutInflater)
    }

    companion object {
        val TAG: String=SplashFragment::class.java.name
    }
}