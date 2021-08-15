package com.kali_corporation.skethcamplus.fx;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kali_corporation.skethcamplus.R;

import java.util.List;

import com.kali_corporation.skethcamplus.graphic_image.GPUImageFilter;
import com.kali_corporation.skethcamplus.graphic_image.GPUImageView;

public class JohnCrittle extends BaseAdapter {

    List<LizDavenport> filterUris;
    Context mContext;
    private Bitmap background;

    private int selectFilter = 0;

    public void setSelectFilter(int selectFilter) {
        this.selectFilter = selectFilter;
    }

    public int getSelectFilter() {
        return selectFilter;
    }

    public JohnCrittle(Context context, List<LizDavenport> effects, Bitmap backgroud) {
        filterUris = effects;
        mContext = context;
        this.background = backgroud;
    }

    @Override
    public int getCount() {
        return filterUris.size();
    }

    @Override
    public Object getItem(int position) {
        return filterUris.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EffectHolder holder = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.filter_item, null);
            holder = new EffectHolder();
            holder.filteredImg = (GPUImageView) convertView.findViewById(R.id.small_filter);
            holder.tv_effect = (TextView) convertView.findViewById(R.id.tv_filter);
            convertView.setTag(holder);
        } else {
            holder = (EffectHolder) convertView.getTag();
        }

        final LizDavenport effect = (LizDavenport) getItem(position);
        holder.tv_effect.setText("filter "+(position+1));
        holder.filteredImg.setImage(background);
        GPUImageFilter filter = DianaVonGrüning.createFilterForType(mContext, effect.getType());
        holder.filteredImg.setFilter(filter);

        return convertView;
    }

    class EffectHolder {
        GPUImageView filteredImg;
        TextView tv_effect;
    }

}
