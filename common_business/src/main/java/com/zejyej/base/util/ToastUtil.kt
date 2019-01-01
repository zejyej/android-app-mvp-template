package com.zejyej.base.util

import android.content.Context
import android.widget.Toast

/**
 * @desc
 * @author zejyej
 * @date 2018/5/2
 */
object ToastUtil {


    fun shortToast(context: Context, msg:String) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

    fun longToast(context: Context,msg: String) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
    }

}