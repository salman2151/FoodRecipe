package com.murata.foodrecipeapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.murata.foodrecipeapp.FoodRecipeApplication
import com.murata.foodrecipeapp.R
import com.murata.foodrecipeapp.network.dto.DtoTag

class SplashFragment : LoadingBaseFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_splash

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callGetTagsApiAndNavigate()
    }

    private fun callGetTagsApiAndNavigate() {
        showProgressDialog()
        FoodRecipeApplication.main {
            val dtoTagResponse = FoodRecipeApplication.getRepository().getTagsList()
            hideProgressDialog()
            dtoTagResponse.body()?.let {
                if (it.arrayListDtoTags.isNullOrEmpty())
                    Toast.makeText(activity, "No Tags found!", Toast.LENGTH_SHORT).show()
                else
                    if (!it.arrayListDtoTags.isNullOrEmpty())
                        goToSearchRecipeScreen(it.arrayListDtoTags)
                    else
                        Toast.makeText(activity, "No Tags found!", Toast.LENGTH_SHORT).show()
            } ?: Toast.makeText(activity, "No Tags found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToSearchRecipeScreen(it: ArrayList<DtoTag>?) {
        findNavController().navigate(
            SplashFragmentDirections.actionFragmentSplashToFragmentSearchRecipe((it as ArrayList).toTypedArray())
        )
    }
}