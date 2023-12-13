package com.techsultan.bookxchange.fragments.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.techsultan.bookxchange.databinding.FragmentImageModalBottomSheetBinding


class ImageModalBottomSheet : BottomSheetDialogFragment() {

    interface ModalBottomSheetListener {
        fun onCameraOptionSelected()
        fun onImageUploadOptionSelected()
    }

    private var listener : ModalBottomSheetListener? = null
    companion object {
        const val TAG = "ModalBottomSheet"
    }
    private var _binding: FragmentImageModalBottomSheetBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentImageModalBottomSheetBinding.inflate(inflater, container, false)

        binding.imageGallery.setOnClickListener {
            (requireParentFragment() as SignupFragment).onImageUploadOptionSelected()
            //listener?.onImageUploadOptionSelected()
            dismiss()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.imageCamera.setOnClickListener {
            dismiss()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}