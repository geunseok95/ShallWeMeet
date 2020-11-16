package com.professionalandroid.apps.capston_meeting.src.bookmarkPage.interfaces

import com.professionalandroid.apps.capston_meeting.src.bookmarkPage.models.BookmarkResponse

interface BookmarkPageView {
    fun searchBookmark(body: List<BookmarkResponse>)
    fun deleteBookmark(position: Int)
}