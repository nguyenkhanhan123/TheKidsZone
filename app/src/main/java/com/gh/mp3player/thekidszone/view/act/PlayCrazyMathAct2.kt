package com.gh.mp3player.thekidszone.view.act

import android.view.View
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.databinding.PlayCrazyMathActBinding
import com.gh.mp3player.thekidszone.view.dialog.ReadyDialog
import com.gh.mp3player.thekidszone.view.fragment.PlayScreenCrazyMath2

class PlayCrazyMathAct2 : BaseActivity<PlayCrazyMathActBinding>() {

    override fun initView() {
        val dialog:ReadyDialog=ReadyDialog(this)
        dialog.event= View.OnClickListener {  showFragment(PlayScreenCrazyMath2.TAG, null, false, R.id.fr_main_crazymath)  }
        dialog.show()
    }

    override fun initViewBinding(): PlayCrazyMathActBinding {
        return PlayCrazyMathActBinding.inflate(layoutInflater)
    }
    companion object {
        val TAG: String = PlayCrazyMathAct2::class.java.name
    }
}