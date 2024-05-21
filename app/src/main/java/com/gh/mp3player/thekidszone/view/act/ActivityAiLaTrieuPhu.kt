package com.gh.mp3player.thekidszone.view.act

import android.widget.Toast
import com.gh.mp3player.thekidszone.databinding.ActivityAiLaTrieuPhuBinding

class ActivityAiLaTrieuPhu : BaseActivity<ActivityAiLaTrieuPhuBinding>() {
    override fun initView() {
        Toast.makeText(this,"Trò chơi đang được cải tiến, quay lại sau!", Toast.LENGTH_SHORT).show()
    }

    override fun initViewBinding(): ActivityAiLaTrieuPhuBinding {
        return ActivityAiLaTrieuPhuBinding.inflate(layoutInflater)
    }
    companion object {
        val TAG: String = ActivityAiLaTrieuPhu::class.java.name
    }
}