package com.churickoid.filmity.presentation.screen.tabs.list

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.churickoid.filmity.R
import com.churickoid.filmity.databinding.FragmentListBinding
import com.churickoid.filmity.domain.ListRepository.Companion.RATED_LIST_TYPE
import com.churickoid.filmity.domain.ListRepository.Companion.WATCH_LIST_TYPE
import com.churickoid.filmity.domain.entity.ActionsAndMovie
import com.churickoid.filmity.presentation.screen.BaseFragment
import com.churickoid.filmity.presentation.screen.findTopNavController
import com.churickoid.filmity.presentation.screen.info.InfoFragment.Companion.ARG_MOVIE
import com.churickoid.filmity.presentation.screen.info.InfoFragment.Companion.ARG_TITLE
import com.churickoid.filmity.presentation.tools.factory
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class ListFragment : BaseFragment() {

    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentListBinding.bind(view)
        binding.listTabLayout.getTabAt(viewModel.type.value)?.select()
            ?: throw IllegalArgumentException("Unknown tab")

        var listToTop = false


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.appbar_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                listToTop = true
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
            }
        }, viewLifecycleOwner, Lifecycle.State.STARTED)


        binding.listTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> viewModel.type.value = WATCH_LIST_TYPE
                    1 -> viewModel.type.value = RATED_LIST_TYPE
                }
                listToTop = true
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewModel.watchlistCounter.observe(viewLifecycleOwner) {
            binding.listTabLayout.getTabAt(0)!!.text = getString(R.string.watchlist, it)
        }

        viewModel.ratedCounter.observe(viewLifecycleOwner) {
            binding.listTabLayout.getTabAt(1)!!.text = getString(R.string.rated, it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            baseViewModel.color.collect { colorMain ->
                binding.listTabLayout.setBackgroundColor(colorMain)
                val adapter = MovieListAdapter(createItemListener(), colorMain)
                binding.moviesRecyclerView.adapter = adapter

                binding.moviesRecyclerView.itemAnimator = null
                binding.moviesRecyclerView.adapter = adapter
                viewModel.movieList.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    if (listToTop) {
                        binding.moviesRecyclerView.scrollToPosition(0)
                        listToTop = false
                    }
                }

            }


        }

        setupOrderDialogFragmentListener(parentFragmentManager)
        setupRatingDialogFragmentListener(parentFragmentManager) {}

    }

    private fun setupOrderDialogFragmentListener(manager: FragmentManager) {
        OrderDialogFragment.setupListener(manager, this) { order ->
            viewModel.order.value = order
        }
    }

    private fun createItemListener(): MovieListAdapter.ItemListener{
        return object : MovieListAdapter.ItemListener {
            override fun onChooseMovie(infoAndMovie: ActionsAndMovie) =
                findTopNavController().navigate(
                    R.id.infoFragment,
                    bundleOf(ARG_MOVIE to infoAndMovie, ARG_TITLE to infoAndMovie.movie.titleMain )
                )


            override fun onDeleteMovie(infoAndMovie: ActionsAndMovie) =
                viewModel.deleteMovieById(infoAndMovie.movie.id)

            override fun onChangeInfo(infoAndMovie: ActionsAndMovie) =
                showRatingDialogFragment(parentFragmentManager, infoAndMovie)


        }
    }


}