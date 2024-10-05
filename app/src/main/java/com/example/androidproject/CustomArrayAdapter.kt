package com.example.androidproject

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class CustomArrayAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    var selectedPosition: Int = 0 // ตัวแปรเพื่อเก็บตำแหน่งที่ถูกเลือก, เริ่มต้นเป็น -1 เพื่อให้ไม่มีค่าในตอนเริ่มต้น

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)

        // เปลี่ยนสีเฉพาะตำแหน่งที่ถูกเลือก
        if (position == selectedPosition) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))  // สีสำหรับตัวเลือกที่เลือก
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary))  // สีปกติสำหรับตัวเลือกอื่น
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)

        // เปลี่ยนสีเฉพาะตำแหน่งที่ถูกเลือก
        if (position == selectedPosition) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))  // สีสำหรับตัวเลือกที่เลือก
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary))  // สีปกติสำหรับตัวเลือกอื่น
        }

        return view
    }
}


