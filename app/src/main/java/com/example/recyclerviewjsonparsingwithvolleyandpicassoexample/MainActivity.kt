package com.example.recyclerviewjsonparsingwithvolleyandpicassoexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.recycler_view)
        mQueue = Volley.newRequestQueue(this)
        jasonParse()
        buildRecyclerView()
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