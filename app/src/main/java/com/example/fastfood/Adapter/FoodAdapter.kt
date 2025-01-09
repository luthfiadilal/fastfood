package com.example.fastfood.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.datastore.preferences.protobuf.Internal.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fastfood.Database.Product
import com.example.fastfood.R
import com.example.fastfood.food.Food
import com.example.fastfood.model.ProductViewModel

class FoodAdapter(private val viewModel: ProductViewModel) : androidx.recyclerview.widget.ListAdapter<Food, FoodAdapter.FoodViewHolder>(FoodDiffCallback()) {

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.image_food_favorite)
        val foodName: TextView = itemView.findViewById(R.id.name_food_favorite)
        val foodPrice: TextView = itemView.findViewById(R.id.price_food_favorite)
        val orderButton: Button = itemView.findViewById(R.id.button_order)
    }

    // Fungsi untuk membuat ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FoodViewHolder(view)
    }

    // Fungsi untuk mengikat data ke ViewHolder
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = getItem(position)



        holder.foodImage.setImageResource(food.image)
        holder.foodName.text = food.name
        holder.foodPrice.text = food.price.toString()

        holder.orderButton.setOnClickListener {
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