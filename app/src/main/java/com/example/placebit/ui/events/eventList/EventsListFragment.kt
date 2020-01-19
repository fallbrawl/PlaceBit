package com.example.placebit.ui.events.eventList

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.example.placebit.R
import com.example.placebit.extensions.obtainViewModel
import com.example.placebit.ui.MainActivity
import com.example.placebit.util.StateEnum
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_events_list.*


class EventsListFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private fun getCurrentViewModel(): EventsListViewModel {
        return obtainViewModel(EventsListViewModel::class.java)
    }

    private fun toggle(toggle: Boolean) {
        val transition: Transition = Slide(Gravity.TOP)
        transition.duration = 600
        transition.addTarget(R.id.filterContainer)
        TransitionManager.beginDelayedTransition(parentContainer, transition)
        filterContainer.visibility = if (toggle) View.VISIBLE else View.GONE
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        when (p0?.itemId) {
            R.id.action_search ->
                return true
            R.id.action_filter -> {
                if (searchResultsHeader.visibility == View.VISIBLE) {
                    searchResultsHeader.visibility = View.GONE
                    toggle(true)
                } else {
                    searchResultsHeader.visibility = View.VISIBLE
                    toggle(false)
                }

                return true
            }

        }
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_events_list, container, false)
        setHasOptionsMenu(true);

        return rootView
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val toolbar =
            activity!!.findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.inflateMenu(R.menu.toolbar_menu)
        toolbar.setOnMenuItemClickListener(this)
        super.onCreateOptionsMenu(menu, inflater)

    }

    private fun initAdapter() {
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context)
        eventsList.layoutManager = mLayoutManager

        eventsList.adapter = getCurrentViewModel().eventAdapter
        val dividerItemDecoration =
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        context?.let { con ->
            dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(
                    con,
                    R.drawable.divider_space
                )!!
            )
        }
        eventsList.addItemDecoration(dividerItemDecoration)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        initAdapter()
        (activity as MainActivity?)!!.supportActionBar!!.show()
        getCurrentViewModel().apply {
            state.observe(this@EventsListFragment, Observer {

                getEvents()
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
            toEventDetails.observe(this@EventsListFragment, Observer {
                val action =
                    EventsListFragmentDirections.actionEventsListFragmentToEventFragment(it.id.toInt())
                (activity as MainActivity).drawer_layout.closeDrawers()
                findNavController().navigate(action)
            })
            addToFav.observe(this@EventsListFragment, Observer {

                if (it.isFavorited) {
                    (eventsList.findViewHolderForAdapterPosition(it.position) as EventsListAdapter.EventsListAdapterViewHolder).imageViewAddToFav.setImageDrawable(
                        context?.getDrawable(R.drawable.ic_fav_active)
                    )
                } else {
                    (eventsList.findViewHolderForAdapterPosition(it.position) as EventsListAdapter.EventsListAdapterViewHolder).imageViewAddToFav.setImageDrawable(
                        context?.getDrawable(R.drawable.ic_fav_deactivated)
                    )
                }
            })
        }
    }
}
