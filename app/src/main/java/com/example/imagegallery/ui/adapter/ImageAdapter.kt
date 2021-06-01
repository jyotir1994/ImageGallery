package com.example.imagegallery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.imagegallery.databinding.EachRowBinding
import com.example.imagegallery.model.ImageResponseModel
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import javax.inject.Inject

class ImageAdapter(val context: Context) :
    PagingDataAdapter<ImageResponseModel, ImageAdapter.GalleryViewHolder>(Diff()) {

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val image = getItem(position)
        if (image != null) {
            holder.binds(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder =
        GalleryViewHolder(
            context,
            EachRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    class GalleryViewHolder(private val context: Context, private val binding: EachRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binds(image: ImageResponseModel) {
            val shimmer = Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.9f) //the alpha of the underlying children
                .setHighlightAlpha(0.8f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()

// This is the placeholder for the imageView
            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }
            binding.apply {
                Glide.with(context)
                    .load(image.download_url)
                    .placeholder(shimmerDrawable)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(this.image)
            }
        }
    }

    class Diff : DiffUtil.ItemCallback<ImageResponseModel>() {
        override fun areItemsTheSame(
            oldItem: ImageResponseModel,
            newItem: ImageResponseModel
        ): Boolean =
            oldItem.download_url == newItem.download_url

        override fun areContentsTheSame(
            oldItem: ImageResponseModel,
            newItem: ImageResponseModel
        ): Boolean =
            oldItem == newItem
    }
}