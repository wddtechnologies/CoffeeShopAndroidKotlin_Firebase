package com.louise.coffeeappevening2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.louise.coffeeappevening2.Model.CategoryModel
import com.louise.coffeeappevening2.R
import com.louise.coffeeappevening2.databinding.ViewholderCategoryBinding

class CategoryAdapter(val items: MutableList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.Viewholder>() {

    private lateinit var context: Context
    private var selectedPosition = -1   // Set initial value to -1 (no selection)
    private var lastSelectedPosition = -1

    inner class Viewholder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]

        // Set the category title
        holder.binding.titleCat.text = item.title

        // Handle click event and update selected position
        holder.binding.root.setOnClickListener {
            if (selectedPosition != position) {
                lastSelectedPosition = selectedPosition  // Store the previously selected position
                selectedPosition = position              // Update to the new selected position
                notifyItemChanged(lastSelectedPosition)  // Refresh old selected item
                notifyItemChanged(selectedPosition)      // Refresh new selected item
            }
        }

        // Update the background depending on whether this item is selected
        if (selectedPosition == position) {
            holder.binding.titleCat.setBackgroundResource(R.drawable.orange_bg)
        } else {
            holder.binding.titleCat.setBackgroundResource(R.drawable.edittext_bg)
        }
    }

    override fun getItemCount(): Int = items.size
}
