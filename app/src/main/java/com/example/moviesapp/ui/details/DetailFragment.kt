package com.example.moviesapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.FragmentDetailsBinding
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.utils.Constants.IMAGE_BASE_URL
import com.example.moviesapp.utils.UiState
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
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadMovieDetails(args.movieId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movie.collect { state ->
                    when (state) {
                        is UiState.Loading -> {

                        }

                        is UiState.Error -> {}
                        is UiState.Success -> {
                            state.data?.let { bindUi(it) }
                        }
                    }


                }
            }

            binding.btnShare.setOnClickListener {
                viewModel.movie.value?.let {
//                val shareIntent = Intent(Intent.ACTION_SEND).apply {
//                    type = "text/plain"
//                    putExtra(Intent.EXTRA_TEXT, "Check out this movie: ${it.title}")
//                }
//                startActivity(Intent.createChooser(shareIntent, "Share via"))
                }
            }

            binding.btnBookmark.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Bookmarked (to be implemented)",
                    Toast.LENGTH_SHORT
                ).show()
                // will wire up to Room DB later
            }
        }
    }

    private fun bindUi(movie: Movie) = with(binding) {
        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        //   tvReleaseDate.text = "Release: ${movie.releaseDate}"
        Glide.with(ivPoster)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(ivPoster)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
