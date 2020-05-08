package com.ikaru.algostudiotest.Adapters

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ikaru.algostudiotest.Models.Meme
import com.ikaru.algostudiotest.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_meme.view.*

class MemeAdapter(layoutResId: Int, data: List<Meme>?=null ) :  BaseQuickAdapter<Meme, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: Meme) {
        val myImageView: ImageView = holder.getView(R.id.iv_meme_list)
        Picasso
            .get()
            .load(item.url) // load the image
            .into(myImageView) // select the ImageView to load it into
    }

    fun refill(items : List<Meme>? = null){
        this.mData = items
        notifyDataSetChanged()
    }
}