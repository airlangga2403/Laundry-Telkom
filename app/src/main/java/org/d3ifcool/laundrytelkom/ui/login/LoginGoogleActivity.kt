package org.d3ifcool.laundrytelkom.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.laundrytelkom.databinding.ActivitySignupGooglesignBinding
import org.d3ifcool.laundrytelkom.helper.UserHelperFirebase
import org.d3ifcool.laundrytelkom.ui.home.HomeActivity

class LoginGoogleActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupGooglesignBinding

    ////    Database
    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupGooglesignBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
        supportActionBar?.hide()
//        INIT DB
        database = FirebaseDatabase.getInstance()
//
        val user = FirebaseAuth.getInstance().currentUser


        binding.signup.setOnClickListener {

            val name = user?.displayName.toString()
            val email = user?.email.toString()
            val mobileNum = binding.mobileNum.text.toString()
            val address = binding.alamat.text.toString()

            if (mobileNum.isEmpty() == true) {
                binding.mobileNum.error = "Phone Number Harus Di Isi"
                binding.mobileNum.requestFocus()
            } else if (mobileNum.length < 11) {
                binding.mobileNum.error = "Phone Number Minimum 11 Character"
                binding.mobileNum.requestFocus()
            } else if (address.isEmpty() == true) {
                binding.alamat.error = "Alamat Harus Di Isi"
                binding.alamat.requestFocus()
            } else if (address.length < 6) {
                binding.alamat.error = "Alamat Minimum 6 Character"
                binding.alamat.requestFocus()
            } else {
                registerWithDatabase(email, name, mobileNum, address)
                changePageIntent(Intent(this, HomeActivity::class.java))
                Toast.makeText(
                    this, "Selamat Datang ${email}", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun registerWithDatabase(
        email: String,
        name: String,
        mobileNum: String,
        address: String
    ) {
        reference = database.getReference("users")
        val helperClass: UserHelperFirebase =
            UserHelperFirebase(email, name, mobileNum, address)
        reference.child(name).setValue(helperClass)
    }

    private fun changePageIntent(listIntent: Intent) {
        startActivity(listIntent)
    }
}