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
import com.example.randommovie.domain.entity.UserInfoAndMovie
import com.example.randommovie.presentation.screen.BaseFragment
import com.example.randommovie.presentation.screen.info.InfoFragment
import com.example.randommovie.presentation.tools.factory

class ListFragment : BaseFragment() {

    private lateinit var binding : FragmentListBinding
    private val viewModel: ListViewModel by viewModels{factory()}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentListBinding.bind(view)

        val adapter = MovieListAdapter(object : MovieListAdapter.ItemListener {
            override fun onChooseMovie(infoAndMovie: UserInfoAndMovie) =
                findNavController().navigate(
                    R.id.action_listFragment_to_informationListFragment,
                    bundleOf(InfoFragment.ARG_MOVIE to infoAndMovie.movie )) //TODO("изменить на инфо")


            override fun onDeleteMovie(infoAndMovie: UserInfoAndMovie) =
                viewModel.deleteMovieById(infoAndMovie.movie.id)

            override fun onChangeInfo(infoAndMovie: UserInfoAndMovie) {
                showRatingDialogFragment(parentFragmentManager,infoAndMovie.movie )//TODO("изменить на инфо")
            }

        })
        binding.moviesRecyclerView.adapter = adapter

        viewModel.movieList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        setupRatingDialogFragmentListener(parentFragmentManager)
    }


}