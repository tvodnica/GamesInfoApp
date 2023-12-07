package com.tvodnica.gamesinfo.adapters

import android.content.Context
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
    private val context: Context,
    private val items: List<GameApi>
) : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]

        val tv_gameName = holder.itemView.findViewById<TextView>(R.id.tv_gameName)
        val iv_gameImage = holder.itemView.findViewById<ImageView>(R.id.iv_gameImage)

        tv_gameName.text = item.name
        Picasso.get().load(item.backgroundImage).resize(400,300) // Set the target width and height
            .onlyScaleDown().centerCrop().into(iv_gameImage)

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("itemId", item.id!!)
            findNavController(it).navigate(R.id.action_gamesList_to_gameDetails, bundle)
        }
    }

    override fun getItemCount() = items.size
}
