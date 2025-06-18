package com.example.moviesapp.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ItemMovieBinding
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.utils.Constants.IMAGE_BASE_URL

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */

class MovieAdapter(
    private val onMovieClick: ((Movie) -> Unit)? = null,
   private val isCarousel: Boolean= false
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallback()) {

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            if(isCarousel){
                val layoutParams = binding.cardView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(15, 0, 15, 0) // left, top, right, bottom in pixels
                binding.cardView.layoutParams = layoutParams
                binding.titleOnImage.visibility = View.VISIBLE
                binding.titleBelowImage.visibility = View.GONE
                binding.titleOnImage.text = movie.title
            }else{
                binding.titleOnImage.visibility = View.GONE
                binding.titleBelowImage.visibility = View.VISIBLE
                binding.titleBelowImage.text = movie.title
                binding.titleBelowImage.textSize = 12f
            }

            Glide.with(binding.posterImage.context)
                .load("$IMAGE_BASE_URL${movie.posterPath}")
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(binding.posterImage)

            binding.root.setOnClickListener {
                onMovieClick?.invoke(movie)
            }


            if (isCarousel) {
                binding.root.scaleX = 1.05f
                binding.root.scaleY = 1.05f
            } else {
                binding.root.scaleX = 1f
                binding.root.scaleY = 1f
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}
