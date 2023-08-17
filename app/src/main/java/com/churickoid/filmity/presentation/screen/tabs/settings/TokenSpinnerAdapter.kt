package com.churickoid.filmity.presentation.screen.tabs.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.churickoid.filmity.databinding.ItemTokenBinding
import com.churickoid.filmity.domain.entity.Token

class TokenSpinnerAdapter(
    private val tokenList: List<Token>,
    private val color: Int,
    private val deleteTokenAction: (Token)->Unit
): BaseAdapter(), View.OnClickListener {
    override fun getCount(): Int {
        return tokenList.size
    }

    override fun getItem(position: Int): Token {
        return tokenList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val binding = view?.tag as ItemTokenBinding? ?: createBinding(parent.context)

        val token = getItem(position)
        binding.tokenTextView.text = token.email
        binding.deleteTokenButton.tag = token
        binding.deleteTokenButton.visibility = if(token.isCustom) View.VISIBLE else View.INVISIBLE

        return binding.root
    }


    override fun onClick(v: View) {
        deleteTokenAction(v.tag as Token)
    }

    private fun createBinding(context: Context): ItemTokenBinding {
        val binding = ItemTokenBinding.inflate(LayoutInflater.from(context))
        binding.tokenTextView.setTextColor(color)
        binding.deleteTokenButton.setOnClickListener(this)
        binding.root.tag = binding
        return binding
    }
}