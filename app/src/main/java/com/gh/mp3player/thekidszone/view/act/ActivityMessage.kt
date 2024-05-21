package com.gh.mp3player.thekidszone.view.act

import CommonUtils
import com.gh.mp3player.thekidszone.view.service.MediaService
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.databinding.MessageLayoutBinding
import com.gh.mp3player.thekidszone.model.MessageModel
import com.gh.mp3player.thekidszone.view.adapter.MessageAdapter
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query


class ActivityMessage : BaseActivity<MessageLayoutBinding>() {
    private lateinit var listenerRegistration: ListenerRegistration
    lateinit var txtNotification: String
    @SuppressLint("SetTextI18n")
    override fun initView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.setReverseLayout(true)
        mbinding.rvMes.setLayoutManager(layoutManager)
        val intent = intent
        var groupId: String = intent?.getStringExtra("key") ?: ""
        txtNotification = if (CommonUtils.getInstance().getPref("NOTIFY").toString()
                .contains(groupId)
        ) "YES" else "NO"
        if (txtNotification == "YES") {
            mbinding.ivNotify.setImageResource(R.drawable.ic_notify)
        }
        mbinding.tvIdGroup.text="Nhóm: $groupId"
        mbinding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        var name = ""
        var link = ""
        var eMail = ""
        val email = FirebaseAuth.getInstance().currentUser?.email
        if (email != null) {
            FirebaseFirestore.getInstance().collection("USER").document(email).collection(email)
                .document("Infor").collection("Infor").whereEqualTo("Gmail", email).get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        val userData = document.data
                        name = userData?.get("Name") as String
                        link = userData["Link"] as String
                        eMail = userData["Gmail"] as String
                    }
                }
        }
        listenerRegistration = FirebaseFirestore.getInstance().collection("GROUP").document(groupId)
            .collection("MESSAGE").addSnapshotListener { value, error ->
                initMessage(groupId) }
        mbinding.btSend.setOnClickListener {
            val currentTime = Timestamp.now()
            val addMessage = hashMapOf(
                "timestamp" to currentTime,
                "Message" to mbinding.edtMessage.text.toString().trim(),
                "Name" to name,
                "Link" to link,
                "Email" to eMail
            )
            FirebaseFirestore.getInstance().collection("GROUP").document(groupId)
                .collection("MESSAGE").add(addMessage)
            mbinding.edtMessage.text.clear()
        }
        initMessage(groupId)
        mbinding.ivNotify.setOnClickListener {
            if (txtNotification == "NO") {
                mbinding.ivNotify.tag = "YES"
                txtNotification = "YES"
                mbinding.ivNotify.setImageResource(R.drawable.ic_notify)
                CommonUtils.getInstance().savePref(
                    "NOTIFY",
                    "${CommonUtils.getInstance().getPref("NOTIFY").toString()}-$groupId"
                )
                Toast.makeText(
                    this,
                    "Thông báo về nhóm có id: $groupId sẽ được gửi tới bạn!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mbinding.ivNotify.tag = "NO"
                txtNotification = "NO"
                mbinding.ivNotify.setImageResource(R.drawable.ic_notify_off)
                CommonUtils.getInstance().savePref(
                    "NOTIFY",
                    CommonUtils.getInstance().getPref("NOTIFY").toString().replace("-$groupId", "")
                )

            }
        }
    }

    private fun initMessage(groupId: String) {
        val list: MutableList<MessageModel> = mutableListOf()
        FirebaseFirestore.getInstance().collection("GROUP").document(groupId).collection("MESSAGE")
            .orderBy("timestamp", Query.Direction.DESCENDING).get()
            .addOnCompleteListener { groupTask ->

                if (groupTask.isSuccessful) {
                    for (j in groupTask.result.documents) {
                        val data2 = j.data
                        val name = data2?.get("Name") as String
                        val mes = data2["Message"] as String
                        val link = data2["Link"] as String
                        val email = data2["Email"] as String
                        val mesModel = MessageModel(name, mes, link, email)
                        list.add(mesModel)
                    }
                    if (list.size != 0) {
                            mbinding.rvMes.adapter = MessageAdapter(list, this)
                            CommonUtils.getInstance()
                                .savePref(groupId, list[0].toString())
                        }
                    }
                }
            }


    override fun initViewBinding(): MessageLayoutBinding {
        return MessageLayoutBinding.inflate(layoutInflater)
    }

    companion object {
        val TAG: String = ActivityMessage::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stopService(Intent(this, MediaService::class.java))
    }
    override fun onResume() {
        super.onResume()
        stopService(Intent(this, MediaService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        listenerRegistration.remove()
        val s = CommonUtils.getInstance().getPref("NOTIFY").toString()
        if (s != "0") {
            startService(Intent(this, MediaService::class.java))
        }
    }
}