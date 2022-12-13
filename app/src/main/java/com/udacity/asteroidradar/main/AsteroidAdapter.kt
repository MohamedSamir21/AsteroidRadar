package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding

class AsteroidAdapter(val clickListener: AsteroidListener) : ListAdapter<Asteroid,
        AsteroidAdapter.AsteroidDetailsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): AsteroidDetailsViewHolder {
        return AsteroidDetailsViewHolder(ListItemAsteroidBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AsteroidDetailsViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.bind(marsProperty)
        holder.itemView.setOnClickListener {
            clickListener.onClick(marsProperty)
        }
    }


    class AsteroidDetailsViewHolder(private var binding: ListItemAsteroidBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class AsteroidListener(val clickListener: (asteroidDetails: Asteroid) -> Unit) {
        fun onClick(asteroidDetails: Asteroid) = clickListener(asteroidDetails)
    }
}





