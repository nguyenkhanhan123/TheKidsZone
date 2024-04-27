package com.gh.mp3player.thekidszone.view.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Toast
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.databinding.AddGroupDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class AddGroupDialog(context: Context) : Dialog(context, R.style.CustomDialogStyle) {
    private val mbinding: AddGroupDialogBinding = AddGroupDialogBinding.inflate(layoutInflater)
    private var s: String = ""
    lateinit var event: View.OnClickListener

    init {
        setContentView(mbinding.root)
        initView()
    }

    private fun initView() {
        mbinding.btOk.setOnClickListener { v->event.onClick(v);dismiss()}
    }

    fun setOK() {
        var i = 0
        mbinding.tvText.text = "ĐÃ TÌM THẤY NHÓM"
        mbinding.btOk.text = "Tham gia ngay!"
        mbinding.edtName.visibility = View.GONE
        mbinding.btOk.setOnClickListener {
            var email = FirebaseAuth.getInstance().currentUser?.email
            if (email == null) {
                email = ""
            }

            val code = FirebaseFirestore.getInstance().collection("GROUP").document(s).collection(s)
            code.get().addOnSuccessListener { query ->
                if (query != null) {
                    i = query.size()+1
                    val addUser = hashMapOf(
                        "Member$i" to email
                    )
                    val id = hashMapOf(
                        "ID" to s
                    )
                    code.document(email).collection(email).add(addUser)
                    FirebaseFirestore.getInstance().collection("USER").document(email).collection(email).add(id)
                }
            }
            dismiss()
        }
    }

    fun setKO() {
        mbinding.tvText.text = "KHÔNG TÌM THẤY NHÓM"
        mbinding.btOk.text = "OK"
        mbinding.edtName.visibility = View.GONE
        mbinding.btOk.setOnClickListener { dismiss() }
    }
    fun setNO() {
        mbinding.tvText.text = "BẠN ĐÃ THAM GIA NHÓM NÀY"
        mbinding.btOk.text = "OK"
        mbinding.edtName.visibility = View.GONE
        mbinding.btOk.setOnClickListener { dismiss() }
    }

    fun setAdd() {
        mbinding.btOk.setOnClickListener {
            var email = FirebaseAuth.getInstance().currentUser?.email
            val nameGroup = mbinding.edtName.text.toString().trim()
            if (email == null) {
                email = ""
            }
            val random1 = ('A'.code + Random.nextInt(26)).toChar()
            val random2 = ('A'.code + Random.nextInt(26)).toChar()
            val random3 = ('A'.code + Random.nextInt(26)).toChar()
            val random4 = ('A'.code + Random.nextInt(26)).toChar()
            val random5 = ('A'.code + Random.nextInt(26)).toChar()
            val random6 = ('A'.code + Random.nextInt(26)).toChar()

            val code = "$random1$random2$random3$random4$random5$random6"
            val user = hashMapOf(
                "HOST" to email, "CODE" to code, "NAME" to nameGroup
            )

            FirebaseFirestore.getInstance().collection("GROUP").document(code).collection(code)
                .document("Leader").collection("Leader").add(user).addOnSuccessListener {
                    CommonUtils.getInstance()
                        .savePref(code,"")
                    Toast.makeText(context, "Tạo nhóm thành công", Toast.LENGTH_SHORT).show()
                }
            val addUser = hashMapOf(
                "Member1" to email
            )
            FirebaseFirestore.getInstance().collection("GROUP").document(code).collection(code).document(email).collection(email).add(addUser)
            val id = hashMapOf(
                "ID" to code
            )
            FirebaseFirestore.getInstance().collection("USER").document(email).collection(email).add(id)
            dismiss()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun setFind() {
        mbinding.tvText.text = "TÌM NHÓM"
        mbinding.btOk.text = "TÌM NHÓM"
        mbinding.edtName.hint = "ID Nhóm"

        mbinding.btOk.setOnClickListener {
            var email = FirebaseAuth.getInstance().currentUser?.email
            if (email != null) {
                val idGroup = mbinding.edtName.text.toString().trim()
                val myQuery = FirebaseFirestore.getInstance().collection("GROUP").document(idGroup)
                    .collection(idGroup)
                myQuery.document("Leader").collection("Leader").whereEqualTo("CODE", idGroup).get()
                    .addOnSuccessListener { query ->
                        if (query.size() != 0) {
                            myQuery.document(email).collection(email).get()
                                .addOnSuccessListener { query2 ->
                                    if (query2.size() != 0) {
                                        setNO()
                                    } else {
                                        s = idGroup
                                        setOK()
                                        CommonUtils.getInstance()
                                            .savePref(idGroup,"")
                                    }
                                }

                        } else {
                            setKO()
                        }
                    }

            }
        }
    }

}
