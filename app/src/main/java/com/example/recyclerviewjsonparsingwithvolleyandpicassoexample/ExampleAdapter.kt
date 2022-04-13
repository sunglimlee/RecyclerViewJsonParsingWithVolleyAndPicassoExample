package com.example.recyclerviewjsonparsingwithvolleyandpicassoexample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.squareup.picasso.Picasso

// Context 를 맨처음부터 받아와야 한다. 만약 안받아오면 this@MainAcitivty로 해도 되기는 하지만 객체지향에 맞지 않잖아..
class ExampleAdapter(var mContext : Context, var mExampleList : ArrayList<ExampleItem>) : Adapter<ExampleAdapter.ExampleViewHolder>() {
    lateinit var mListener : OnExampleClickListener

    fun setOnExampleClickListener(listener : ExampleAdapter.OnExampleClickListener) {
        mListener = listener
    }
    interface OnExampleClickListener { //나만의 클릭리스너를 만든다.
        fun onclick(position: Int) {

        }
    }
    //자동으로
    class ExampleViewHolder : RecyclerView.ViewHolder {
        //지금 헷갈리는 부분이 아답타와 홀더의 관계인데.. itemView 를 가지고 있으니깐.. 생성자에서 만들었나?
        //ㅎㅎㅎㅎㅎㅎ 맞네.. 생성자에서 만들었네.. init 에서
        val mImageView : ImageView = itemView.findViewById(R.id.image_view)
        val mTextViewCreator : TextView = itemView.findViewById(R.id.text_view_creator)
        val mTextViewLikes : TextView = itemView.findViewById(R.id.text_view_like)
        // 이제는 init 도 제거시키고 바로 itemView.findViewById() 를 넣어줬다. 가능하고..
        // 이제 연결되어 있으니간 이 클래스를 사용할 때 해당 멤버 변수(and 함수)를 쓰면 되는거지..
        constructor(itemView : View, listener: OnExampleClickListener) : super(itemView) {
            itemView.setOnClickListener {
                if (listener != null) {
                    val position : Int = this.adapterPosition //각각의 ViewHolder는 adapterPostion이 있다.
                    //따라서 viewHolder의 생성자에서 사용할 수 있다.
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onclick(position)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        //val v : View = View.inflate(mContext, R.layout.example_item, parent)
        val v : View = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false)
        //니가 여기서 잘못하고 있다. 아이템레이아웃을 inflate 했으면 그걸 ExampleViewHolder를 사용하기 위해서 객체를 생성하고
        //객체를 생성해야 멤버 변수(함수)를 사용할 수 있잖아.. 그리고 만든 view를 인자로 넣어주어야 하는거고..
        //그래야 들어온 인자를 이용해서 값들을 연결 시킬수 있는거고..
        // return v as ExampleViewHolder  (x)
        return ExampleViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        //이제 이부분에서 알아야 할게 뭐냐면..
        // 1. 외부에서 mContext 받아왔고,
        // 2. 외부에서 mExampleList : ArrayList<ExampleItem> 받아왔고..
        // 3. 그리고 ViewHolder클래서에서 만든 ExampleViewHolder가 사용대기 중이고..
        // 그래서 이제는 이 데이터와 holder를 이용해서 값을 넣어주는 기능만 하면 RecyclerView에서 알아서 데이터를 보여줄때마다
        // 여기서 생성하고 바인딩해서 보여주게 된다. 그러니깐 메모리에서 허락하는 양만큼 보여주고 다시 보여줘야하면 새로 생성하고
        // 바인딩해서 보여주는 거지..
        val currentItem : ExampleItem = mExampleList.get(position)
        Picasso.get().load(currentItem.getImageUrl()).fit().centerInside().into(holder.mImageView)
        holder.mTextViewCreator.setText("ID : ${currentItem.getCreator().toString()}")
        holder.mTextViewLikes.text = "Likes : ${currentItem.getLikecount().toString()}"

    }

    override fun getItemCount(): Int {
        return mExampleList.size
    }
}