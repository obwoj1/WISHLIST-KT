package com.example.wishit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val items = mutableListOf<WishlistItems>()
    private lateinit var adapter: WishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get references to all the views
        val itemNameEt = findViewById<EditText>(R.id.itemNameEt)
        val itemPriceEt = findViewById<EditText>(R.id.itemPriceEt)
        val itemUrlEt = findViewById<EditText>(R.id.itemUrlEt)
        val addButton = findViewById<Button>(R.id.addButton)
        val wishlistRv = findViewById<RecyclerView>(R.id.wishlistRv)

        // Initialize the adapter and set it to the RecyclerView
        adapter = WishlistAdapter(items)
        wishlistRv.adapter = adapter
        wishlistRv.layoutManager = LinearLayoutManager(this)

        // Set up the click listener for the 'Add' button
        addButton.setOnClickListener {
            // Get text from the EditText fields
            val name = itemNameEt.text.toString()
            val price = itemPriceEt.text.toString()
            val url = itemUrlEt.text.toString()

            // Check if all fields are filled before adding
            if (name.isNotEmpty() && price.isNotEmpty() && url.isNotEmpty()) {
                // Create a new WishlistItem object
                val newItem = WishlistItems(name, price, url)

                // Add the new item to the list
                items.add(newItem)

                // Notify the adapter that an item has been added
                adapter.notifyItemInserted(items.size - 1)

                // Clear the input fields
                itemNameEt.text.clear()
                itemPriceEt.text.clear()
                itemUrlEt.text.clear()
            }
        }
    }
}