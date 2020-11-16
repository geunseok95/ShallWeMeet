package com.professionalandroid.apps.capston_meeting.src.applyPage

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
import com.professionalandroid.apps.capston_meeting.*
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.src.applyPage.interfaces.ApplyPageView
import com.professionalandroid.apps.capston_meeting.src.applyPage.models.ApplyResponse
import com.professionalandroid.apps.capston_meeting.src.applyPage.models.Bookmark
import com.professionalandroid.apps.capston_meeting.src.detailPage.DetailPage
import kotlinx.android.synthetic.main.fragment_apply_filter.view.*
import kotlinx.android.synthetic.main.fragment_apply_page.view.*
import kotlinx.android.synthetic.main.list_item2.view.*

class ApplyPage : Fragment(),
    ApplyPageView,
    RecyclerAdapter.OnListItemSelelctedInterface,
    ApplyFilter.ApplyFilterSelectedInterface {

    lateinit var mApplyPageService: ApplyPageService

    private var isNext = false // 다음페이지 유무
    private var page = 0 // 현재 페이지
    private var size = "10" // 한번에 가져올 아이템의 수
    var boards = mutableListOf<ApplyResponse?>()

    lateinit var mapplyfilter: ApplyFilter
    lateinit var mRecyclerView: RecyclerView
    private var mRecyclerAdapter: RecyclerAdapter? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mRecyclerAdapter = RecyclerAdapter(context, this, boards)
        mApplyPageService = ApplyPageService(this, context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_apply_page, container, false)
        mRecyclerView = view.recyclerview
        mRecyclerView.layoutManager = GridLayoutManager(context,2)
        mRecyclerView.adapter = mRecyclerAdapter
        page = 0

        loadPosts("상관없음", "상관없음", "상관없음", "상관없음", "상관없음")

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


    // 최초로 넣어줄 데이터를 load 한다
    private fun loadPosts(location1: String, location2: String, num_type :String, age: String, date: String){
        mApplyPageService.searchBoard(getPage(), size, location1, location2, num_type, age, user, date, "female")
    }



    override fun setBoard(new_boards:List<ApplyResponse>?){
        if(new_boards?.size != 0) {
            this.boards.apply {
                clear()
                addAll(new_boards!!)
            }
            mRecyclerAdapter?.notifyDataSetChanged()
        }
        else{
            Toast.makeText(activity as MainActivity, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show()
        }

    }


    fun addBoards(boards: List<ApplyResponse>){
        this.boards.addAll(boards)
        mRecyclerAdapter?.notifyDataSetChanged()
    }


    private fun getPage(): String{
        page++
        return page.toString()
    }

    private fun hasNextPage(): Boolean{
        return isNext
    }

    private fun setHasNextPage(b: Boolean){
        isNext = b
    }


    override fun onItemSelected(v: View, position: Int) {
        val viewholder: RecyclerAdapter.ViewHolder = mRecyclerView.findViewHolderForAdapterPosition(position) as RecyclerAdapter.ViewHolder

        val detailpage =
            DetailPage()
        val bundle = Bundle()

        Log.d("test_position", position.toString())
        Log.d("test", "${viewholder.index}")
        bundle.putLong("href", boards[viewholder.index!!]!!.idx)
        detailpage.arguments = bundle
        (activity as MainActivity).move_next_fragment(detailpage)
    }

    override fun onStarChecked(v: View, position: Int) {
        val viewholder: RecyclerAdapter.ViewHolder = mRecyclerView.findViewHolderForAdapterPosition(position) as RecyclerAdapter.ViewHolder

        // 체크시 즐겨찾기 추가, 체크 해제시 즐겨찾기 제거
        if(v.star_btn.isChecked){

            val data = Bookmark(user, boards[viewholder.index!!]?.idx!!)
            mApplyPageService.addBookmark(data)
        }
        else{
            mApplyPageService.deleteBookmark(user, boards[viewholder.index!!]?.idx!!)
        }
    }

    override fun applyfilter_listener(v: View) {
        v.filter_btn.setOnClickListener {
            val spinner_location1 = v.spinner_location.selectedItem.toString()
            val radiobutton_num_type = v.findViewById<RadioButton>(v.radioGroup.checkedRadioButtonId).text.toString()[0].toString()
            val seekbar_age = v.filter_age.text.toString()

            // retrofit 서버연결
            page = 0
            loadPosts(spinner_location1, "상관없음", radiobutton_num_type, seekbar_age, "상관없음")
        }
    }
}