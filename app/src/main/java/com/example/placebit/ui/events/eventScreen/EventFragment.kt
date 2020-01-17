package com.example.placebit.ui.events.eventScreen


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.example.placebit.R
import com.example.placebit.data.model.QrCodeModel
import com.example.placebit.extensions.obtainViewModel
import com.example.placebit.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_event.*


class EventFragment : Fragment() {
    private var fav: Boolean = true
    private var container: Boolean = false
    private lateinit var qrAdapter: QrCodesAdapter
    private val qrsList: ArrayList<QrCodeModel> = arrayListOf()

    private fun getCurrentViewModel(): EventViewModel {
        return obtainViewModel(EventViewModel::class.java)
    }

    private fun addATicket() {


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    private fun toggleBuyContainer() {
        buttonBuy.visibility = View.GONE
        container = container.not()
        val transition: Transition = Slide(Gravity.TOP)
        transition.duration = 600
        transition.addTarget(R.id.buyTicketContainer)
        TransitionManager.beginDelayedTransition(motion, transition)
        buyTicketContainer.visibility = if (container) View.VISIBLE else View.GONE
    }

    private fun initQrAdapter() {
        context?.let {
            qrsList.clear()
            qrAdapter =  QrCodesAdapter(it)
            qrsList.add(QrCodeModel())
            qrAdapter.items = qrsList
            recyclerViewQrCodes.visibility = View.VISIBLE
            recyclerViewQrCodes.adapter  = qrAdapter
            qrAdapter.actionBuyMoreDetails = {
                qrsList.add(QrCodeModel())
                qrAdapter.notifyItemInserted(qrAdapter.itemCount)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)!!.supportActionBar!!.hide()
        (activity as MainActivity).drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        initQrAdapter()


        buttonBuy.setOnClickListener {
            toggleBuyContainer()
        }

        imageViewBack.setOnClickListener {
            activity?.onBackPressed()
        }

        imageViewFav.setOnClickListener {
            fav = if (fav) {
                imageViewFav.setImageDrawable(resources.getDrawable(R.drawable.ic_fav_deactivated))
                false
            } else {
                imageViewFav.setImageDrawable(resources.getDrawable(R.drawable.ic_fav_active))
                true
            }
        }

        getCurrentViewModel().apply {
            state.observe(this@EventFragment, Observer {

            })
        }
    }
}
