package com.professionalandroid.apps.capston_meeting

import com.google.gson.annotations.SerializedName

data class board(
    @SerializedName("title")
    var title:String,

    @SerializedName("img1")
    var img1:String,

    @SerializedName("img2")
    var img2:String,

    @SerializedName("img3")
    var img3:String,

    @SerializedName("keyword")
    var keyword:String,

    @SerializedName("location")
    var location:String,

    @SerializedName("num_type")
    var num_type:String,

    @SerializedName("gender")
    var gender:String,

    @SerializedName("createdDate")
    var createdDate:String,

    @SerializedName("updatedDate")
    var updatedDate:String,

    @SerializedName("_links")
    var _links:_links2,

    @SerializedName("user")
    var user:user
)

data class _links2(
    var self: self,
    @SerializedName("board")
    var board: board2,
    var user: user2
)

data class board2(
    var href: String
)

data class user2(
    var href:String
)

data class user(
    var idx:Int
)

