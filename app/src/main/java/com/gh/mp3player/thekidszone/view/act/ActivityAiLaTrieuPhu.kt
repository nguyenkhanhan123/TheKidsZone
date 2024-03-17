package com.gh.mp3player.thekidszone.view.act

import com.gh.mp3player.thekidszone.databinding.ActivityAiLaTrieuPhuBinding

class ActivityAiLaTrieuPhu : BaseActivity<ActivityAiLaTrieuPhuBinding>() {
    override fun initView() {

    }

    override fun initViewBinding(): ActivityAiLaTrieuPhuBinding {
        return ActivityAiLaTrieuPhuBinding.inflate(layoutInflater)
    }
    companion object {
        val TAG: String = ActivityAiLaTrieuPhu::class.java.name
    }
}