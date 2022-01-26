package com.example.fragmentmaktab

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class TestFragment : Fragment(R.layout.test_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.number).text = arguments!!.getInt("number").toString()
    }
}
