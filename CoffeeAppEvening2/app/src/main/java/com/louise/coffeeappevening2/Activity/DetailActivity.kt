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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.louise.coffeeappevening2.Adapter.SizeAdapter
import com.louise.coffeeappevening2.Helper.ManagmentCart

class DetailActivity : BaseActivity() {
    lateinit var  binding:ActivityDetailBinding
    private lateinit var item: ItemsModel
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        managmentCart=ManagmentCart(this)

        bundle()
        initSizeList()

    }

    private fun initSizeList() {
        val sizeList= ArrayList<String>()
            sizeList.add("1")
            sizeList.add("2")
            sizeList.add("3")
            sizeList.add("4")

        binding.sizeList.adapter=SizeAdapter(sizeList)
        binding.sizeList.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        val colorList = ArrayList<String>()
        for(ImageUrl in item.picUrl){
            colorList.add(ImageUrl)
        }
        Glide.with(this)
            .load(colorList[0])
            .apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
            .into(binding.picMain)


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