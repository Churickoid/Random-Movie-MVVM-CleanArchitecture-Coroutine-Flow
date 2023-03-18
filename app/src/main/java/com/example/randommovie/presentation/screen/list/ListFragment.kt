package com.example.randommovie.presentation.screen.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentListBinding
import com.example.randommovie.presentation.screen.BaseFragment
import com.example.randommovie.presentation.screen.info.InfoFragment
import com.example.randommovie.presentation.tools.factory

class ListFragment : BaseFragment() {

    private lateinit var binding : FragmentListBinding
    private val viewModel: ListViewModel by viewModels{factory()}
    private lateinit var movieListAdapter: MovieListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentListBinding.bind(view)
        setRecyclerView()
        viewModel.getAllMovies()
        viewModel.movieList.observe(viewLifecycleOwner){
            movieListAdapter.userInfoAndMovieList = it
        }

    }

    private fun setRecyclerView(){
        movieListAdapter = MovieListAdapter()
        binding.moviesRecyclerView.adapter = movieListAdapter
          movieListAdapter.onItemClickListener = {
            findNavController().navigate(
                R.id.action_listFragment_to_informationListFragment,
                bundleOf(InfoFragment.ARG_MOVIE to it)
            )
        }
    }
}