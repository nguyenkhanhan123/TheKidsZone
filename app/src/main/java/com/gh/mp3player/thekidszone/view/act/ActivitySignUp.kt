package com.gh.mp3player.thekidszone.view.act

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TableRow
import android.widget.Toast
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

class ActivitySignUp : BaseActivity<ActivitySignupBinding>() {
    override fun initView() {
        mbinding.btSignup.setOnClickListener {
            if (mbinding.edtName.text.toString().isNotEmpty() && mbinding.edtSdt.text.toString()
                    .isNotEmpty() && mbinding.edtEmail.text.isNotEmpty()
            ) {
                onClickSignUp()
            }
            else{
                Toast.makeText(this,"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show()
            }
        }
        mbinding.edtPass.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                mbinding.trNotify.visibility = View.VISIBLE
                val layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
                )

                val marginValueInPixels = resources.getDimensionPixelSize(R.dimen.dp_10)
                layoutParams.setMargins(0, marginValueInPixels, 0, 0)

                mbinding.trRePass.layoutParams = layoutParams
            } else {
                val edt = mbinding.edtPass.text
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

        mbinding.edtPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val edt = mbinding.edtPass.text
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


        mbinding.edtRePass.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                mbinding.trNotify2.visibility = View.VISIBLE
                val layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
                )

                val marginValueInPixels = resources.getDimensionPixelSize(R.dimen.dp_10)
                layoutParams.setMargins(0, marginValueInPixels, 0, 0)

                mbinding.btSignup.layoutParams = layoutParams
            } else {
                val edt = mbinding.edtRePass.text
                if (edt.toString()==mbinding.edtPass.text.toString()
                ) {
                    mbinding.trNotify2.visibility = View.GONE
                    val layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
                    )

                    val marginValueInPixels = resources.getDimensionPixelSize(R.dimen.dp_30)
                    layoutParams.setMargins(0, marginValueInPixels, 0, 0)

                    mbinding.btSignup.layoutParams = layoutParams
                }
            }
        }

        mbinding.edtRePass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val edt = mbinding.edtRePass.text
                if (edt.toString()==mbinding.edtPass.text.toString()
                ) {
                    mbinding.btSignup.isEnabled=true
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

    private fun onClickSignUp() {
        val email = mbinding.edtEmail.text.toString().trim()
        val password = mbinding.edtPass.text.toString().trim()
        val name = mbinding.edtName.text.toString().trim()
        val sdt = mbinding.edtSdt.text.toString().trim()
        val job = mbinding.edtJob.selectedItem.toString().trim()
        val mAuth = FirebaseAuth.getInstance()
        val user = hashMapOf(
            "Name" to name,
            "Gmail" to email,
            "Sdt" to sdt,
            "Job" to job,
            "Link" to "https://i.pinimg.com/564x/57/fb/31/57fb3190d0cc1726d782c4e25e8561e9.jpg",
        )
        val mFireStore = FirebaseFirestore.getInstance()
        mFireStore.collection("USER").document(email).collection(email).document("Infor")
            .collection("Infor").add(user)
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val uSer = mAuth.currentUser
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }.addOnFailureListener(this) { exception ->
            if (exception is FirebaseAuthUserCollisionException) {
                Toast.makeText(this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initViewBinding(): ActivitySignupBinding {
        return ActivitySignupBinding.inflate(layoutInflater)
    }

    companion object {
        val TAG: String = ActivitySignUp::class.java.name
    }
}