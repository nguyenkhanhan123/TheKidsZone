package com.gh.mp3player.thekidszone.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gh.mp3player.thekidszone.view.adapter.DetailAdapter
import com.gh.mp3player.thekidszone.model.GameModel
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.databinding.LearnAndPlayFragmentBinding
import com.gh.mp3player.thekidszone.viewmodel.CommonViewModel
import com.gh.mp3player.thekidszone.view.act.ActivityAiLaTrieuPhu
import com.gh.mp3player.thekidszone.view.act.ActivityCrazyMath
import com.gh.mp3player.thekidszone.view.act.ActivityKidsMusic

class LearnAndPlayFragment:
    BaseFragment<LearnAndPlayFragmentBinding, CommonViewModel>() {
    override fun getClassVM(): Class<CommonViewModel> {
        return CommonViewModel::class.java
    }
    val listNull:List<GameModel> = listOf(
        GameModel(R.drawable.img_crazy_math,
        R.string.crazy_math_text,ActivityCrazyMath.TAG
    ),GameModel(R.drawable.img_ai_la_trieu_phu,
            R.string.ai_la_trieu_phu_text,ActivityAiLaTrieuPhu.TAG),
        GameModel(R.drawable.img_kids_music,R.string.kids_music_text,ActivityKidsMusic.TAG)
    )

    override fun initView() {
        val adapter = DetailAdapter(requireContext(), listNull)
        mbinding.vpActivities.adapter = adapter
        mbinding.vpActivities.setCurrentItem(0, true)
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LearnAndPlayFragmentBinding {
        return LearnAndPlayFragmentBinding.inflate(layoutInflater)
    }

    companion object {
        val TAG: String=LearnAndPlayFragment::class.java.name
    }

}

