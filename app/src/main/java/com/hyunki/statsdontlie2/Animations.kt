package com.hyunki.statsdontlie2

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi

object Animations {
    private lateinit var checker: Animation
    private lateinit var fadeIn: Animation
    private lateinit var fadeOut: Animation

    fun getChecker(v: View?): Animation? {
        checker = AnimationUtils.loadAnimation(v!!.context, R.anim.right_or_wrong)
        checker.setAnimationListener(object : AnimationListener {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            override fun onAnimationStart(animation: Animation) {
                v.visibility = View.VISIBLE
            }
            override fun onAnimationEnd(animation: Animation) {
                v.visibility = View.INVISIBLE
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })
        return checker
    }

    fun getFadeIn(v: View): Animation? {
        fadeIn = AnimationUtils.loadAnimation(v.context, R.anim.fade_in)
        fadeIn.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                toggleView(v, true)
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })
        return fadeIn
    }

    fun getFadeOut(v: View): Animation? {
        fadeOut = AnimationUtils.loadAnimation(v.context, R.anim.fade_out)
        fadeOut.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                toggleView(v,false)
            }
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })
        return fadeOut
    }

    fun getCardFlipAnimation(v: View, parentTop: Int): AnimatorSet {
        val y = v.y
        val top = parentTop.toFloat()
        val setDuration = 250L

        v.pivotX = 0f
        v.pivotY = 0f

        val translate = ObjectAnimator.ofFloat(v,"translationY",y,top,0f)
        translate.duration = setDuration

        val alpha = ObjectAnimator.ofFloat(v, "alpha",1.0f, 0.0f,1.0f)
        alpha.duration = setDuration

        val rotate = ObjectAnimator.ofFloat(v, "rotation", 0f,25f,0f)
        rotate.duration = setDuration

        val set = AnimatorSet()
        set.playTogether(translate,alpha,rotate)
        return set
    }

    private fun toggleView(v: View, boolean: Boolean){
        v.isClickable = boolean
        v.isEnabled = boolean
    }
}