package com.professionalandroid.apps.capston_meeting.applyPage

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.*
import com.professionalandroid.apps.capston_meeting.retrofit.ConnectRetrofit
import com.professionalandroid.apps.capston_meeting.retrofit.board
import com.professionalandroid.apps.capston_meeting.retrofit.boards
import kotlinx.android.synthetic.main.fragment_apply_filter.view.*
import kotlinx.android.synthetic.main.fragment_apply_page.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyPage : Fragment(),
    RecyclerAdapter.OnListItemSelelctedInterface,
    ApplyFilter.ApplyFilterSelectedInterface {

    lateinit var retrofitService: ConnectRetrofit
    lateinit var mapplyfilter: ApplyFilter
    lateinit var mRecyclerView: RecyclerView
    private var mRecyclerAdapter: RecyclerAdapter? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mRecyclerAdapter = RecyclerAdapter(context, this, meetinglist)
        retrofitService = ConnectRetrofit(context)
    }

    var imageview_img1:RoundedImageView? = null
    var imageview_title:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val connect_server = retrofitService.retrofitService()
        // retrofit 서버연결
        connect_server.requestSearchBoard().enqueue(object: Callback<boards> {
            override fun onFailure(call: Call<boards>, t: Throwable) {
                Log.d("test","서버연결 실패 BoardActivity")
            }

            override fun onResponse(call: Call<boards>, response: Response<boards>) {
                val boards: boards = response.body()!!

                val templist = boards._embedded.board_list
                for(i in templist.indices){
                    meetinglist.add(
                        list_item_data(
                            templist[i].idx,
                            templist[i].title,
                            templist[i].img1,
                            templist[i].img2,
                            templist[i].img3,
                            templist[i].tag1,
                            templist[i].tag2,
                            templist[i].tag3,
                            templist[i].location,
                            templist[i].num_type,
                            templist[i].gender,
                            templist[i].createdDate,
                            templist[i].updatedDate,
                            templist[i].age,
                            templist[i]._links
                        )
                    )
                }
                mRecyclerAdapter?.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_apply_page, container, false)
        mRecyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        mRecyclerView.layoutManager = GridLayoutManager(context,2)
        mRecyclerView.adapter = mRecyclerAdapter

        view.apply_filter.apply {
            setOnClickListener {
                mapplyfilter =
                    ApplyFilter(
                        this@ApplyPage
                    )
                mapplyfilter.show((activity as MainActivity).supportFragmentManager, "apply_filter")
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerAdapter?.notifyDataSetChanged()
    }

    fun setmeeting(item:MutableList<list_item_data>){
        for(i in item){
            if(!meetinglist.contains(i)){
                meetinglist.add(i)
                mRecyclerAdapter?.notifyItemInserted(meetinglist.indexOf(i))
            }
        }
    }

    override fun onItemSelected(v: View, position: Int) {
        val viewholder: RecyclerAdapter.ViewHolder = mRecyclerView.findViewHolderForAdapterPosition(position) as RecyclerAdapter.ViewHolder
        imageview_img1 = viewholder.img1!!
        imageview_title  = viewholder.title?.text.toString()

        val detailpage = DetailPage()
        val bundle = Bundle()

        Log.d("test", "${viewholder.index}")
        bundle.putString("href", meetinglist[viewholder.index!!]._links?.board?.href)
        detailpage.arguments = bundle
        (activity as MainActivity).move_next_fragment(detailpage)
    }

    override fun applyfilter_listener(v: View) {
        v.filter_btn.setOnClickListener {

            val spinner_location = v.spinner_location.selectedItem.toString()
            val radiobutton_num_type = v.findViewById<RadioButton>(v.radioGroup.checkedRadioButtonId).text.toString()[0].toString()
            val seekbar_age = v.filter_age.text.toString()
            Log.d("test", radiobutton_num_type)
            meetinglist.clear()

            Log.d("test", "meetinglist 초기화")
            Log.d("test", seekbar_age)
            val connect_server = retrofitService.retrofitService()
            // retrofit 서버연결
            connect_server.requestSearchBoard2(spinner_location, radiobutton_num_type, seekbar_age).enqueue(object: Callback<List<board>> {
                override fun onFailure(call: Call<List<board>>, t: Throwable) {
                    Log.d("test","서버연결 실패 BoardActivity")
                }

                override fun onResponse(call: Call<List<board>>, response: Response<List<board>>) {
                    if (response.body()?.size != 0) {
                        val boards: List<board> = response.body()!!
                        Log.d("test", "${response.body()}")
                        Log.d("test", "${boards}, ${boards[0].title}")
                        val templist = boards
                        for (i in templist.indices) {
                            meetinglist.add(
                                list_item_data(
                                    templist[i].idx,
                                    templist[i].title,
                                    templist[i].img1,
                                    templist[i].img2,
                                    templist[i].img3,
                                    templist[i].img1,
                                    templist[i].img2,
                                    templist[i].img3,
                                    templist[i].location,
                                    templist[i].num_type,
                                    templist[i].gender,
                                    templist[i].createdDate,
                                    templist[i].updatedDate,
                                    templist[i].age,
                                    templist[i]._links
                                )
                            )
                        }
                        mRecyclerAdapter?.notifyDataSetChanged()
                    }
                    else{
                        Toast.makeText(activity as MainActivity, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            })

        }
    }

}