package org.d3ifcool.laundrytelkom.ui.login

import android.app.Activity.RESULT_OK
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
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.laundrytelkom.databinding.LoginTabFragmentBinding
import org.d3ifcool.laundrytelkom.helper.SharedPreference
import org.d3ifcool.laundrytelkom.ui.home.HomeActivity
import org.d3ifcool.laundrytelkom.ui.login.reset.ResetPassword

class LoginFragment : Fragment() {

    private lateinit var binding: LoginTabFragmentBinding
    private lateinit var auth: FirebaseAuth
    lateinit var sp: SharedPreference
    private val contract = FirebaseAuthUIActivityResultContract()
    private val signInLauncher = registerForActivityResult(contract) {
        this.onSignInResult(it)
    }
    lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginTabFragmentBinding.inflate(layoutInflater, container, false)
        getActivity()?.setRequestedOrientation(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = binding.emailLogin
        val pass = binding.passwordLogin
        val forgotPass = binding.forgotPassLogin
        val login_btn = binding.loginBtnLogin

        email.setTranslationX(800F)
        pass.setTranslationX(800F)
        forgotPass.setTranslationX(800F)
        login_btn.setTranslationX(800F)

        email.setAlpha(0F)
        pass.setAlpha(0F)
        forgotPass.setAlpha(0F)
        login_btn.setAlpha(0F)

        email.animate().translationX(0F).alpha(1F).setDuration(800).setStartDelay(300).start()
        pass.animate().translationX(0F).alpha(1F).setDuration(800).setStartDelay(500).start()
        forgotPass.animate().translationX(0F).alpha(1F).setDuration(800).setStartDelay(500).start()
        login_btn.animate().translationX(0F).alpha(1F).setDuration(800).setStartDelay(700).start()
//        INIT
        auth = FirebaseAuth.getInstance()
        sp = SharedPreference(requireActivity())
//        Login Btn
        binding.loginBtnLogin.setOnClickListener {
            login()
        }
//        Login Google ( Gmail )
        binding.fabGoogle.setOnClickListener {
            loginAppsWithGoogleAccount()
        }

//        RESET pw
        binding.forgotPassLogin.setOnClickListener{
            changePageIntent(Intent(requireActivity(),ResetPassword::class.java))

        }
    }

    private fun readData(userName: String) {
        val user = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("users")
        reference.child(userName).get().addOnSuccessListener {
            if (it.exists()) {
                changePageIntent(Intent(requireActivity(), HomeActivity::class.java))
            } else {
                changePageIntent(Intent(requireActivity(), LoginGoogleActivity::class.java))
            }
            Toast.makeText(
                requireActivity(),
                "Selamat Datang ${user?.email} ",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun loginAppsWithGoogleAccount() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(intent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            val nama = FirebaseAuth.getInstance().currentUser?.displayName
            val user = FirebaseAuth.getInstance().currentUser
            sp.setStatusSignin(true)
            readData(user?.displayName.toString())
            Log.i("LOGIN", "$nama berhasil login")
        } else {
            Log.i("LOGIN", "Login gagal: ${response?.error}")
        }
    }

    private fun changePageIntent(listIntent: Intent) {
        startActivity(listIntent)
    }


    private fun login() {
        val email = binding.emailLogin.text.toString()
        val password = binding.passwordLogin.text.toString()


        if (email.isEmpty() == true) {
            binding.emailLogin.error = "Email Harus Di Isi"
            binding.emailLogin.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLogin.error = "Email Invalid"
            binding.emailLogin.requestFocus()
        } else if (password.isEmpty() == true) {
            binding.passwordLogin.error = "Password Harus Di Isi"
            binding.passwordLogin.requestFocus()
        } else if (password.length < 6) {
            binding.passwordLogin.error = "Password Minimum 6 Character"
            binding.passwordLogin.requestFocus()
        } else {
            loginApi(email, password)
        }
    }

    private fun loginApi(email: String, password: String) {
        if (::auth.isInitialized) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireActivity(),
                            "Selamat Datang $email ",
                            Toast.LENGTH_SHORT
                        )
                            .show()
//                    Move TO other Page
                        changePageIntent(Intent(requireActivity(), HomeActivity::class.java))
                        sp.setStatusSignin(true)
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

}