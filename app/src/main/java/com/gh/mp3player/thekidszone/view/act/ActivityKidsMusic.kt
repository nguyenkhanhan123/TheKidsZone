package com.gh.mp3player.thekidszone.view.act

import android.widget.Toast
import com.gh.mp3player.thekidszone.databinding.ActivityKidsMusicBinding


class ActivityKidsMusic : BaseActivity<ActivityKidsMusicBinding>() {
    override fun initView() {
Toast.makeText(this,"Trò chơi đang được cải tiến, quay lại sau!",Toast.LENGTH_SHORT).show()
    }

    override fun initViewBinding(): ActivityKidsMusicBinding {
        return ActivityKidsMusicBinding.inflate(layoutInflater)
    }
    companion object {
        val TAG: String = ActivityKidsMusic::class.java.name
    }
}