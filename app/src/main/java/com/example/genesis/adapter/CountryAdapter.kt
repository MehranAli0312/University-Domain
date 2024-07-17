package com.example.genesis.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.genesis.databinding.CountryItemBinding
import com.example.genesis.models.UniversityModelItem
import com.example.genesis.utils.hide
import com.example.genesis.utils.show


class CountryAdapter(private val clickOnItem: (item: UniversityModelItem) -> Unit) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        CountryViewHolder(
            CountryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with((holder as CountryViewHolder).binding) {
            holder.bind(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class CountryViewHolder(val binding: CountryItemBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: UniversityModelItem) {
            binding.apply {
                universityName.text = "University Name: ${item.universityName}"
                universityDomains.text = "Domains: ${item.universityDomains[0]}"
                countryCode.text = "Country Code: ${item.countryCode}"
                webPages.text = "Web Sites: ${item.webPages[0]}"


                stateProvince.text = "State / Province: ${item.stateProvince}"
                countryName.text = "Country: ${item.countryName}"

                if (item.expand) {
                    toggleView.text = "-"
                    bottomContainer.show()
                } else {
                    toggleView.text = "+"
                    bottomContainer.hide()
                }

                toggleView.setOnClickListener {
                    item.expand = !item.expand
                    notifyItemChanged(adapterPosition)
                }
                root.setOnClickListener {
                    clickOnItem(item)
                }
            }
        }

    }


    private val differCallback = object : DiffUtil.ItemCallback<UniversityModelItem>() {
        override fun areItemsTheSame(
            oldItem: UniversityModelItem, newItem: UniversityModelItem
        ): Boolean {
            return oldItem.universityName == newItem.universityName
        }

        override fun areContentsTheSame(
            oldItem: UniversityModelItem, newItem: UniversityModelItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}