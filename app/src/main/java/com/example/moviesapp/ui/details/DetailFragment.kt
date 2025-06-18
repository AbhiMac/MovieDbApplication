package com.example.moviesapp.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentDetailsBinding
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.ui.bookmark.BookmarkViewModel
import com.example.moviesapp.utils.Constants.IMAGE_BASE_URL
import com.example.moviesapp.utils.UiState
import com.example.moviesapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observer()
        clickListener()
    }


    private fun clickListener() {

        binding.btnShare.setOnClickListener {
            viewModel.movie.value.let {
              //  Utils.shareMovie(requireContext(), (it as UiState.Success).data)
                val deepLink = "mymovieapp://movie/${args.movieId}"
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Check out this movie: $deepLink")
                }
                startActivity(Intent.createChooser(shareIntent, "Share via"))
            }
        }

        binding.btnBookmark.setOnClickListener {
            val state = viewModel.movie.value
            if (state is UiState.Success) {
                bookmarkViewModel.toggleBookmark(state.data)
            }
        }

    }

    private fun init() {
        viewModel.loadMovieDetails(args.movieId)
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.movie.collect { state ->
                        when (state) {
                            is UiState.Loading -> {

                            }

                            is UiState.Error -> {

                            }

                            is UiState.Success -> {
                                state.data?.let {
                                    bindUi(it)
                                    bookmarkViewModel.checkBookmarkStatus(it.id)
                                }

                            }
                        }


                    }
                }
                launch {

                    viewLifecycleOwner.lifecycleScope.launch {
                        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                            launch {
                                bookmarkViewModel.isBookmarked.collect { isBookmarked ->
                                    binding.btnBookmark.setImageResource(
                                        if (isBookmarked) R.drawable.ic_bookmark_filled
                                        else R.drawable.ic_bookmark_border
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private fun bindUi(movie: Movie) = with(binding) {
        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        Glide.with(ivPoster)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .centerCrop()
            .into(ivPoster)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
