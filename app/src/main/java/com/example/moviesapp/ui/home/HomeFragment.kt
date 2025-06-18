package com.example.moviesapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.ui.search.SearchFragmentDirections
import com.example.moviesapp.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var nowPlayingAdapter: MovieAdapter
    private lateinit var latestMoviesAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        observeUiStates()

    }

    private fun setupRecyclerViews() {
        nowPlayingAdapter = MovieAdapter({ movie ->
            val action = SearchFragmentDirections.actionSearchToDetail(movie.id)
            findNavController().navigate(action)
        }, isCarousel = true)
        latestMoviesAdapter = MovieAdapter({ movie ->
            val action = SearchFragmentDirections.actionSearchToDetail(movie.id)
            findNavController().navigate(action)
        })
        binding.rvNowPlaying.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = nowPlayingAdapter
            setPadding(48, 0, 48, 0)
            clipToPadding = false
            PagerSnapHelper().attachToRecyclerView(this)
            post {
                scrollToPosition(1)
            }
        }

        binding.rvLatestMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = latestMoviesAdapter
        }
    }

    private fun observeUiStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                            launch {
                                viewModel.nowPlayingState.collect { state ->
                                    when (state) {
                                        is UiState.Loading -> {
                                            binding.nowPlayingProgress.isVisible = true
                                            binding.rvNowPlaying.isVisible = false
                                            binding.nowPlayingEmptyText.isVisible = false
                                        }

                                        is UiState.Success -> {
                                            binding.nowPlayingProgress.isVisible = false
                                            binding.rvNowPlaying.isVisible = state.data.isNotEmpty()
                                            binding.nowPlayingEmptyText.isVisible =
                                                state.data.isEmpty()
                                            nowPlayingAdapter.submitList(state.data)
                                        }

                                        is UiState.Error -> {
                                            binding.nowPlayingProgress.isVisible = false
                                            binding.rvNowPlaying.isVisible = false
                                            binding.nowPlayingEmptyText.isVisible = true
                                        }
                                    }
                                }
                            }

                            launch {
                                viewModel.latestMoviesState.collect { state ->
                                    when (state) {
                                        is UiState.Loading -> {
                                            binding.latestProgress.isVisible = true
                                            binding.rvLatestMovies.isVisible = false
                                            binding.latestEmptyText.isVisible = false
                                        }

                                        is UiState.Success -> {
                                            binding.latestProgress.isVisible = false
                                            binding.rvLatestMovies.isVisible =
                                                state.data.isNotEmpty()
                                            binding.latestEmptyText.isVisible = state.data.isEmpty()
                                            latestMoviesAdapter.submitList(state.data)
                                        }

                                        is UiState.Error -> {
                                            binding.latestProgress.isVisible = false
                                            binding.rvLatestMovies.isVisible = false
                                            binding.latestEmptyText.isVisible = true
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}