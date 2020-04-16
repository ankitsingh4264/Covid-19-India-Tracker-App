package com.example.covid19_india

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {


     lateinit var dapter:RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchresult()
    }

    private fun fetchresult() {

        GlobalScope.launch {
            val response = withContext(Dispatchers.IO) {
                Client.api.execute()
            }
            if (response.isSuccessful){

                val data=Gson().fromJson(response.body?.string(),Response::class.java)
                launch(Dispatchers.Main){
                    bindcombineddata(data.statewise[0])
                    bindlistdata(data.statewise)
                }

            }
        }
    }

    private fun bindlistdata(data: List<StatewiseItem>) {
        Log.i("main","${data.size}")
        dapter= RecyclerViewAdapter(data)

        recyclerview.apply {
            adapter=dapter
            layoutManager= LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity,DividerItemDecoration.VERTICAL))


        }

    }

    private fun bindcombineddata(data: StatewiseItem) {

        val lastUpdatedTime = data.lastupdatedtime
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        lastupdateTv.text = "Last Updated\n ${getTimeAgo(
            simpleDateFormat.parse(lastUpdatedTime)
        )}"
        confimedTv.text=data.confirmed;
        activeTv.text=data.active
        recoveredTv.text=data.recovered
        deathTv.text=data.deaths

    }


    fun getTimeAgo(past: Date): String {
        val now = Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)

        return when {
            seconds < 60 -> {
                "Few seconds ago"
            }
            minutes < 60 -> {
                "$minutes minutes ago"
            }
            hours < 24 -> {
                "$hours hour ${minutes % 60} min ago"
            }
            else -> {
                SimpleDateFormat("dd/MM/yy, hh:mm a").format(past).toString()
            }
        }
    }
}
