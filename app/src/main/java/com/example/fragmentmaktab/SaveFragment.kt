package com.example.fragmentmaktab

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.graphics.Color.RED
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.LinearLayout.LayoutParams.*
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class SaveFragment : Fragment(R.layout.save_fragment) {
    lateinit var app: LoginActivity

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments!!
        val list = view.findViewById<LinearLayout>(R.id.save_list)
        val drawable = app.getDrawable(R.drawable.simple_circle)!!
        val param = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
            setMargins(0, 60, 0, 0)
        }
        val color = Color.parseColor("#E149D2")
        for (i in 0 .. 5) {
            addView(list, bundle.getString("$i")!!, drawable, param, color)
        }
        list.addView(MaterialButton(app).apply {
            cornerRadius = 90
            setPadding(30)
            textSize = 35f
            layoutParams = param
            text = "Save"
            setBackgroundColor(RED)
            setOnClickListener {
                save()
                app.reset()
            }
        })
    }

    private fun save() {
        val edit = app.getSharedPreferences(app.packageName, MODE_PRIVATE).edit()
        edit.putBoolean("hasSaved", true)
        with(arguments!!) {
            for (key in this.keySet()) {
                edit.putString(key, getString(key))
            }
        }
        edit.apply()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        app = context as LoginActivity
    }

    private fun addView(root: LinearLayout, string: String, drawable: Drawable, param: LayoutParams, color: Int) {
        root.addView(TextView(app).apply {
            text = string
            textSize = 30f
            setTextColor(color)
            background = drawable
            gravity = Gravity.CENTER
            layoutParams = param
            setPadding(20)
        })
    }
}