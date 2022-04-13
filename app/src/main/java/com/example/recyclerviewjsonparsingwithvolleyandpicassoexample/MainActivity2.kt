package com.example.recyclerviewjsonparsingwithvolleyandpicassoexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class MainActivity2 : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var textView1 : TextView
    private lateinit var textView2 : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        imageView = findViewById(R.id.imageview_mainactivity2)
        textView1 = findViewById(R.id.textview1_mainactivity2)
        textView2 = findViewById(R.id.textview2_mainactivity2)
        getIntentFromMainActivity()
    }

    private fun getIntentFromMainActivity() {
        var bundle : Bundle
        val intent : Intent = this.intent
        bundle = intent.extras!!
        //Toast.makeText(this, "${intent.getBundleExtra(MainActivity.IMAGEVIEW_MAINACTIVITY2)}", Toast.LENGTH_SHORT).show()
        Picasso.get().load(bundle!!.getString(MainActivity.IMAGEVIEW_MAINACTIVITY2).toString()).fit().centerInside().into(imageView)
        textView1.setText(bundle!!.getString(MainActivity.TEXTVIEW1_MAINACTIVITY2).toString())
        textView2.setText(bundle!!.getString(MainActivity.TEXTVIEW2_MAINACTIVITY2).toString())
    }
}