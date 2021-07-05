package com.chipmong.dms.utils

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream


object BitmapUtils {
    fun resizeBitmap(context: Context, image: Drawable, size: Int = 50): Bitmap {
        val b = (image as BitmapDrawable).bitmap
        return Bitmap.createScaledBitmap(b, size, size, false)
    }

     fun getImageUri(context: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }


}