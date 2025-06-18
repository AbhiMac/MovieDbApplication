package com.example.moviesapp.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.databinding.FragmentBookmarksBinding
import com.example.moviesapp.ui.home.MovieAdapter
import com.example.moviesapp.ui.search.SearchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarksBinding? = null
    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private lateinit var bookmarkMoviesAdapter: MovieAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
//        bookmarkViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observer()
    }

    private fun init() {

        bookmarkMoviesAdapter = MovieAdapter({ movie ->
            val action = SearchFragmentDirections.actionSearchToDetail(movie.id)
            findNavController().navigate(action)
        })
        binding.rvBookmarks.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = bookmarkMoviesAdapter
        }
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookmarkViewModel.bookmarks.collect { data ->
                    if (data.isNotEmpty()) {
                        binding.rvBookmarks.visibility = View.VISIBLE
                        binding.tvEmpty.visibility = View.GONE
                        bookmarkMoviesAdapter.submitList(data)
                    } else {
                        binding.tvEmpty.visibility = View.VISIBLE
                        binding.rvBookmarks.visibility = View.GONE
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