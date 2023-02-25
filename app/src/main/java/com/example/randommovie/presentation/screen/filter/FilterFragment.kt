package com.example.randommovie.presentation.screen.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentFilterBinding
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.presentation.screen.factory
import com.google.android.material.slider.RangeSlider

class FilterFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding
    private val viewModel: FilterViewModel by activityViewModels { factory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFilterBinding.bind(view)

        setSliders()

        binding.genresEditBox.setOnClickListener {
            viewModel.getGenresList()
        }
        viewModel.genres.observe(viewLifecycleOwner) {
            showListDialog(it, DIALOG_GENRES)
        }
        binding.countryEditBox.setOnClickListener {
            viewModel.getCountryList()
        }
        viewModel.countries.observe(viewLifecycleOwner) {
            showListDialog(it, DIALOG_COUNTRY)
        }

        binding.yearSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                viewModel.setYearFilter(slider.values[0].toInt(), slider.values[1].toInt())
            }
        })

        binding.yearSlider.addOnChangeListener { slider, _, _ ->
            binding.yearTextView.text = getString(
                R.string.year, slider.values[0].toInt(), slider.values[1].toInt()
            )
        }

        binding.ratingSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                viewModel.setRatingFilter(slider.values[0].toInt(), slider.values[1].toInt())
            }
        })

        binding.orderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                viewModel.setOrderFilter(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                viewModel.setTypeFilter(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

    }

    private fun setSliders() {
        val filter = viewModel.getDefaultFilterValue()
        binding.yearSlider.setValues(filter.yearBottom.toFloat(), filter.yearTop.toFloat())
        binding.ratingSlider.setValues(filter.ratingBottom.toFloat(), filter.ratingTop.toFloat())
        binding.yearTextView.text = getString(R.string.year, filter.yearBottom, filter.yearTop)

    }

    private fun showListDialog(list: List<ItemFilter>, dialogType: Int) {
        var checkboxes = booleanArrayOf()
        var names = arrayOf<String>()
        list.forEach {
            names += it.name
            checkboxes += it.isActive
        }

        val dialog = AlertDialog.Builder(requireContext()).setTitle("Choose Items")
            .setMultiChoiceItems(names, checkboxes) { _, index, isChecked ->
                list[index].isActive = isChecked
            }.setPositiveButton("Apply") { _, _ ->
                saveEditBoxResult(dialogType, list)
            }.create()
        dialog.show()

    }

    private fun saveEditBoxResult(dialogType: Int, list: List<ItemFilter>) {
        var checkedNames = ""
        val ids = mutableListOf<Int>()
        list.forEach {
            if (it.isActive) {
                checkedNames += "${it.name}, "
                ids += it.id
            }
        }
        checkedNames = checkedNames.dropLast(2)

        when (dialogType) {
            DIALOG_GENRES -> {
                binding.genresEditBox.text = checkedNames
                viewModel.setGenresFilter(ids)
            }
            DIALOG_COUNTRY -> {
                binding.countryEditBox.text = checkedNames
                viewModel.setCountryFilter(ids)
            }
        }
    }

    companion object {

        const val DIALOG_GENRES = 0
        const val DIALOG_COUNTRY = 1
    }
}
