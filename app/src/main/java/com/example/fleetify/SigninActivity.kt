package com.example.fleetify

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signin2.*
import kotlinx.android.synthetic.main.activity_signup2.*

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin2)

        signupBtn_link.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        login_btn.setOnClickListener{

            loginUser()
        }
    }

    private fun loginUser() {
        val progressDialog = ProgressDialog(this@SigninActivity)
        val userEmail=email_signup.text.toString()
        val password=password_signup.text.toString()
        when{
            TextUtils.isEmpty(userEmail)->Toast.makeText(this,"email address is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password)->Toast.makeText(this,"password is required", Toast.LENGTH_LONG).show()
            else -> {
                progressDialog.setTitle("signing in")
                progressDialog.setMessage("Please wait, this may take a while...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()
                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(userEmail, password).addOnCompleteListener{task ->
                    if (task.isSuccessful){
                        progressDialog.dismiss()

                        val intent= Intent(this@SigninActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser!== null){
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }



}