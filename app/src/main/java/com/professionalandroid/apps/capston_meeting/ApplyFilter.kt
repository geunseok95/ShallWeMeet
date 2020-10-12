package com.professionalandroid.apps.capston_meeting

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_apply_filter.view.*

class ApplyFilter(
    applyfilter_listener: ApplyFilterSelectedInterface
) : BottomSheetDialogFragment() {

    interface ApplyFilterSelectedInterface{
        fun applyfilter_listener(v:View)
    }
    private var mapplyfilter_listener: ApplyFilterSelectedInterface? = applyfilter_listener
    lateinit var spinneradapter:ArrayAdapter<String>



    override fun onAttach(context: Context) {
        super.onAttach(context)
        val items = resources.getStringArray(R.array.location)
        spinneradapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, items)
    }

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_apply_filter, container, false)
        mapplyfilter_listener?.applyfilter_listener(view)
        view.spinner_location.adapter = spinneradapter
        view.spinner_location.prompt = "지역을 선택하세요"
        return view
    }

}