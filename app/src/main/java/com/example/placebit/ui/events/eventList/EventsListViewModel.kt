package com.example.placebit.ui.events.eventList

import android.app.Application
import com.example.placebit.data.model.EventModel
import com.example.placebit.util.BaseViewModel
import com.example.placebit.util.SingleLiveEvent

class EventsListViewModel(context: Application) : BaseViewModel(context) {

    private var eventsList = arrayListOf<EventModel>()
    internal val addToFav = SingleLiveEvent<EventModel>()
    internal val toEventDetails = SingleLiveEvent<EventModel>()
    val eventAdapter: EventsListAdapter = EventsListAdapter(context)

    init {
        eventAdapter.actionAddToFav = {
            addToFav(it)
        }
        eventAdapter.actionToEventDetails = {
            toEvent(it)
        }
    }

    private fun toEvent(model: EventModel) {
        toEventDetails.postValue(model)
    }

    private fun addToFav(model: EventModel) {
        addToFav.postValue(model)
    }

    fun getEvents() {
        eventsList.clear()
        for (i in 1..10) {
            eventsList.add(EventModel(id = i.toString()))
        }
        eventAdapter.items.addAll(eventsList)
        eventAdapter.notifyDataSetChanged()
    }
}