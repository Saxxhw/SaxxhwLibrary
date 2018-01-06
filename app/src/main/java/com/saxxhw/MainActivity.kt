package com.saxxhw

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.saxxhw.library.base.BaseActivity
import com.saxxhw.library.glide.GlideEngine
import com.saxxhw.library.widget.SelectPictureView
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), SelectPictureView.onPictureSelectListener {


    override fun getLayout(): Int = R.layout.activity_main

    override fun initEventAndData(savedInstanceState: Bundle?) {

    }

    override fun bindListener() {
        add.setOnPictureSelectListener(this)
    }

    override fun onPictureSelect() {
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(true)
                .capture(true)
                .captureStrategy(CaptureStrategy(true, "com.saxxhw.fileprovider"))
                .maxSelectable(add.missingCount)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(GlideEngine())
                .forResult(12)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                12 -> add.setData(Matisse.obtainPathResult(data))
            }
        }
    }
}