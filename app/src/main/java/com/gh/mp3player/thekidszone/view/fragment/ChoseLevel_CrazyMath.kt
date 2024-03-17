package com.gh.mp3player.thekidszone.view.fragment

import CommonUtils
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gh.mp3player.thekidszone.databinding.ChoseLevelCrazyMathBinding
import com.gh.mp3player.thekidszone.view.act.PlayCrazyMathAct
import com.gh.mp3player.thekidszone.viewmodel.CommonViewModel


class ChoseLevel_CrazyMath : BaseFragment<ChoseLevelCrazyMathBinding, CommonViewModel>() {
    override fun getClassVM(): Class<CommonViewModel> {
        return CommonViewModel::class.java
    }

    override fun onStart() {
        super.onStart()
        var bestScore = CommonUtils.getInstance().getPref("BEST")
        if (bestScore != null) {
            mbinding.bestNormal.text = "ĐIỂM CAO: $bestScore"
        }
    }

    override fun initView() {
        mbinding.level1.setOnClickListener {
            val intent = Intent(context, Class.forName(PlayCrazyMathAct.TAG))
            requireContext().startActivity(intent)
        }
    }

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): ChoseLevelCrazyMathBinding {
        return ChoseLevelCrazyMathBinding.inflate(layoutInflater)
    }


    companion object {
        val TAG: String = ChoseLevel_CrazyMath::class.java.name
    }
}
