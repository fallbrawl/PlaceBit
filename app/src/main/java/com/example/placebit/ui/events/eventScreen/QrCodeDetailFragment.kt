package com.example.placebit.ui.events.eventScreen


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.placebit.R
import com.example.placebit.data.model.QrCodeModel
import kotlinx.android.synthetic.main.fragment_qr_code_detail.*

const val QR = "arg_page_number"

class QrCodeDetailFragment : Fragment() {

    lateinit var qr:QrCodeModel

    companion object {
        fun newInstance(qr: QrCodeModel): QrCodeDetailFragment {
            val pageFragment = QrCodeDetailFragment()
            val arguments = Bundle()
            arguments.putSerializable(QR, qr)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getSerializable(QR)?.let {
            qr = it as QrCodeModel
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewQrNumber.text = "Ticket ${qr.id}"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qr_pager, container, false)
    }
}