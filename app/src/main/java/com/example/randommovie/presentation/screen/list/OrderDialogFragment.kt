package com.example.randommovie.presentation.screen.list

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.example.randommovie.R
import com.example.randommovie.domain.ListRepository.Companion.ALPHABET_ORDER
import com.example.randommovie.domain.ListRepository.Companion.QUEUE_ORDER
import com.example.randommovie.domain.ListRepository.Companion.RATING_ORDER
import com.example.randommovie.domain.ListRepository.Companion.USER_RATING_ORDER
import com.example.randommovie.domain.ListRepository.Companion.YEAR_ORDER

class OrderDialogFragment : DialogFragment() {

    private val order: Int
        get() = requireArguments().getInt(ARG_ORDER)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var chosenOrder = order
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.order_by)
            .setSingleChoiceItems(R.array.orderList, chosenOrder) { _, id ->
                chosenOrder = id
                parentFragmentManager.setFragmentResult(
                    TAG,
                    bundleOf(KEY_ORDER_RESPONSE to chosenOrder)
                )
                dismiss()
            }
            .create()
    }

    companion object {
        private val TAG = OrderDialogFragment::class.java.simpleName
        private const val KEY_ORDER_RESPONSE = "KEY_ORDER_RESPONSE"
        private const val ARG_ORDER = "ARG_ORDER"


        fun show(manager: FragmentManager, volume: Int) {
            val dialogFragment = OrderDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_ORDER to volume)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (Int) -> Unit
        ) {
            manager.setFragmentResultListener(TAG, lifecycleOwner) { _, result ->
                listener.invoke(result.getInt(KEY_ORDER_RESPONSE))
            }
        }

    }

}