package com.example.recyclerviewjsonparsingwithvolleyandpicassoexample

class ExampleItem(var mImageUrl :String, var mCreator : String, var mLikes : String) {

    fun getImageUrl() : String {
        return mImageUrl
    }

    fun getCreator() : String {
        return mCreator
    }

    fun getLikecount() : String {
        return mLikes
    }

}