package com.example.placebit.ui.events.eventScreen

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.placebit.R
import com.example.placebit.data.model.QrCodeModel
import kotlinx.android.synthetic.main.item_buy_more.view.*
import kotlinx.android.synthetic.main.item_qr_code.view.*
import kotlinx.android.synthetic.main.item_qr_code.view.textViewNumber


const val BUY_BUTTON = 0
const val QR_CODE = 1

class QrCodesAdapter(var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = ArrayList<QrCodeModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var actionToQrCodeDetails: (referral: QrCodeModel) -> Unit = {}
    var actionBuyMoreDetails: () -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (viewType == BUY_BUTTON) {
            return QrCodesBuyMoreAdapterViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_buy_more,
                    parent,
                    false
                )
            )
        } else {
            return QrCodesItemsAdapterViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_qr_code,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            BUY_BUTTON
        } else QR_CODE

    }

    override fun getItemCount(): Int {
        return items.size
    }

//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            BUY_BUTTON -> {
                (holder as QrCodesBuyMoreAdapterViewHolder).constraintLayoutBuyMoreButton.setOnClickListener {
                    actionBuyMoreDetails.invoke()
                }
            }
            QR_CODE -> {

                (holder as QrCodesItemsAdapterViewHolder).qrContain.setOnClickListener {
                    actionToQrCodeDetails.invoke(items[position])
                }
                holder.ticketNumber.text = position.toString()

                items[position].qrCode.let {
                    Glide.with(context)
                        .asDrawable().dontAnimate()
                        .load(context.getDrawable(R.drawable.ic_qr))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .transform(CenterCrop(), RoundedCorners(10)).fitCenter()
                        .into(holder.qrCode)
                }
            }
        }
    }

    class QrCodesItemsAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val qrCode: ImageView = itemView.imageViewQrCode
        val ticketNumber: TextView = itemView.textViewNumber
        val qrContain: LinearLayout = itemView.qrContain

    }

    class QrCodesBuyMoreAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val constraintLayoutBuyMoreButton: ConstraintLayout = itemView.constraintLayoutBuyMoreButton

    }
}