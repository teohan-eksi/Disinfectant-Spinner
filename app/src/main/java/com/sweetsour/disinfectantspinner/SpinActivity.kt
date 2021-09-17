//TODO: implement adding questions and actions feature.

package com.sweetsour.disinfectantspinner

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
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

        /* How it works:
        first check if the bottle is at the initial position, which is rotation=0f.
        if it's not at the right position, animate it to come there.
        after the bottle is at the right initial position, just spin it!
        */

        //put animator objects in a list, so that they can be played by the animator set object
        val animatorList: MutableList<Animator> = mutableListOf()

        //clip the extra rotation of the bottle for easier calculation.
        val bottleRotation = bottle.rotation % 360
        Log.d("SpinActivity", "bottle rotation is: $bottleRotation")
        //TODO: turn the bottle according to tipping side.
        if(bottleRotation == 0f){
            //since the bottle is at the right initial position, let the program flow spin it.
            Log.d("SpinActivity", "the bottle at 0f")
        }else if(bottleRotation > 0f && bottleRotation <= 180f){
            //the bottle tipped to the right side.
            //so turn it to left until it has rotation = 0f
            Log.d("SpinActivity", "the bottle at $bottleRotation")
            animatorList.add(spinBottleAnimator(bottleRotation, 0f, 1000))
        }else{
            //turn right until rotation=0f
            Log.d("SpinActivity", "the bottle at $bottleRotation")
            animatorList.add(spinBottleAnimator(bottleRotation, 0f, 1000))
        }

        //creating start and stop angles
        //Turn the bottle 2 times before stopping.
        //positive values turn clockwise
        val startAngle: Float = -720f //initial position
        //Stop the bottle at a random angle btw 0 and 360 degrees, after turning 2 times.
        val stopAngle: Float = Random.nextFloat() * 360
        //animate the bottle turning from -720f degrees to stopAngle
        animatorList.add(spinBottleAnimator(startAngle, stopAngle, 3000))

        //animatorSet can play multiple animations sequentially or together to create complex ones.
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(animatorList)
        animatorSet.start()

        Log.d("SpinActivity", "End of spinBtnClick")
    }

    //the function returns an Animator object,
    // because animatorSet only accepts a list of Animator objects
    private fun spinBottleAnimator(startA: Float, stopA: Float, duration: Long): Animator{
        Log.d("SpinActivity", "spinBottle function starts")

        //notice that animator variable is of type ObjectAnimator,
        //which is a subclass of ValueAnimator which extends Animator
        val animator = ObjectAnimator.ofFloat(
            bottle,
            "rotation",
            startA,
            stopA
        )

        animator.duration = duration //in ms

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

        // animator is of ObjectAnimator but function returns Animator
        // and animatorSet works fine, which means animator of type ObjectAnimator
        // turned into Animator somewhere along the way.
        //TODO: this needs a little bit research.
        return animator
    }

}