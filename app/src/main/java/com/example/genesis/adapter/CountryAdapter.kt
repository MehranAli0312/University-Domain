package com.example.genesis.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.genesis.databinding.CountryNamesItemBinding
import com.example.genesis.models.UniversityModelItem


// Adapter class for a RecyclerView that displays a list of country names
class CountryAdapter(
    private val currentList: List<String>, // List of country names to display
    private val clickOnItem: (item: String) -> Unit // Click handler for when an item is clicked
) : RecyclerView.Adapter<ViewHolder>() {

    // Inflates the item view and returns a ViewHolder for it
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        CountryViewHolder(
            CountryNamesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    // Binds the data (country name) to the item view (ViewHolder)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with((holder as CountryViewHolder).binding) {
            holder.bind(currentList[position]) // Binds the country name at the given position
        }
    }

    // Returns the total number of items in the list
    override fun getItemCount(): Int = currentList.size

    // ViewHolder class for the country name item view
    inner class CountryViewHolder(val binding: CountryNamesItemBinding) : ViewHolder(binding.root) {
        // Binds the country name to the TextView and sets the click listener
        @SuppressLint("SetTextI18n")
        fun bind(item: String) {
            binding.apply {
                countryName.text = item // Sets the country name text
                root.setOnClickListener {
                    clickOnItem(item) // Calls the click handler with the clicked item
                }
            }
        }
    }
}
