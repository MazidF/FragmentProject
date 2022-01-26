package com.example.fragmentmaktab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.fragmentmaktab.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        addLogin()
    }

    private fun addLogin() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<LoginFragment>(R.id.loginContainer)
        }
    }

    fun addSaver(bundle: Bundle) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<SaveFragment>(R.id.loginContainer, args = bundle)
            addToBackStack(null)
        }
    }

    fun reset() {
        super.onBackPressed()
    }

    override fun onBackPressed() {}
}
