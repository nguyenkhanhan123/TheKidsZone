package com.gh.mp3player.thekidszone.view.act

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TableRow
import android.widget.Toast
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.databinding.ActivityChangepasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ActivityChangePassword : BaseActivity<ActivityChangepasswordBinding>() {
    override fun initView() {

        mbinding.edtNewPass.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                mbinding.trNotify.visibility = View.VISIBLE
                val layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
                )

                val marginValueInPixels = resources.getDimensionPixelSize(R.dimen.dp_10)
                layoutParams.setMargins(0, marginValueInPixels, 0, 0)

                mbinding.trRePass.layoutParams = layoutParams
            } else {
                val edt = mbinding.edtNewPass.text
                if (containsLowerCase(edt) == 1 && containsUpperCase(edt) == 1 && containsSpecialCharacter(
                        edt
                    ) == 1
                ) {
                    mbinding.trNotify.visibility = View.GONE
                    val layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
                    )

                    val marginValueInPixels = resources.getDimensionPixelSize(R.dimen.dp_30)
                    layoutParams.setMargins(0, marginValueInPixels, 0, 0)

                    mbinding.trRePass.layoutParams = layoutParams
                }
            }
        }

        mbinding.edtNewPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val edt = mbinding.edtNewPass.text
                if (containsLowerCase(edt) == 1 && containsUpperCase(edt) == 1 && containsSpecialCharacter(
                        edt
                    ) == 1
                ) {
                    mbinding.ivNotify.setColorFilter(resources.getColor(R.color.green))
                    mbinding.tvNotify.setTextColor(resources.getColor(R.color.green))
                } else {
                    mbinding.ivNotify.setColorFilter(resources.getColor(R.color.chossed))
                    mbinding.tvNotify.setTextColor(resources.getColor(R.color.chossed))
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


        mbinding.edtReNewPass.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                mbinding.trNotify2.visibility = View.VISIBLE
                val layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
                )

                val marginValueInPixels = resources.getDimensionPixelSize(R.dimen.dp_10)
                layoutParams.setMargins(0, marginValueInPixels, 0, 0)

                mbinding.btChangePassword.layoutParams = layoutParams
            } else {
                val edt = mbinding.edtReNewPass.text
                if (edt.toString()==mbinding.edtNewPass.text.toString()
                ) {
                    mbinding.trNotify2.visibility = View.GONE
                    val layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
                    )

                    val marginValueInPixels = resources.getDimensionPixelSize(R.dimen.dp_30)
                    layoutParams.setMargins(0, marginValueInPixels, 0, 0)

                    mbinding.btChangePassword.layoutParams = layoutParams
                }
            }
        }

        mbinding.edtReNewPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val edt = mbinding.edtReNewPass.text
                if (edt.toString()==mbinding.edtNewPass.text.toString()
                ) {
                    mbinding.btChangePassword.isEnabled=true
                    mbinding.ivNotify2.setColorFilter(resources.getColor(R.color.green))
                    mbinding.tvNotify2.setTextColor(resources.getColor(R.color.green))
                } else {
                    mbinding.ivNotify2.setColorFilter(resources.getColor(R.color.chossed))
                    mbinding.tvNotify2.setTextColor(resources.getColor(R.color.chossed))
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        mbinding.btChangePassword.setOnClickListener {
            onClickChangePassword()
        }
    }
    private fun onClickChangePassword() {
        Toast.makeText(this, "Đang kiểm tra...", Toast.LENGTH_SHORT).show()
        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider.getCredential(user?.email.toString(),mbinding.edtOldPass.text.toString())
        user?.reauthenticate(credential)
            ?.addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    user.updatePassword(mbinding.edtNewPass.text.toString())
                        .addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                Toast.makeText(this, "Đã đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Đổi mật khẩu không thành công, vui lòng kiểm tra lại thông tin!", Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show()
                }
            }

    }
    fun containsLowerCase(text: CharSequence): Int {
        for (char in text) {
            if (char.isLowerCase()) {
                return 1
            }
        }
        return 0
    }

    fun containsUpperCase(text: CharSequence): Int {
        for (char in text) {
            if (char.isUpperCase()) {
                return 1
            }
        }
        return 0
    }

    fun containsSpecialCharacter(text: CharSequence): Int {
        val specialCharacters = "~`!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?"
        for (char in text) {
            if (specialCharacters.contains(char)) {
                return 1
            }
        }
        return 0
    }

    override fun initViewBinding(): ActivityChangepasswordBinding {
        return ActivityChangepasswordBinding.inflate(layoutInflater)
    }
    companion object {
        val TAG: String = ActivityChangePassword::class.java.name
    }
}