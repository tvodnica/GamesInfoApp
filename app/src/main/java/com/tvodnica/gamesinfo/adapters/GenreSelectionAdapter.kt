package com.tvodnica.gamesinfo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tvodnica.gamesinfo.R
import com.tvodnica.gamesinfo.api.apimodels.GenreApi

class GenreSelectionAdapter(
    private val items: List<GenreApi>,
    private val selectedItems: MutableSet<GenreApi>
) : RecyclerView.Adapter<GenreSelectionAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]

        val tvGenreName = holder.itemView.findViewById<TextView>(R.id.tv_genreName)
        val ivGenreImage = holder.itemView.findViewById<ImageView>(R.id.iv_genreImage)
        val ivGenreSelected = holder.itemView.findViewById<ImageView>(R.id.iv_genreSelected)

        tvGenreName.text = item.name

        Picasso.get()
            .load(item.image)
            .error(R.mipmap.ic_launcher)
            .resize(400, 400)
            .onlyScaleDown()
            .centerInside()
            .into(ivGenreImage)

        ivGenreSelected.visibility = if (selectedItems.contains(item)) View.VISIBLE else View.INVISIBLE

        holder.itemView.setOnClickListener {
            if (!selectedItems.contains(item)) {
                selectedItems.add(item)
                ivGenreSelected.visibility = View.VISIBLE
            } else {
                selectedItems.remove(item)
                ivGenreSelected.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount() = items.size
}
