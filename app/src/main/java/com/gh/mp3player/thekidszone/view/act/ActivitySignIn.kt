package com.gh.mp3player.thekidszone.view.act

import android.content.Intent

import com.gh.mp3player.thekidszone.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth

class ActivitySignIn : BaseActivity<ActivitySigninBinding>() {
    override fun initView() {
        mbinding.btSignin.setOnClickListener { onClickSignIn() }
    }
    private fun onClickSignIn() {
        val email = mbinding.edtEmail.text.toString().trim()
        val password = mbinding.edtPass.text.toString().trim()
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
    }

    override fun initViewBinding(): ActivitySigninBinding {
        return ActivitySigninBinding.inflate(layoutInflater)
    }
    companion object {
        val TAG: String = ActivitySignIn::class.java.name
    }
}