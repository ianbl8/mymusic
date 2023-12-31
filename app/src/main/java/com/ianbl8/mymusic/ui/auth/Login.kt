package com.ianbl8.mymusic.ui.auth

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.databinding.LoginBinding
import com.ianbl8.mymusic.utils.createLoader
import com.ianbl8.mymusic.utils.hideLoader
import com.ianbl8.mymusic.utils.showLoader
import timber.log.Timber

class Login: AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth

    lateinit var loader: AlertDialog
    private lateinit var loginBinding: LoginBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = LoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.emailSignInButton.setOnClickListener(this)
        loginBinding.emailCreateAccountButton.setOnClickListener(this)
        loginBinding.signOutButton.setOnClickListener(this)
        loginBinding.verifyEmailButton.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()

        loader = createLoader(this)
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun createAccount(email: String, password: String) {
        Timber.i("createAccount: $email")
        if (!validateForm()) {
            return
        }

        showLoader(loader, "creating account...")

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Timber.i("createUserWithEmail: success")
                val user = auth.currentUser
                updateUI(user)
            } else {
                Timber.w("createUserWithEmail: failed, ${task.exception}")
                Toast.makeText(baseContext, "authentication failed", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }

            hideLoader(loader)
        }
    }

    private fun signIn(email: String, password: String) {
        Timber.i("signIn: $email")
        if (!validateForm()) {
            return
        }

        showLoader(loader, "logging in...")

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Timber.i("signInWithEmail: success")
                val user = auth.currentUser
                updateUI(user)
            } else {
                Timber.w("signInWithEmail: failed, ${task.exception}")
                Toast.makeText(baseContext, "authentication failed", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }

            if (!task.isSuccessful) {
                loginBinding.loginStatus.setText(R.string.auth_failed)
            }
            hideLoader(loader)
        }
    }

    private fun signOut() {
        auth.signOut()
        updateUI(null)
    }

    private fun sendEmailVerification() {
        loginBinding.verifyEmailButton.isEnabled = false

        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener(this) { task ->
            loginBinding.verifyEmailButton.isEnabled = true

            if (task.isSuccessful) {
                Toast.makeText(baseContext, "verification email sent to ${user.email}", Toast.LENGTH_SHORT).show()
            } else {
                Timber.e("sendEmailVerification: failed, ${task.exception}")
                Toast.makeText(baseContext, "failed to send verification email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = loginBinding.fieldEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            loginBinding.fieldEmail.error = "Required"
            valid = false
        } else {
            loginBinding.fieldEmail.error = null
        }

        val password = loginBinding.fieldPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            loginBinding.fieldPassword.error = "Required"
            valid = false
        } else {
            loginBinding.fieldPassword.error = null
        }

        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        hideLoader(loader)

        if (user != null) {
            loginBinding.loginStatus.text = getString(R.string.emailpassword_status_fmt, user.email, user.isEmailVerified)
            loginBinding.loginDetail.text = getString(R.string.firebase_status_fmt, user.uid)

            loginBinding.emailPasswordButtons.visibility = View.GONE
            loginBinding.emailPasswordFields.visibility = View.GONE
            loginBinding.signedInButtons.visibility = View.VISIBLE

            loginBinding.verifyEmailButton.isEnabled = !user.isEmailVerified
        } else {
            loginBinding.loginStatus.setText(R.string.signed_out)
            loginBinding.loginDetail.text = null

            loginBinding.emailPasswordButtons.visibility = View.VISIBLE
            loginBinding.emailPasswordFields.visibility = View.VISIBLE
            loginBinding.signedInButtons.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.emailCreateAccountButton -> createAccount(loginBinding.fieldEmail.text.toString(), loginBinding.fieldPassword.text.toString())
            R.id.emailSignInButton -> signIn(loginBinding.fieldEmail.text.toString(), loginBinding.fieldPassword.text.toString())
            R.id.signOutButton -> signOut()
            R.id.verifyEmailButton -> sendEmailVerification()
        }
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}