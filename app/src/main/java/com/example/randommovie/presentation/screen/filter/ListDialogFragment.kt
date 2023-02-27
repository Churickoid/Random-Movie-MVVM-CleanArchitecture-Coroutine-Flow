package com.example.randommovie.presentation.screen.filter

import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.randommovie.R
import com.example.randommovie.domain.entity.ItemFilter

class ListDialogFragment : DialogFragment() {


    private val list: ArrayList<ItemFilter>
        get() = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> arguments?.getParcelableArrayList(
                ARG_LIST_ITEM, ItemFilter::class.java
            ) ?: throw IllegalArgumentException("Can't launch without list")
            else -> @Suppress("DEPRECATION")
            arguments?.getParcelableArrayList(
                ARG_LIST_ITEM
            ) ?: throw IllegalArgumentException("Can't launch without list")
        }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var checkboxes = booleanArrayOf()
        var names = arrayOf<String>()


        list.forEach {
            names += it.name
            checkboxes += it.isActive
        }

        return AlertDialog.Builder(requireContext()).setTitle("Choose Items")
            .setMultiChoiceItems(names, checkboxes) { _, index, isChecked ->
                list[index].isActive = isChecked
            }.setPositiveButton("Apply") { _, _ ->
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY, bundleOf(KEY_LIST_ITEM_RESPONSE to list)
                )
            }.create()

    }



    companion object {
        private val TAG = ListDialogFragment::class.java.simpleName
        private val KEY_LIST_ITEM_RESPONSE = "KEY_LIST_ITEM_RESPONSE"

        private const val ARG_LIST_ITEM = "ARG_LIST_ITEM"

        val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(manager: FragmentManager, list: List<ItemFilter>) {
            val dialogFragment = ListDialogFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_LIST_ITEM, list as ArrayList<ItemFilter>)
            dialogFragment.arguments = args
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (ArrayList<ItemFilter>) -> Unit
        ) {
            manager.setFragmentResultListener(
                REQUEST_KEY,
                lifecycleOwner,
                FragmentResultListener { _, result ->
                    listener.invoke(
                        when {
                            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> result.getParcelableArrayList(
                                KEY_LIST_ITEM_RESPONSE, ItemFilter::class.java
                            ) ?: throw IllegalArgumentException("Can't launch without list")
                            else -> @Suppress("DEPRECATION")
                            result.getParcelableArrayList(
                                KEY_LIST_ITEM_RESPONSE
                            ) ?: throw IllegalArgumentException("Can't launch without list")
                        }
                    )
                })
        }


    }

}
