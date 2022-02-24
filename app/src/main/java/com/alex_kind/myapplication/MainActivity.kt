package com.alex_kind.myapplication

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alex_kind.myapplication.ImageViewScrolling.IEventEnd
import com.alex_kind.myapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), IEventEnd {
    lateinit var bind: ActivityMainBinding
    var count_down = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.image1.setEventEnd(this@MainActivity)
        bind.image2.setEventEnd(this@MainActivity)
        bind.image3.setEventEnd(this@MainActivity)

        tap()
    }

    private fun tap() {
        bind.buttonPush.setOnClickListener {
            if (Common.SCORE >= 50) {

                bind.image1.setRandomValue(Random.nextInt(6), Random.nextInt(10) + 5)
                bind.image2.setRandomValue(Random.nextInt(6), Random.nextInt(15) + 15)
                bind.image3.setRandomValue(Random.nextInt(6), Random.nextInt(20) + 30)

                Common.SCORE -= 50
                bind.txtScore.text = Common.SCORE.toString()

            } else {
                Toast.makeText(applicationContext, "Not enough money", Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun video() {
        bind.videoView.visibility = View.VISIBLE
        val link = "https://www.youtube.com/watch?v=r3uus133dDs"
        val video = bind.videoView
        video.setVideoURI(Uri.parse(link))
        video.setMediaController(MediaController(this))
        video.requestFocus(0)
        video.start()
    }


    override fun eventEnd(result: Int, count: Int) {
        if (count_down < 2) {
            count_down++
        } else {
            count_down = 0

            if (bind.image1.value == bind.image2.value && bind.image2.value == bind.image3.value) {
                Toast.makeText(applicationContext, "You win BIG prize", Toast.LENGTH_SHORT).show()
                Common.SCORE += 300
                bind.txtScore.text = Common.SCORE.toString()
            } else if (bind.image1.value == bind.image2.value ||
                bind.image2.value == bind.image3.value ||
                bind.image1.value == bind.image3.value
            ) {

                Toast.makeText(applicationContext, "You win SMALL prize", Toast.LENGTH_SHORT).show()
                Common.SCORE += 100
                bind.txtScore.text = Common.SCORE.toString()
            } else {
                Toast.makeText(applicationContext, "You lose", Toast.LENGTH_SHORT).show()
            }
        }
    }
}