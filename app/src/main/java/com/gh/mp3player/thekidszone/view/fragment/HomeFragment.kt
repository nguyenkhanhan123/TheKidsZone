package com.gh.mp3player.thekidszone.view.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gh.mp3player.thekidszone.databinding.FragmentHomeBinding
import com.gh.mp3player.thekidszone.view.act.ActivitySignIn
import com.gh.mp3player.thekidszone.view.act.ActivitySignUp
import com.gh.mp3player.thekidszone.viewmodel.CommonViewModel

class HomeFragment: BaseFragment<FragmentHomeBinding, CommonViewModel>() {
    override fun getClassVM(): Class<CommonViewModel> {
        return CommonViewModel::class.java
    }
    override fun initView() {
        mbinding.btSignin.setOnClickListener {
            val intent = Intent(context, Class.forName(ActivitySignIn.TAG))
            requireContext().startActivity(intent)
        }
        mbinding.lnSignUp.setOnClickListener {
            val intent = Intent(context, Class.forName(ActivitySignUp.TAG))
            requireContext().startActivity(intent)
        }
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    companion object {
        val TAG: String=HomeFragment::class.java.name
    }
}