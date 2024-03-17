package com.gh.mp3player.thekidszone.view.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.gh.mp3player.thekidszone.databinding.FragmentHomeDetailBinding
import com.gh.mp3player.thekidszone.view.dialog.AddGroupDialog
import com.gh.mp3player.thekidszone.viewmodel.CommonViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeDetailFragment: BaseFragment<FragmentHomeDetailBinding, CommonViewModel>() {
    override fun getClassVM(): Class<CommonViewModel> {
        return CommonViewModel::class.java
    }
    override fun initView() {
        val email = FirebaseAuth.getInstance().currentUser?.email
        if (email != null) {
            FirebaseFirestore.getInstance().collection("USER").document(email).collection("Infor").whereEqualTo("Gmail", email)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        val userData = document.data
                        val name = userData?.get("Name") as String
                        val email = userData["Gmail"] as String
                        val sdt = userData["Sdt"] as String
                        val job = userData["Job"] as String
                        val link = userData["Link"] as String

                        mbinding.tvName.text = name
                        mbinding.tvGmail.text = "Gmail: $email"
                        mbinding.tvSdt.text = "SĐT: $sdt"
                        if (job.equals("Phụ Huynh")){
                            mbinding.addGroup.visibility=View.INVISIBLE
                        }
                        Glide.with(requireContext()).load(link).into(mbinding.iv)
                    }
                }
                .addOnFailureListener { e ->
                    println("Lỗi khi truy vấn dữ liệu: $e")
                }
        } else {
            println("Người dùng hiện tại không có địa chỉ email.")
        }

        mbinding.addGroup.setOnClickListener {
            var dialog: AddGroupDialog = AddGroupDialog(requireContext())
            dialog.setAdd()
            dialog.show()
        }
        mbinding.findGroup.setOnClickListener {
            val dialog: AddGroupDialog = AddGroupDialog(requireContext())
            dialog.setFind()
            var kq:Int=0
            dialog.show()
        }

    }



    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeDetailBinding {
        return FragmentHomeDetailBinding.inflate(layoutInflater)
    }

    companion object {
        val TAG: String=HomeDetailFragment::class.java.name
    }
}