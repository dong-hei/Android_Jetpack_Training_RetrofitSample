package com.dk.retrofitsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dk.retrofitsample.adapter.CustomAdapter
import com.dk.retrofitsample.api.MyApi
import com.dk.retrofitsample.api.RetrofitInstance
import com.dk.retrofitsample.model.Post
import com.dk.retrofitsample.viewModel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Simple Retrofit Sample and  CallBack 지옥
 *
 *  CallBack 지옥
 * API 통신을 여러개 할때 비동기적으로 통신을 하기때문에 순서가 뒤죽박죽이다.
 * => 응답안에 응답을 넣고 그응답안에 응답을 넣고 그응답의 응답안에 응답을 넣으면 해결가능하다
 * 하지만 콜백이 덕지덕지 붙이면 가독성이 너무 떨어진다.
 *
 * ==> 이런것들의 해결책이 바로 코루틴
*/
class MainActivity : AppCompatActivity() {

    lateinit var vm : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProvider(this).get(MainViewModel::class.java)
        vm.getPost1()
        vm.getPostNum(3)
        vm.getPostAll()

        val area1 = findViewById<TextView>(R.id.area1)
        val area2 = findViewById<TextView>(R.id.area2)

        vm.liveWord1.observe(this, Observer{
            area1.text = it.toString()
        })

        vm.liveWord2.observe(this, Observer{
            area2.text = it.toString()
        })

        val rv = findViewById<RecyclerView>(R.id.rv)

        vm.liveWordList.observe(this, Observer {
            val customAdapter = CustomAdapter(it as ArrayList<Post>)
            rv.adapter = customAdapter
            rv.layoutManager = LinearLayoutManager(this)
        })
    }
}

/**
 * 콜백 지옥의 예시
 *  //1
    val api = RetrofitInstance.getInstance().create(MyApi::class.java)
    api.getPost1().enqueue(object  : Callback<Post>{
    override fun onResponse(call: Call<Post>, response: Response<Post>) {
    Log.d("API1", response.body().toString())

    //2
    api.getPostNum(10).enqueue(object  : Callback<Post>{
    override fun onResponse(call: Call<Post>, response: Response<Post>) {
    Log.d("API NUM 10", response.body().toString())

    //3
    api.getPostNum(9).enqueue(object  : Callback<Post>{
    override fun onResponse(call: Call<Post>, response: Response<Post>) {
    Log.d("API NUM 3", response.body().toString())

    //4
    api.getPostNum(8).enqueue(object  : Callback<Post>{
    override fun onResponse(call: Call<Post>, response: Response<Post>) {
    Log.d("API NUM 4", response.body().toString())

    }

    override fun onFailure(call: Call<Post>, t: Throwable) {
    Log.d("API NUM 4", "faild")
    }
    })
    }

    override fun onFailure(call: Call<Post>, t: Throwable) {
    Log.d("API NUM 3", "faild")
    }
    })
    }

    override fun onFailure(call: Call<Post>, t: Throwable) {
    Log.d("API NUM 10", "faild")
    }
    })
    }

    override fun onFailure(call: Call<Post>, t: Throwable) {
    Log.d("API1", "faild")
    }
    })
 */