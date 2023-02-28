package com.example.randommovie.presentation.screen.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentFilterBinding
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.presentation.tools.factory
import com.google.android.material.slider.RangeSlider

class FilterFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding
    private val viewModel: FilterViewModel by viewModels { factory() }

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
            it.getValue()?.let { list ->
                showListDialogFragment(list,REQUEST_KEY_GENRES)
            }
        }
        binding.countryEditBox.setOnClickListener {
            viewModel.getCountryList()
        }
        viewModel.countries.observe(viewLifecycleOwner) {
            it.getValue()?.let { list ->
                Log.e("!!!", list.toString())
                showListDialogFragment(list, REQUEST_KEY_COUNTRIES)
            }
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
        setupListDialogListener()
    }

    private fun setSliders() {
        val filter = viewModel.getDefaultFilterValue()
        binding.yearSlider.setValues(filter.yearBottom.toFloat(), filter.yearTop.toFloat())
        binding.ratingSlider.setValues(filter.ratingBottom.toFloat(), filter.ratingTop.toFloat())
        binding.yearTextView.text = getString(R.string.year, filter.yearBottom, filter.yearTop)

    }


    private fun showListDialogFragment(list: List<ItemFilter>,requestKey: String) {
        ListDialogFragment.show(parentFragmentManager, list,requestKey)
    }

    private fun setupListDialogListener() {

        val listener: (String ,ArrayList<ItemFilter>) -> Unit = { requestKey,list ->
            var checkedNames = ""
            val ids = mutableListOf<Int>()
            list.forEach {
                if (it.isActive) {
                    checkedNames += "${it.name}, "
                    ids += it.id
                }
            }
            checkedNames = checkedNames.dropLast(2)
            saveResultAndChangeEditBox(requestKey,checkedNames,ids)
        }
        ListDialogFragment.setupListener(parentFragmentManager,  REQUEST_KEY_GENRES, this,listener)
        ListDialogFragment.setupListener(parentFragmentManager,  REQUEST_KEY_COUNTRIES, this,listener)
    }
    private fun saveResultAndChangeEditBox(key:String,text: String?, ids: List<Int>) {
        when (key){
            REQUEST_KEY_GENRES -> {
                binding.genresEditBox.text = text
                viewModel.setGenresFilter(ids)
            }
            REQUEST_KEY_COUNTRIES -> {
                binding.countryEditBox.text = text
                viewModel.setCountryFilter(ids)
            }
            else -> throw Exception("Unknown Dialog Type")
        }
    }


    companion object {

        const val REQUEST_KEY_GENRES = "REQUEST_KEY_GENRES"
        const val REQUEST_KEY_COUNTRIES = "REQUEST_KEY_COUNTRIES"
    }
}
