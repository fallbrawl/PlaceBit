package com.example.placebit.ui.events.eventScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.placebit.R
import com.example.placebit.data.model.QrCodeModel
import kotlinx.android.synthetic.main.item_qr_code.view.*

class QrCodesAdapter (var context: Context) :
    RecyclerView.Adapter<QrCodesAdapter.QrCodesAdapterViewHolder>() {
    var items = ArrayList<QrCodeModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var actionToEventDetails: (referral: QrCodeModel) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QrCodesAdapterViewHolder {
        return QrCodesAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_qr_code,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: QrCodesAdapterViewHolder, position: Int) {
        holder.ticketNumber.text = position.toString()

        items[position].qrCode.let {
            Glide.with(context)
                .asDrawable().dontAnimate()
                .load(context.getDrawable(R.drawable.ic_qr))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CenterCrop(), RoundedCorners(10)).fitCenter()
                .into(holder.qrCode)
        }
    }

    class QrCodesAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val qrCode: ImageView = itemView.imageViewQrCode
        val ticketNumber: TextView = itemView.textViewNumber

    }
}