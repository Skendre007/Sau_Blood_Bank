package com.saurabh.saubloodbank.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saurabh.saubloodbank.HomePage.HomePageActivity
import com.saurabh.saubloodbank.databinding.ActivityLoginBinding
import com.saurabh.saubloodbank.registration.RegistrationActivity
import com.google.firebase.messaging.FirebaseMessaging
import login

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseMessaging.getInstance().subscribeToTopic("All")

        binding.btnLogSubmit.setOnClickListener {

            if (binding.edLogEmail.text?.isEmpty() == true) {
                binding.edLogEmail.error = "Please Provide Email"

            } else if (binding.edLogPassword.text?.isEmpty() == true) {
                binding.edLogEmail.error = "Please Provide Password"
            } else {
                login(
                    binding.edLogEmail.text.toString(),
                    binding.edLogPassword.text.toString(),
                    this@LoginActivity
                ) { success ->
                    if (success) {
                        startActivity(Intent(this, HomePageActivity::class.java))
                    }

                }

             }

        }
        binding.btnCreat.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
         moveTaskToBack(true)
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(1)
    }
}