package com.thatta.mycocktail.viewModel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thatta.mycocktail.R
import com.thatta.mycocktail.databinding.ItemCocktailBinding
import com.thatta.mycocktail.models.Drink

class CocktailRecyclerAdapter: RecyclerView.Adapter<CocktailRecyclerAdapter.ViewHolder>() {

    var drinks: MutableList<Drink> = ArrayList()
    lateinit var context: Context



    fun CocktailRecyclerAdapter(drinks: MutableList<Drink>, context: Context) {
        this.drinks = drinks
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_cocktail, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = drinks[position]
        holder.bind(item, context)
    }

    override fun getItemCount(): Int = drinks.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCocktailBinding.bind(view)

        fun bind(drink: Drink, context: Context) {
            binding.tvName.text = drink.name
            binding.tvAlcohol.text = drink.alcohol
            binding.ivCocktail.loadUrl(drink.thumbUrl)

        }

        private fun ImageView.loadUrl(url: String) {
            Glide.with(context).load(url).centerCrop().into(binding.ivCocktail)
        }

    }
}
