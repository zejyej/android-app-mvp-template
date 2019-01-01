package com.zejyej.template.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zejyej.provider.widget.RxDialog;
import com.zejyej.template.R;

/**
 * @author zejyej
 * @desc
 * @date 2018/5/5
 */
public class RxVideoDown extends RxDialog {
    private TextView mTvSure;
    private TextView mTvCancel;
    private TextView mTvContent;
    private TextView mTvTitle;
    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }


    public TextView getTvContent() {
        return mTvContent;
    }

    public void setContent(String content) {
        this.mTvContent.setText(content);
    }

    public TextView getTvSure() {
        return mTvSure;
    }

    public void setSure(String strSure) {
        this.mTvSure.setText(strSure);
    }


    public TextView getTvCancel() {
        return mTvCancel;
    }

    public void setCancel(String strCancel) {
        this.mTvCancel.setText(strCancel);
    }


    public RxVideoDown(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public RxVideoDown(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public RxVideoDown(Context context) {
        super(context);
        initView();
    }

    public RxVideoDown(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    private void initView() {
        View dialog_view = LayoutInflater.from(getContext()).inflate(R.layout.app_dialog_video_down, null);
        mTvTitle = (TextView) dialog_view.findViewById(R.id.tv_logo);
        mTvSure = (TextView) dialog_view.findViewById(R.id.tv_sure);
        mTvCancel = (TextView) dialog_view.findViewById(R.id.tv_cancle);
        mTvContent = (TextView) dialog_view.findViewById(R.id.tv_content);
        setContentView(dialog_view);
    }

}