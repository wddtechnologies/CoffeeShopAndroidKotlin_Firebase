package com.louise.ticketingbookingapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.disklrucache.DiskLruCache.Value
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.louise.ticketingbookingapp.Adapter.SliderAdapter
import com.louise.ticketingbookingapp.Model.SliderItems
import com.louise.ticketingbookingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  database: FirebaseDatabase
    private val sliderHandler = Handler(Looper.getMainLooper())
    private val sliderRunnable = Runnable {
        binding.viewPager2.currentItem=binding.viewPager2.currentItem +1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        database=FirebaseDatabase.getInstance()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initBanner()

    }

    private fun initBanner() {
        val myRef: DatabaseReference = database.getReference("Banners")
        binding.progressBarSlider.visibility = View.VISIBLE
        myRef.addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               val lists = mutableListOf<SliderItems>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SliderItems::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                binding.progressBarSlider.visibility = View.GONE
                banners(lists)
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle error appropriately
            }
        })

    }
    private fun banners(lists: MutableList<SliderItems>) {
        // Initialize the ViewPager2 adapter with the list of banners
        binding.viewPager2.adapter = SliderAdapter(lists, binding.viewPager2)

        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.offscreenPageLimit = 3

        // Disable over-scrolling effect
        binding.viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        // Set page transformer for animations
        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))  // Margin between pages
            addTransformer (ViewPager2.PageTransformer{ page, position ->
                val r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f  // Scale effect
            })
        }

        binding.viewPager2.setPageTransformer(compositePageTransformer)

        // Start from the first item (0)
        binding.viewPager2.currentItem = 1
        // Auto-slide the banners
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Reset the handler when the page changes
           sliderHandler.removeCallbacks(sliderRunnable)
                // sliderHandler.postDelayed(sliderRunnable, 3000)  // Slide every 3 seconds
            }
        })
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)  // Stop auto-slide when paused
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)  // Resume auto-slide
    }
}



