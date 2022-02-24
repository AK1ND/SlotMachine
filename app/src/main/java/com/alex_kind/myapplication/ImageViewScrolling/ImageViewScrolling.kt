package com.alex_kind.myapplication.ImageViewScrolling

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.alex_kind.myapplication.R
import kotlinx.android.synthetic.main.image_view_scrolling.view.*

class ImageViewScrolling(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    internal lateinit var eventEnd: IEventEnd
    internal var lastResult = 0
    internal var oldValue = 0

    companion object {
        private const val ANIMATION_DURATION = 150
    }

    val value: Int
        get() = Integer.parseInt(nextImage.tag.toString())

    fun setEventEnd(eventEnd: IEventEnd) {
        this.eventEnd = eventEnd
    }

    init {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.image_view_scrolling, this)
        nextImage.translationY = height.toFloat()
    }

    fun setRandomValue(image: Int, numRoll: Int) {
        currentImage.animate()
            .translationY(-height.toFloat())
            .setDuration(ANIMATION_DURATION.toLong()).start()

        nextImage.translationY = nextImage.height.toFloat()
        nextImage.animate()
            .translationY(0f).setDuration(ANIMATION_DURATION.toLong())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    setImage(currentImage, oldValue % 6)
                    currentImage.translationY = 0f
                    if (oldValue != numRoll) {
                        setRandomValue(image, numRoll)
                        oldValue++
                    } else {
                        lastResult = 0
                        oldValue = 0
                        setImage(nextImage, image)
                        eventEnd.eventEnd(image % 6, numRoll)

                    }

                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                }

            }).start()

    }

    private fun setImage(currentImage: ImageView, value: Int) {
        when (value) {
            Util.zero -> currentImage.setImageResource(R.drawable.avatar_zero)
            Util.one -> currentImage.setImageResource(R.drawable.avatar_one)
            Util.two -> currentImage.setImageResource(R.drawable.avatar_two)
            Util.three -> currentImage.setImageResource(R.drawable.avatar_three)
            Util.four -> currentImage.setImageResource(R.drawable.avatar_four)
            Util.five -> currentImage.setImageResource(R.drawable.avatar_five)
        }

        currentImage.tag = value
        lastResult = value
    }


}
