package com.techsultan.bookxchange.fragments.authentication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.R
import com.techsultan.bookxchange.databinding.FragmentLoginBinding
import com.techsultan.bookxchange.databinding.ResetPasswordDialogBinding
import com.techsultan.bookxchange.viewmodel.AuthViewModel


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var authViewModel: AuthViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        auth = Firebase.auth
        binding.progressBar.visibility = View.GONE

        binding.tvForgotPassword.setOnClickListener {

            forgotPasswordDialog()
        }

        binding.loginBtn.setOnClickListener {
            loginUser()
        }

        binding.signupTv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

        // Check if user is already logged in


    private fun loginUser() {


        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isEmpty() || password.isEmpty() ) {

            Toast.makeText(activity, "All fields are required", Toast.LENGTH_LONG).show()
            return
        }
        binding.progressBar.visibility = View.VISIBLE
        authViewModel.loginUser(email, password)

        authViewModel.signInResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                findNavController().navigate(R.id.action_global_home_dest2)
            } else {
                Toast.makeText(context, "Email or password is incorrect", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }
        }


    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        if (currentUser != null) {

            findNavController().navigate(R.id.action_global_home_dest2)
        }
    }

    private fun forgotPasswordDialog() {

        val builder = AlertDialog.Builder(requireContext())
        val alertDialogViewBinding = ResetPasswordDialogBinding.inflate(layoutInflater)
        builder.setView(alertDialogViewBinding.root)

        val emailAddress = alertDialogViewBinding.etEmail.text.toString()
        val dialog = builder.create()

        alertDialogViewBinding.resetBtn.setOnClickListener {

            auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email sent.")
                        Toast.makeText(context, "Email sent", Toast.LENGTH_SHORT).show()
                    }
                }
            dialog.dismiss()
        }

        alertDialogViewBinding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}