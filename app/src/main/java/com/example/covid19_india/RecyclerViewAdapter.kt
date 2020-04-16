package com.example.covid19_india

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_list.view.activeTv
import kotlinx.android.synthetic.main.item_list.view.recoveredTv

class RecyclerViewAdapter(val list: List<StatewiseItem>) : RecyclerView.Adapter<viewholder>() {
    var header=0;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {

//        if (header==0){
//            header=1;
//            return viewholder(LayoutInflater.from(parent.context).inflate(R.layout.header,parent,false))
//
//
//
//        }


        return viewholder(LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false))

    }

    override fun getItemCount(): Int {
        Log.i("info","${list.size}")

        return list.size

    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
//        Log.i("info","${header}")

//        if (header!=0&&position!=0) {
//            Log.i("info","${position}")
//            holder.bind(list[position-1])
//        }

        holder.bind(list[position])


    }

}
class viewholder(itemView: View) :RecyclerView.ViewHolder(itemView){
    fun bind(data: StatewiseItem) {
        with(itemView){
            stateTv.text=data.state
            confirmedTv.text=SpannableString("${data.confirmed} \n ${spanstring("↑${data.deltaconfirmed}",1)}")
            recoveredTv.text="${data.recovered} \n ${spanstring("↑${data.deltarecovered}",3)}"
            activeTv.text="${data.active} \n ${spanstring("↑${data.deltaactive}",2)}"
            deceasedTv.text="${data.deaths} \n ${spanstring("↑${data.deltadeaths}",4)}"

        }

    }

    private fun spanstring( string:String,color:Int ) : SpannableString {
        var cc="0";
        if (color==1){
            cc="#D32F2F"
        }else if (color==2){
            cc="#1976D2"
        }else if (color==3){
            cc="#388E3C"
        } else {
            cc="#FBC02D"
        }
        val ss=SpannableString(string)
        val foregroundColorSpan=ForegroundColorSpan(Color.RED)
        ss.setSpan(foregroundColorSpan,0,string.length-1,Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        return SpannableString(ss)

    }

}