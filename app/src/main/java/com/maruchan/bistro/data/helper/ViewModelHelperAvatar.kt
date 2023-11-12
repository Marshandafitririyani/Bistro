package com.maruchan.bistro.data.helper

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.crocodic.core.helper.StringHelper
import com.maruchan.bistro.R
import java.io.File

class ViewModelHelperAvatar {
    companion object {
        @JvmStatic
        @BindingAdapter(value = ["imageUrlAvatar", "userHelper"], requireAll = true)
        fun loadImageRecipe(view: ImageView, imageUrlAvatar: String?, userHelper: String?) {

            view.setImageDrawable(null)

            val avatar = StringHelper.generateTextDrawable(
                StringHelper.getInitial(userHelper?.trim()),
                ContextCompat.getColor(view.context, R.color.army), 320
            )

            if (imageUrlAvatar.isNullOrEmpty()) {
                view.setImageDrawable(avatar)

            } else {
                imageUrlAvatar.let {
                    val requestOptions = RequestOptions()
                        .placeholder(R.drawable.img_loading)
                        .circleCrop()
                    Glide
                        .with(view.context)
                        .load(StringHelper.validateEmpty(imageUrlAvatar))
                        .apply(requestOptions)
                        .error(avatar)
                        .transition(DrawableTransitionOptions.withCrossFade())
//                        .apply(RequestOptions.centerCropTransform())
                        .into(view)

                }

            }

        }
        fun File.writeBitmap(
            bitmap: Bitmap,
            format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
            quality: Int = 100
        ) {
            outputStream().use { out ->
                bitmap.compress(format, quality, out)
                out.flush()
            }
        }

    }
}