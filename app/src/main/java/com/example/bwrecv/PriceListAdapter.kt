package com.example.bwrecv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bwrecv.model.login.DataXX

class PriceListAdapter(private val context: Context,
private val list: List<DataXX>): RecyclerView.Adapter<PriceListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var itemImage: ImageView
        var categoryC: TextView
        var nameTv: TextView
        var priceToPay: TextView
        init {
            itemImage = view.findViewById(R.id.itemImageView)
            categoryC = view.findViewById(R.id.categoryC)
            nameTv = view.findViewById(R.id.nameTv)
            priceToPay = view.findViewById(R.id.priceToPay)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
          if (list[position].BW_Photo_url__c != null){
              Glide.with(context).load(list[position].BW_Photo_url__c).into(holder.itemImage)
          }
          holder.categoryC.text = list[position].Category__c
          holder.nameTv.text = list[position].Name
        holder.priceToPay.text = list[position].Price_to_pay_customer__c

    }

    override fun getItemCount(): Int {
        return list.size
    }
}