package com.tvodnica.gamesinfo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tvodnica.gamesinfo.R
import com.tvodnica.gamesinfo.model.Genre

class GenreSelectionAdapter(
    private val context: Context,
    private val items: MutableList<Genre>,
    private val selectedItems: MutableSet<Genre>
) : RecyclerView.Adapter<GenreSelectionAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]

        val tv_genreName = holder.itemView.findViewById<TextView>(R.id.tv_genreName)
        val iv_genreImage = holder.itemView.findViewById<ImageView>(R.id.iv_genreImage)
        val iv_genreSelected = holder.itemView.findViewById<ImageView>(R.id.iv_genreSelected)

        tv_genreName.text = item.name
        Picasso.get().load(item.image).resize(400,400) // Set the target width and height
            .onlyScaleDown().centerInside().into(iv_genreImage)

        holder.itemView.setOnClickListener {
            if (!selectedItems.contains(item)){
                selectedItems.add(item)
                iv_genreSelected.visibility = View.VISIBLE
            }
            else{
                selectedItems.remove(item)
                iv_genreSelected.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount() = items.size
}
