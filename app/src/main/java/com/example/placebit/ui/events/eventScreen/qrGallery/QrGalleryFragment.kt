package com.example.placebit.ui.events.eventScreen.qrGallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.placebit.R
import com.example.placebit.data.model.QrCodeModel
import com.example.placebit.databinding.FragmentQrGalleryBinding
import com.example.placebit.extensions.obtainViewModel
import com.example.placebit.ui.MainActivity
import com.example.placebit.util.StateEnum
import com.github.chrisbanes.photoview.PhotoView
import kotlinx.android.synthetic.main.fragment_qr_gallery.*
import java.util.*


class QrGalleryFragment : Fragment() {

    companion object {
        const val POSITION = "position"
        const val QRS = "qrs"
    }

    private lateinit var viewDataBinding: FragmentQrGalleryBinding
    private lateinit var currentModel: QrGalleryViewModel

    private fun getCurrentViewModel(): QrGalleryViewModel {
        return obtainViewModel(QrGalleryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentQrGalleryBinding.inflate(inflater, container, false).apply {
            currentModel = obtainViewModel(QrGalleryViewModel::class.java)
            viewModel = currentModel
            viewModel?.apply {
                state.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    when (it) {
                        StateEnum.COMPLETE -> {

                            val callback = object : OnBackPressedCallback(true /** true means that the callback is enabled */) {
                                override fun handleOnBackPressed() {
                                    // Show your dialog and handle navigation
                                    Navigation.findNavController(view!!).popBackStack()
                                }
                            }

                            // note that you could enable/disable the callback here as well by setting callback.isEnabled = true/false

                            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
                        }
                    }
                })
            }
        }
        // Inflate the layout for this fragment
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.supportActionBar?.hide()
        arguments?.getInt(POSITION)?.let { position ->
            arguments?.getSerializable(QRS)?.let { qrs ->
                context?.let { context ->
                    view_pager2.adapter =
                        SamplePagerAdapter(qrs as ArrayList<QrCodeModel>, context)
                    view_pager2.currentItem = position - 2
                }
            }
        }
    }

    internal class SamplePagerAdapter(
        private var images: ArrayList<QrCodeModel>,
        private var context: Context
    ) : PagerAdapter() {

        override fun getCount(): Int {
            return images.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): View {
            val photoView = PhotoView(container.context)
            images[position].qrCode.let {
                Glide.with(context)
                    .load("http://www.rasteredge.com/how-to/csharp-imaging/barcode-generating-qrcode/lib/QRCode.png")
                    .into(photoView)
            }
            // Now just add PhotoView to ViewPager and return it
            container.addView(
                photoView,
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
            return photoView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }
}