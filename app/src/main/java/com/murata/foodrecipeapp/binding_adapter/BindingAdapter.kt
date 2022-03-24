package com.murata.foodrecipeapp.binding_adapter

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.murata.foodrecipeapp.R


class BindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter(*["imageUrl", "progressbar"])
        fun loadImage(view: ImageView, imageUrl: String?, progressBar: ProgressBar) {
            view.context?.let { itContext ->
                progressBar.visibility = View.VISIBLE
                Glide.with(view.context).load(imageUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            view.setImageDrawable(
                                ContextCompat.getDrawable(
                                    itContext,
                                    R.drawable.no_image_available
                                )
                            )
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }
                    }).into(view)
            }
        }
    }
}