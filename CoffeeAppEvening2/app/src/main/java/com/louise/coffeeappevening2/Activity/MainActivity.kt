package com.louise.coffeeappevening2.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.louise.coffeeappevening2.Adapter.CategoryAdapter
import com.louise.coffeeappevening2.Adapter.OfferAdapter
import com.louise.coffeeappevening2.Adapter.PopularAdapter
import com.louise.coffeeappevening2.ViewModel.MainViewModel
import com.louise.coffeeappevening2.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    lateinit var  binding:ActivityMainBinding
    private val viewModel= MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCategory()
        initPopular()
        initOffers()
        bottomMenu()

    }

    private fun bottomMenu() {
  binding.cartBtn.setOnClickListener{startActivity(Intent(this,CartActivity::class.java))}
    }

    private fun initOffers(){
        binding.progressBarOffer.visibility =View.VISIBLE
        viewModel.offer.observe(this, Observer {
            binding.recyclerViewOffer.layoutManager=
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL,false)
            binding.recyclerViewOffer.adapter = OfferAdapter(it)
            binding.progressBarOffer.visibility=View.GONE
        })
        viewModel.loadOffer()


    }
    private fun initPopular(){
        binding.progressBarPopular.visibility =View.VISIBLE
        viewModel.popular.observe(this, Observer {
            binding.recyclerViewPopular.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL,false)
            binding.recyclerViewPopular.adapter = PopularAdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        })
        viewModel.loadPopular()

    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.category.observe(this, Observer {
            binding.recyclerViewCategory.layoutManager=
                LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            binding.recyclerViewCategory.adapter=CategoryAdapter(it)
            binding.progressBarCategory.visibility = View.GONE
        })
        viewModel.loadCategory()
    }
}