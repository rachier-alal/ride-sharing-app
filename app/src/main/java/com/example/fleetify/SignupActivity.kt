package com.example.fleetify

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup2.*

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)

        login_link_btn.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }
        signup_btn.setOnClickListener{
            createAccount()
        }
    }

    private fun createAccount() {
        val progressDialog = ProgressDialog(this@SignupActivity)
        val fullName=fullname_signup.text.toString()
        val userName=username_signup.text.toString()
        val userEmail=email_signup.text.toString()
        val password=password_signup.text.toString()

        when{
            TextUtils.isEmpty(fullName)->Toast.makeText(this,"full name is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(userName)->Toast.makeText(this,"user name is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(userEmail)->Toast.makeText(this,"email address is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password)->Toast.makeText(this,"password is required", Toast.LENGTH_LONG).show()

            else->{

                progressDialog.setTitle("sign up")
                progressDialog.setMessage("Please wait, this may take a while...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()



                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(userEmail, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveUserInfo(fullName, userName, userEmail)
                        } else {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error $message", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }

            }
        }
    }

    private fun saveUserInfo(fullName: String, userName: String, userEmail: String) {

        val currentUserID= FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserID
        userMap["fullName"] = fullName
        userMap["userName"] = userName
        userMap["email"] = userEmail
        userMap["bio"] = "hi, Im a Driver/ User"
        usersRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener{ task ->
                val progressDialog = ProgressDialog(this@SignupActivity)
                if(task.isSuccessful){


                    Toast.makeText(this, "Account has been created successfully", Toast.LENGTH_LONG)

                    val intent= Intent(this@SignupActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()


                }
                else{
                    val message = task.exception!!.toString()
                    Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }

    }

}