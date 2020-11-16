package com.professionalandroid.apps.capston_meeting.src.bookmarkPage

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.capston_meeting.src.detailPage.DetailPage
import com.professionalandroid.apps.capston_meeting.MainActivity
import com.professionalandroid.apps.capston_meeting.MainActivity.Companion.user
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.src.bookmarkPage.interfaces.BookmarkPageView
import com.professionalandroid.apps.capston_meeting.src.bookmarkPage.models.BookmarkResponse
import kotlinx.android.synthetic.main.fragment_bookmark_page.view.*
import kotlinx.android.synthetic.main.list_item2.view.*


class BookmarkPage : Fragment(), BookmarkPageView, BookmarkRecyclerViewAdapter.OnListItemSelelctedInterface {

    var boards = mutableListOf<BookmarkResponse>()

    lateinit var mBookmarkPageService: BookmarkPageService
    lateinit var mRecyclerView: RecyclerView
    lateinit var mBookmarkRecyclerViewAdapter: BookmarkRecyclerViewAdapter
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mBookmarkPageService = BookmarkPageService(this, context)
        mBookmarkRecyclerViewAdapter = BookmarkRecyclerViewAdapter(context, this, boards)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookmark_page, container, false)
        mRecyclerView = view.favorite_recyclerview
        mRecyclerView.layoutManager = GridLayoutManager(context,2)
        mRecyclerView.adapter = mBookmarkRecyclerViewAdapter

        mBookmarkPageService.searchBookmark(user)

        return view
    }

    override fun onItemSelected(v: View, position: Int) {
        val viewholder: BookmarkRecyclerViewAdapter.ViewHolder = mRecyclerView.findViewHolderForAdapterPosition(position) as BookmarkRecyclerViewAdapter.ViewHolder

        val detailpage = DetailPage()
            .apply {
            arguments = Bundle().apply {
                putLong("href", boards[viewholder.index!!].board.idx)
            }
        }

        Log.d("test_position", position.toString())
        Log.d("test", "${viewholder.index}")

        (activity as MainActivity).move_next_fragment(detailpage)
    }

    override fun onStarChecked(v: View, position: Int) {
        if(!v.star_btn.isChecked){
            val viewHolder: BookmarkRecyclerViewAdapter.ViewHolder = mRecyclerView.findViewHolderForAdapterPosition(position) as BookmarkRecyclerViewAdapter.ViewHolder
            mBookmarkPageService.deleteBookmark(user, boards[viewHolder.index!!].board.idx, viewHolder.index!!)
        }
    }

    override fun searchBookmark(body: List<BookmarkResponse>) {
        boards.clear()
        boards.addAll(body)
        mBookmarkRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun deleteBookmark(position: Int) {
        boards.removeAt(position)
        mBookmarkRecyclerViewAdapter.notifyDataSetChanged()
    }

}