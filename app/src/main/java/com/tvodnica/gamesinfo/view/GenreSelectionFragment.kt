package com.tvodnica.gamesinfo.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tvodnica.gamesinfo.R
import com.tvodnica.gamesinfo.adapters.GenreSelectionAdapter
import com.tvodnica.gamesinfo.api.apimodels.GenreResultApi
import com.tvodnica.gamesinfo.api.RawgClient
import com.tvodnica.gamesinfo.api.apimodels.GenreApi
import com.tvodnica.gamesinfo.dao.DatabaseFactory
import com.tvodnica.gamesinfo.databinding.FragmentGenreSelectionBinding
import com.tvodnica.gamesinfo.helpers.FIRST_LAUNCH_DONE
import com.tvodnica.gamesinfo.helpers.hasInternetAccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreSelectionFragment : Fragment() {

    private lateinit var binding: FragmentGenreSelectionBinding
    private val selectedGenres = mutableSetOf<GenreApi>()
    private var editing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGenreSelectionBinding.inflate(layoutInflater, container, false)
        editing = PreferenceManager.getDefaultSharedPreferences(requireContext()).getBoolean(FIRST_LAUNCH_DONE, false)
        setupUI()
        loadData()
        setupListeners()
        return binding.root
    }

    private fun setupUI() {
        if (!editing) {
            binding.llFirstTimeAddingGenres.visibility = View.VISIBLE
            binding.llEditingGenres.visibility = View.GONE
        } else {
            binding.llFirstTimeAddingGenres.visibility = View.GONE
            binding.llEditingGenres.visibility = View.VISIBLE
        }
    }

    private fun setupListeners() {
        binding.btnFinish.setOnClickListener {
            if (selectedGenres.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.select_at_least_one_genre), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            DatabaseFactory.getDatabase(requireContext()).saveSelectedGenresIds(selectedGenres)
            PreferenceManager.getDefaultSharedPreferences(requireContext()).edit().putBoolean(FIRST_LAUNCH_DONE, true).apply()
            if (editing){
                findNavController().navigateUp()
            }
            else{
                findNavController().popBackStack(R.id.gamesListFragment, false)
            }
        }
        binding.btnGoBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadData() {

        if (!requireContext().hasInternetAccess()) {
            showNoInternetDialog()
            return
        }

        val request = RawgClient().rawgApi.getGenres()
        request.enqueue(object : Callback<GenreResultApi> {
            override fun onResponse(call: Call<GenreResultApi>, response: Response<GenreResultApi>) {
                handleResponse(response)
            }
            override fun onFailure(call: Call<GenreResultApi>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
                showErrorDialog()
            }
        })

    }

    private fun handleResponse(response: Response<GenreResultApi>) {

        if (response.isSuccessful) {
            response.body()?.let { items ->
                val selectedGenresIds = DatabaseFactory.getDatabase(requireContext()).getSelectedGenresIds()
                items.results.forEach { genre ->
                    if (selectedGenresIds.contains(genre.id)) {
                        selectedGenres.add(genre)
                    }
                }
                binding.rvGenres.layoutManager = LinearLayoutManager(context)
                binding.rvGenres.adapter = GenreSelectionAdapter(items.results, selectedGenres)
                binding.llLoading.visibility = View.GONE
            }
        } else {
            showErrorDialog()
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.error)
            setMessage(R.string.error_downloading_data_message)
            setPositiveButton(R.string.try_again) { _, _ -> loadData() }
            show()
        }
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.no_internet)
            setMessage(R.string.no_internet_message)
            setPositiveButton(R.string.try_again) { _, _ -> loadData() }
            show()
        }
    }
}
