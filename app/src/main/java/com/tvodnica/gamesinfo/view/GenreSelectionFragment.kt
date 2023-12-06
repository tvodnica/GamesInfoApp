package com.tvodnica.gamesinfo.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tvodnica.gamesinfo.R
import com.tvodnica.gamesinfo.adapters.GenreSelectionAdapter
import com.tvodnica.gamesinfo.api.apimodels.GenreResultApi
import com.tvodnica.gamesinfo.api.RawgClient
import com.tvodnica.gamesinfo.databinding.FragmentGenreSelectionBinding
import com.tvodnica.gamesinfo.model.Genre
import com.tvodnica.gamesinfo.helpers.saveSelectedGenresToFile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            findNavController().navigate(R.id.action_genreSelection_to_gamesList)
        }
    }

    private fun loadItems() {
        val items = mutableListOf<Genre>()
        val request = RawgClient().rawgApi.getGenres()

        request.enqueue(object : Callback<GenreResultApi> {
            override fun onResponse(
                call: Call<GenreResultApi>,
                response: Response<GenreResultApi>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.results.forEach { apiGerne ->
                            items.add(
                                Genre(apiGerne.id, apiGerne.name, apiGerne.slug, apiGerne.gamesCount, apiGerne.image)
                            )
                        }
                        binding.rvGenres.layoutManager = LinearLayoutManager(context)
                        binding.rvGenres.adapter = GenreSelectionAdapter(requireContext(), items, selectedGenres)
                    }
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<GenreResultApi>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }
        })

    }
}
