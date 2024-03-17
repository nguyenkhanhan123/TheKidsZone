package com.gh.mp3player.thekidszone.view.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gh.mp3player.thekidszone.databinding.FragmentSettingBinding
import com.gh.mp3player.thekidszone.view.act.MainActivity
import com.gh.mp3player.thekidszone.viewmodel.CommonViewModel
import com.google.firebase.auth.FirebaseAuth

class SettingFragment: BaseFragment<FragmentSettingBinding, CommonViewModel>() {
    override fun getClassVM(): Class<CommonViewModel> {
        return CommonViewModel::class.java
    }
    override fun initView() {
       mbinding.btSignout.setOnClickListener { signOut() }
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(layoutInflater)
    }

    companion object {
        val TAG: String=SettingFragment::class.java.name
    }
}