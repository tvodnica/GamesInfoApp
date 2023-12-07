package com.tvodnica.gamesinfo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tvodnica.gamesinfo.R
import com.tvodnica.gamesinfo.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)
        setupListeners()
        return binding.root
    }
    private fun setupListeners() {
        binding.btnGetStarted.setOnClickListener {
             findNavController().navigate(R.id.action_welcome_to_genreSelection)
        }
    }

}