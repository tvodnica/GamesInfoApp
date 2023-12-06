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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameDetailsFragment : Fragment() {

    private lateinit var binding: FragmentGameDetailsBinding
    private val gameId = 3498
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameDetailsBinding.inflate(layoutInflater, container, false)
        loadData()
        return binding.root
    }

    private fun setupListeners(gameApi: GameApi) {
        binding.btnGoBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnReadDescription.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle(getString(R.string.description))
                setMessage(gameApi.descriptionRaw)
                setPositiveButton(getString(R.string.close), null)
                show()
            }
        }
        binding.btnCheckSystemRequirements.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle(getString(R.string.systemRequirements))
                setMessage("")
                setPositiveButton(getString(R.string.close), null)
                show()
            }
        }
    }

    private fun loadData() {
        val request = RawgClient().rawgApi.getGameById(gameId)

        request.enqueue(object : Callback<GameApi> {
            override fun onResponse(
                call: Call<GameApi>,
                response: Response<GameApi>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {

                        var genres = ""
                        var developers = ""
                        var publishers = ""
                        var platforms = ""
                        var tags = ""

                        it.genres.forEach { genres = genres + it.name + ", " }
                        it.developers.forEach { developers = developers + it.name + ", " }
                        it.publishers.forEach { publishers = publishers + it.name + ", " }
                        it.platforms.forEach { platforms = platforms + it.platformApi?.name + ", " }
                        it.tags.forEach { tags = tags + it.name + ", " }

                        binding.tvGameName.text = it.name
                        binding.tvReleaseDate.text = it.released
                        binding.tvMetacritic.text = it.metacritic.toString()
                        binding.tvGenres.text = genres.removeSuffix(", ")
                        binding.tvDevelopers.text = developers.removeSuffix(", ")
                        binding.tvPublishers.text = publishers.removeSuffix(", ")
                        binding.tvPlatforms.text = platforms.removeSuffix(", ")
                        binding.tvTags.text = tags.removeSuffix(", ")

                        Picasso.get().load(it.backgroundImage).resize(900,500).centerInside().into(binding.ivImage)

                        setupListeners(it)
                    }
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<GameApi>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }
        })
    }

}