package com.example.genesis.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.genesis.databinding.UniversityItemBinding
import com.example.genesis.models.UniversityModelItem
import com.example.genesis.utils.hide
import com.example.genesis.utils.show


class UniversityAdapter(private val clickOnItem: (item: UniversityModelItem) -> Unit) :
    RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>() {

    // Creates and inflates the ViewHolder for the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder =
        UniversityViewHolder(
            UniversityItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    // Binds data to the ViewHolder
    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    // Returns the size of the list
    override fun getItemCount(): Int = differ.currentList.size

    // Inner ViewHolder class to hold the view for each item
    inner class UniversityViewHolder(val binding: UniversityItemBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: UniversityModelItem) {
            binding.apply {
                // Set the text views with the data from the item
                universityName.text = "University Name: ${item.universityName}"
                universityDomains.text = "Domains: ${item.universityDomains[0]}"
                countryCode.text = "Country Code: ${item.countryCode}"
                webPages.text = "Web Sites: ${item.webPages[0]}"
                stateProvince.text = "State / Province: ${item.stateProvince}"
                countryName.text = "Country: ${item.countryName}"

                // Handle the expansion state of the item
                if (item.expand) {
                    toggleView.text = "-"
                    bottomContainer.show()
                } else {
                    toggleView.text = "+"
                    bottomContainer.hide()
                }

                // Toggle the expansion state on click
                toggleView.setOnClickListener {
                    item.expand = !item.expand
                    notifyItemChanged(adapterPosition)
                }

                // Handle item click to trigger the passed lambda function
                root.setOnClickListener {
                    clickOnItem(item)
                }
            }
        }
    }

    // Callback for calculating the diff between two non-null items in a list
    private val differCallback = object : DiffUtil.ItemCallback<UniversityModelItem>() {
        override fun areItemsTheSame(
            oldItem: UniversityModelItem, newItem: UniversityModelItem
        ): Boolean {
            // Compare the unique identifier of the items
            return oldItem.universityName == newItem.universityName
        }

        override fun areContentsTheSame(
            oldItem: UniversityModelItem, newItem: UniversityModelItem
        ): Boolean {
            // Compare the content of the items
            return oldItem == newItem
        }
    }

    // Differ to handle list changes asynchronously
    val differ = AsyncListDiffer(this, differCallback)
}
