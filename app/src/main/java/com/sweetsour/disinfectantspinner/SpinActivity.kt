package com.sweetsour.disinfectantspinner

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class SpinActivity: AppCompatActivity() {
    lateinit var bottle: ImageView
    lateinit var spinBtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spin_activity)

        bottle = findViewById(R.id.bottle)

        spinBtn = findViewById(R.id.spinTextView)
        spinBtn.setOnClickListener{
            spinBtnClick()
        }
    }

    private fun spinBtnClick(){
        Log.d("SpinActivity", "spinBtnClick")

        //Turn the bottle 3 times before stopping.
        //minus values turn clockwise
        val threeRevolution: Float = -1080f
        //Stop the bottle at a random angle btw 0 and 360 degrees, after turning 3 times.
        val stopAngle: Float = Random.nextFloat() * -360
        //animate turning from -1080 degrees to stopAngle
        val animator = ObjectAnimator.ofFloat(
            bottle,
            "rotation",
            threeRevolution,
            stopAngle
        )

        animator.duration = 3000 //in ms

        animator.addListener(object: AnimatorListenerAdapter(){
            //deactivate the spin button while animation is running
            override fun onAnimationStart(animation: Animator?) {
                Log.d("SpinActivity", "onAnimationStart")
                spinBtn.isEnabled = false
            }
            //activate the spin button after animation ends
            override fun onAnimationEnd(animation: Animator?) {
                Log.d("SpinActivity", "onAnimationEnd")
                spinBtn.isEnabled = true
            }
        })

        //fire up!
        animator.start()
        Log.d("SpinActivity", "End of spinBtnClick")
    }
}