package com.example.fragmentmaktab

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

class GameFragment : Fragment(R.layout.game_fragment) {
    var answer = false
    var question = ""
    var userSeenTheAnswer = false
    var hasCheated = false
    var hasAnswered = false
    lateinit var app: QuizGameActivity
    lateinit var buttons: Array<Button>
    lateinit var manager: FragmentManager
    var tag: Int = -1

    companion object {
        const val QUESTION = "question"
        const val ANSWER = "answer"
        const val CORRECT = "Correct!!"
        const val INCORRECT = "Incorrect!!"
        const val CHEAT = "Cheating is Wrong!!"
        const val HAS_SEEN = "hasSeen"
        const val TAG = "tag"
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        app = context as QuizGameActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments ?: throw Exception("bundle in game fragment is null!!!")
        manager = parentFragmentManager
        tag = bundle.getInt(TAG)
        with(view) {
            answer = bundle.getBoolean(ANSWER)
            question = bundle.getString(QUESTION)!!

            findViewById<TextView>(R.id.game_question).apply {
                text = question
            }


            buttons = arrayOf (
                findViewById<Button>(R.id.game_true).apply {
                    setOnClickListener {
                        if (hasAnswered) return@setOnClickListener
                        toast(if (answer) (if (userSeenTheAnswer) CHEAT else CORRECT) else INCORRECT)
                        done()
                    }
                },
                findViewById<Button>(R.id.game_false).apply {
                    setOnClickListener {
                        if (hasAnswered) return@setOnClickListener
                        toast(if (!answer) (if (userSeenTheAnswer) CHEAT else CORRECT) else INCORRECT)
                        done()
                    }
                },
                findViewById<Button>(R.id.game_cheat).apply {
                    setOnClickListener {
                        if (hasAnswered) return@setOnClickListener
                        app.cheat(bundle)
                    }
                }
            )

            findViewById<Button>(R.id.game_next).apply {
                if (this@GameFragment.tag >= 9) {
                    this.visibility = View.INVISIBLE
                    return@apply
                }
                setOnClickListener {
                    next()
                }
            }
            findViewById<Button>(R.id.game_previous).apply {
                if (this@GameFragment.tag <= 0) {
                    this.visibility = View.INVISIBLE
                    return@apply
                }
                setOnClickListener {
                    prev()
                }
            }
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    private fun done() {
        for (button in buttons) {
            button.isEnabled = false
        }
        hasAnswered = true
        hasCheated = answer && userSeenTheAnswer
    }

    fun next() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.gameContainer, manager.findFragmentByTag("${tag + 1}")!!)
//            addToBackStack("main")
        }
    }

    fun prev() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.gameContainer, manager.findFragmentByTag("${tag - 1}")!!)
//            addToBackStack("main")
        }
    }

    private fun log(msg: String) {
        Log.d("tag_tag", msg)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(HAS_SEEN, userSeenTheAnswer)
        outState.putBoolean("hasAnswered", hasAnswered)
        log("save")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.run {
            hasAnswered = getBoolean("hasAnswered", false)
            userSeenTheAnswer = getBoolean(HAS_SEEN, false)
            log("load")
        }
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
    }

    override fun onStart() {
        super.onStart()
        if (hasAnswered) {
            done()
        }
        if (hasCheated) {
            toast("SHAME!!!")
        }
        log("onStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }
}
