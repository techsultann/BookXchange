package com.techsultan.bookxchange.fragments.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.techsultan.bookxchange.R
import com.techsultan.bookxchange.databinding.FragmentBottomSheetBinding
import com.techsultan.bookxchange.viewmodel.AuthViewModel

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        binding.emailPrompt.setOnClickListener {

            authViewModel.sendAuthEmail()
            findNavController().navigate(R.id.action_bottomSheetDialogFragment_to_login_dest)

            /*authViewModel.sendEmailResult.observe(viewLifecycleOwner) {

            }*/
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}