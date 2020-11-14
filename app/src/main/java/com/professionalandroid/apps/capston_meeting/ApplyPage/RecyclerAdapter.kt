package com.professionalandroid.apps.capston_meeting.applyPage

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.professionalandroid.apps.capston_meeting.R
import com.professionalandroid.apps.capston_meeting.retrofit.board
import kotlinx.android.synthetic.main.list_item2.view.*

class RecyclerAdapter(private val boards:MutableList<board?>):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    // list와 연결할 listener
    interface OnListItemSelelctedInterface{
        fun onItemSelected(v: View, position: Int)
        fun onStarChecked(v: View, position: Int)
    }

    val mSelectedItems: SparseBooleanArray = SparseBooleanArray(0)

    var mContext: Context? = null
    private var mListener: OnListItemSelelctedInterface? = null

    constructor(context: Context, listener: OnListItemSelelctedInterface,boards: MutableList<board?>) : this(boards) {
        this.mContext = context
        this.mListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflateView = LayoutInflater.from(parent.context).inflate(R.layout.list_item2, parent, false)
        return ViewHolder(inflateView)
    }

    override fun getItemCount(): Int {
        return boards.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title?.text = boards[position]?.title
        Glide.with(mContext!!)
            .load(boards[position]?.img1)
            .centerCrop()
            .into(holder.img1!!)
        holder.location?.text = boards[position]?.location1
        holder.num_type?.text = boards[position]?.num_type
        holder.age?.text = boards[position]?.age.toString()
        holder.tag1?.text = boards[position]?.tag1
        holder.tag2?.text = boards[position]?.tag2
        holder.tag3?.text = boards[position]?.tag3
        holder.index = position
        holder.parentview.star_btn.isChecked = boards[position]!!.check

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var parentview = view
        var title: TextView? = null
        var img1: RoundedImageView? = null
        var location: TextView? = null
        var num_type: TextView? = null
        var age: TextView? = null
        var tag1: TextView? = null
        var tag2: TextView? = null
        var tag3: TextView? = null

        // index를 이용해서 상속받은 class에서
        var index: Int? = null

        init {
            title = view.imageview_title
            img1 = view.imageview_img1
            location = view.imageview_location
            age = view.imageview_age
            num_type = view.imageview_num_type
            tag1 = view.imageview_tag1
            tag2 = view.imageview_tag2
            tag3 = view.imageview_tag3
            index = 0
            parentview.item2_card_view.setOnClickListener {
                mListener?.onItemSelected(view, adapterPosition)
            }
            parentview.star_btn.setOnClickListener{
                val position = adapterPosition
                toggleItemSelectied(position)
                boards[position]?.check = !boards[position]!!.check
                mListener?.onStarChecked(view, adapterPosition)
            }

        }

        override fun toString(): String {
            return super.toString() + " '" + title!!.text + "'"
        }
    }

    fun toggleItemSelectied(position:Int){
        if(mSelectedItems[position, false]){
            mSelectedItems.delete(position)
        }
        else{
            mSelectedItems.put(position, true)
        }
        notifyItemChanged(position)
    }
}