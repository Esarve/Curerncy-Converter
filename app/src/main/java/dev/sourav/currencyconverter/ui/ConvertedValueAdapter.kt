package dev.sourav.currencyconverter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.sourav.currencyconverter.CurrencyConverter
import dev.sourav.currencyconverter.others.Utils
import dev.sourav.currencyconverter.databinding.ItemConvertedValueBinding
import dev.sourav.currencyconverter.models.ConvertedValue

/**
 * Created by Sourav
 * On 3/25/2023 8:20 PM
 * For Currency Converter
 */
class ConvertedValueAdapter(private var convertedValues: List<ConvertedValue>) :
    RecyclerView.Adapter<ConvertedValueAdapter.ConvertedValueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvertedValueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemConvertedValueBinding.inflate(inflater, parent, false)
        return ConvertedValueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConvertedValueViewHolder, position: Int) {
        holder.bind(convertedValues[position], position)
    }

    override fun getItemCount(): Int = convertedValues.size

    fun setData(list :List<ConvertedValue>){
        convertedValues = list
        notifyDataSetChanged()
    }

    inner class ConvertedValueViewHolder(private val binding: ItemConvertedValueBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(convertedValue: ConvertedValue, position: Int) {
            binding.convertedValue = convertedValue
            binding.currencyCode.background = Utils.avatarMap[position % Utils.avatarMap.size].first
            binding.currencyCode.setTextColor(ContextCompat.getColor(CurrencyConverter.context, Utils.avatarMap[position % Utils.avatarMap.size].second))
        }
    }
}
