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
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.presentation.screen.BaseFragment
import com.example.randommovie.presentation.screen.info.InfoFragment
import com.example.randommovie.presentation.screen.info.InfoViewModel
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
            movieListAdapter.moviesList = it
        }

    }

    private fun setRecyclerView(){
        movieListAdapter = MovieListAdapter()
        binding.moviesRecyclerView.adapter = movieListAdapter
       /* movieListAdapter.moviesList = listOf(
            Movie(id=180613, titleMain="Дилер 3", titleSecond="Pusher III", posterUrl="https://kinopoiskapiunofficial.tech/images/posters/kp_small/180613.jpg", genre= listOf("триллер", "криминал"), year=200, ratingKP=7.1, ratingIMDB=7.3, country=listOf("Дания")),
            Movie(id=81692, titleMain="Дилер 2", titleSecond="Pusher II", posterUrl="https://kinopoiskapiunofficial.tech/images/posters/kp_small/81692.jpg", genre=listOf("драма", "криминал"), year=2004, ratingKP=7.1, ratingIMDB=7.3, country=listOf("Великобритания", "Дания")),
            Movie(id=17115, titleMain="Дилер", titleSecond="Pusher", posterUrl="https://kinopoiskapiunofficial.tech/images/posters/kp_small/17115.jpg", genre=listOf("триллер", "драма", "криминал"), year=1996, ratingKP=7.0, ratingIMDB=7.3, country=listOf("Дания")))
    */    movieListAdapter.onItemClickListener = {
            findNavController().navigate(
                R.id.action_listFragment_to_informationListFragment,
                bundleOf(InfoFragment.ARG_MOVIE to it)
            )
        }
    }
}