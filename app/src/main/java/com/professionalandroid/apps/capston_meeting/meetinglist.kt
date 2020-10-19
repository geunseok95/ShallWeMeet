package com.professionalandroid.apps.capston_meeting

var meetinglist: MutableList<list_item_data> =
    mutableListOf(
       )

var viewpager_list: MutableList<viewpager_list_item> =
    mutableListOf(
        viewpager_list_item("건대 3대3", R.drawable.thestray.toString()),
        viewpager_list_item("홍대 4대4", R.drawable.sun.toString()),
        viewpager_list_item("이태원 3대3", R.drawable.imyourxyz.toString())

    )

var viewpager_list2: MutableList<viewpager_list_item> =
    mutableListOf(
        viewpager_list_item("잠실 3대3", R.drawable.watermelon.toString()),
        viewpager_list_item("홍대 4대4", R.drawable.happy.toString()),
        viewpager_list_item("천호 3대3", R.drawable.imyourxyz.toString())

    )


data class viewpager_list_item(
    var title:String,
    var img1: String
)