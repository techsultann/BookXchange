package com.techsultan.bookxchange.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.R
import com.techsultan.bookxchange.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


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

        auth = Firebase.auth

        binding.loginBtn.setOnClickListener {
            loginUser()
        }

        binding.signupTv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

        // Check if user is already logged in
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        if (currentUser != null) {

            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    private fun loginUser() {


        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isEmpty() || password.isEmpty() ) {

            Toast.makeText(activity, "All fields are required", Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)

            .addOnCompleteListener(requireActivity()){ task ->

                if (task.isSuccessful) {
                    val user = auth.currentUser
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                } else{
                    Snackbar.make(requireView(), "Login Error", Snackbar.LENGTH_SHORT).show()
                    return@addOnCompleteListener

                }
            }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}