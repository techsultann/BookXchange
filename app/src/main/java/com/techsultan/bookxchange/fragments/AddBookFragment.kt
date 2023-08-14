package com.techsultan.bookxchange.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.techsultan.bookxchange.R
import com.techsultan.bookxchange.databinding.FragmentAddBookBinding
import com.techsultan.bookxchange.model.Book
import java.util.UUID


class AddBookFragment : Fragment() {
    private var _binding: FragmentAddBookBinding? = null
    private val binding get() = _binding!!

    private val fireStore = Firebase.firestore
    private val storageRef = Firebase.storage.reference

    private var uri: Uri? = null

    //This launches the image picker activity
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()) {
        binding.bookImageView.setImageURI(it)
        if (it != null) {
            uri = it
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sellBtn = binding.btnSell
        val buyBtn = binding.btnBuy
        val swapBtn = binding.btvSwap

        binding.btnBuy.setOnClickListener { handleButtonClick(buyBtn) }
        binding.btvSwap.setOnClickListener { handleButtonClick(swapBtn) }
        binding.btnSell.setOnClickListener { handleButtonClick(sellBtn) }

        binding.bookImageView.setOnClickListener {
            openFileChooser()
        }

        binding.btnUpload.setOnClickListener {
            uploadBook()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun openFileChooser() {
        pickImageLauncher.launch("image/*")

    }

    // When a button is clicked, it deactivates the other buttons
    private fun deactivateButtons(activeButton: Button) {
        val sellBtn = binding.btnSell
        val buyBtn = binding.btnBuy
        val swapBtn = binding.btvSwap


        val buttons = listOf(sellBtn, buyBtn, swapBtn)

        for (button in buttons) {
            button.isEnabled = (button == activeButton)

        }
    }
        // Function to activate all buttons
        private fun activateButtons() {
            val sellBtn = binding.btnSell
            val buyBtn = binding.btnBuy
            val swapBtn = binding.btvSwap
            val buttons = listOf(sellBtn, buyBtn, swapBtn)

            for (button in buttons) {
                button.isEnabled = true

            }
        }


    // Generate a unique ID for the book
    private fun generateUniqueBookId(): String {
        // You can use any unique identifier generation mechanism here, such as UUID
        return UUID.randomUUID().toString()
    }


    // Function to handle button clicks
    private var selectedButton: Button? = null
    //private val price = binding.etPrice.text.toString
    private fun handleButtonClick(button: Button) {
        val actionText = binding.tvWantTo
        if (selectedButton == button) {
            // The selected button is clicked again, reactivate all buttons
            activateButtons()
            selectedButton = null
            // Resets the action text view
            actionText.text = ""
        } else {
            // Deactivate other buttons
            deactivateButtons(button)
            selectedButton = button

            // Updates the textview based on other buttons
            when (button) {
                binding.btnSell -> {
                    val price = binding.etBookPrice.text.toString()
                    if (price.isNotEmpty()) {
                        actionText.text = getString(R.string.wants_to_sell_with_price, price)
                    } else {
                        actionText.text = getString(R.string.action_sell)
                    }
                }
                binding.btnBuy -> actionText.text = getString(R.string.action_buy)
                binding.btvSwap -> actionText.text = getString(R.string.action_swap)
                else -> actionText.text = ""
            }
        }
    }

    private fun uploadBook(){



        val bookId = generateUniqueBookId()
        val bookName = binding.etBookName.text.toString()
        val bookDescription = binding.etDescription.text.toString()
        val bookAuthor = binding.etAuthorName.text.toString()
        val bookPages = binding.etNoOfPages.text.toString()
        val category = selectedButton?.text.toString()
        val actionText = binding.tvWantTo.text.toString()


        val bookImageName = "book_image${System.currentTimeMillis()}"

        if (selectedButton != null) {

            uri.let { imageUri ->

                if (imageUri != null) {
                    // Store image to Firebase storage
                    storageRef.child("Book Cover Images")
                        .child(bookImageName)
                        .putFile(imageUri)
                        // Download the url of the image and convert to string
                        .addOnSuccessListener { task->
                            task.metadata!!.reference!!.downloadUrl
                                .addOnSuccessListener { url ->
                                    val imageUrl = url.toString()

                                    // Checks for the current user and upload the book to firebase fire store
                                    val currentUser = Firebase.auth.currentUser
                                    val userId = currentUser?.uid
                                    val userName = currentUser?.displayName
                                    val userProfileImage = currentUser?.photoUrl.toString()

                                    if (userId != null) {

                                        val bookData = Book(
                                            bookId,
                                            bookName,
                                            bookDescription,
                                            bookAuthor,
                                            imageUrl,
                                            userName,
                                            bookPages,
                                            category,
                                            actionText,
                                            userProfileImage
                                        )

                                        // This save the book to firebase firestore
                                        fireStore.collection("users").document(userId).collection("books")
                                            .add(bookData)
                                            .addOnSuccessListener {
                                                Toast.makeText(context, "book saved successfully", Toast.LENGTH_SHORT).show()

                                                findNavController().navigate(R.id.action_addBookFragment_to_homeFragment)
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()

                                                return@addOnFailureListener

                                            }
                                    }
                                }
                        }
                }
            }

        }









    }

}