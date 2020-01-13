package com.example.placebit.ui.events.eventList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.placebit.R
import com.example.placebit.data.model.EventModel
import com.example.placebit.ui.events.eventScreen.EventViewModel
import kotlinx.android.synthetic.main.item_event.view.*


class EventsListAdapter(var context: Context) :
    RecyclerView.Adapter<EventsListAdapter.EventsListAdapterViewHolder>() {
    var items = ArrayList<EventModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var actionToEventDetails: (referral: EventModel) -> Unit = {}
    var actionAddToFav: (referral: EventModel) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsListAdapterViewHolder {
        return EventsListAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_event,
                parent,
                false
            )
        )
    }
    fun change() {

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: EventsListAdapterViewHolder, position: Int) {
        holder.textViewEventTypeTag.text = items[position].type

        if (items[position].isOnSale) {
            holder.textViewSaleTag.visibility = View.VISIBLE
        } else {
            holder.textViewSaleTag.visibility = View.GONE
        }

        items[position].image.let {
            Glide.with(context)
                .asDrawable().dontAnimate()
                .load(context.getDrawable(R.drawable.splash_img))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CenterCrop(), RoundedCorners(8)).fitCenter()
                .into(holder.imageViewEventPic)
        }

        holder.textViewEventName.text = items[position].name
        holder.textViewEventDescription.text = items[position].description
        holder.textViewEventPrice.text = "$${items[position].price.toString()}"
        holder.textViewAddress.text = items[position].address
        holder.textViewEventDate.text = items[position].date

        if (items[position].isFavorited) {
            (holder.imageViewAddToFav.setImageDrawable(context?.getDrawable(R.drawable.ic_fav_active)))
        } else {
            (holder.imageViewAddToFav.setImageDrawable(context?.getDrawable(R.drawable.ic_fav_deactivated)))
        }

        holder.eventContainer.setOnClickListener {
            actionToEventDetails.invoke(items[position])
        }
        holder.imageViewAddToFav.setOnClickListener {
            items[position].isFavorited = !items[position].isFavorited
            items[position].position = position

            actionAddToFav.invoke(items[position])
        }
    }


    class EventsListAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageViewAddToFav: ImageView = itemView.imageViewAddToFav
        val textViewEventTypeTag: TextView = itemView.textViewEventTypeTag
        val textViewSaleTag: TextView = itemView.textViewSaleTag
        val imageViewEventPic: ImageView = itemView.imageViewEventPic
        val eventContainer: ConstraintLayout = itemView.eventContainer
        val textViewEventName: TextView = itemView.textViewEventName
        val textViewEventDescription: TextView = itemView.textViewEventDescription
        val textViewEventPrice: TextView = itemView.textViewEventPrice
        val textViewAddress: TextView = itemView.textViewAddress
        val textViewEventDate: TextView = itemView.textViewEventDate

        public fun chan() {

        }

    }
}