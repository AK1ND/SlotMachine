package com.alex_kind.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alex_kind.myapplication.ImageViewScrolling.IEventEnd
import com.alex_kind.myapplication.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity(), IEventEnd {
    lateinit var bind: ActivityMainBinding
    private var countDown = 0
    var sharedPreferences: SharedPreferences? = null
    var sharedEditor: SharedPreferences.Editor? = null


    private var score = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        if (savedInstanceState == null) {
            checkFirstRun()
        }

        loadData()

        bind.image1.setEventEnd(this@MainActivity)
        bind.image2.setEventEnd(this@MainActivity)
        bind.image3.setEventEnd(this@MainActivity)

        tap()

        addScore()
    }

    private fun addScore() {
        bind.buttonAdd.setOnClickListener {
            score += 100
            saveData()
            bind.txtScore.text = score.toString()
        }
    }

    private fun checkFirstRun() {

        sharedPreferences = getPreferences(MODE_PRIVATE)
        sharedEditor = sharedPreferences!!.edit()
        if (isFirstRun()) {
            score = 100
            saveData()
        }
    }


    private fun isFirstRun(): Boolean {
        return if (sharedPreferences!!.getBoolean("firstTime", true)) {
            sharedEditor!!.putBoolean("firstTime", false)
            sharedEditor!!.apply()
            true
        } else {
            false
        }
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences(
            "sharedPrefs",
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.apply {
            putInt("STRING_KEY", score).apply()
        }
    }

    private fun loadData() {

        val sharedPreferences = getSharedPreferences(
            "sharedPrefs",
            Context.MODE_PRIVATE
        )
        val savedString = sharedPreferences.getInt(
            "STRING_KEY",
            0
        )
        score = savedString
        bind.txtScore.text = savedString.toString()
    }


    private fun tap() {

        bind.buttonPush.setOnClickListener {

            if (score >= 50) {
                bind.buttonPush.visibility = View.GONE

                bind.image1.setRandomValue(Random.nextInt(6), Random.nextInt(10) + 5)
                bind.image2.setRandomValue(Random.nextInt(6), Random.nextInt(10) + 10)
                bind.image3.setRandomValue(Random.nextInt(6), Random.nextInt(10) + 15)

                score -= 50
                saveData()
                bind.txtScore.text = score.toString()

            } else {
                Toast.makeText(applicationContext, "Not enough money", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }


    override fun eventEnd(result: Int, count: Int) {
        if (countDown < 2) {
            countDown++
        } else {
            countDown = 0
            bind.buttonPush.visibility = View.VISIBLE

            if (bind.image1.value == bind.image2.value && bind.image2.value == bind.image3.value) {
                Toast.makeText(applicationContext, "You win BIG prize", Toast.LENGTH_SHORT).show()
                score += 300
                saveData()
                bind.txtScore.text = score.toString()

            } else if (bind.image1.value == bind.image2.value ||
                bind.image2.value == bind.image3.value ||
                bind.image1.value == bind.image3.value
            ) {

                Toast.makeText(applicationContext, "You win SMALL prize", Toast.LENGTH_SHORT).show()
                score += 100
                saveData()
                bind.txtScore.text = score.toString()

            } else {
                Toast.makeText(applicationContext, "You lose", Toast.LENGTH_SHORT).show()
            }
        }
    }
}