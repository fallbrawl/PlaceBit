package com.example.placebit.ui.events.eventScreen


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.placebit.R
import com.example.placebit.data.model.QrCodeModel
import com.example.placebit.extensions.obtainViewModel
import com.example.placebit.ui.MainActivity
import com.example.placebit.ui.events.eventScreen.qrGallery.QrGalleryFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_event.*


class EventFragment : Fragment() {
    private var fav: Boolean = true
    private var container: Boolean = false
    private lateinit var qrAdapter: QrCodesAdapter
    private val qrsList: ArrayList<QrCodeModel> = arrayListOf()
    private var i = 1;

    private fun getCurrentViewModel(): EventViewModel {
        return obtainViewModel(EventViewModel::class.java)
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
//        val transition: Transition = Slide(Gravity.TOP)
//        transition.duration = 600
//        transition.addTarget(R.id.buyTicketContainer)
//        TransitionManager.beginDelayedTransition(motion, transition)
        buyTicketContainer.visibility = if (container) View.VISIBLE else View.GONE
    }

    private fun initQrAdapter() {
        context?.let {
            qrsList.clear()

            qrAdapter = QrCodesAdapter(it)
            qrsList.add(QrCodeModel())
            qrAdapter.items = qrsList
            recyclerViewQrCodes.visibility = View.VISIBLE
            recyclerViewQrCodes.adapter = qrAdapter
            i++
            qrAdapter.actionBuyMoreDetails = {
                if (qrAdapter.itemCount < 9) {
                    qrsList.add(QrCodeModel(id = i.toString()))
                    i++
                    qrAdapter.notifyDataSetChanged()
                }
            }
            qrAdapter.actionToQrCodeDetails = { clickedQrCode ->

                val bundle = Bundle()
                bundle.putInt(QrGalleryFragment.POSITION, clickedQrCode.id.toInt())
                qrsList.removeAt(0)
                bundle.putSerializable(QrGalleryFragment.QRS, qrsList)

                view?.findNavController()?.navigate(
                    R.id.action_eventFragment_to_qrGalleryFragment,
                    bundle
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)!!.supportActionBar!!.hide()
        (activity as MainActivity).drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        initQrAdapter()
        buyTicketContainer.visibility = if (container) View.VISIBLE else View.GONE

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
            state.observe(viewLifecycleOwner, Observer {

            })
        }
    }
}
