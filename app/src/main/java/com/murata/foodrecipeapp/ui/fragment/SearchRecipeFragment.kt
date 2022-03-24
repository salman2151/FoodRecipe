package com.murata.foodrecipeapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.murata.foodrecipeapp.FoodRecipeApplication
import com.murata.foodrecipeapp.R
import com.murata.foodrecipeapp.databinding.FragmentSearchRecipeBinding
import com.murata.foodrecipeapp.network.Repository
import com.murata.foodrecipeapp.network.dto.DtoRecipe
import com.murata.foodrecipeapp.network.dto.DtoTag
import com.murata.foodrecipeapp.ui.adapter.RecipeVerticalListAdapter
import com.murata.foodrecipeapp.ui.adapter.TagsAdapter

class SearchRecipeFragment : LoadingBaseFragment() {

    private var binding: FragmentSearchRecipeBinding? = null
    override val layoutId: Int
        get() = R.layout.fragment_search_recipe

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)
        arguments?.let {
            setTagsAdapter(SearchRecipeFragmentArgs.fromBundle(it).arrayListDtoTag)
            binding?.tagsAdapter?.setSelectedIndex(0)

        } ?: Toast.makeText(activity, "No tags received", Toast.LENGTH_SHORT).show()
    }

    private fun setTagsAdapter(arrayListDtoTag: Array<DtoTag>?) {
        arrayListDtoTag?.toList()?.let {
            binding?.tagsAdapter =
                TagsAdapter(it.toList() as ArrayList<DtoTag>, getTagSelectCallback())
        }
    }

    private fun getTagSelectCallback() = object : TagsAdapter.TagCallback {
        override fun onTagSelected(dtoTag: DtoTag) {
            dtoTag.name?.let { searchRecipesBasedOnTagName(it) }
        }
    }

    private fun searchRecipesBasedOnTagName(tagName: String) {
        showProgressDialog()
        FoodRecipeApplication.main {
            val response = FoodRecipeApplication.getRepository()
                .searchRecipesByTagList(size = 20, from = 0, tag = tagName)
            hideProgressDialog()
            response.body()?.let {
                if (it.arrayListDtoRecipe.isNullOrEmpty())
                    setVisibility(View.GONE, View.VISIBLE)
                else {
                    setVisibility(View.VISIBLE, View.GONE)
                    binding?.rcvRecipe?.adapter = RecipeVerticalListAdapter(
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
            } ?: setVisibility(View.GONE, View.VISIBLE)
        }
    }

    fun setVisibility(rcvVisibility: Int, tvtErrorVisibility: Int) {
        binding?.rcvRecipe?.visibility = rcvVisibility
        binding?.tvtRecipeListError?.visibility = tvtErrorVisibility
    }
}