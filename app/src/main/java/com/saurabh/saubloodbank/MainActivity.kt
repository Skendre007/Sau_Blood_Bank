package com.saurabh.saubloodbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.saurabh.saubloodbank.HomePage.HomePageActivity
import com.saurabh.saubloodbank.databinding.ActivityMainBinding
import com.saurabh.saubloodbank.login.LoginActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {




    lateinit var binding: ActivityMainBinding
    val auth = FirebaseAuth.getInstance()
    private lateinit var authListener: FirebaseAuth.AuthStateListener

    private var titlelist = mutableListOf<String>()
    private var deslist = mutableListOf<String>()
    private var imageslist = mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user: FirebaseUser? = firebaseAuth.currentUser
            if (user != null) {
                startActivity(Intent(this, HomePageActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }

        }
        auth.addAuthStateListener(authListener)
        postToList()

        binding.viewPager2.adapter = ViewPagerAdapter(titlelist, deslist, imageslist)

        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        FirebaseApp.initializeApp(this)


        FirebaseMessaging.getInstance().subscribeToTopic("All")

    }

    private fun replaceFragment(fragment:Fragment) {

        val fragmentmanager=supportFragmentManager
        val fragmentTransaction=fragmentmanager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment)
        fragmentTransaction.commit()
    }


    private fun addToList(title: String, description: String, image: Int) {
        titlelist.add(title)
        deslist.add(description)
        imageslist.add(image)
    }

    private fun postToList() {
        for (i in 1..5) {
            addToList("Title $i", "Description $i", R.mipmap.ic_launcher_round)
        }
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authListener)
    }
}