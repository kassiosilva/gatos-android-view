package com.example.gatos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.example.gatos.R
import com.example.gatos.databinding.ItemCatBinding
import com.example.gatos.network.model.Cat


class CatsAdapter(private val catsList: List<Cat>) :
    RecyclerView.Adapter<CatsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cats = catsList[position])
    }

    override fun getItemCount(): Int {
        return catsList.size
    }

    class ViewHolder(private val binding: ItemCatBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cats: Cat) {
            with(binding) {
                ivItemCat.load(cats.url)
            }
        }

    }
}