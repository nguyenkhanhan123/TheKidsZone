package com.gh.mp3player.thekidszone.view.act

import android.content.Intent
import android.widget.Toast

import com.gh.mp3player.thekidszone.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth

class ActivitySignIn : BaseActivity<ActivitySigninBinding>() {
    override fun initView() {
        mbinding.btSignin.setOnClickListener {
            if (mbinding.edtEmail.text.toString().isNotEmpty() && mbinding.edtPass.text.toString()
                    .isNotEmpty()
            ) {
                onClickSignIn()
            }
            else{
                Toast.makeText(this,"Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
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
                else{
                    Toast.makeText(this,"Vui lòng kiểm tra lại tài khoản và mật khẩu!", Toast.LENGTH_SHORT).show()
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