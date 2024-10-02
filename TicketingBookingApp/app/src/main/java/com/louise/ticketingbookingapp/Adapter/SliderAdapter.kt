package com.louise.ticketingbookingapp.Adapter

import com.louise.ticketingbookingapp.Model.SliderItems

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.louise.ticketingbookingapp.databinding.ViewholderSliderBinding

class SliderAdapter(
    private var sliderItems: MutableList<SliderItems>,

    private val viewPager2: ViewPager2

) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private var context: Context? = null

    // Runnable for adding more items to simulate an infinite scroll
    private val runnable = Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

    // ViewHolder class with binding
    inner class SliderViewHolder(private val binding: ViewholderSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sliderItem: SliderItems) {
            val requestOptions = RequestOptions().transform(CenterCrop(),RoundedCorners(60))
            context?.let {
                Glide.with(it)
                    .load(sliderItem.image)
                    .apply(requestOptions)// Apply rounded corners with 60dp radius

                    .into(binding.imageSlider)  // Make sure this ID matches the XML layout view ID
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        context = parent.context
        // Inflate the view using View Binding
        val binding = ViewholderSliderBinding.inflate(LayoutInflater.from(context), parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(sliderItems[position])

        // Infinite scroll logic
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int = sliderItems.size
}