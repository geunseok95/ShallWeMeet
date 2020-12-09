package com.professionalandroid.apps.capston_meeting.src.applyPage

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.professionalandroid.apps.capston_meeting.R
import kotlinx.android.synthetic.main.fragment_apply_filter.view.*


class ApplyFilter(
    applyfilter_listener: ApplyFilterSelectedInterface
) : BottomSheetDialogFragment() {

    lateinit var spinneradapter1: ArrayAdapter<String>
    lateinit var spinneradapter2: ArrayAdapter<String>
    var location2Array = arrayOf<String>()

    interface ApplyFilterSelectedInterface{
        fun applyfilter_listener(v:View)
    }
    private var mapplyfilter_listener: ApplyFilterSelectedInterface? = applyfilter_listener
    lateinit var spinneradapter:ArrayAdapter<String>


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val items = resources.getStringArray(R.array.location)
        val location1Array = resources.getStringArray(R.array.location)
        location2Array = arrayOf("상관없음")
        spinneradapter1 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location1Array)
        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
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
        view.spinner_location1.apply {
            adapter = spinneradapter1
            prompt = "지역을 선택하세요"
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    (p0?.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                    if(spinneradapter1.getItem(p2).equals("상관없음")){
                        location2Array = arrayOf("상관없음")
                        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
                        view.spinner_location2.apply {
                            adapter = spinneradapter2
                            prompt = "상세주소를 선택하세요"
                            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                }

                                override fun onItemSelected(
                                    p0: AdapterView<*>?,
                                    p1: View?,
                                    p2: Int,
                                    p3: Long
                                ) {
                                    (p0?.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                                }

                            }

                        }
                    }

                    else if(spinneradapter1.getItem(p2).equals("서울")){
                        location2Array = resources.getStringArray(R.array.Seoul)
                        Log.d("test", location2Array.joinToString())
                        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
                        view.spinner_location2.apply {
                            adapter = spinneradapter2
                            prompt = "상세주소를 선택하세요"
                            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                }

                                override fun onItemSelected(
                                    p0: AdapterView<*>?,
                                    p1: View?,
                                    p2: Int,
                                    p3: Long
                                ) {
                                    (p0?.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                                }

                            }
                        }
                    }
                    else if(spinneradapter1.getItem(p2).equals("광주")){
                        location2Array = resources.getStringArray(R.array.Gwangju)
                        Log.d("test", location2Array.joinToString())
                        spinneradapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, location2Array)
                        view.spinner_location2.apply {
                            adapter = spinneradapter2
                            prompt = "상세주소를 선택하세요"
                            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                }

                                override fun onItemSelected(
                                    p0: AdapterView<*>?,
                                    p1: View?,
                                    p2: Int,
                                    p3: Long
                                ) {
                                    (p0?.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                                }

                            }
                        }
                    }
                }
            }
        }


        view.filter_age_seekbar.apply {
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    view.filter_age.text = "$p1"
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    view.filter_age.text = "${p0?.progress}"
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    view.filter_age.text = "${p0?.progress}"
                }

            })
        }

        return view
    }

}