package com.gh.mp3player.thekidszone.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.databinding.AddGroupDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class AddGroupDialog(context: Context) : Dialog(context, R.style.CustomDialogStyle) {
    private val mbinding: AddGroupDialogBinding = AddGroupDialogBinding.inflate(layoutInflater)
    lateinit var event: View.OnClickListener

    init {
        setContentView(mbinding.root)
        initView()
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    private fun initView() {
        mbinding.tvOk.setOnClickListener { v->event.onClick(v);dismiss()}
    }

    fun setFind() {
        mbinding.tvText.text = "TÌM NHÓM"
        mbinding.tvOk.text = "Tham gia ngay!"
        mbinding.edtName.hint = "ID Nhóm"
    }

    fun setOK() {

        mbinding.tvText.text="ĐÃ TÌM THẤY NHÓM"
        mbinding.tvOk.text = "Tham gia ngay!"
        mbinding.edtName.visibility= View.GONE

    }
    fun setKO() {
        mbinding.tvText.text="KHÔNG TÌM THẤY NHÓM"
        mbinding.tvOk.text = "OK"
        mbinding.edtName.visibility= View.GONE
    }

    fun setAdd() {
        mbinding.tvOk.setOnClickListener {
            val email = FirebaseAuth.getInstance().currentUser?.email
            val nameGroup = mbinding.edtName.text.toString().trim()
            val random1 = ('A'.code + Random.nextInt(26)).toChar()
            val random2 = ('A'.code + Random.nextInt(26)).toChar()
            val random3 = ('A'.code + Random.nextInt(26)).toChar()
            val random4 = ('A'.code + Random.nextInt(26)).toChar()
            val random5 = ('A'.code + Random.nextInt(26)).toChar()
            val random6 = ('A'.code + Random.nextInt(26)).toChar()

            val code = "$random1$random2$random3$random4$random5$random6"
            val user = hashMapOf(
                "HOST" to email, "CODE" to code
            )
            FirebaseFirestore.getInstance().collection("GROUP").document(nameGroup)
                .collection("Member").add(user)
            dismiss()
        }
    }
}
