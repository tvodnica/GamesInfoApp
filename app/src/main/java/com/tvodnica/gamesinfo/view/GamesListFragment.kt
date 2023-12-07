package com.tvodnica.gamesinfo.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tvodnica.gamesinfo.R
import com.tvodnica.gamesinfo.adapters.GamesAdapter
import com.tvodnica.gamesinfo.api.RawgClient
import com.tvodnica.gamesinfo.api.apimodels.GameApi
import com.tvodnica.gamesinfo.api.apimodels.GameResultApi
import com.tvodnica.gamesinfo.dao.DatabaseFactory
import com.tvodnica.gamesinfo.databinding.FragmentGamesListBinding
import com.tvodnica.gamesinfo.helpers.hasInternetAccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GamesListFragment : Fragment() {

    private lateinit var binding: FragmentGamesListBinding
    private val games = mutableListOf<GameApi>()
    private val gamesAdapter = GamesAdapter(games)
    private var isDataBeingLoaded = true
    private var pageQueryParam = 1
    private var selectedGenresIdsQueryParam = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGamesListBinding.inflate(layoutInflater, container, false)
        prepareToLoadItems()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_gamesList_to_settings)
        }
    }

    private fun prepareToLoadItems() {
        if (!requireContext().hasInternetAccess()) {
            showNoInternetDialog()
            return
        }
        loadQueryParams()
        setupRecyclerView()
        loadGameData()
    }

    private fun loadQueryParams() {
        pageQueryParam = 1
        selectedGenresIdsQueryParam = ""
        DatabaseFactory.getDatabase(requireContext()).getSelectedGenresIds().forEach {id ->
            selectedGenresIdsQueryParam = "$selectedGenresIdsQueryParam$id,"
        }
    }

    private fun setupRecyclerView() {
        games.clear()
        binding.rvGames.layoutManager = GridLayoutManager(context, 2)
        binding.rvGames.adapter = gamesAdapter

        binding.rvGames.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isDataBeingLoaded && totalItemCount <= (lastVisibleItem + 3)) {
                    isDataBeingLoaded = true
                    loadGameData()
                }
            }
        })

    }

    private fun loadGameData() {

        val request = RawgClient().rawgApi.getGamesByGenres(selectedGenresIdsQueryParam, pageQueryParam++)

        request.enqueue(object : Callback<GameResultApi> {
            override fun onResponse(call: Call<GameResultApi>, response: Response<GameResultApi>) {
                handleResponse(response, games)
            }

            override fun onFailure(call: Call<GameResultApi>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
                showErrorDialog()
                isDataBeingLoaded = false
            }
        })
    }

    private fun handleResponse(response: Response<GameResultApi>, games: MutableList<GameApi>) {
        if (response.isSuccessful) {
            response.body()?.let {
                val previousItemCount = games.size
                it.results.forEach(games::add)
                gamesAdapter.notifyItemRangeInserted(previousItemCount, games.size)
                binding.llLoading.visibility = View.GONE
                isDataBeingLoaded = false
            }
        } else {
            showErrorDialog()
            isDataBeingLoaded = false
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.error)
            setMessage(R.string.error_downloading_data_message)
            setPositiveButton(R.string.try_again) { _, _ -> prepareToLoadItems() }
            show()
        }
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.no_internet)
            setMessage(R.string.no_internet_message)
            setPositiveButton(R.string.try_again) { _, _ -> prepareToLoadItems() }
            show()
        }
    }
}
