package org.d3ifcool.laundrytelkom.ui.login.reset


import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.databinding.ActivityResetPasswordBinding

class ResetPassword : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar

        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        actionBar?.title = "Reset Password"

        actionBar?.setDisplayHomeAsUpEnabled(true)

//        INIT
        binding.loginBtnLogin.setOnClickListener {
            val email = binding.emailLogin.text.toString()

            if (email.isEmpty()) {
                binding.emailLogin.error = "Email Harus Di Isi"
                binding.emailLogin.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailLogin.error = "Email Invalid"
                binding.emailLogin.requestFocus()
            }
            resetPw(email)
        }



    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun resetPw(email: String) {
        val auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Reset Password Telah dikirim ke $email Silahkan Cek Email Anda",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    // Error occurred
                    Toast.makeText(
                        this,
                        "Reset Password Gagal",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
    }
}
