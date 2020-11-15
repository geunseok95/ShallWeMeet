package com.professionalandroid.apps.capston_meeting.applyPage

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.professionalandroid.apps.capston_meeting.retrofit.*
import kotlinx.android.synthetic.main.fragment_apply_filter.view.*
import kotlinx.android.synthetic.main.fragment_apply_page.*
import kotlinx.android.synthetic.main.fragment_apply_page.view.*
import kotlinx.android.synthetic.main.list_item2.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyPage : Fragment(),
    RecyclerAdapter.OnListItemSelelctedInterface,
    ApplyFilter.ApplyFilterSelectedInterface {

    private var isNext = false // 다음페이지 유무
    private var page = 0 // 현재 페이지
    private var size = "10" // 한번에 가져올 아이템의 수
    var boards = mutableListOf<board?>()

    lateinit var connect_server: RetrofitService
    lateinit var mapplyfilter: ApplyFilter
    lateinit var mRecyclerView: RecyclerView
    private var mRecyclerAdapter: RecyclerAdapter? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mRecyclerAdapter = RecyclerAdapter(context, this, boards)
        connect_server = ConnectRetrofit(context).retrofitService()
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
        initScrollListener()

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
        // retrofit 서버연결
        connect_server.requestSearchBoard(getPage(), size, location1, location2, num_type, age, user, date, "남").enqueue(object: Callback<List<board>> {
            override fun onFailure(call: Call<List<board>>, t: Throwable) {
                Log.d("test","서버연결 실패 BoardActivity")
            }

            override fun onResponse(call: Call<List<board>>, response: Response<List<board>>) {
                Log.d("test", response.body().toString())
                if(response.body()?.size != 0) {
                    val new_boards: List<board> = response.body()!!
                    setBoard(new_boards)
                }
                else{
                    Toast.makeText(activity as MainActivity, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show()
                }

            }
        })

    }

    // 리사이클러뷰에 더 보여줄 데이터를 로드하는 경우
    private fun loadMorePosts(){
        //mRecyclerAdapter.setLoadingView(true)

        Handler(Looper.getMainLooper()).postDelayed({
            connect_server.requestSearchBoard(getPage(), size, "상관없음", "상관없음", "상관없음", "상관업음", user, "상관없음", "남")
                .enqueue(object : Callback<List<board>> {
                    override fun onFailure(call: Call<List<board>>, t: Throwable) {
                        Log.d("test", "서버연결 실패 BoardActivity")
                    }

                    override fun onResponse(
                        call: Call<List<board>>,
                        response: Response<List<board>>
                    ) {
                        val boards: List<board> = response.body()!!
                        addBoards(boards)
                    }
                })
        },1000)
    }

    //
    private fun initScrollListener(){
        mRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                Log.d("test", "paging")

                val layoutManager = recyclerview.layoutManager

                // 다음페이지가 있는 경우
                if(hasNextPage()){
                    val lastVisibleItem = (layoutManager as GridLayoutManager)
                        .findLastCompletelyVisibleItemPosition()
                    // 마지막으로 보여진 아이템 position 이 전체 아이템 개수보다 5개 모자란경우, 데이터를 loadMore한다
                    if(layoutManager.itemCount <= lastVisibleItem + 5){
                        loadMorePosts()
                        setHasNextPage(false)
                    }
                }
            }

        })
    }


    private fun setBoard(new_boards:List<board>){
        this.boards.apply {
            clear()
            addAll(new_boards)
        }
        mRecyclerAdapter?.notifyDataSetChanged()
    }


    fun addBoards(boards: List<board>){
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

        val detailpage = DetailPage()
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

            val data = bookmark(user.toLong(), boards[viewholder.index!!]?.idx!!)
            Log.d("test", data.toString())

            connect_server.addFavorite(data).enqueue(object : Callback<favorite>{
                override fun onFailure(call: Call<favorite>, t: Throwable) {
                    Log.d("test", "실패")
                }

                override fun onResponse(call: Call<favorite>, response: Response<favorite>) {
                    Log.d("test", "성공")
                }
            })
        }
        else{
            connect_server.deleteFavorite(user, boards[viewholder.index!!]?.idx!!).enqueue(object : Callback<favorite>{
                override fun onFailure(call: Call<favorite>, t: Throwable) {
                    Log.d("test", "실패")
                }

                override fun onResponse(call: Call<favorite>, response: Response<favorite>) {
                    Log.d("test", "성공")
                }

            })
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