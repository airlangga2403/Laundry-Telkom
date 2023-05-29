package org.d3ifcool.laundrytelkom.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar

        // Customize the back button
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        actionBar?.title = "Profile User"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val user = FirebaseAuth.getInstance().currentUser
        readData(user?.displayName.toString())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun readData(userName: String) {
        reference = FirebaseDatabase.getInstance().getReference("users")
        reference.child(userName).get().addOnSuccessListener {
            if (it.exists()) {
                val name = it.child("name").value.toString()
                val email = it.child("email").value.toString()
                val address = it.child("address").value.toString()
                val mobileNum = it.child("mobileNum").value.toString()

                binding.userName.setText(name)
//                SET EMAIL TEXT
                binding.textView6.setText(email)
//                SET ADDRESS TEXT
                binding.textView18.setText("Alamat : ${address}")
//                Set Mobile Phone Number
                binding.textView20.setText("Phone : ${mobileNum}")

            } else {
                Toast.makeText(
                    this,
                    "User Doesn't Exist Username :  ${userName}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT)
                .show()
        }
    }
}