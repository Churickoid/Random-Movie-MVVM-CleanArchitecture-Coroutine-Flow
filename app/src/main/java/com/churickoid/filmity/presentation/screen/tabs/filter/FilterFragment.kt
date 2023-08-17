package com.churickoid.filmity.presentation.screen.tabs.filter

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.churickoid.filmity.R
import com.churickoid.filmity.databinding.FragmentFilterBinding
import com.churickoid.filmity.domain.entity.ItemFilter
import com.churickoid.filmity.domain.entity.SearchFilter
import com.churickoid.filmity.presentation.screen.BaseFragment
import com.churickoid.filmity.presentation.tools.factory
import com.google.android.material.slider.RangeSlider
import kotlinx.coroutines.launch


class FilterFragment : BaseFragment() {
    private lateinit var binding: FragmentFilterBinding
    private val viewModel: FilterViewModel by viewModels { factory() }
    private var isSelectionFromTouch = false
    private var orderPosition = 0
    private var typePosition = 0

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
            changeEditBoxState(REQUEST_KEY_GENRES, View.INVISIBLE, View.VISIBLE)
        }
        viewModel.genresEvent.observe(viewLifecycleOwner) {
            it?.getValue()?.let { list -> showListDialogFragment(list, REQUEST_KEY_GENRES) }
        }
        binding.countryEditBox.setOnClickListener {
            viewModel.getCountryList()
            changeEditBoxState(REQUEST_KEY_COUNTRIES, View.INVISIBLE, View.VISIBLE)
        }
        viewModel.countriesEvent.observe(viewLifecycleOwner) {
            it.getValue()?.let { list -> showListDialogFragment(list, REQUEST_KEY_COUNTRIES) }
        }

        viewModel.countryText.observe(viewLifecycleOwner) {
            binding.countryEditBox.text = it
            changeEditBoxState(REQUEST_KEY_COUNTRIES, View.VISIBLE, View.INVISIBLE)
        }
        viewModel.genreText.observe(viewLifecycleOwner) {
            binding.genresEditBox.text = it
            changeEditBoxState(REQUEST_KEY_GENRES, View.VISIBLE, View.INVISIBLE)
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

        binding.orderSpinner.setOnTouchListener { v, _ ->
            isSelectionFromTouch = true
            v.performClick()
            false
        }
        binding.typeSpinner.setOnTouchListener { v, _ ->
            isSelectionFromTouch = true
            v.performClick()
            false

        }
        binding.orderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?, p1: View?, position: Int, p3: Long
            ) {
                if (isSelectionFromTouch) {
                    viewModel.setOrderFilter(position)
                    isSelectionFromTouch = false
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?, p1: View?, position: Int, p3: Long
            ) {
                if (isSelectionFromTouch) {
                    viewModel.setTypeFilter(position)
                    isSelectionFromTouch = false
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }



        binding.defaultButton.setOnClickListener {
            viewModel.setDefaultFilter()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            toastError(it)
            changeEditBoxState(REQUEST_KEY_COUNTRIES, View.VISIBLE, View.INVISIBLE)
            changeEditBoxState(REQUEST_KEY_GENRES, View.VISIBLE, View.INVISIBLE)

        }

        viewModel.startFilter.observe(viewLifecycleOwner) {
            setupFilter(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            baseViewModel.color.collect { colorMain ->
                val colorSecond = 100 shl 24 or (colorMain and 0x00ffffff)

                setupSliderColor(binding.yearSlider, colorMain, colorSecond)
                setupSliderColor(binding.ratingSlider, colorMain, colorSecond)

                setupSpinner(binding.orderSpinner, R.array.orderFilter, colorMain)
                setupSpinner(binding.typeSpinner, R.array.type, colorMain)

                binding.defaultButton.setBackgroundColor(colorMain)

                binding.orderSpinner.setSelection(orderPosition)
                binding.typeSpinner.setSelection(typePosition)


            }
        }

        setupListDialogListener()
    }


    private fun setupSliderColor(slider: RangeSlider, colorMain: Int, colorSecond: Int) {
        val colorState = ColorStateList.valueOf(colorMain)
        slider.thumbTintList = colorState
        slider.trackActiveTintList = colorState
        slider.trackInactiveTintList = ColorStateList.valueOf(colorSecond)
        slider.tickInactiveTintList = colorState
        slider.haloTintList = colorState
    }

    private fun setupSpinner(spinner: Spinner, arrayId: Int, color: Int) {
        spinner.adapter = ColoredArrayAdapter(
            requireContext(),
            resources.getStringArray(arrayId),
            color
        )
    }

    private fun setupFilter(filter: SearchFilter) {
        with(binding) {
            yearSlider.setValues(filter.yearBottom.toFloat(), filter.yearTop.toFloat())
            ratingSlider.setValues(
                filter.ratingBottom.toFloat(), filter.ratingTop.toFloat()
            )
            yearTextView.text =
                getString(R.string.year_with_ints, filter.yearBottom, filter.yearTop)
            orderPosition = filter.order.ordinal
            typePosition = filter.type.ordinal
            orderSpinner.setSelection(orderPosition)
            typeSpinner.setSelection(typePosition)


        }
    }


    private fun showListDialogFragment(list: List<ItemFilter>, requestKey: String) {
        FilterListDialogFragment.show(parentFragmentManager, list, requestKey)
    }

    private fun setupListDialogListener() {
        val listener: (String, ArrayList<ItemFilter>) -> Unit = { requestKey, list ->
            viewModel.listDialogHandler(requestKey, list)
        }
        FilterListDialogFragment.setupListener(
            parentFragmentManager, REQUEST_KEY_GENRES, this, listener
        )
        FilterListDialogFragment.setupListener(
            parentFragmentManager, REQUEST_KEY_COUNTRIES, this, listener
        )
    }

    private fun changeEditBoxState(requestKey: String, stateView: Int, stateProgressBar: Int) {
        when (requestKey) {
            REQUEST_KEY_GENRES -> {
                binding.genresEditBox.visibility = stateView
                binding.genresProgressBar.visibility = stateProgressBar
            }
            REQUEST_KEY_COUNTRIES -> {
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

