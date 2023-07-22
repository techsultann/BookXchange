package com.techsultan.bookxchange.fragments.signup

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.techsultan.bookxchange.R
import com.techsultan.bookxchange.databinding.FragmentSignupBinding


class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val storageRef = Firebase.storage.reference

    private var uri: Uri? = null

    //This launches the image picker activity
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()) {
        binding.profileImage.setImageURI(it)
        if (it != null) {
            uri = it
        }

    }


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

        binding.profileImage.setOnClickListener {
            openFileChooser()
        }
    }

    private fun openFileChooser() {
        pickImageLauncher.launch("image/*")

    }

    private fun uploadProfileImage() {


    }

    private fun signUpUser() {

        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        val profileImageName = "profile_image${System.currentTimeMillis()}"


        if (name.isEmpty() || email.isEmpty() || password.isEmpty() ) {

            Snackbar.make(requireView(), "All fields are required", Snackbar.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->

                if (task.isSuccessful) {

                    val currentUser = Firebase.auth.currentUser
                    uri.let { profileUri ->

                        if (profileUri != null)
                            storageRef.child("Profile image")
                                .child(profileImageName)
                                .putFile(profileUri)
                                .addOnSuccessListener { task ->
                                    task.metadata!!.reference!!.downloadUrl
                                        .addOnSuccessListener { url ->
                                            val profileUrl = url.toString()

                                            val profileUpdates = UserProfileChangeRequest.Builder()
                                                .setDisplayName(name)
                                                .setPhotoUri(Uri.parse(profileUrl))
                                                .build()

                                            currentUser?.updateProfile(profileUpdates)

                                                ?.addOnCompleteListener { updateProfile ->
                                                    if (updateProfile.isSuccessful)

                                                        findNavController().navigate(R.id.action_signupFragment_to_loginFragment , null)
                                                }
                                        }
                                }
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