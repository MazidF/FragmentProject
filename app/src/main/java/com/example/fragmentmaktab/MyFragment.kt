package com.example.fragmentmaktab

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment

class MyFragment : Fragment(R.layout.my_fragment) {
    lateinit var images: Array<Int>
    lateinit var imageView: ImageView
    lateinit var next: Button
    lateinit var previous: Button
    var current = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        images = requireArguments().get("images") as Array<Int>
        with(view) {
            imageView = findViewById(R.id.imageView)
            next = findViewById(R.id.nextImage)
            next.setOnClickListener {
                ++current
                setImage()
            }
            previous = findViewById(R.id.previous)
            previous.setOnClickListener {
                --current
                setImage()
            }
            setImage()
        }
    }

    private fun setImage() {
        if (current == -1) {
            current = images.size - 1
        }
        if (current == images.size) {
            current = 0
        }
        imageView.setImageResource(images[current])
    }
}
