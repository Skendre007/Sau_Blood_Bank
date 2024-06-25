package com.saurabh.saubloodbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.saurabh.saubloodbank.databinding.ActivityProfileBinding
import com.saurabh.saubloodbank.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.saurabh.saubloodbank.requestblood.RequestBloodActivity
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val user: FirebaseUser? = auth.currentUser
        val userId=user!!.uid
        val database = FirebaseDatabase.getInstance()
        val userReference: DatabaseReference = database.getReference("users").child(userId)


        binding.requestBack.setOnClickListener {
            onBackPressed()
        }
        binding.support.setOnClickListener {
            startActivity(Intent(this, CustomerSupportActivity::class.java))
        }
        binding.history.setOnClickListener {
            startActivity(Intent(this, MedicalHistoryActivity::class.java))
        }
        binding.settings.setOnClickListener {
            Toast.makeText(this, "Not Yet Implemented", Toast.LENGTH_SHORT).show()
        }


        lifecycleScope.launch {
            val fieldRef = userReference.child("name")

            userReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val fieldValue = dataSnapshot.child("name").getValue(String::class.java)
                        val blood = dataSnapshot.child("blood").getValue(String::class.java)
                        val phone = dataSnapshot.child("phone").getValue(String::class.java)
                        if (fieldValue != null) {
                            // Set the retrieved field value (name) to your TextView
                            binding.personName.text = fieldValue
                            binding.myblood.text=blood
                            binding.myphone.text=phone
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                 }
            })
        }

        binding.logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
        }

        binding.editprofile.setOnClickListener {

            startActivity(Intent(this,UpdateProfileActivity::class.java))
        }

    }
}