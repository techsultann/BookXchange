package com.techsultan.bookxchange.fragments.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.R
import com.techsultan.bookxchange.databinding.FragmentSignupBinding


class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.signupBtn.setOnClickListener {
            signUpUser()
        }

        binding.loginTv.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
    }

    private fun signUpUser() {

        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() ) {

            Snackbar.make(requireView(), "All fields are required", Snackbar.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->

                if (task.isSuccessful) {
                    val currentUser = Firebase.auth.currentUser

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    currentUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateProfile ->
                            if (updateProfile.isSuccessful)

                                findNavController().navigate(R.id.action_signupFragment_to_loginFragment , null)
                        }

                } else {

                    Toast.makeText(context, "Signup failed", Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener

                }
            }


    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}