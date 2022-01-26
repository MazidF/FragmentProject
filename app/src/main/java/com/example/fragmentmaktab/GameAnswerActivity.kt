package com.example.fragmentmaktab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.fragmentmaktab.GameFragment.Companion.ANSWER
import com.example.fragmentmaktab.GameFragment.Companion.HAS_SEEN
import com.example.fragmentmaktab.GameFragment.Companion.QUESTION
import com.example.fragmentmaktab.databinding.ActivityGameAnswerBinding

class GameAnswerActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameAnswerBinding
    var userSeenTheAnswer = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val extra = intent!!.extras!!
        with(binding) {
            answerQuestion.text = extra.getString(QUESTION)
            answerAnswer.text = extra.getBoolean(ANSWER).toString()
            answerShowBtn.setOnClickListener {
                Log.d("myTag", "OnClick")
                answerAnswer.visibility = View.VISIBLE
                userSeenTheAnswer = true
            }
        }
    }

    override fun onBackPressed() {
        setResult(10, Intent().apply {
            putExtra(HAS_SEEN, userSeenTheAnswer)
        })
        finish()
        super.onBackPressed()
    }
}