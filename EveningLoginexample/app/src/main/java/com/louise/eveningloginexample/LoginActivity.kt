package com.louise.eveningloginexample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.louise.eveningloginexample.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {


    private lateinit var  binding: ActivityLoginBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Initialize Firebase Database
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("user")

        binding.loginButton.setOnClickListener{
            val loginUsername= binding.loginUsername.text.toString()
            val loginPassword= binding.loginPassword.text.toString()

            if(loginUsername.isNotEmpty() && loginPassword.isNotEmpty()){
                loginUser(loginUsername, loginPassword)
            } else{
                Toast.makeText(this@LoginActivity, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }
        binding.signRedirect.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            finish()
        }

    }
    private fun loginUser(username: String, password: String){
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(userSnapshot in dataSnapshot.children){
                        val userData = userSnapshot.getValue(UserData::class.java)

                        if(userData != null && userData.password == password){
                            Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity((Intent(this@LoginActivity, MainActivity::class.java)))
                            finish()
                            return
                        }
                    }

                }
                Toast.makeText(this@LoginActivity, "Login Not Successful", Toast.LENGTH_SHORT).show()

            }
            override fun onCancelled(databaseerror: DatabaseError) {
                Toast.makeText(this@LoginActivity, "Database Error: ${databaseerror.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}