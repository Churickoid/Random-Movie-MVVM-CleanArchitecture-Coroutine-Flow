package com.example.randommovie.presentation.screen.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentFilterBinding
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.SearchFilter
import com.example.randommovie.presentation.screen.BaseFragment
import com.example.randommovie.presentation.tools.factory
import com.google.android.material.slider.RangeSlider

class FilterFragment : BaseFragment() {
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

        binding.genresEditBox.setOnClickListener {
            viewModel.getGenresList()
            changeEditBoxState(REQUEST_KEY_GENRES,View.INVISIBLE,View.VISIBLE)
        }
        viewModel.genres.observe(viewLifecycleOwner) {
            it?.getValue()?.let { list ->
                showListDialogFragment(list,REQUEST_KEY_GENRES)
            }
            changeEditBoxState(REQUEST_KEY_GENRES,View.VISIBLE,View.INVISIBLE)
        }
        binding.countryEditBox.setOnClickListener {
            viewModel.getCountryList()
            changeEditBoxState(REQUEST_KEY_COUNTRIES,View.INVISIBLE,View.VISIBLE)
        }
        viewModel.countries.observe(viewLifecycleOwner) {
            it?.getValue()?.let { list ->
                showListDialogFragment(list, REQUEST_KEY_COUNTRIES)
            }
            changeEditBoxState(REQUEST_KEY_COUNTRIES,View.VISIBLE,View.INVISIBLE)
        }

        viewModel.countryText.observe(viewLifecycleOwner){
            binding.countryEditBox.text = it
        }
        viewModel.genreText.observe(viewLifecycleOwner){
            binding.genresEditBox.text = it
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
                R.string.year_with_ints, slider.values[0].toInt(), slider.values[1].toInt()
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
        binding.defaultButton.setOnClickListener {
            setupFilter(viewModel.getDefaultFilterValue())
        }

        setupFilter(viewModel.getDefaultFilterValue())
        setupListDialogListener()
    }



    private fun setupFilter(filter: SearchFilter) {
        with(binding) {
            yearSlider.setValues(filter.yearBottom.toFloat(), filter.yearTop.toFloat())
            ratingSlider.setValues(
                filter.ratingBottom.toFloat(),
                filter.ratingTop.toFloat()
            )
            yearTextView.text =
                getString(R.string.year_with_ints, filter.yearBottom, filter.yearTop)

            orderSpinner.setSelection(0)
            typeSpinner.setSelection(0)

            countryEditBox.text= ""
            genresEditBox.text= ""
        }
    }


    private fun showListDialogFragment(list: List<ItemFilter>,requestKey: String) {
        FilterListDialogFragment.show(parentFragmentManager, list,requestKey)
    }

    private fun setupListDialogListener() {
        val listener: (String ,ArrayList<ItemFilter>) -> Unit = { requestKey,list ->
            viewModel.listDialogHandler(requestKey,list)
        }
        FilterListDialogFragment.setupListener(parentFragmentManager,  REQUEST_KEY_GENRES, this,listener)
        FilterListDialogFragment.setupListener(parentFragmentManager,  REQUEST_KEY_COUNTRIES, this,listener)
    }

    private fun changeEditBoxState(requestKey: String, stateView: Int, stateProgressBar: Int){
        when(requestKey){
            REQUEST_KEY_GENRES -> {
                binding.genresEditBox.visibility = stateView
                binding.genresProgressBar.visibility = stateProgressBar
            }
            REQUEST_KEY_COUNTRIES ->{
                binding.countryEditBox.visibility = stateView
                binding.countryProgressBar.visibility = stateProgressBar
            }
        }
    }
    companion object {
        const val REQUEST_KEY_GENRES = "REQUEST_KEY_GENRES"
        const val REQUEST_KEY_COUNTRIES = "REQUEST_KEY_COUNTRIES"
    }
}
