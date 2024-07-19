package com.example.genesis.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.genesis.databinding.CountryNamesItemBinding
import com.example.genesis.models.UniversityModelItem


class CountryAdapter(
    private val currentList: List<String>,
    private val clickOnItem: (item: String) -> Unit
) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        CountryViewHolder(
            CountryNamesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with((holder as CountryViewHolder).binding) {
            holder.bind(currentList[position])
        }
    }

    override fun getItemCount(): Int = currentList.size

    inner class CountryViewHolder(val binding: CountryNamesItemBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: String) {
            binding.apply {
                countryName.text = item
                root.setOnClickListener {
                    clickOnItem(item)
                }
            }
        }

    }
}