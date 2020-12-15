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
import com.professionalandroid.apps.capston_meeting.src.MainActivity
import com.professionalandroid.apps.capston_meeting.src.MainActivity.Companion.gender
import com.professionalandroid.apps.capston_meeting.src.MainActivity.Companion.user
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

    var location1 = "상관없음"
    var location2 = "상관없음"
    var num_type = "상관없음"
    var age = "상관없음"
    var date = "상관없음"

    private var page = 0 // 현재 페이지
    private var size = "10" // 한번에 가져올 아이템의 수
    var boards = mutableListOf<ApplyResponse?>()

    lateinit var mapplyfilter: ApplyFilter
    lateinit var mRecyclerView: RecyclerView
    private var mRecyclerAdapter: RecyclerAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        val gm = GridLayoutManager(context,2)
        mRecyclerView = view.recyclerview
        mRecyclerView.layoutManager = gm
        mRecyclerAdapter = RecyclerAdapter(context!!, this, boards, gm)
        mRecyclerAdapter?.setRecyclerView(mRecyclerView)
        mRecyclerView.adapter = mRecyclerAdapter
        page = 0
        boards.clear()

        view.apply_filter.apply {
            setOnClickListener {
                mapplyfilter =
                    ApplyFilter(
                        this@ApplyPage
                    )
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPosts()
    }

    // 최초로 넣어줄 데이터를 load 한다
    private fun loadPosts(){
        mApplyPageService.searchBoard(getPage(), size, location1, location2, num_type, age, user, date, gender)
    }

    override fun loadmore(new_boards:List<ApplyResponse>?){
        if(new_boards?.size != 0) {
            this.boards.addAll(new_boards!!)
            mRecyclerAdapter?.notifyDataSetChanged()
            mRecyclerAdapter?.isMoreLoading = false
        }
        else{

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

    override fun onLoadMore() {
        mApplyPageService.searchBoard(getPage(), size, location1, location2, num_type, age, user, date, gender)
    }

    override fun onItemSelected(v: View, position: Int) {
        val viewholder: RecyclerAdapter.ViewHolder = mRecyclerView.findViewHolderForAdapterPosition(position) as RecyclerAdapter.ViewHolder

        val detailpage = DetailPage().apply{
            arguments = Bundle().apply {
                putLong("href", boards[viewholder.index!!]!!.idx)
            }
        }

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
            // retrofit 서버연결
            page = 0
            location1 = v.spinner_location1.selectedItem.toString()
            location2 = v.spinner_location2.selectedItem.toString()
            num_type = v.findViewById<RadioButton>(v.radioGroup.checkedRadioButtonId).text.toString()
            age = v.filter_age.text.toString()
            this.boards.clear()
            loadPosts()
        }
    }
}