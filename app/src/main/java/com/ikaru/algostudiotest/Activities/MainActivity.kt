package com.ikaru.algostudiotest.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
import com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_LEFT
import com.ikaru.algostudiotest.Adapters.MemeAdapter
import com.ikaru.algostudiotest.Models.DataResponse
import com.ikaru.algostudiotest.Models.Meme
import com.ikaru.algostudiotest.R
import com.ikaru.algostudiotest.Utils.DataRepository
import com.peekandpop.shalskar.peekandpop.PeekAndPop
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val postServices = DataRepository.create()
    var memes:List<Meme> = ArrayList()
    val memeAdapter =  MemeAdapter(R.layout.item_meme,memes)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getDataFromInternet()
        memeAdapter.openLoadAnimation(SLIDEIN_LEFT)


        rv_meme.adapter = memeAdapter
        rv_meme.layoutManager = GridLayoutManager(this,3)

        itemsswipetorefresh.setOnRefreshListener {
              rv_meme.visibility = View.GONE
              getDataFromInternet()
              itemsswipetorefresh.isRefreshing = false
              rv_meme.visibility = View.VISIBLE
        }


        memeAdapter.onItemClickListener =  BaseQuickAdapter.OnItemClickListener { adapter, view, position ->

            var intent = Intent(this,DetailActivity::class.java)
            intent.putExtra("Meme",memes.get(position))
            startActivity(intent)

            true
        }

//        memeAdapter.onItemLongClickListener =  BaseQuickAdapter.OnItemLongClickListener { adapter, view, position ->
//            PeekAndPop.Builder(this)
//                .peekLayout(R.layout.item_pop)
//                .longClickViews(view)
//                .build();
//
//            true
//        }

    }

    fun getDataFromInternet(){
        postServices.getMeme().enqueue(object : Callback<DataResponse> {
            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Please Connect To Internet",Toast.LENGTH_SHORT).show()
                Log.e("DEBUG","MainActivity : " + t.message)
            }

            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                var dataResponse = response.body()
                var x  = 0;
                if (dataResponse != null) {
                    memes = dataResponse.getData()?.memes!!
                }
                var test: MutableList<Meme> = ArrayList()
                while (x < 20){
                    Log.e("DEBUG","MainActivity : Add "+ (memes.get(x).name))
                    test.add(memes.get(x))
                    Log.e("DEBUG","MainActivity : X "+ x)
                    x++;
                }
                memes = test
                memeAdapter.refill(memes)
                Log.e("DEBUG","MainActivity : "+ (memes.get(0).name))
            }
        })
    }
}
