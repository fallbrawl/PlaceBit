package com.example.placebit.ui.events.eventScreen


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.placebit.R
import kotlinx.android.synthetic.main.item_qr_fragment.*

const val ARGUMENT_PAGE_NUMBER = "arg_page_number"
class QrCodeDetailFragment:Fragment() {

    var pageNumber = 0

    fun newInstance(page: Int): QrCodeDetailFragment? {
        val pageFragment = QrCodeDetailFragment()
        val arguments = Bundle()
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
        pageFragment.arguments = arguments
        return pageFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt(ARGUMENT_PAGE_NUMBER)?.let {
            pageNumber = it
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewQrNumber.text = "Page $pageNumber"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qr_details, container,false)
    }
}