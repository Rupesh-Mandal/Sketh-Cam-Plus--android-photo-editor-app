package com.kali_corporation.skethcamplus.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kali_corporation.skethcamplus.ui.model.EffectModel;
import com.kali_corporation.skethcamplus.ui.effect.Effects;
import com.kali_corporation.skethcamplus.R;
import com.kali_corporation.skethcamplus.ui.activity.EditorActivity;

import java.util.List;

public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.DataHolder> {
    EditorActivity activity;
    List<EffectModel> datalist;


    public EffectAdapter(EditorActivity photoEditorActivity, List<EffectModel> list) {
        this.activity = photoEditorActivity;
        this.datalist = list;
    }

    public DataHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new DataHolder(LayoutInflater.from(activity).inflate(R.layout.effect_items, viewGroup, false));
    }

    public void onBindViewHolder(DataHolder dataHolder, final int i) {
        dataHolder.imageView.setImageResource(this.datalist.get(i).getEffectName());
        dataHolder.tv_title.setText(this.datalist.get(i).getName());
        switch (i) {
            case 0:
                Effects.applyEffectNone(dataHolder.imageView);
                break;
            case 1:
                Effects.applyEffect1(dataHolder.imageView);
                break;
            case 2:
                Effects.applyEffect2(dataHolder.imageView);
                break;
            case 3:
                Effects.applyEffect3(dataHolder.imageView);
                break;
            case 4:
                Effects.applyEffect4(dataHolder.imageView);
                break;
            case 5:
                Effects.applyEffect5(dataHolder.imageView);
                break;
            case 6:
                Effects.applyEffect6(dataHolder.imageView);
                break;
            case 7:
                Effects.applyEffect7(dataHolder.imageView);
                break;
            case 8:
                Effects.applyEffect8(dataHolder.imageView);
                break;
            case 9:
                Effects.applyEffect9(dataHolder.imageView);
                break;
            case 10:
                Effects.applyEffect10(dataHolder.imageView);
                break;
            case 11:
                Effects.applyEffect11(dataHolder.imageView);
                break;
            case 12:
                Effects.applyEffect12(dataHolder.imageView);
                break;
            case 13:
                Effects.applyEffect13(dataHolder.imageView);
                break;
            case 14:
                Effects.applyEffect14(dataHolder.imageView);
                break;
            case 15:
                Effects.applyEffect15(dataHolder.imageView);
                break;
            case 16:
                Effects.applyEffect16(dataHolder.imageView);
                break;
            case 17:
                Effects.applyEffect17(dataHolder.imageView);
                break;
            case 18:
                Effects.applyEffect18(dataHolder.imageView);
                break;
            case 19:
                Effects.applyEffect19(dataHolder.imageView);
                break;
        }
        dataHolder.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
       //        activity.ApplyEffectNone(i);
            }
        });
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
