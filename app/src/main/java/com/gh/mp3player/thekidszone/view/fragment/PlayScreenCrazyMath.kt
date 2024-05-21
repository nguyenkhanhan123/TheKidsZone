package com.gh.mp3player.thekidszone.view.fragment

import CommonUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gh.mp3player.thekidszone.databinding.PlayScreenCrazyMathBinding
import com.gh.mp3player.thekidszone.view.dialog.ReadyDialog
import com.gh.mp3player.thekidszone.viewmodel.PlayScreenModel


class PlayScreenCrazyMath : BaseFragment<PlayScreenCrazyMathBinding, PlayScreenModel>() {

    override fun getClassVM(): Class<PlayScreenModel> {
        return PlayScreenModel::class.java
    }

    override fun initView() {
        viewmodel.startCoutDown()
        viewmodel.initQuestion()
        viewmodel.question.observe(this) { s ->
            if (s == null || s.isEmpty()) return@observe
            mbinding.question.text = s
        }
        viewmodel.ansA.observe(this) { s -> mbinding.ansA.text = String.format("%s", s) }
        viewmodel.ansB.observe(this) { s -> mbinding.ansB.text = String.format("%s", s) }
        viewmodel.ansC.observe(this) { s -> mbinding.ansC.text = String.format("%s", s) }
        viewmodel.best.observe(this) { s -> mbinding.best.text = String.format("Best: %s", s) }
        viewmodel.score.observe(this) { s -> mbinding.score.text = String.format("Score: %s", s) }
        viewmodel.time.observe(this) { s ->
            mbinding.time.text = String.format("%s", s)
            if (s == 1) gameOver()
        }
        var bestScore = CommonUtils.getInstance().getPref("BEST")
        if (bestScore != null) {
            viewmodel.best.postValue(bestScore.toInt())
        }
        mbinding.time.setOnClickListener(this)
        mbinding.ansA.setOnClickListener(this)
        mbinding.ansB.setOnClickListener(this)
        mbinding.ansC.setOnClickListener(this)
    }

    override fun clickView(v: View) {
        super.clickView(v)
        if (v == mbinding.ansA || v == mbinding.ansB || v == mbinding.ansC) {
            val rs: Boolean = viewmodel.check((v as? TextView)?.text.toString())
            if (!rs) {
                gameOver()
            }
        }

    }

    private fun gameOver() {
        viewmodel.gameOver()
        var score: Int = 0
        var best: Int = 0
        if (viewmodel.score.value != null) {
            score = viewmodel.score.value?.toInt() ?: 0
        }
        if (viewmodel.best.value != null) {
            best = viewmodel.best.value?.toInt() ?: 0
        }
        if (score > best) {

            viewmodel.best.postValue(score)
            CommonUtils.getInstance().savePref("BEST", score.toString())
        }
        var dialog: ReadyDialog = ReadyDialog(requireContext())
        dialog.setLose()
        dialog.event = View.OnClickListener {
            viewmodel.playAgain()
        }
        dialog.show()
    }

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): PlayScreenCrazyMathBinding {
        return PlayScreenCrazyMathBinding.inflate(layoutInflater)
    }

    companion object {
        val TAG: String = PlayScreenCrazyMath::class.java.name
    }

}
