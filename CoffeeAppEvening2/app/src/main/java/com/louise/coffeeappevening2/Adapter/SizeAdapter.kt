package com.louise.coffeeappevening2.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.louise.coffeeappevening2.R
import com.louise.coffeeappevening2.databinding.ViewholderSizeBinding

class SizeAdapter(val items: MutableList<String>) : RecyclerView.Adapter<SizeAdapter.Viewholder>() {
    private var selectedPosition = -1
    private lateinit var context: Context

    inner class Viewholder(val binding: ViewholderSizeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderSizeBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: SizeAdapter.Viewholder, position: Int) {
        // Set the background depending on whether the current item is selected or not
        if (selectedPosition == position) {
            holder.binding.img.setBackgroundResource(R.drawable.orange_bg)
        } else {
            holder.binding.img.setBackgroundResource(R.drawable.size_bg)
        }

        // Set the image size depending on the position
        val imageSize = when (position) {
            0 -> 45.dpToPx(context)
            1 -> 50.dpToPx(context)
            2 -> 55.dpToPx(context)
            3 -> 65.dpToPx(context)
            else -> 70.dpToPx(context)
        }

        val layoutParams = holder.binding.img.layoutParams
        layoutParams.width = imageSize
        layoutParams.height = imageSize
        holder.binding.img.layoutParams = layoutParams

        // Handle item click and update selected position
        holder.binding.root.setOnClickListener {
            if (selectedPosition != position) {
                val lastSelectedPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(lastSelectedPosition)  // Update the previous selected item
                notifyItemChanged(selectedPosition)      // Update the new selected item
            }
        }
    }

    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

    override fun getItemCount(): Int = items.size
}
