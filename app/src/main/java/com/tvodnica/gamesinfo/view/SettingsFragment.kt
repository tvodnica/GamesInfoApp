package com.tvodnica.gamesinfo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tvodnica.gamesinfo.R
import com.tvodnica.gamesinfo.databinding.FragmentGamesListBinding
import com.tvodnica.gamesinfo.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.btnGoBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}