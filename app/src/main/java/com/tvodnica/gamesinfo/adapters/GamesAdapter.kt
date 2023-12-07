package com.tvodnica.gamesinfo.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tvodnica.gamesinfo.R
import com.tvodnica.gamesinfo.api.apimodels.GameApi

class GamesAdapter(
    private val items: MutableList<GameApi>
) : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]

        val tvGameName = holder.itemView.findViewById<TextView>(R.id.tv_gameName)
        val ivGameImage = holder.itemView.findViewById<ImageView>(R.id.iv_gameImage)

        tvGameName.text = item.name

        Picasso
            .get()
            .load(item.backgroundImage)
            .error(R.mipmap.ic_launcher)
            .resize(400, 300)
            .onlyScaleDown()
            .centerCrop()
            .into(ivGameImage)

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            item.id?.let { id -> bundle.putInt("itemId", id) }
            findNavController(it).navigate(R.id.action_gamesList_to_gameDetails, bundle)
        }
    }

    override fun getItemCount() = items.size
}
