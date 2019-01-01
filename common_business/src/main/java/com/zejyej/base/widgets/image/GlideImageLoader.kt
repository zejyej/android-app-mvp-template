package com.zejyej.base.widgets.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.qiyukf.unicorn.api.ImageLoaderListener
import com.qiyukf.unicorn.api.UnicornImageLoader

/**
 * @desc
 * @author zejyej
 * @date 2018/5/3
 */
class GlideImageLoader(context: Context) : UnicornImageLoader {

    private val context: Context = context.applicationContext

    override fun loadImageSync(uri: String, width: Int, height: Int): Bitmap? {
        return null
    }

    override fun loadImage(uri: String, width: Int, height: Int, listener: ImageLoaderListener?) {
        var widthLoad = width
        var heightLoad = height
        if (widthLoad <= 0 || heightLoad <= 0) {
            heightLoad = Integer.MIN_VALUE
            widthLoad = heightLoad
        }

        Glide.with(context).load(uri).asBitmap().into(object : SimpleTarget<Bitmap>(widthLoad, heightLoad) {
            override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                listener?.onLoadComplete(resource)
            }

            override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                listener?.onLoadFailed(e)
            }
        })
    }
}
