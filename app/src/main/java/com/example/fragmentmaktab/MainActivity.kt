package com.example.fragmentmaktab

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resources.getIdentifier("head1", "drawable", packageName).toast()
        init()
    }

    private fun init() {
        var bundle: Bundle
        supportFragmentManager.commit {
            setReorderingAllowed(true)


            bundle = bundleOf(
                "images" to arrayOf(
                    R.drawable.head1,
                    R.drawable.head2,
                    R.drawable.head3,
                    R.drawable.head4,
                    R.drawable.head5,
                    R.drawable.head6,
                    R.drawable.head7,
                    R.drawable.head8,
                    R.drawable.head9,
                    R.drawable.head10,
                    R.drawable.head11,
                    R.drawable.head12
                )
            )
            add<MyFragment>(R.id.headContainer, args = bundle)

            bundle = bundleOf(
                "images" to arrayOf(
                    R.drawable.body1,
                    R.drawable.body2,
                    R.drawable.body3,
                    R.drawable.body4,
                    R.drawable.body5,
                    R.drawable.body6,
                    R.drawable.body7,
                    R.drawable.body8,
                    R.drawable.body9,
                    R.drawable.body10,
                    R.drawable.body11,
                    R.drawable.body12
                )
            )
            add<MyFragment>(R.id.bodyContainer, args = bundle)

            bundle = bundleOf(
                "images" to arrayOf(
                    R.drawable.legs1,
                    R.drawable.legs2,
                    R.drawable.legs3,
                    R.drawable.legs4,
                    R.drawable.legs5,
                    R.drawable.legs6,
                    R.drawable.legs7,
                    R.drawable.legs8,
                    R.drawable.legs9,
                    R.drawable.legs10,
                    R.drawable.legs11,
                    R.drawable.legs12
                )
            )
            add<MyFragment>(R.id.legContainer, args = bundle)
        }
    }

    private fun Any.toast() {
        Toast.makeText(this@MainActivity, toString(), Toast.LENGTH_SHORT).show()
        Log.d("myTag", toString())
    }
}
