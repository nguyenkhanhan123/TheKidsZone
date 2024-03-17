package com.gh.mp3player.thekidszone.view.act

import com.gh.mp3player.thekidszone.databinding.ActivityKidsMusicBinding


class ActivityKidsMusic : BaseActivity<ActivityKidsMusicBinding>() {
    override fun initView() {

    }

    override fun initViewBinding(): ActivityKidsMusicBinding {
        return ActivityKidsMusicBinding.inflate(layoutInflater)
    }
    companion object {
        val TAG: String = ActivityKidsMusic::class.java.name
    }
}