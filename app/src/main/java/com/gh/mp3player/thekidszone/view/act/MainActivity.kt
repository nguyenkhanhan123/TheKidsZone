package com.gh.mp3player.thekidszone.view.act

import com.gh.mp3player.thekidszone.view.fragment.MainFragment
import com.gh.mp3player.thekidszone.view.fragment.SplashFragment
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun initView() {
      CoroutineScope(Dispatchers.Main).launch {
          showFragment(SplashFragment.TAG, null, false, R.id.ln_main)
          delay(3000)
          showFragment(MainFragment.TAG, null, false, R.id.ln_main)
      }

    }
    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

}

