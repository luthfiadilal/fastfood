package com.example.fastfood.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fastfood.Adapter.FoodAdapter.FoodDiffCallback
import com.example.fastfood.Adapter.FoodAdapter.FoodViewHolder
import com.example.fastfood.Database.Product
import com.example.fastfood.R
import com.example.fastfood.food.Food
import com.example.fastfood.model.ProductViewModel
import com.google.android.material.card.MaterialCardView

class FoodListAdapter(private val viewModel: ProductViewModel) : ListAdapter<Food, FoodListAdapter.FoodListViewHolder>(FoodDiffCallback()) {

    class FoodListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.image_food)
        val foodname : TextView = itemView.findViewById(R.id.name_food)
        val foodprice : TextView = itemView.findViewById(R.id.price_food)
        val buttonKeranjang : MaterialCardView = itemView.findViewById(R.id.button_keranjang_food)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListAdapter.FoodListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodListViewHolder(view)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: FoodListAdapter.FoodListViewHolder, position: Int) {
        val food = getItem(position)


        holder.foodname.text = food.name
        holder.foodprice.text = food.price.toString()
        holder.foodImage.setImageResource(food.image)

        holder.buttonKeranjang.setOnClickListener {
            val product = Product(
                name = food.name,
                image = holder.foodImage.resources.getResourceEntryName(food.image), // Ambil nama resource
                quantity = 1,
                price = food.price.toDouble()
            )
            viewModel.insertProduct(product)
        }

    }

    class FoodDiffCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.id == newItem.id // Bandingkan berdasarkan ID
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem == newItem // Bandingkan seluruh konten
        }
    }
}