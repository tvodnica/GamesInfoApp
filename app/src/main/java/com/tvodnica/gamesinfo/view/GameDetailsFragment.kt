package com.tvodnica.gamesinfo.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.tvodnica.gamesinfo.R
import com.tvodnica.gamesinfo.api.RawgClient
import com.tvodnica.gamesinfo.api.apimodels.GameApi
import com.tvodnica.gamesinfo.databinding.FragmentGameDetailsBinding
import com.tvodnica.gamesinfo.helpers.hasInternetAccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameDetailsFragment : Fragment() {

    private lateinit var binding: FragmentGameDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameDetailsBinding.inflate(layoutInflater, container, false)
        loadData()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.btnGoBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadData() {

        if (!requireContext().hasInternetAccess()) {
            showNoInternetDialog()
            return
        }

        val gameId = arguments?.getInt("itemId")
        val request = RawgClient().rawgApi.getGameById(gameId)

        request.enqueue(object : Callback<GameApi> {

            override fun onResponse(call: Call<GameApi>, response: Response<GameApi>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        displayGameInfo(it)
                    }
                } else {
                    showErrorDialog()
                }
            }

            override fun onFailure(call: Call<GameApi>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
                showErrorDialog()
            }
        })
    }

    private fun displayGameInfo(it: GameApi) {

        var genres = ""
        var developers = ""
        var publishers = ""
        var platforms = ""
        var tags = ""
        var stores = ""

        it.genres.forEach { genres = genres + it.name + ", " }
        it.developers.forEach { developers = developers + it.name + ", " }
        it.publishers.forEach { publishers = publishers + it.name + ", " }
        it.platforms.forEach { platforms = platforms + it.platformApi?.name + ", " }
        it.tags.forEach { tags = tags + it.name + ", " }
        it.stores.forEach { stores = stores + it.storeApi?.name + ", " }

        binding.tvGameName.text = it.name
        binding.tvReleaseDate.text = it.released
        binding.tvMetacritic.text = it.metacritic.toString()
        binding.tvAgeRating.text = it.esrbRatingApi?.name ?: getString(R.string.n_a)
        binding.tvGenres.text = genres.removeSuffix(", ")
        binding.tvDevelopers.text = developers.removeSuffix(", ")
        binding.tvPublishers.text = publishers.removeSuffix(", ")
        binding.tvPlatforms.text = platforms.removeSuffix(", ")
        binding.tvTags.text = tags.removeSuffix(", ")
        binding.tvStores.text = stores.removeSuffix(", ")

        Picasso.get()
            .load(it.backgroundImage)
            .error(R.mipmap.ic_launcher)
            .resize(900, 500)
            .centerInside().into(binding.ivImage)

        binding.btnReadDescription.setOnClickListener { v ->
            AlertDialog.Builder(requireContext()).apply {
                setTitle(getString(R.string.description))
                setMessage(it.descriptionRaw)
                setPositiveButton(getString(R.string.close), null)
                show()
            }
        }
        binding.btnCheckRatings.setOnClickListener { v ->

            var ratingMessage = "Average rating: ${it.rating}\n\n"

            it.ratings.forEach { rating ->
                ratingMessage =
                    ratingMessage + rating.title?.replaceFirstChar(Char::titlecase) + ": " + rating.percent + "%\n"
            }

            AlertDialog.Builder(requireContext()).apply {
                setTitle(getString(R.string.ratings))
                setMessage(ratingMessage)
                setPositiveButton(getString(R.string.close), null)
                show()
            }
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