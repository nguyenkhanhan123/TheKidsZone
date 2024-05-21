package com.gh.mp3player.thekidszone.view.act

import com.gh.mp3player.thekidszone.view.fragment.ChoseLevelCrazyMath
import com.gh.mp3player.thekidszone.view.fragment.SplashFragmentCrazyMath
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.databinding.ActivityCrazyMathBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ActivityCrazyMath : BaseActivity<ActivityCrazyMathBinding>() {
    override fun initView() {
        CoroutineScope(Dispatchers.Main).launch {
            showFragment(SplashFragmentCrazyMath.TAG, null, false, R.id.ln_crazymath)
            delay(3000)
            showFragment(ChoseLevelCrazyMath.TAG, null, false, R.id.ln_crazymath)
        }
    }

    override fun initViewBinding(): ActivityCrazyMathBinding {
        return ActivityCrazyMathBinding.inflate(layoutInflater)
    }
    companion object {
        val TAG: String = ActivityCrazyMath::class.java.name
    }
}