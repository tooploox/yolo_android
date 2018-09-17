package com.tooploox.pokerml.app.gatewayimpl

import android.graphics.Bitmap
import android.os.Environment
import com.tooploox.pokerml.domain.gateway.StoredBitmapProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.graphics.BitmapFactory



class BitmapStorageImpl : StoredBitmapProvider {

    private val path = "${Environment.getExternalStorageDirectory()}${File.separator}bitmap.png"

    override fun store(bitmap: Bitmap) {
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(path)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun retreive(): Bitmap {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val bitmap = BitmapFactory.decodeFile(path, options)
        return bitmap
    }
}
