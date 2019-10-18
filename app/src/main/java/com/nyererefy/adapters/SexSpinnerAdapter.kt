package com.nyererefy.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.nyererefy.R
import com.nyererefy.graphql.type.Sex

val sexList: MutableList<Sex>
    get() {
        val list = mutableListOf<Sex>()

        for (i in Sex.values()) {
            if (i != Sex.`$UNKNOWN`) {
                list.add(i)
            }
        }
        return list
    }

class SexSpinnerAdapter(val context: Context) : BaseAdapter() {
    private var inflater: LayoutInflater = LayoutInflater.from(context)
    private val list = sexList

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val v: View = inflater.inflate(R.layout.spinner_sex, parent, false)

        val title: TextView = v.findViewById(R.id.title)

        val text = when (list[position]) {
            Sex.MALE -> context.getString(R.string.male)
            Sex.FEMALE -> context.getString(R.string.female)
            else -> context.getString(R.string.other)
        }

        title.text = text
        return v
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.size
    }
}
