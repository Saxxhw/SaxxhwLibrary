package com.saxxhw.library.glide

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView

import com.bumptech.glide.Priority
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zhihu.matisse.engine.ImageEngine

/**
 * Created by Saxxhw on 2017/7/13.
 * 邮箱：Saxxhw@126.com
 * 功能：图片选择框架-图片加载配置
 */

class GlideEngine : ImageEngine {

    override fun loadThumbnail(context: Context, resize: Int, placeholder: Drawable, imageView: ImageView, uri: Uri) {
        // 加载图片
        GlideApp.with(context)
                .asBitmap()
                .load(uri)
                .placeholder(placeholder)
                .override(resize, resize)
                .centerCrop()
                .transition(BitmapTransitionOptions().crossFade(300))
                .into(imageView)
    }

    override fun loadGifThumbnail(context: Context, resize: Int, placeholder: Drawable, imageView: ImageView, uri: Uri) {
        GlideApp.with(context)
                .asBitmap()
                .load(uri)
                .placeholder(placeholder).override(resize, resize).centerCrop()
                .transition(BitmapTransitionOptions().crossFade(300))
                .into(imageView)
    }

    override fun loadImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri) {
        GlideApp.with(context)
                .load(uri)
                .priority(Priority.HIGH).override(resizeX, resizeY).centerCrop()
                .transition(DrawableTransitionOptions().crossFade(300))
                .into(imageView)
    }

    override fun loadGifImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri) {
        GlideApp.with(context)
                .asGif()
                .load(uri)
                .priority(Priority.HIGH).override(resizeX, resizeY).centerCrop()
                .into(imageView)
    }

    override fun supportAnimatedGif(): Boolean = true
}
