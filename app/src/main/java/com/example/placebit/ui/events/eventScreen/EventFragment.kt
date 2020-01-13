package com.example.placebit.ui.events.eventScreen


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.placebit.R
import com.example.placebit.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_event.*


/**
 * A simple [Fragment] subclass.
 */
class EventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)!!.supportActionBar!!.hide()
        toolbar_image.setOnClickListener {
            activity?.onBackPressed()
        }

//        (activity as MainActivity?)!!.setSupportActionBar(toolbar)
    }


}
