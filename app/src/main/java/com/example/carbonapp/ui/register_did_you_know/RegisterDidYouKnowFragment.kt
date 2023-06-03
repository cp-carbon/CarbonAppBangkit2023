package com.example.carbonapp.ui.register_did_you_know

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import com.example.carbonapp.R
import com.example.carbonapp.helper.Navigator

class RegisterDidYouKnowFragment : Fragment() {

    private lateinit var messages: List<String>

    private val bodyTextAnimatorSet = AnimatorSet()
    private var isBodyTextAnimationCancelled = false
    private var animationRepeatCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Setup did you know messages
        messages = listOf(
            resources.getString(R.string.register_did_you_know_body1),
            resources.getString(R.string.register_did_you_know_body2),
            resources.getString(R.string.register_did_you_know_body3)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_register_did_you_know, container, false)

        val bodyTextView = v.findViewById<TextView>(R.id.pr_body)
        bodyTextView.apply {
            text = messages[0]
            alpha = 0f
        }
        setupBodyTextViewAnimation(bodyTextView)

        return v
    }

    override fun onDestroy() {
        super.onDestroy()
        bodyTextAnimatorSet.cancel()
    }

    private fun setupBodyTextViewAnimation(bodyTextView: TextView) {
        val fadeInDuration = resources.getInteger(R.integer.rdyk_text_fade_in_duration).toLong()
        val fadeOutDuration = resources.getInteger(R.integer.rdyk_text_fade_out_duration).toLong()
        val fadeInDelay = resources.getInteger(R.integer.rdyk_text_fade_in_delay).toLong()
        val fadeOutDelay = resources.getInteger(R.integer.rdyk_text_fade_out_delay).toLong()

        val fadeInAnimator = ObjectAnimator.ofFloat(bodyTextView, "alpha", 1.0f)
        fadeInAnimator.apply {
            duration = fadeInDuration
            startDelay = fadeInDelay
        }
        val fadeOutAnimator = ObjectAnimator.ofFloat(bodyTextView, "alpha", 0.0f)
        fadeOutAnimator.apply {
            duration = fadeOutDuration
            startDelay = fadeOutDelay
        }

        bodyTextAnimatorSet.doOnCancel {
            isBodyTextAnimationCancelled = true
        }
        bodyTextAnimatorSet.doOnEnd {
            if (isBodyTextAnimationCancelled) {
                return@doOnEnd
            }
            if (animationRepeatCount < messages.size-1) {
                animationRepeatCount++
                bodyTextView.text = messages[animationRepeatCount]
                bodyTextAnimatorSet.start()
            } else {
                navigateToRegisterBeginJourney()
            }
        }
        bodyTextAnimatorSet.playSequentially(fadeInAnimator, fadeOutAnimator)
        bodyTextAnimatorSet.start()
    }

    private fun navigateToRegisterBeginJourney() {
        Navigator.toRegisterBeginJourneyFragment(this)
    }
}