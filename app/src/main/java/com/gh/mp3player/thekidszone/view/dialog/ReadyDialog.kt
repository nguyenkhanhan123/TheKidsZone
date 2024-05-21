package com.gh.mp3player.thekidszone.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.databinding.ViewReadyBinding

class ReadyDialog(context: Context) : Dialog(context, R.style.CustomDialogStyle) {
    private val mbinding: ViewReadyBinding = ViewReadyBinding.inflate(layoutInflater)
    lateinit var event: View.OnClickListener

    init {
        setContentView(mbinding.root)
        initView()
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    private fun initView() {
        mbinding.tvOk.setOnClickListener { v -> event.onClick(v);dismiss() }
    }

    fun setLose() {
        mbinding.tvText.text = "OH NO, YOU LOSE!"
        mbinding.tvOk.text = "PLAY AGAIN"
    }

}
