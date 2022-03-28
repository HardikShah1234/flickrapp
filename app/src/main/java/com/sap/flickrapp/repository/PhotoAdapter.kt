package com.sap.flickrapp.repository

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sap.flickrapp.data.PhotoResponse
import com.sap.flickrapp.databinding.ItemPhotoListBinding

class PhotoAdapter :
    PagingDataAdapter<PhotoResponse, RecyclerView.ViewHolder>(PhotoDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemPhotoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            (holder as PhotoViewHolder).bind(currentItem)
        }
    }

    class PhotoDiffCallBack : DiffUtil.ItemCallback<PhotoResponse>() {
        override fun areItemsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean {
            return oldItem == newItem
        }

    }


    class PhotoViewHolder(private val binding: ItemPhotoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photoResponse: PhotoResponse?) {
            binding.apply {
                val photoURL =
                    "https://farm${photoResponse?.farm}.staticflickr.com/${photoResponse?.server}/${photoResponse?.id}_${photoResponse?.secret}.jpg"

                Glide.with(itemView.context)
                    .load(photoURL)
                    .into(cvIvPhotoPoster)
            }
        }
    }
}