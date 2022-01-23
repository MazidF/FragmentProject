package com.example.fragmentmaktab

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.lang.Exception

class LoginFragment : Fragment(R.layout.login_fragment) {
    lateinit var app: LoginActivity
    lateinit var views: Array<EditText>
    lateinit var saveButton: Button
    lateinit var gender: RadioGroup
    lateinit var genderTitle: TextView
    var index = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    @SuppressLint("SetTextI18n")
    private fun init(root: View) {
        with(root) {
            views = arrayOf(
            findViewById(R.id.login_fullName),
            findViewById(R.id.login_userName),
            findViewById(R.id.login_email),
            findViewById(R.id.login_password),
            findViewById(R.id.login_retryPassword)
            )
            saveButton = findViewById(R.id.login_register)
            saveButton.apply {
                setOnClickListener {
                    if (check()) {
                        val bundle = Bundle()
                        for (i in views.indices) {
                            bundle.putString("$i", views[i].text.toString())
                        }
                        val gender = if (index == R.id.login_female) "Female" else "Male"
                        bundle.putString("5", gender)
                        app.addSaver(bundle)
                    }
                }
            }
            gender = findViewById(R.id.login_gender)
            genderTitle = findViewById(R.id.login_gender_title)
            gender.setOnCheckedChangeListener { gruop, i ->
                index = i
                genderTitle.error = null
            }
        }
        load()
    }

    private fun load(){
        val sharedPreferences = app.getSharedPreferences(app.packageName, Context.MODE_PRIVATE)
        with(sharedPreferences) {
            if (getBoolean("hasSaved", false)) {
                for (i in views.indices) {
                    views[i].setText(getString("$i", null) ?: throw Exception("???"))
                }
                val genderName = getString("5", null) ?: throw Exception("????")
                val viewId = if (genderName == "Female") R.id.login_female else R.id.login_male
                gender.check(viewId)
            }
        }
    }

    private fun check(): Boolean {
        var result = true
        for (view in views.reversed()) {
            if (view.text.trim().isEmpty()) {
                view.error = "Invalid Input!!!"
                result = false
            }
        }
        if (index == -1) {
            result = false
            genderTitle.error = "Invalid Input!!!"
        }
        return result
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        app = context as LoginActivity
    }
}
