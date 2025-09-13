package com.example.wishit

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat // FIX 1: Add this import for ContextCompat

// FIX 2: Change WishlistItems to WishlistItem (singular)
class WishlistAdapter(private val items: MutableList<WishlistItems>) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTv: TextView = itemView.findViewById(R.id.itemNameTv)
        val itemPriceTv: TextView = itemView.findViewById(R.id.itemPriceTv)
        val itemUrlTv: TextView = itemView.findViewById(R.id.itemUrlTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // FIX 3: Change R.layout.wishes to R.layout.wishlist_item
        val wishlistItemView = inflater.inflate(R.layout.wishes, parent, false)
        return ViewHolder(wishlistItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val item = items[position]

        // Set item views based on views and data model
        holder.itemNameTv.text = item.name
        holder.itemPriceTv.text = item.price
        holder.itemUrlTv.text = item.url

        // Set the long click listener for deletion
        holder.itemView.setOnLongClickListener {
            // Get the position of the item that was long-pressed
            val currentPosition = holder.adapterPosition

            // Check if the position is valid
            if (currentPosition != RecyclerView.NO_POSITION) {
                // Remove the item from the list
                items.removeAt(currentPosition)
                // Notify the adapter that the item has been removed
                notifyItemRemoved(currentPosition)
            }
            // This is the crucial line: return true to consume the event
            true
        }

        // Set the regular click listener to open the URL
        holder.itemView.setOnClickListener {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                ContextCompat.startActivity(it.context, browserIntent, null)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(it.context, "Invalid URL for " + item.name, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}