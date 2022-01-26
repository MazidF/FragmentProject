package com.example.fragmentmaktab

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.fragmentmaktab.GameFragment.Companion.ANSWER
import com.example.fragmentmaktab.GameFragment.Companion.HAS_SEEN
import com.example.fragmentmaktab.databinding.ActivityQuizGameBinding
import com.example.fragmentmaktab.GameFragment.Companion.QUESTION
import com.example.fragmentmaktab.GameFragment.Companion.TAG
import kotlin.math.log
import kotlin.random.Random

class QuizGameActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuizGameBinding
    lateinit var cheatLauncher: ActivityResultLauncher<Bundle>
    lateinit var cheatLauncher2: ActivityResultLauncher<Intent>
    lateinit var manager: FragmentManager
    var currentTag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        manager = supportFragmentManager
        launcherInit()
        if (savedInstanceState == null) {
            init()
        }
    }

    fun launcherInit() {
        cheatLauncher = registerForActivityResult(object : ActivityResultContract<Bundle, Boolean>() {
            override fun createIntent(context: Context, input: Bundle): Intent {
                return Intent(this@QuizGameActivity, GameAnswerActivity::class.java).apply {
                    putExtras(input)
                }
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
                return intent!!.getBooleanExtra(HAS_SEEN, false)
            }

        }) { result ->
            (supportFragmentManager.findFragmentById(R.id.gameContainer) as GameFragment).apply {
                if (!userSeenTheAnswer) {
                    userSeenTheAnswer = result
                }
            }
        }

        cheatLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            (supportFragmentManager.findFragmentById(R.id.gameContainer) as GameFragment).apply {
                userSeenTheAnswer = it.data!!.getBooleanExtra(HAS_SEEN, false)
            }
        }
    }

    private fun init() {
        val questions = List(10) {
            "آیا این درست است شماره$it"
        }
        val answers = List(10) {
            Random.nextBoolean()
        }
        add(questions, answers)
    }

    private fun add(questions: List<String>, answers: List<Boolean>) {
        var bundle: Bundle
        var j = questions.size - 1
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            for (i in questions.indices) {
                bundle = bundleOf(QUESTION to questions[j], ANSWER to answers[j], TAG to j)
                add<GameFragment>(R.id.gameContainer, args = bundle, tag = (j).toString())
                j--
            }
            addToBackStack("next")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun next() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.gameContainer, manager.findFragmentByTag("${++currentTag}")!!)
        }
    }

    fun prev() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.gameContainer, manager.findFragmentByTag("${--currentTag}")!!)
        }
    }

    fun cheat(bundle: Bundle) {
        cheatLauncher.launch(bundle)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putInt("currentTag", currentTag)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        with(savedInstanceState) {
            currentTag = getInt("currentTag", currentTag)
        }
    }
}
