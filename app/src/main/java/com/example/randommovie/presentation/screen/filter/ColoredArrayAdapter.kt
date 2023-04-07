package com.example.randommovie.presentation.screen.filter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.randommovie.R

class ColoredArrayAdapter(context: Context, recourse: Array<String>, private val color: Int) :
    ArrayAdapter<String>(context, R.layout.item_spinner, recourse) {
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val v = super.getView(position, convertView, parent)
        val text = v.findViewById(android.R.id.text1) as TextView
        text.setTextColor(color)
        return v
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v = super.getDropDownView(position, convertView, parent)
        val text = v.findViewById(android.R.id.text1) as TextView
        text.setTextColor(color)
        return v
    }
}
