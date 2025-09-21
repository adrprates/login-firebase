package com.dm.login_firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.dm.login_firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        if (auth.currentUser != null) {
            goToHome()
        }

        binding.buttonRegister.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        goToHome()
                    } else {
                        val exception = task.exception
                        if (exception is FirebaseAuthUserCollisionException) {
                            Toast.makeText(this, "Este e-mail já está em uso. Tente outro.", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Falha no cadastro: ${exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        goToHome()
                    } else {
                        Toast.makeText(this, "Falha no login: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}