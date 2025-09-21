package com.dm.login_firebase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dm.login_firebase.databinding.ActivityHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = Firebase.auth.currentUser
        user?.let {
            val email = it.email
            binding.textWelcome.text = "Bem-vindo, $email!"
        }

        binding.buttonLogout.setOnClickListener {
            Firebase.auth.signOut()

            val intent = Intent(this, MainActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            finish()
        }
    }
}