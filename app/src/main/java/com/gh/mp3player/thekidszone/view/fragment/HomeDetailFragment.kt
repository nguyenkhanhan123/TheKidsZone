package com.gh.mp3player.thekidszone.view.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.R
import com.bumptech.glide.Glide
import com.gh.mp3player.thekidszone.App
import com.gh.mp3player.thekidszone.databinding.FragmentHomeDetailBinding
import com.gh.mp3player.thekidszone.model.GroupModel
import com.gh.mp3player.thekidszone.view.adapter.GroupAdapter
import com.gh.mp3player.thekidszone.view.dialog.AddGroupDialog
import com.gh.mp3player.thekidszone.viewmodel.CommonViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeDetailFragment : BaseFragment<FragmentHomeDetailBinding, CommonViewModel>() {

    override fun getClassVM(): Class<CommonViewModel> {
        return CommonViewModel::class.java
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        val email = FirebaseAuth.getInstance().currentUser?.email
        if (email != null) {
            FirebaseFirestore.getInstance().collection("USER").document(email).collection(email)
                .document("Infor").collection("Infor").whereEqualTo("Gmail", email).get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        val userData = document.data
                        val name = userData?.get("Name") as String
                        val eMail = userData["Gmail"] as String
                        val sdt = userData["Sdt"] as String
                        val job = userData["Job"] as String
                        val link = userData["Link"] as String

                        mbinding.tvName.text = name
                        mbinding.tvGmail.text = "Gmail: $eMail"
                        mbinding.tvSdt.text = "SĐT: $sdt"
                        if (job == "Phụ Huynh") {
                            mbinding.addGroup.visibility = View.INVISIBLE
                        }
                        Glide.with(requireContext()).load(link).into(mbinding.iv)
                    }
                }
            initListGroup()
        }
        mbinding.addGroup.setOnClickListener {
            val dialog = AddGroupDialog(requireContext())
            dialog.setAdd()
            dialog.show()
        }
        mbinding.findGroup.setOnClickListener {
            val dialog = AddGroupDialog(requireContext())
            dialog.setFind()
            dialog.show()
            initListGroup()
        }
        initListGroup()
    }

    private fun initListGroup() {
        val list: MutableList<GroupModel> = mutableListOf()
        val email = FirebaseAuth.getInstance().currentUser?.email
        if (email != null) {
            FirebaseFirestore.getInstance().collection("USER").document(email).collection(email)
                .whereNotEqualTo("ID", "").get().addOnCompleteListener { userTask ->
                    if (userTask.isSuccessful) {
                        for (i in userTask.result.documents) {
                            val data = i.data
                            val id = data?.get("ID") as String
                            FirebaseFirestore.getInstance().collection("GROUP").document(id)
                                .collection(id).document("Leader").collection("Leader").whereEqualTo("CODE",id).get()
                                .addOnCompleteListener { groupTask ->
                                    if (groupTask.isSuccessful) {
                                        for (j in groupTask.result.documents) {
                                            val data2 = j.data
                                            val name = data2?.get("NAME") as String
                                            val code = data2["CODE"] as String
                                            val host = data2["HOST"] as String
                                            val groupModel = GroupModel(name, code, host)
                                            list.add(groupModel)
                                        }
                                        Toast.makeText(context,list.size.toString(),Toast.LENGTH_SHORT).show()
                                        mbinding.rvGroup.setAdapter(GroupAdapter(list,requireContext()) { v ->
                                            v.startAnimation(
                                                AnimationUtils.loadAnimation(
                                                    context,
                                                    R.anim.abc_fade_in
                                                )
                                            )
                                        })
                                    }
                                }
                        }
                    }
                }
        }

    }



    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeDetailBinding {
        return FragmentHomeDetailBinding.inflate(layoutInflater)
    }

    companion object {
        val TAG: String = HomeDetailFragment::class.java.name
    }
}