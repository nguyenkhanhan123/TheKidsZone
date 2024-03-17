package com.gh.mp3player.thekidszone.view.fragment

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.databinding.MainFragmentBinding
import com.gh.mp3player.thekidszone.viewmodel.CommonViewModel
import com.google.firebase.auth.FirebaseAuth


class MainFragment : BaseFragment<MainFragmentBinding, CommonViewModel>() {
    override fun getClassVM(): Class<CommonViewModel> {
        return CommonViewModel::class.java
    }

    override fun initView() {
        clickView(mbinding.includedActionBar.lnWorld)
        mbinding.includedActionBar.lnHome.setOnClickListener(this)
        mbinding.includedActionBar.lnSetting.setOnClickListener(this)
        mbinding.includedActionBar.lnWorld.setOnClickListener(this)
    }

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): MainFragmentBinding {
        return MainFragmentBinding.inflate(layoutInflater)
    }

    override fun clickView(v: View) {
        super.clickView(v)
        val listIC: Array<ImageView> = arrayOf(
            mbinding.includedActionBar.icHome,
            mbinding.includedActionBar.icWorld,
            mbinding.includedActionBar.icSetting
        )
        val listLN: Array<LinearLayout> = arrayOf(
            mbinding.includedActionBar.lnHome,
            mbinding.includedActionBar.lnWorld,
            mbinding.includedActionBar.lnSetting
        )
        for (i in 0..listLN.size - 1) {
            if (listLN[i].id == v.id) {
                listIC[i].setColorFilter(ContextCompat.getColor(requireContext(), android.R.color.white), PorterDuff.Mode.SRC_IN)
                listLN[i].backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.chossed))
            } else {
                listIC[i].setColorFilter(ContextCompat.getColor(requireContext(), android.R.color.black), PorterDuff.Mode.SRC_IN)
                listLN[i].backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.not_ch))
            }
        }
        if (v == mbinding.includedActionBar.lnWorld) {
            showFragment(LearnAndPlayFragment.TAG, null, false, R.id.fr_main)
        }
        if (v == mbinding.includedActionBar.lnHome) {
            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                showFragment(HomeFragment.TAG, null, false, R.id.fr_main)
            } else {
                showFragment(HomeDetailFragment.TAG, null, false, R.id.fr_main)
            }
        }
        if (v == mbinding.includedActionBar.lnSetting) {
            showFragment(SettingFragment.TAG, null, false, R.id.fr_main)
        }
    }

    companion object {
        val TAG: String = MainFragment::class.java.name
    }
}
