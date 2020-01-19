package com.example.placebit.ui.events.eventScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.placebit.data.model.QrCodeModel
import kotlinx.android.synthetic.main.fragment_qr_pager.*


class QrCodesPagerFragment : Fragment() {

    var pager: ViewPager? = null
    var pagerAdapter: PagerAdapter? = null
    var qrs: ArrayList<QrCodeModel> = arrayListOf()
    var position: String = ""

    companion object {
        const val QRS = "qrs"
        const val POSITION = "pos"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.example.placebit.R.layout.fragment_qr_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey(QRS)) {
                qrs = it.getSerializable(QRS) as ArrayList<QrCodeModel>
            }
            if (it.containsKey(POSITION)) {
                position = it.getString(POSITION)!!
            }
        }

        pagerAdapter = MyFragmentPagerAdapter(fragmentManager)
        qrPager.adapter = pagerAdapter
        qrPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrolled(
                position: Int, positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        pager?.currentItem = position.toInt()
    }

    inner class MyFragmentPagerAdapter(fm: FragmentManager?) :
        FragmentPagerAdapter(fm!!) {
        override fun getItem(position: Int): QrCodeDetailFragment {
            return QrCodeDetailFragment.newInstance(qrs[position])
        }

        override fun getCount(): Int {
            return qrs.size
        }
    }
}