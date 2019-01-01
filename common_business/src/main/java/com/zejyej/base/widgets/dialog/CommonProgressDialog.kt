package com.zejyej.base.widgets.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.widget.ImageView
import com.zejyej.base.R


/**
 * @desc
 * @author zejyej
 * @date 2018/5/2
 */
class CommonProgressDialog private constructor(
        context: Context,
        theme: Int
): Dialog(context,theme),IProgressDialog{

    companion object {
        private lateinit var dialog: CommonProgressDialog
        private var animDrawable: AnimationDrawable?= null

        fun create(context: Context): CommonProgressDialog {
            dialog = CommonProgressDialog(context, R.style.LightProgressDialog)
            dialog.setContentView(R.layout.base_progress_dialog)
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.window.attributes.gravity = Gravity.CENTER

            val lp = dialog.window.attributes
            lp.dimAmount = 0.2f
            dialog.window.attributes = lp

            val loadingView = dialog.findViewById<ImageView>(R.id.ivLoading)
            animDrawable = loadingView.background as AnimationDrawable

            return dialog
        }
    }



    override fun showLoading() {
        super.show()
        animDrawable?.start()
    }

    override fun hideLoading() {
        super.dismiss()
        animDrawable?.stop()
    }


}