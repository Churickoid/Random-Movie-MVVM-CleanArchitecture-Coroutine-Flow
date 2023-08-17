package com.churickoid.filmity.presentation.screen.tabs.filter

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.churickoid.filmity.R
import com.churickoid.filmity.domain.entity.ItemFilter
import com.churickoid.filmity.presentation.tools.parcelableArrayList

class FilterListDialogFragment : DialogFragment() {


    private val list: ArrayList<ItemFilter>
        get() = requireArguments().parcelableArrayList(ARG_LIST_ITEM)
            ?: throw IllegalArgumentException("Can't launch without list")


    private val requestKey: String
        get() = arguments?.getString(ARG_REQUEST_KEY)
            ?: throw IllegalArgumentException("Can't launch without key")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var checkboxes = booleanArrayOf()
        var names = arrayOf<String>()
        list.forEach {
            names += it.name
            checkboxes += it.isActive
        }

        return AlertDialog.Builder(requireContext()).setTitle(getString(R.string.choose))
            .setMultiChoiceItems(names, checkboxes) { _, index, isChecked ->
                list[index].isActive = isChecked
            }.setPositiveButton(getString(R.string.apply)) { _, _ ->
                callResult(list)
            }
            .setNeutralButton(getString(R.string.clear)) { _, _ ->
                list.forEach { it.isActive = false }
                callResult(list)
            }
            .create()

    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        callResult(list)
    }

    private fun callResult(list: ArrayList<ItemFilter>) {
        parentFragmentManager.setFragmentResult(
            requestKey, bundleOf(KEY_LIST_ITEM_RESPONSE to list)
        )
    }

    companion object {
        private val TAG = FilterListDialogFragment::class.java.simpleName
        private const val KEY_LIST_ITEM_RESPONSE = "KEY_LIST_ITEM_RESPONSE"

        private const val ARG_LIST_ITEM = "ARG_LIST_ITEM"
        private const val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"


        fun show(manager: FragmentManager, list: List<ItemFilter>, requestKey: String) {

            val dialogFragment = FilterListDialogFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_LIST_ITEM, list as ArrayList<ItemFilter>)
            args.putString(ARG_REQUEST_KEY, requestKey)
            dialogFragment.arguments = args
            dialogFragment.show(manager, TAG)

        }

        fun setupListener(
            manager: FragmentManager,
            requestKey: String,
            lifecycleOwner: LifecycleOwner,
            listener: (String, ArrayList<ItemFilter>) -> Unit
        ) {
            manager.setFragmentResultListener(
                requestKey,
                lifecycleOwner
            ) { _, result ->
                listener.invoke(
                    requestKey,
                    result.parcelableArrayList(KEY_LIST_ITEM_RESPONSE)
                        ?: throw IllegalArgumentException("Array List doesn't exist")
                )
            }
        }


    }

}
