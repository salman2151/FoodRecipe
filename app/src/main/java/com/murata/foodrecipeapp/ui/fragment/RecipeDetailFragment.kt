package com.murata.foodrecipeapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.murata.foodrecipeapp.FoodRecipeApplication
import com.murata.foodrecipeapp.R
import com.murata.foodrecipeapp.databinding.FragmentRecipeDetailBinding
import com.murata.foodrecipeapp.databinding.FragmentSearchRecipeBinding
import com.murata.foodrecipeapp.network.dto.DtoRecipe
import com.murata.foodrecipeapp.ui.adapter.RecipeVerticalListAdapter

class RecipeDetailFragment : BaseFragment() {

    private var binding: FragmentRecipeDetailBinding? = null
    override val layoutId: Int
        get() = R.layout.fragment_recipe_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view)
        setSelectedRecipeToView()
        getSimilarRecipesOfRecipe()

    }

    private fun setSelectedRecipeToView() {
        binding?.let { itBinding ->
            arguments?.let { itArguments ->
                itBinding.dtoRecipe =
                    RecipeDetailFragmentArgs.fromBundle(itArguments).selectedRecipe
            }
        }
    }

    private fun getSimilarRecipesOfRecipe() {
        binding?.let { itBinding ->
            itBinding.dtoRecipe?.let { itDtoRecipe ->
                setSimilarRecipeLoaderVisibility(View.VISIBLE)
                FoodRecipeApplication.main {
                    val response = FoodRecipeApplication.getRepository()
                        .getListSimilarityByRecipeId(itDtoRecipe.id)
                    setSimilarRecipeLoaderVisibility(View.GONE)
                    response.body()?.let {
                        if (it.arrayListDtoRecipe.isNullOrEmpty())
                            setSimilarRecipeLabelVisibility(View.GONE)
                        else {
                            setSimilarRecipeLabelVisibility(View.VISIBLE)
                            binding?.rcvSimilarRecipe?.adapter = RecipeVerticalListAdapter(
                                it.arrayListDtoRecipe!!,
                                object : RecipeVerticalListAdapter.RecipeCallback {
                                    override fun onRecipeSelected(dtoRecipe: DtoRecipe) {
                                        findNavController().navigate(
                                            SearchRecipeFragmentDirections.actionFragmentSearchRecipeToFragmentRecipeDetail(
                                                dtoRecipe
                                            )
                                        )
                                    }
                                })
                        }
                    } ?: setSimilarRecipeLabelVisibility(View.GONE)
                }
            }

        }
    }

    private fun setSimilarRecipeLoaderVisibility(visibility: Int) {
        binding?.pbSimilarRecipe?.visibility = visibility
    }

    private fun setSimilarRecipeLabelVisibility(visibility: Int) {
        binding?.tvtSimilarRecipesLabel?.visibility = visibility
    }

}