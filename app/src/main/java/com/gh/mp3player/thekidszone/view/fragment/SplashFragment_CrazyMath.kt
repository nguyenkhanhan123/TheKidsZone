package com.gh.mp3player.thekidszone.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gh.mp3player.thekidszone.databinding.SplashCrazyMathBinding
import com.gh.mp3player.thekidszone.viewmodel.CommonViewModel

class SplashFragmentCrazyMath: BaseFragment<SplashCrazyMathBinding, CommonViewModel>() {
    override fun getClassVM(): Class<CommonViewModel> {
        return CommonViewModel::class.java
    }

    override fun initView() {

    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): SplashCrazyMathBinding {
        return SplashCrazyMathBinding.inflate(layoutInflater)
    }

    companion object {
        val TAG: String=SplashFragmentCrazyMath::class.java.name
    }
}