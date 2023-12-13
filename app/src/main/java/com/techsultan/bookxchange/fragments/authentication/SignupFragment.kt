package com.techsultan.bookxchange.fragments.authentication

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.techsultan.bookxchange.R
import com.techsultan.bookxchange.databinding.FragmentSignupBinding
import com.techsultan.bookxchange.viewmodel.AuthViewModel


class SignupFragment : Fragment(), ImageModalBottomSheet.ModalBottomSheetListener {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val storageRef = Firebase.storage.reference
    private lateinit var authViewModel: AuthViewModel

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

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        auth = Firebase.auth
        binding.progressBar.visibility = View.GONE



        binding.profileImage.setOnClickListener {
            val modalBottomSheet = ImageModalBottomSheet()
            modalBottomSheet.show(childFragmentManager, ImageModalBottomSheet.TAG)
        }

        binding.signupBtn.setOnClickListener {

            val profileImageName = "profile_image${System.currentTimeMillis()}"
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()


                if (password == confirmPassword) {

                    if (isStrongPassword(password)) {
                        binding.progressBar.visibility = View.VISIBLE
                        authViewModel.signUpWithEmailAndPassword(
                            email,
                            password,
                            uri,
                            profileImageName,
                            lastName
                        )
                    } else {
                        Toast.makeText(
                            context,
                            "Your password must be at least 8 characters \n" +
                                    " and must have a number and an uppercase letter",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(context, "Password mismatch", Toast.LENGTH_SHORT).show()
                }

                authViewModel.signupResult.observe(viewLifecycleOwner) {  isSuccess ->
                    if (isSuccess) {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            authViewModel.saveFirstNameLastNameToFirestore(
                                userId,
                                firstName,
                                lastName
                            )
                        }
                        findNavController().navigate(R.id.action_signup_dest_to_login_dest)
                    } else {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "Signup Failed", Toast.LENGTH_SHORT).show()
                    }

            }

            binding.loginTv.setOnClickListener {
                findNavController().navigate(R.id.action_signup_dest_to_login_dest)
            }

            binding.profileImage.setOnClickListener {
                openFileChooser()
            }
        }

    }

    private fun openFileChooser() {
        pickImageLauncher.launch("image/*")

    }

    private fun isStrongPassword(password: String): Boolean {
        return password.length >= 8 && // Example: At least 8 characters
                password.any { it.isDigit() } && // Example: At least one digit
                password.any { it.isUpperCase() } // Example: At least one uppercase letter
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCameraOptionSelected() {
        TODO("Not yet implemented")
    }

    override fun onImageUploadOptionSelected() {
        openFileChooser()
    }
}