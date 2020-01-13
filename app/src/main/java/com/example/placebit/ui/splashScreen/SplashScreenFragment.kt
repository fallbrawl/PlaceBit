package com.example.placebit.ui.splashScreen

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.placebit.R
import com.example.placebit.extensions.obtainViewModel
import com.example.placebit.util.StateEnum


class SplashScreenFragment : Fragment() {

    private fun getCurrentViewModel(): SplashScreenViewModel {
        return obtainViewModel(SplashScreenViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        postDelayedMoveToWelcomeScreen(1000, view)
        getCurrentViewModel().apply {
            state.observe(this@SplashScreenFragment, Observer {
                when (it) {
                    StateEnum.COMPLETE -> {

                    }
                    StateEnum.ERROR -> {
                        if (!this.errorMessage.get().isNullOrEmpty()) {
                            Toast.makeText(activity, errorMessage.get(), Toast.LENGTH_LONG)
                                .show()
                        }
                        errorMessage.set(null)
                    }
                }
            })
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    private fun postDelayedMoveToWelcomeScreen(
        delayMs: Long,
        view: View
    ) {
        Handler().apply {
            val runnable = Runnable {
                view.findNavController()
                    .navigate(R.id.action_splashScreen_to_eventsListFragment)
            }
            postDelayed(runnable, delayMs)
        }
    }
}