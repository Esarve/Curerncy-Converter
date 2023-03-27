package dev.sourav.currencyconverter.others

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import dev.sourav.currencyconverter.CurrencyConverter
import dev.sourav.currencyconverter.R

/**
 * Created by Sourav
 * On 3/25/2023 8:35 PM
 * For Currency Converter
 */
class Utils {

    companion object{
        private val radii = 50f

        val avatarMap = listOf(
            Pair(GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadii = floatArrayOf(radii, radii, radii, radii, radii, radii, radii, radii)
                color = ColorStateList.valueOf(ContextCompat.getColor(
                    CurrencyConverter.context,
                    R.color.ac_bg_1
                ))
            }, R.color.ac_txt_1),

            Pair(GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadii = floatArrayOf(radii, radii, radii, radii, radii, radii, radii, radii)
                color = ColorStateList.valueOf(ContextCompat.getColor(
                    CurrencyConverter.context,
                    R.color.ac_bg_2
                ))
            }, R.color.ac_txt_2),
            Pair(GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadii = floatArrayOf(radii, radii, radii, radii, radii, radii, radii, radii)
                color = ColorStateList.valueOf(ContextCompat.getColor(
                    CurrencyConverter.context,
                    R.color.ac_bg_3
                ))
            }, R.color.ac_txt_3),
            Pair(GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadii = floatArrayOf(radii, radii, radii, radii, radii, radii, radii, radii)
                color = ColorStateList.valueOf(ContextCompat.getColor(
                    CurrencyConverter.context,
                    R.color.ac_bg_4
                ))
            }, R.color.ac_txt_4)

        )
    }


}