package com.gh.mp3player.thekidszone.view.fragment

import CommonUtils
import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gh.mp3player.thekidszone.databinding.ChoseLevelCrazyMathBinding
import com.gh.mp3player.thekidszone.view.act.PlayCrazyMathAct
import com.gh.mp3player.thekidszone.view.act.PlayCrazyMathAct2
import com.gh.mp3player.thekidszone.viewmodel.CommonViewModel


class ChoseLevelCrazyMath : BaseFragment<ChoseLevelCrazyMathBinding, CommonViewModel>() {
    override fun getClassVM(): Class<CommonViewModel> {
        return CommonViewModel::class.java
    }
    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        val bestScore = CommonUtils.getInstance().getPref("BEST")
        if (bestScore != null) {
            mbinding.bestNormal.text = "ĐIỂM CAO: $bestScore"
        }
        val bestScore2 = CommonUtils.getInstance().getPref("BEST2")
        if (bestScore2 != null) {
            mbinding.bestHard.text = "ĐIỂM CAO: $bestScore2"
        }
    }

    override fun initView() {
        mbinding.level1.setOnClickListener {
            val intent = Intent(context, Class.forName(PlayCrazyMathAct.TAG))
            requireContext().startActivity(intent)
        }
        mbinding.level2.setOnClickListener {
            val intent = Intent(context, Class.forName(PlayCrazyMathAct2.TAG))
            requireContext().startActivity(intent)
        }
    }

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): ChoseLevelCrazyMathBinding {
        return ChoseLevelCrazyMathBinding.inflate(layoutInflater)
    }


    companion object {
        val TAG: String = ChoseLevelCrazyMath::class.java.name
    }
}
