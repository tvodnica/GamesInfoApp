package com.tvodnica.gamesinfo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tvodnica.gamesinfo.R
import com.tvodnica.gamesinfo.adapters.GenreSelectionAdapter
import com.tvodnica.gamesinfo.databinding.FragmentGenreSelectionBinding
import com.tvodnica.gamesinfo.model.Genre
import com.tvodnica.gamesinfo.helpers.saveSelectedGenresToFile

class GenreSelectionFragment : Fragment() {
    private lateinit var binding: FragmentGenreSelectionBinding
    private val selectedGenres = mutableSetOf<Genre>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenreSelectionBinding.inflate(layoutInflater, container, false)
        loadItems()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.btnFinish.setOnClickListener {
            if (selectedGenres.isEmpty()){
                Toast.makeText(requireContext(),
                    getString(R.string.select_at_least_one_genre), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            saveSelectedGenresToFile(requireContext(), selectedGenres)
            PreferenceManager.getDefaultSharedPreferences(requireContext()).edit().putBoolean("firstLaunchDone", true).apply()
        }
    }

    private fun loadItems() {
        val items = mutableListOf<Genre>()

        items.add(Genre(1, "Adventure", "Slug", 12, ""))
        items.add(Genre(2, "FPS", "Slug", 12, ""))
        items.add(Genre(3, "RPG", "Slug", 11, ""))

        binding.rvGenres.layoutManager = LinearLayoutManager(context)
        binding.rvGenres.adapter = GenreSelectionAdapter(requireContext(), items, selectedGenres)
    }
}