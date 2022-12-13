package com.udacity.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.udacity.asteroidradar.main.ASteroidApiStatus
import com.udacity.asteroidradar.main.AsteroidAdapter
import com.udacity.asteroidradar.main.MainFragment

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?) {
    if (!data.isNullOrEmpty()){
        val adapter = recyclerView.adapter as AsteroidAdapter
        adapter.submitList(data)
    }
}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions())
            .into(imgView)
    }
}

@BindingAdapter("hideProgressBar")
fun hideProgressBar(view: View, status: ASteroidApiStatus?) {
        when (status) {
            ASteroidApiStatus.LOADING -> {
                view.visibility = View.VISIBLE
            }
            ASteroidApiStatus.ERROR -> {
                view.visibility = View.GONE
                Toast.makeText(MainFragment.applicationContext(), "Error!! Maybe there is no connection", Toast.LENGTH_LONG).show()
            }
            ASteroidApiStatus.DONE -> {
                view.visibility = View.GONE
            }
        }
}



@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

