package com.kali_corporation.skethcamplus.ui.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kali_corporation.skethcamplus.sketchimage.SketchImage;
import com.kali_corporation.skethcamplus.Utils.OnItemClickListener;
import com.kali_corporation.skethcamplus.R;
import com.kali_corporation.skethcamplus.ui.model.SketchModel;
import com.kali_corporation.skethcamplus.ui.activity.EditorActivity;

import java.util.List;

public class SketchAdapter extends RecyclerView.Adapter<SketchAdapter.DataHolder> {
    EditorActivity activity;
    List<SketchModel> datalist;
    OnItemClickListener itemClickListener;
    private SketchImage sketchImage;
    Bitmap bmOriginal;

    public SketchAdapter(EditorActivity photoEditorActivity, List<SketchModel> list, SketchImage sketchImage) {
        this.activity = photoEditorActivity;
        this.datalist = list;
        this.sketchImage = sketchImage;
    }

    public DataHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new DataHolder(LayoutInflater.from(activity).inflate(R.layout.effect_items, viewGroup, false));
    }

    public void onBindViewHolder(DataHolder dataHolder, final int i) {
        dataHolder.imageView.setImageBitmap(sketchImage.getImageAs(i, 100));
      //  dataHolder.imageView.setImageResource(this.datalist.get(i).getSketchName());
        dataHolder.tv_title.setText(this.datalist.get(i).getName());

        dataHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               // Toast.makeText(activity, "" + i, Toast.LENGTH_SHORT).show();
                itemClickListener.onItemClick(i);
            }
        });


    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.itemClickListener = clickListener;
    }

    public int getItemCount() {
        return this.datalist.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_title;

        public DataHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.e3d);
            this.tv_title = (TextView) view.findViewById(R.id.title);
        }
    }
}
