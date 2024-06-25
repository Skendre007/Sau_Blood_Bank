package com.saurabh.saubloodbank.feeds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.saurabh.saubloodbank.Fragment1
import com.saurabh.saubloodbank.Fragment2
import com.saurabh.saubloodbank.R
import com.saurabh.saubloodbank.databinding.ActivityBloodRequestBinding
import com.google.firebase.database.FirebaseDatabase

class BloodRequestActivity : AppCompatActivity() {


    lateinit var binding: ActivityBloodRequestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloodRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = FirebaseDatabase.getInstance().getReference("requests")



        val back=findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }
        binding.fragment1.setOnClickListener {

            binding.fragment1.isChecked=true;
            binding.fragment2.isChecked=false;

            replaceFragment(Fragment1())
        }

        binding.fragment2.setOnClickListener {
            binding.fragment1.isChecked=false;
            binding.fragment2.isChecked=true;
            replaceFragment(Fragment2())
        }




    }
    private fun replaceFragment(fragment: Fragment) {

        val fragmentmanager=supportFragmentManager
        val fragmentTransaction=fragmentmanager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment)
        fragmentTransaction.commit()
    }
}