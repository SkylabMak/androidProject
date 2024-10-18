package com.example.androidproject

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class CustomArrayAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    private var mainPosition: Int = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)

        if (position == mainPosition) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary))
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)

        if (position == mainPosition) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary))
        }

        return view
    }
}


