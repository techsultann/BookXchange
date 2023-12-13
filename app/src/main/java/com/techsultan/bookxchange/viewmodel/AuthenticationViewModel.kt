package com.techsultan.bookxchange.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.techsultan.bookxchange.R

class AuthViewModel(
    app: Application
) : AndroidViewModel(app) {

    private lateinit var auth: FirebaseAuth
    private val storageRef = Firebase.storage.reference
    private val fireStore = Firebase.firestore
    val signInResult: MutableLiveData<Boolean> = MutableLiveData()
    val signupResult: MutableLiveData<Boolean> = MutableLiveData()
   // val sendEmailResult: MutableLiveData<Boolean> = MutableLiveData()


    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        profileUri: Uri?,
        profileImageName: String,
        lastName: String
    ) {
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser

                    profileUri?.let { uri ->
                        storageRef.child("Profile image")
                            .child(profileImageName)
                            .putFile(uri)
                            .addOnSuccessListener { uploadTask ->
                                uploadTask.metadata!!.reference!!.downloadUrl
                                    .addOnSuccessListener { url ->
                                        val profileUrl = url.toString()

                                        val profileUpdates = UserProfileChangeRequest.Builder()
                                            .setDisplayName(lastName)
                                            .setPhotoUri(Uri.parse(profileUrl))
                                            .build()

                                        currentUser?.updateProfile(profileUpdates)
                                        signupResult.postValue(true)
                                    }
                            }
                    }
                } else {
                    // Handle signup failure here
                    val exception = task.exception
                    Log.e("SignupError", "Signup failed: ${exception?.message}")
                    signupResult.postValue(false)
                }
            }
    }

    fun saveFirstNameLastNameToFirestore(userId: String, firstName: String, lastName: String) {

        val userDocRef = fireStore.collection("users").document(userId)
        val userData = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName
        )

        userDocRef.set(userData)
            .addOnSuccessListener {
                // Successfully saved data to Firestore
                return@addOnSuccessListener
            }
            .addOnFailureListener {
                // Handle failure to save data to Firestore
                Log.e("FirestoreError", "Firestore write failed: ${it.message}")
            }
    }


    fun loginUser(email: String, password: String) {

        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)

            .addOnCompleteListener{ task ->

                if (task.isSuccessful) {
                    signInResult.postValue(true)
                } else{
                    Log.e("LoginError", "Signup failed:")
                    signInResult.postValue(false)

                }
            }
    }

    fun sendAuthEmail() {
        auth = Firebase.auth
        auth.currentUser!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Successful", "Email sent")
                   // sendEmailResult.postValue(true)
                } else {
                    Log.e("Email verification failed", "Failed to verify email")
                  //  sendEmailResult.postValue(false)
                }
            }
    }

}