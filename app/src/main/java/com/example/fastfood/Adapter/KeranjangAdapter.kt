package com.example.fastfood.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fastfood.Adapter.FoodListAdapter.FoodDiffCallback
import com.example.fastfood.Adapter.FoodListAdapter.FoodListViewHolder
import com.example.fastfood.Database.Product
import com.example.fastfood.R
import com.example.fastfood.databinding.ItemKeranjangBinding
import com.example.fastfood.food.Food
import com.example.fastfood.model.ProductViewModel

class KeranjangAdapter(private val viewModel: ProductViewModel) : ListAdapter<Product, KeranjangAdapter.KeranjangViewHolder>(KeranjangDiffCallback()) {

    class KeranjangViewHolder(private val binding: ItemKeranjangBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("DefaultLocale")
        fun bind(product: Product, viewModel: ProductViewModel) {

            val formattedPrice = String.format("%.0f", product.price)
            binding.nameFoodKeranjang.text = product.name
            binding.kuantity.text = product.quantity.toString()
            binding.priceFoodKeranjang.text = formattedPrice

            val imageResId = binding.root.context.resources.getIdentifier(product.image, "drawable", binding.root.context.packageName)
            // Load image (e.g., Glide)
            Glide.with(binding.root.context)
                .load(imageResId)
                .into(binding.imageFoodKeranjang)

            binding.plus.setOnClickListener {
                val newQuantity = product.quantity + 1
                val pricePerUnit = product.price / product.quantity // Menghitung harga per unit
                val newPrice = pricePerUnit * newQuantity // Mengalikan harga per unit dengan kuantitas baru
                viewModel.updateProductQuantity(product.id, newQuantity, newPrice)
            }

            binding.minus.setOnClickListener {
                if (product.quantity > 1) {
                    val newQuantity = product.quantity - 1
                    val pricePerUnit = product.price / product.quantity // Menghitung harga per unit
                    val newPrice = pricePerUnit * newQuantity // Mengalikan harga per unit dengan kuantitas baru
                    viewModel.updateProductQuantity(product.id, newQuantity, newPrice)
                }
            }
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KeranjangAdapter.KeranjangViewHolder {
        val binding = ItemKeranjangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KeranjangViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KeranjangAdapter.KeranjangViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, viewModel)

    }

    class KeranjangDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}