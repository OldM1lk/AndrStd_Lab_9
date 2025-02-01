package com.example.lab_9

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class Adapter(diffCallback: DiffCallback) : ListAdapter<WeatherEntry, RecyclerView.ViewHolder>(diffCallback) {

    private val VIEW_TYPE_HOT = 1
    private val VIEW_TYPE_COLD = 2

    override fun getItemViewType(position: Int): Int {
        val tempCel = getItem(position).main.temp - 273.15
        return if (tempCel >= 0) VIEW_TYPE_HOT else VIEW_TYPE_COLD
    }
    inner class ViewHolderHot(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weatherEntry: WeatherEntry) {
            itemView.findViewById<TextView>(R.id.date).text = weatherEntry.dt_txt
            val tempCel = weatherEntry.main.temp - 273.15
            itemView.findViewById<TextView>(R.id.temp).text = String.format("%.0f °C", tempCel)
            val iconUrl = "https://openweathermap.org/img/wn/${weatherEntry.weather[0].icon}@2x.png"
            Glide.with(itemView.context)
                .load(iconUrl)
                .into(itemView.findViewById(R.id.temp_icon))
        }
    }

    inner class ViewHolderCold(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weatherEntry: WeatherEntry) {
            itemView.findViewById<TextView>(R.id.date).text = weatherEntry.dt_txt
            val tempCel = weatherEntry.main.temp - 273.15
            itemView.findViewById<TextView>(R.id.temp).text = String.format("%.0f °C", tempCel)
            val iconUrl = "https://openweathermap.org/img/wn/${weatherEntry.weather[0].icon}@2x.png"
            Glide.with(itemView.context)
                .load(iconUrl)
                .into(itemView.findViewById(R.id.temp_icon))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HOT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_items_hot, parent, false)
                ViewHolderHot(view)
            }
            VIEW_TYPE_COLD -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_items_cold, parent, false)
                ViewHolderCold(view)
            }
            else -> throw IllegalArgumentException("Неизвестный тип представления $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val weatherEntry = getItem(position)
        when (holder) {
            is ViewHolderHot -> holder.bind(weatherEntry)
            is ViewHolderCold -> holder.bind(weatherEntry)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<WeatherEntry>() {
    override fun areItemsTheSame(oldItem: WeatherEntry, newItem: WeatherEntry): Boolean {
        return oldItem.dt_txt == newItem.dt_txt
    }

    override fun areContentsTheSame(oldItem: WeatherEntry, newItem: WeatherEntry): Boolean {
        return oldItem == newItem
    }
}