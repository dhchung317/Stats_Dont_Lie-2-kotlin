package com.hyunki.statsdontlie

import android.os.Build
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi

object Animations {
    private lateinit var checker: Animation
    private var fadeIn: Animation? = null
    private var fadeOut: Animation? = null
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
                v.isClickable = true
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        return fadeIn
    }

    fun getFadeOut(v: View): Animation? {
        fadeOut = AnimationUtils.loadAnimation(v.context, R.anim.fade_out)
        fadeOut.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                v.isClickable = false
            }

            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })
        return fadeOut
    }
}