package org.d3ifcool.laundrytelkom.ui.register

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.laundrytelkom.databinding.SignupTabFragmentBinding
import org.d3ifcool.laundrytelkom.helper.UserHelperFirebase
import org.d3ifcool.laundrytelkom.ui.login.LoginActivity
import java.util.*
import kotlin.concurrent.schedule


class RegisterFragment : Fragment() {
    private lateinit var binding: SignupTabFragmentBinding
    lateinit var auth: FirebaseAuth

    ////    Database
    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignupTabFragmentBinding.inflate(layoutInflater, container, false)
        getActivity()?.setRequestedOrientation(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = binding.email
        val pass = binding.password
        val name = binding.nama
        val address = binding.alamat
        val mobileNumber = binding.mobileNum


        email.setTranslationX(800F)
        pass.setTranslationX(800F)
        name.setTranslationX(800F)
        address.setTranslationX(800F)
        mobileNumber.setTranslationX(800F)

        email.setAlpha(0F)
        pass.setAlpha(0F)
        address.setAlpha(0F)
        mobileNumber.setAlpha(0F)

        email.animate().translationX(0F).alpha(1F).setDuration(800).setStartDelay(300).start()
        pass.animate().translationX(0F).alpha(1F).setDuration(800).setStartDelay(500).start()
        name.animate().translationX(0F).alpha(1F).setDuration(800).setStartDelay(500).start()
        address.animate().translationX(0F).alpha(1F).setDuration(800).setStartDelay(600).start()
        mobileNumber.animate().translationX(0F).alpha(1F).setDuration(800).setStartDelay(700)
            .start()
//        INIT FIREBASE
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        binding.signup.setOnClickListener {
            signUpUser()
        }
    }

    private fun changePageIntent(listIntent: Intent) {
        startActivity(listIntent)
    }


    private fun signUpUser() {

        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val name = binding.nama.text.toString()
        val mobileNum = binding.mobileNum.text.toString()
        val address = binding.alamat.text.toString()

        if (email.isEmpty()) {
            binding.email.error = "Email Harus Di Isi"
            binding.email.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.error = "Email Invalid"
            binding.email.requestFocus()
        } else if (password.isEmpty()) {
            binding.password.error = "Password Harus Di Isi"
            binding.password.requestFocus()
        } else if (password.length < 6) {
            binding.password.error = "Password Minimum 6 Character"
            binding.password.requestFocus()
        } else if (name.isEmpty()) {
            binding.nama.error = "Nama Harus Di Isi"
            binding.nama.requestFocus()
        } else if (name.length < 3) {
            binding.nama.error = "Nama Minimum 3 Character"
            binding.nama.requestFocus()
        } else if (name!!.matches(Regex("[^a-zA-Z0-9]"))) {
            binding.nama.error = "Error Input cannot contain symbols!"
            binding.nama.requestFocus()
        } else if (mobileNum.isEmpty()) {
            binding.mobileNum.error = "No Hp Harus Di Isi"
            binding.mobileNum.requestFocus()
        } else if (mobileNum.length < 11) {
            binding.mobileNum.error = "No Hp Minimum 11 Character"
            binding.mobileNum.requestFocus()
        } else if (mobileNum!!.matches(Regex("[^0-9]"))) {
            // If the input contains symbols or letters, print an error message
            binding.mobileNum.error = "Input cannot contain symbols or letters!"
            binding.mobileNum.requestFocus()
        } else if (address.isEmpty()) {
            binding.alamat.error = "Alamat Harus Di Isi"
            binding.alamat.requestFocus()
        } else if (address.length < 6) {
            binding.alamat.error = "Alamat Minimum 6 Character"
            binding.alamat.requestFocus()
        } else {
            Timer().schedule(2000) {
                registToServer(email, password)
            }
            registerWithDatabase(email, name, mobileNum, address)
            changePageIntent(Intent(requireActivity(), LoginActivity::class.java))
        }
    }

    private fun registToServer(email: String, password: String) {
        if (::auth.isInitialized) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        val name = binding.nama.text.toString()
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { taskx ->
                                if (task.isSuccessful) {
                                    Log.d("TAG", "User profile updated.")
                                } else {
                                    Log.d("error","${taskx.exception?.message}")
                                }
                            }
                        Toast.makeText(
                            requireActivity(),
                            "Registrasi Berhasil $email Silahkan Login",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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
}
