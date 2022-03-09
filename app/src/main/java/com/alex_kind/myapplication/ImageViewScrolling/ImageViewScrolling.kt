package com.alex_kind.myapplication.ImageViewScrolling

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.alex_kind.myapplication.R
import com.alex_kind.myapplication.databinding.ImageViewScrollingBinding

class ImageViewScrolling(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private lateinit var eventEnd: IEventEnd
    private var binding: ImageViewScrollingBinding =
        ImageViewScrollingBinding.inflate(LayoutInflater.from(context))

    private var lastResult = 0
    private var oldValue = 0

    companion object {
        private const val ANIMATION_DURATION = 150
    }

    val value: Int
        get() = Integer.parseInt(binding.nextImage.tag.toString())

    fun setEventEnd(eventEnd: IEventEnd) {
        this.eventEnd = eventEnd
    }

    init {
        addView(binding.root)
    }


    fun setRandomValue(image: Int, numRoll: Int) {
        binding.currentImage.animate()
            .translationY(-height.toFloat())
            .setDuration(ANIMATION_DURATION.toLong()).start()

        binding.nextImage.translationY = binding.nextImage.height.toFloat()
        binding.nextImage.animate()
            .translationY(0f).setDuration(ANIMATION_DURATION.toLong())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    setImage(binding.currentImage, oldValue % 6)
                    binding.currentImage.translationY = 0f
                    if (oldValue != numRoll) {
                        setRandomValue(image, numRoll)
                        oldValue++
                    } else {
                        lastResult = 0
                        oldValue = 0
                        setImage(binding.nextImage, image)
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
