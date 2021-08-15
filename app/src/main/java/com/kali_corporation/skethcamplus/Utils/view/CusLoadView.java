package com.kali_corporation.skethcamplus.Utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kali_corporation.skethcamplus.R;

public class CusLoadView extends LinearLayout {

    private ImageView imageView;
    private LinearLayout bgLL;

    public CusLoadView(Context context) {
        super(context);
        initUI(context);
    }

    public CusLoadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    public CusLoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context);
    }

    public CusLoadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initUI(context);
    }

    private void initUI(Context context){
        LayoutInflater.from(context).inflate(R.layout.custom_view_load, this);
        initLoadView(context);

    }
    private void initLoadView(Context context){
        imageView = findViewById(R.id.iv_load);
        bgLL = findViewById(R.id.ll_bg);
//        bgLL.setVisibility(View.GONE);
        setOnClickListener(null);

        Glide.with(context).load(R.drawable.view_laod).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);

    }

//    public void show(){
//        bgLL.setVisibility(View.VISIBLE);
//    }

//    public void hide(){
//        bgLL.setVisibility(View.GONE);
//        imageView.setVisibility(GONE);
//    }
}
