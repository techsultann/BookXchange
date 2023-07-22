 package com.techsultan.bookxchange.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.techsultan.bookxchange.R
import com.techsultan.bookxchange.databinding.FragmentHomeBinding
import com.techsultan.bookxchange.databinding.FragmentProfileBinding


 class ProfileFragment : Fragment(), MenuProvider {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
     private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

         val menuHost: MenuHost = requireActivity()
         menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

         auth = Firebase.auth

         if (auth.currentUser != null) {

             auth.currentUser?.let {

                binding.tvPersonName.text = it.displayName
                 Glide.with(requireContext())
                     .load(it.photoUrl)
                     .into(binding.profileImage)

             }
         }
     }

     fun signOutUser() {

         auth.signOut()
         findNavController().navigate(R.id.action_profile_dest_to_authenticationNav)
     }



     override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
         menuInflater.inflate(R.menu.profile_meu, menu)
     }

     override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
         when(menuItem.itemId) {
             R.id.profileMenu -> {
                 signOutUser()
             }
         }
         return true
     }


     override fun onDestroy() {
         super.onDestroy()
         _binding = null
     }

}