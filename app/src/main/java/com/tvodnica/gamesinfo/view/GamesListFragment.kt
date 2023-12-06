package com.tvodnica.gamesinfo.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tvodnica.gamesinfo.R
import com.tvodnica.gamesinfo.adapters.GamesAdapter
import com.tvodnica.gamesinfo.api.RawgClient
import com.tvodnica.gamesinfo.api.apimodels.GameResultApi
import com.tvodnica.gamesinfo.databinding.FragmentGamesListBinding
import com.tvodnica.gamesinfo.helpers.getSelectedGenresIds
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GamesListFragment : Fragment() {
    private lateinit var binding: FragmentGamesListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGamesListBinding.inflate(layoutInflater, container, false)
        loadItems()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_gamesList_to_settings)
        }
    }

    private fun loadItems() {
        var selectedGenresIds = ""
        getSelectedGenresIds(requireContext()).forEach {
            selectedGenresIds = "$selectedGenresIds$it,"
        }
        val request = RawgClient().rawgApi.getGamesByGenres(selectedGenresIds)

        request.enqueue(object : Callback<GameResultApi> {
            override fun onResponse(
                call: Call<GameResultApi>,
                response: Response<GameResultApi>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        binding.rvGames.layoutManager = GridLayoutManager(context, 2)
                        binding.rvGames.adapter = GamesAdapter(requireContext(), it.results)
                    }
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<GameResultApi>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }*/
    }
}
