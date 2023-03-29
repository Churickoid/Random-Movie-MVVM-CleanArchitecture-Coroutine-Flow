package com.example.randommovie.presentation.screen.list

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentListBinding
import com.example.randommovie.domain.ListRepository.Companion.RATED_LIST_TYPE
import com.example.randommovie.domain.ListRepository.Companion.WATCH_LIST_TYPE
import com.example.randommovie.domain.entity.UserInfoAndMovie
import com.example.randommovie.presentation.screen.BaseFragment
import com.example.randommovie.presentation.screen.info.InfoFragment
import com.example.randommovie.presentation.tools.factory
import com.google.android.material.tabs.TabLayout

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
        var listToTop = false
        binding.listTabLayout.getTabAt(viewModel.type.value)?.select() ?: throw IllegalArgumentException("Unknown tab")

        val adapter = MovieListAdapter(object : MovieListAdapter.ItemListener {
            override fun onChooseMovie(infoAndMovie: UserInfoAndMovie) =
                findNavController().navigate(
                    R.id.action_listFragment_to_informationListFragment,
                    bundleOf(InfoFragment.ARG_MOVIE to infoAndMovie.movie )) //TODO("изменить на инфо")


            override fun onDeleteMovie(infoAndMovie: UserInfoAndMovie) =
                viewModel.deleteMovieById(infoAndMovie.movie.id)

            override fun onChangeInfo(infoAndMovie: UserInfoAndMovie) =
                showRatingDialogFragment(parentFragmentManager,infoAndMovie )


        })



        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.appbar_list_icons, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_sort -> {
                        OrderDialogFragment.show(parentFragmentManager, viewModel.order.value)
                        true
                    }
                    R.id.action_reverse -> {
                        viewModel.reverseAsc()
                        true
                    }
                    else -> false
            }
        }}, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.moviesRecyclerView.adapter = adapter

        viewModel.movieList.observe(viewLifecycleOwner){
            adapter.submitList(it)
            if (listToTop) {
                binding.moviesRecyclerView.smoothScrollToPosition(0)
                listToTop = false
            }
        }

        binding.listTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position){
                    0 -> viewModel.type.value = WATCH_LIST_TYPE
                    1 -> viewModel.type.value = RATED_LIST_TYPE
                }
                listToTop = true
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewModel.watchlistCounter.observe(viewLifecycleOwner){
            binding.listTabLayout.getTabAt(0)!!.text = "Watchlist ($it)"
        }

        viewModel.ratedCounter.observe(viewLifecycleOwner){
            binding.listTabLayout.getTabAt(1)!!.text = "Rated ($it)"
        }

        setupOrderDialogFragmentListener(parentFragmentManager)
        setupRatingDialogFragmentListener(parentFragmentManager)
    }
    private fun setupOrderDialogFragmentListener(manager: FragmentManager) {
        OrderDialogFragment.setupListener(manager, this) { order ->
            viewModel.order.value = order
        }
    }
}