package com.murata.foodrecipeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murata.foodrecipeapp.databinding.ItemRecipeBinding
import com.murata.foodrecipeapp.network.dto.DtoRecipe

class RecipeVerticalListAdapter(
    private val dtoRecipeArrayList: ArrayList<DtoRecipe>,
    private val recipeCallback: RecipeCallback
) :
    RecyclerView.Adapter<RecipeVerticalListAdapter.ItemRecipeViewHolder>() {
    private var lastSelectedIndex = -1

    inner class ItemRecipeViewHolder(private val itemRecipeBinding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(itemRecipeBinding.root) {
        fun bind(dtoRecipe: DtoRecipe, position: Int) {
            itemRecipeBinding.dtoRecipe = dtoRecipe
            itemRecipeBinding.clMain.setOnClickListener {
                recipeCallback.onRecipeSelected(dtoRecipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecipeViewHolder {
        return ItemRecipeViewHolder(
            ItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemRecipeViewHolder, position: Int) {
        holder.bind(dtoRecipeArrayList[position], position)
    }

    override fun getItemCount() = dtoRecipeArrayList.size

    interface RecipeCallback {
        fun onRecipeSelected(dtoRecipe: DtoRecipe)
    }
}
//class TagsAdapter {
//}