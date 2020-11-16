package com.professionalandroid.apps.capston_meeting.src.bookmarkPage

import android.content.Context
import android.util.Log
import com.professionalandroid.apps.capston_meeting.src.GlobalApplication
import com.professionalandroid.apps.capston_meeting.src.applyPage.models.DefaultResponse
import com.professionalandroid.apps.capston_meeting.src.bookmarkPage.interfaces.BookmarkPageRetrofitInterface
import com.professionalandroid.apps.capston_meeting.src.bookmarkPage.interfaces.BookmarkPageView
import com.professionalandroid.apps.capston_meeting.src.bookmarkPage.models.BookmarkResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkPageService(val mBookmarkPageView: BookmarkPageView, val context: Context) {
    val mBookmarkPageRetrofitInterface: BookmarkPageRetrofitInterface =
        GlobalApplication.retrofitService(context)!!.create(BookmarkPageRetrofitInterface::class.java)

    fun searchBookmark(userId: Long){
        mBookmarkPageRetrofitInterface.getFavorite(userId).enqueue(object : Callback<List<BookmarkResponse>>{
            override fun onFailure(call: Call<List<BookmarkResponse>>, t: Throwable) {
                Log.d("test", "즐겨찾기 조회 실패")
            }

            override fun onResponse(
                call: Call<List<BookmarkResponse>>,
                response: Response<List<BookmarkResponse>>
            ) {
                val body = response.body()
                if(body != null){
                    mBookmarkPageView.searchBookmark(body)
                }
            }

        })
    }

    fun deleteBookmark(userId: Long, boardId: Long, position: Int){
        mBookmarkPageRetrofitInterface.deleteBookmark(userId, boardId).enqueue(object : Callback<DefaultResponse>{
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.d("test", "즐겨찾기 제거 실패")
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if(response.body()?.code == 200){
                    Log.d("test", "즐겨찾기 제거 성공")
                    mBookmarkPageView.deleteBookmark(position)
                }
            }
        })
    }
}