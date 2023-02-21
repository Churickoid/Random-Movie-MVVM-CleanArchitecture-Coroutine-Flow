package com.example.randommovie.presentation.screen.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentFilterBinding
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.presentation.screen.factory

class FilterFragment: Fragment() {
    private lateinit var binding: FragmentFilterBinding
    private val viewModel: FilterViewModel by activityViewModels{ factory() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFilterBinding.bind(view)

        binding.genresEditBox.setOnClickListener {
            viewModel.getGenresList()
        }
        viewModel.genres.observe(viewLifecycleOwner){
            showAlertDialog(it)
        }

    }

    private fun showAlertDialog(list:List<ItemFilter>){
        var checkboxes = booleanArrayOf()
        var names = arrayOf<String>()
        list.forEach{names+=it.name
        checkboxes+=it.isActive
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Genre")
            .setMultiChoiceItems(names,checkboxes) {_,index,isChecked ->


            }
            .setPositiveButton("Apply") { _, _ ->

                //updateUi()
            }

            .create()
        dialog.show()
    }
}
