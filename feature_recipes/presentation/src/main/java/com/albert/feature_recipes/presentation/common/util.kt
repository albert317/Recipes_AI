package com.albert.feature_recipes.presentation.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadUrl(url)
}
