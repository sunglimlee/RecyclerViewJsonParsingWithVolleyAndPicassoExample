package com.example.recyclerviewjsonparsingwithvolleyandpicassoexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var mQueue : RequestQueue
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mExampleList : ArrayList<ExampleItem>
    private lateinit var mExampleAdapter : ExampleAdapter // 이게 밖에 있어야 하는 이유는 뭘까?

    companion object {
        const val IMAGEVIEW_MAINACTIVITY2 = "imageview_mainactivity2"
        const val TEXTVIEW1_MAINACTIVITY2 = "textview1_mainactivity2"
        const val TEXTVIEW2_MAINACTIVITY2 = "textview2_mainactivity2"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.recycler_view)
        mQueue = Volley.newRequestQueue(this)
        jasonParse()
        buildRecyclerView()
        addExampleClickListener()
    }

    private fun addExampleClickListener() {
        mExampleAdapter.setOnExampleClickListener(object : ExampleAdapter.OnExampleClickListener {
            override fun onclick(position : Int) {
                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                //1. 각각의 값을 받아서 Intent에 넘겨주기로 한다.
                val imageview : String = mExampleList[position].getImageUrl()
                val textview1 : String = mExampleList[position].getCreator()
                val textview2 : String = mExampleList[position].getLikecount()
                //Toast.makeText(this@MainActivity, textview1, Toast.LENGTH_LONG).show()
                //이거 다틀린거다. 왜냐면 startActivity에서 쓰이는 bundle은 animation같은 ActivityOptions class
                //를 상속받아서 사용하는거다. 그래서 일반 bundle을 넘기고 싶으면 그냥 putExtra로 넘겨라.
                val bundle : Bundle = Bundle()
                bundle.putString(IMAGEVIEW_MAINACTIVITY2, imageview)
                bundle.putString(TEXTVIEW1_MAINACTIVITY2, textview1)
                bundle.putString(TEXTVIEW2_MAINACTIVITY2, textview2)
                intent.putExtras(bundle)
                startActivity(intent);
            }
        })
    }

    private fun buildRecyclerView() {
        mExampleAdapter = ExampleAdapter(this@MainActivity, mExampleList)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mExampleAdapter
    }

    private fun jasonParse() {
        mExampleList = ArrayList<ExampleItem>()
        val url : String = "https://pixabay.com/api/?key=26643278-06227db2f385ab5a01d631617&q=kitten&image_type=photo&pretty=true"
        val request : JsonObjectRequest = JsonObjectRequest(
            url,
            {
                val jsonArray = it.getJSONArray("hits")
                try {
                    for (i in 0 until jsonArray.length()) {
                        val kitten : JSONObject = jsonArray.getJSONObject(i)
                        val webformatURL : String = kitten.getString("webformatURL")
                        val id : String = kitten.getString("id")
                        val likes : Int = kitten.getInt("likes")
                        mExampleList.add(i, ExampleItem(webformatURL, id, likes.toString()))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            {
            it.printStackTrace()
            }
        )
        mQueue.add(request)
    }
}