package com.louise.coffeeappevening2.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.louise.coffeeappevening2.Model.ItemsModel
import com.louise.coffeeappevening2.R
import com.louise.coffeeappevening2.databinding.ActivityDetailBinding
import android.os.Parcel
import android.os.Parcelable
import com.louise.coffeeappevening2.Helper.ManagmentCart

class DetailActivity : AppCompatActivity() {
    lateinit var  binding:ActivityDetailBinding
    private lateinit var item: ItemsModel
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        bundle()

    }

    private fun bundle() {
        binding.apply {

            item = intent.getParcelableExtra("object")!!
            titleTxt.text = item.title
            descriptionTxt.text = item.description
            priceTxt.text = "$" + item.price
            ratingBar.rating = item.rating.toFloat()


            addToCartBtn.setOnClickListener {
                item.numberInCart = Integer.valueOf(numberItemTxt.text.toString()
                )

            managmentCart.insertItems(item)
            }
            backBtn.setOnClickListener { startActivity(Intent(this@DetailActivity, MainActivity::class.java)) }

            plusCart.setOnClickListener {
                numberItemTxt.text=(item.numberInCart+1).toString()
                item.numberInCart++
            }

            minusCart.setOnClickListener {
                if(item.numberInCart>0){
                    numberItemTxt.text = (item.numberInCart-1).toString()
                    item.numberInCart--
                }
            }
        }
    }
}