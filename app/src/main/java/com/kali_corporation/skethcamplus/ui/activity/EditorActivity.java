package com.kali_corporation.skethcamplus.ui.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kali_corporation.skethcamplus.sketchimage.SketchImage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.slider.Slider;
import com.kali_corporation.skethcamplus.Utils.OnItemClickListener;
import com.kali_corporation.skethcamplus.fx.JohnCrittle;
import com.kali_corporation.skethcamplus.fx.LizDavenport;
import com.kali_corporation.skethcamplus.fx.Cooper;
import com.kali_corporation.skethcamplus.ui.model.EffectModel;
import com.kali_corporation.skethcamplus.R;
import com.kali_corporation.skethcamplus.ui.adapter.SketchAdapter;
import com.kali_corporation.skethcamplus.ui.model.SketchModel;
import com.kali_corporation.skethcamplus.databinding.ActivityEditorBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.kali_corporation.skethcamplus.external_libs.widget.AdapterView;
import com.kali_corporation.skethcamplus.graphic_image.GPUImageBrightnessFilter;
import com.kali_corporation.skethcamplus.graphic_image.GPUImageContrastFilter;
import com.kali_corporation.skethcamplus.graphic_image.GPUImageExposureFilter;
import com.kali_corporation.skethcamplus.graphic_image.GPUImageFilterGroup;
import com.kali_corporation.skethcamplus.graphic_image.GPUImageHueFilter;
import com.kali_corporation.skethcamplus.graphic_image.GPUImageSaturationFilter;
import com.kali_corporation.skethcamplus.graphic_image.GPUImageToneCurveFilter;
import com.kali_corporation.skethcamplus.graphic_image.GPUImageWhiteBalanceFilter;

import com.kali_corporation.skethcamplus.fx.DianaVonGrüning.*;

import org.jetbrains.annotations.NotNull;

public class EditorActivity extends AppCompatActivity implements Slider.OnChangeListener {

    float cont = 1f, bright = 0f, sat = 1f;
    boolean isCamera;


    private static int DisplayWidth, DisplayHeight;
    List<LizDavenport> filters;

    int PrevCurvePosition = 0, PrevBrightness = 50, PrevContrast = 50, PrevSaturation = 50, PrevVignette = 75, PrevSharpness = 50, PrevHue = 0, PrevSepia = 0, PrevMonochrome = 0, PrevWhiteBalance = 100, PrevColorBalance = 0, PrevLevels = 100, PrevExposure = 50, PrevOpacity = 255;
    ProgressDialog progressDialog;
    Bitmap mBitmapTemp;

    GPUImageFilterGroup filterGroup;
    GPUImageBrightnessFilter brightnessFilter;
    GPUImageContrastFilter contrastFilter;
    GPUImageSaturationFilter saturationFilter;
    GPUImageHueFilter hueFilter;
    GPUImageWhiteBalanceFilter whiteBalanceFilter;
    GPUImageExposureFilter exposureFilter;
    GPUImageToneCurveFilter curveFilter;

    private FilterAdjuster mContrastFilterAdjuster;
    private FilterAdjuster mBrightnessFilterAdjuster;
    private FilterAdjuster mVignetteFilterAdjuster;
    private FilterAdjuster mSaturationFilterAdjuster;
    private FilterAdjuster mSharpnessFilterAdjuster;
    private FilterAdjuster mHueFilterAdjuster;
    private FilterAdjuster mSepiaFilterAdjuster;
    private FilterAdjuster mMonochromeFilterAdjuster;
    private FilterAdjuster mWhiteBalanceFilterAdjuster;
    private FilterAdjuster mColorBalanceFilterAdjuster;
    private FilterAdjuster mExposureFilterAdjuster;
    private FilterAdjuster mLevelsFilterAdjuster;

    private static final String TAG = "data-->";
    List<EffectModel> EffectList = new ArrayList();
    List<SketchModel> SketchList = new ArrayList();
    ActivityEditorBinding editorBinding;
    Uri imgUtl, smal;
    private String string;
    private SketchImage sketchImage;
    Bitmap bmOriginal;
    private int MAX_PROGRESS;
    SketchAdapter sketchAdapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editorBinding = ActivityEditorBinding.inflate(getLayoutInflater());
        setContentView(editorBinding.getRoot());
        Display display = getWindowManager().getDefaultDisplay();
        DisplayWidth = display.getWidth();
        DisplayHeight = display.getHeight()-editorBinding.topLayout.getHeight();

        progressDialog = new ProgressDialog(EditorActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        filters = Cooper.getInst().getLocalFilters();
        CurveImage();
        setFilters();
        editorBinding.imageViewDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imgUtl = Uri.parse(extras.getString("IMAGE_URI"));
            isCamera = extras.getBoolean("iscamera");
        }

        try {
          /*  if (isCamera) {
                bmOriginal = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse("file://" + imgUtl));
            } else {*/
                bmOriginal = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUtl);
         //   }
        } catch (IOException e) {
            e.printStackTrace();
        }

        editorBinding.imageView1.setImageBitmap(bmOriginal);

        editorBinding.drawingViewContainer.setDrawingCacheEnabled(true);
        editorBinding.drawingViewContainer.buildDrawingCache();

        ViewTreeObserver vto = editorBinding.MainGPUImageView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {


                int finalHeight = (DisplayHeight-editorBinding.topLayout.getHeight()-editorBinding.frmControlls.getHeight());
                int finalWidth = editorBinding.imageView1.getMeasuredWidth();

                RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(DisplayWidth, finalHeight);
                imageLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                editorBinding.drawingViewContainer.setLayoutParams(imageLayoutParams);
                editorBinding.imageView1.setVisibility(View.GONE);
                editorBinding.MainGPUImageView.setImage(bmOriginal);
                return true;
            }
        });

        sketchImage = new SketchImage.Builder(this, bmOriginal).build();

        for (int i = 0; i < 11; i++) {
            SketchModel sketchModel = new SketchModel();
            if (i == 0) {
                sketchModel.setName("Original");
            } else {
                sketchModel.setName("sketch " + i);
            }
            sketchModel.setSketchName(R.drawable.usr);
            this.SketchList.add(sketchModel);
        }

        sketchAdapter = new SketchAdapter(EditorActivity.this, this.SketchList, new SketchImage.Builder(this, BitmapFactory.decodeResource(getResources(), R.drawable.usr)).build());
        editorBinding.recyclerViewSketch.setAdapter(sketchAdapter);
        editorBinding.recyclerViewSketch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


        editorBinding.navigation.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.id_sketh:
                                sketh();
                                break;
                            case R.id.id_filter:
                                filter();
                                break;
                            case R.id.id_adjust:
                                adjust();
                                break;
                        }
                        return true;
                    }
                });

        editorBinding.ajutmentNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ll_brightness:
                        brightnessClick();
                        break;
                    case R.id.ll_contrast:
                        contrastClick();
                        break;
                    case R.id.ll_highlight:
                        highlight();
                        break;
                    case R.id.ll_temp:
                        tempClick();
                        break;
                    case R.id.ll_tone:
                        toneClick();
                        break;
//                    case R.id.ll_saturn:
//                        saturn();
//                        break;
                }

                return true;
            }
        });

        editorBinding.seekBarforBright.addOnChangeListener(this);
        editorBinding.seekBarforContrast.addOnChangeListener(this);
        editorBinding.seekBarforHigh.addOnChangeListener(this);
        editorBinding.seekBarforTemp.addOnChangeListener(this);
        editorBinding.seekBarforTone.addOnChangeListener(this);
        editorBinding.seekBarforSaturn.addOnChangeListener(this);

        sketchAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                // Glide.with(ImageEditorActivity.this).load(sketchImage.getImageAs(pos, MAX_PROGRESS)).into(editorBinding.imageView1);

                EditActionTask editActionTask = new EditActionTask(pos,sketchImage);
                editActionTask.execute();


//              progressDialog.show();
//                editorBinding.imageView1.setImageBitmap(sketchImage.getImageAs(pos, 100));
//               progressDialog.dismiss();
//                bmOriginal = ((BitmapDrawable) editorBinding.imageView1.getDrawable()).getBitmap();
            }
        });

        editorBinding.textViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save();
            }
        });
    }

    private void brightnessClick() {
        editorBinding.seekBarforBright.setVisibility(View.VISIBLE);

        editorBinding.seekBarforSaturn.setVisibility(View.GONE);
        editorBinding.seekBarforContrast.setVisibility(View.GONE);
        editorBinding.seekBarforHigh.setVisibility(View.GONE);
        editorBinding.seekBarforTemp.setVisibility(View.GONE);
        editorBinding.seekBarforTone.setVisibility(View.GONE);
    }

    private void contrastClick() {
        editorBinding.seekBarforContrast.setVisibility(View.VISIBLE);

        editorBinding.seekBarforSaturn.setVisibility(View.GONE);
        editorBinding.seekBarforBright.setVisibility(View.GONE);
        editorBinding.seekBarforHigh.setVisibility(View.GONE);
        editorBinding.seekBarforTemp.setVisibility(View.GONE);
        editorBinding.seekBarforTone.setVisibility(View.GONE);
    }

    private void highlight() {
        editorBinding.seekBarforHigh.setVisibility(View.VISIBLE);

        editorBinding.seekBarforSaturn.setVisibility(View.GONE);
        editorBinding.seekBarforBright.setVisibility(View.GONE);
        editorBinding.seekBarforTemp.setVisibility(View.GONE);
        editorBinding.seekBarforContrast.setVisibility(View.GONE);
        editorBinding.seekBarforTone.setVisibility(View.GONE);
    }

    private void tempClick() {
        editorBinding.seekBarforTemp.setVisibility(View.VISIBLE);

        editorBinding.seekBarforSaturn.setVisibility(View.GONE);
        editorBinding.seekBarforBright.setVisibility(View.GONE);
        editorBinding.seekBarforHigh.setVisibility(View.GONE);
        editorBinding.seekBarforContrast.setVisibility(View.GONE);
        editorBinding.seekBarforTone.setVisibility(View.GONE);
    }

    private void toneClick() {
        editorBinding.seekBarforTone.setVisibility(View.VISIBLE);

        editorBinding.seekBarforSaturn.setVisibility(View.GONE);
        editorBinding.seekBarforBright.setVisibility(View.GONE);
        editorBinding.seekBarforHigh.setVisibility(View.GONE);
        editorBinding.seekBarforContrast.setVisibility(View.GONE);
        editorBinding.seekBarforTemp.setVisibility(View.GONE);
    }

    private void saturn() {
        editorBinding.seekBarforSaturn.setVisibility(View.VISIBLE);
        editorBinding.seekBarforBright.setVisibility(View.GONE);
        editorBinding.seekBarforContrast.setVisibility(View.GONE);
        editorBinding.seekBarforHigh.setVisibility(View.GONE);
        editorBinding.seekBarforTemp.setVisibility(View.GONE);
        editorBinding.seekBarforTone.setVisibility(View.GONE);
    }



    private void adjust() {
        editorBinding.layoutAdjust.setVisibility(View.VISIBLE);
        editorBinding.curveList.setVisibility(View.GONE);
        editorBinding.recyclerViewSketch.setVisibility(View.GONE);
    }

    private void filter() {
        editorBinding.curveList.setVisibility(View.VISIBLE);
        editorBinding.layoutAdjust.setVisibility(View.GONE);
        editorBinding.recyclerViewSketch.setVisibility(View.GONE);


        if (mContrastFilterAdjuster != null) {
            mContrastFilterAdjuster.adjust(PrevContrast);
        }

        if (mSaturationFilterAdjuster != null) {
            mSaturationFilterAdjuster.adjust(PrevSaturation);
        }

        if (mVignetteFilterAdjuster != null) {
            mVignetteFilterAdjuster.adjust(PrevVignette);
        }

        if (mBrightnessFilterAdjuster != null) {
            mBrightnessFilterAdjuster.adjust(PrevBrightness);
        }
        if (mSharpnessFilterAdjuster != null) {
            mSharpnessFilterAdjuster.adjust(PrevSharpness);
        }
        if (mHueFilterAdjuster != null) {
            mHueFilterAdjuster.adjust(PrevHue);
        }
        if (mSepiaFilterAdjuster != null) {
            mSepiaFilterAdjuster.adjust(PrevSepia);
        }
        if (mMonochromeFilterAdjuster != null) {
            mMonochromeFilterAdjuster.adjust(PrevMonochrome);
        }
        if (mWhiteBalanceFilterAdjuster != null) {
            mWhiteBalanceFilterAdjuster.adjust(PrevWhiteBalance);
        }
        if (mColorBalanceFilterAdjuster != null) {
            mColorBalanceFilterAdjuster.adjust(PrevColorBalance);
        }
        if (mLevelsFilterAdjuster != null) {
            mLevelsFilterAdjuster.adjust(PrevLevels);
        }
        if (mExposureFilterAdjuster != null) {
            mExposureFilterAdjuster.adjust(PrevExposure);
        }
        editorBinding.MainGPUImageView.requestRender();
    }

    private void sketh() {
        editorBinding.recyclerViewSketch.setVisibility(View.VISIBLE);
        editorBinding.layoutAdjust.setVisibility(View.GONE);
        editorBinding.curveList.setVisibility(View.GONE);
    }

    public void CurveImage() {
        Bitmap smallImageBackgroud = ((BitmapDrawable) getResources().getDrawable(R.drawable.circleeffect)).getBitmap();

        initCurveFilterToolBar(smallImageBackgroud);

    }

    private void initCurveFilterToolBar(Bitmap smallImageBackgroud) {

        final JohnCrittle adapter = new JohnCrittle(EditorActivity.this, filters, smallImageBackgroud);
        editorBinding.curveList.setAdapter(adapter);
        editorBinding.curveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (adapter.getSelectFilter() != arg2) {


                    adapter.setSelectFilter(arg2);

                    curveFilter.setFromCurveFileInputStream(getResources().openRawResource(filters.get(arg2).getFilterfileRaw()));

                    editorBinding.MainGPUImageView.setFilter(filterGroup);
                }
            }
        });
    }


    public Bitmap viewToBitmap(View view) {
        progressDialog.show();
        Toast.makeText(EditorActivity.this, "start", Toast.LENGTH_SHORT).show();
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void setFilters() {
        curveFilter = new GPUImageToneCurveFilter();
        curveFilter.setFromCurveFileInputStream(getResources().openRawResource(filters.get(0).getFilterfileRaw()));

        contrastFilter = new GPUImageContrastFilter(1.0f);
        mContrastFilterAdjuster = new FilterAdjuster(contrastFilter);

        brightnessFilter = new GPUImageBrightnessFilter(0f);
        mBrightnessFilterAdjuster = new FilterAdjuster(brightnessFilter);

        saturationFilter = new GPUImageSaturationFilter(1.0f);
        mSaturationFilterAdjuster = new FilterAdjuster(saturationFilter);

        hueFilter = new GPUImageHueFilter(0.0f);
        mHueFilterAdjuster = new FilterAdjuster(hueFilter);


        whiteBalanceFilter = new GPUImageWhiteBalanceFilter(5000.0f, 0.0f);
        mWhiteBalanceFilterAdjuster = new FilterAdjuster(whiteBalanceFilter);

        exposureFilter = new GPUImageExposureFilter(0.0f);
        mExposureFilterAdjuster = new FilterAdjuster(exposureFilter);

        filterGroup = new GPUImageFilterGroup();
        filterGroup.addFilter(contrastFilter);
        filterGroup.addFilter(curveFilter);
        filterGroup.addFilter(brightnessFilter);
        filterGroup.addFilter(saturationFilter);
        filterGroup.addFilter(hueFilter);
        filterGroup.addFilter(whiteBalanceFilter);
        filterGroup.addFilter(exposureFilter);

        editorBinding.MainGPUImageView.setFilter(filterGroup);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @Override
    public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
        switch (slider.getId()) {
            case R.id.seekBarforBright:
                PrevBrightness = (int) value;
                if (mBrightnessFilterAdjuster != null) {
                    mBrightnessFilterAdjuster.adjust((int) value);
                }
                editorBinding.MainGPUImageView.requestRender();
                break;

            case R.id.seekBarforContrast:
                PrevContrast = (int) value;
                if (mContrastFilterAdjuster != null) {
                    mContrastFilterAdjuster.adjust((int) value);
                }
                editorBinding.MainGPUImageView.requestRender();
                break;

            case R.id.seekBarforHigh:
                PrevExposure = (int) value;
                if (mExposureFilterAdjuster != null) {
                    mExposureFilterAdjuster.adjust((int) value);
                }
                editorBinding.MainGPUImageView.requestRender();
                break;

            case R.id.seekBarforTemp:
                PrevWhiteBalance = (int) value;
                if (mWhiteBalanceFilterAdjuster != null) {
                    mWhiteBalanceFilterAdjuster.adjust((int) value);
                }
                editorBinding.MainGPUImageView.requestRender();
                break;

            case R.id.seekBarforTone:
                PrevHue = (int) value;
                if (mHueFilterAdjuster != null) {
                    mHueFilterAdjuster.adjust((int) value);
                }
                editorBinding.MainGPUImageView.requestRender();
                break;

            case R.id.seekBarforSaturn:
                PrevSaturation = (int) value;
                if (mSaturationFilterAdjuster != null) {
                    mSaturationFilterAdjuster.adjust((int) value);
                }
                editorBinding.MainGPUImageView.requestRender();
                break;

        }

    }

    class EditActionTask extends AsyncTask<Void, Void, Bitmap> {


        int pos;
        private SketchImage sketchImage;

        //

        public EditActionTask(int pos, SketchImage sketchImage) {
            this.pos = pos;
            this.sketchImage = sketchImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("xxxx","start");
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            mBitmapTemp= sketchImage.getImageAs(pos, 100);
            return mBitmapTemp;
        }

        @Override protected void onPostExecute(Bitmap result) {
            // update ui from result butmap
            if (result != null) {
                Log.e("xxxx","edn");
                editorBinding.imageView1.setImageBitmap(result);
                bmOriginal = ((BitmapDrawable) editorBinding.imageView1.getDrawable()).getBitmap();
                progressDialog.dismiss();
            }else {
                Log.e("xxxx","xxxxxxx");
                progressDialog.dismiss();
            }


        }

    }

    public void save() {

        progressDialog.show();
        Bitmap bmOverlay = Bitmap.createBitmap(editorBinding.drawingViewContainer.getWidth(), editorBinding.drawingViewContainer.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmOverlay);
        //canvas.drawBitmap(ThirdFinalBitmap, new Matrix(), null);
        canvas.drawBitmap(editorBinding.drawingViewContainer.getDrawingCache(), 0, 0, null);
        //canvas.drawBitmap(bitmapframe, 0, 0, null);
        try {
            Bitmap gpubitmap = editorBinding.MainGPUImageView.capture();

            bmOverlay = merge(bmOverlay, gpubitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        saveImageToSD(bmOverlay, "sketchx_" + generateRandomName(1000000, 5000000) + ".jpg", Bitmap.CompressFormat.JPEG);
        progressDialog.dismiss();
    }
    public String saveImageToSD(Bitmap bmp, String filename, Bitmap.CompressFormat format) {
        File file2 = null;
        try {
            String path1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString()
                    + getString(R.string.directory);
            FileOutputStream fos = null;
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bmp.compress(format, 100, bytes);
            File file1 = new File(path1);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            file2 = new File(file1, filename);
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos = new FileOutputStream(file2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                fos.write(bytes.toByteArray());
                fos.close();
                Toast.makeText(this, "Sucessfully Saved !", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
            /*if (dia.isShowing()) {
                dia.dismiss();
            }*/
            //       PatrickCox.FinalBitmap = bmp;
            //  FinalURI = "" + path1 + "/RPMovieFXPhoto/Gallery/" + filename;

            ContentValues image = new ContentValues();
            String dateStr = "04/05/2010";

            SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
            Date dateObj = curFormater.parse(dateStr);
            SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");

            String newDateStr = postFormater.format(dateObj);
            image.put(MediaStore.Images.Media.TITLE, filename);
            image.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
            image.put(MediaStore.Images.Media.DESCRIPTION, filename);
            image.put(MediaStore.Images.Media.DATE_ADDED, newDateStr);
            image.put(MediaStore.Images.Media.DATE_TAKEN, "");
            image.put(MediaStore.Images.Media.DATE_MODIFIED, "");
            image.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
            image.put(MediaStore.Images.Media.ORIENTATION, 0);

            File parent = file2.getParentFile();
            String path = parent.toString().toLowerCase();
            String name = parent.getName().toLowerCase();
            image.put(MediaStore.Images.ImageColumns.BUCKET_ID, path.hashCode());
            image.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, name);
            image.put(MediaStore.Images.Media.SIZE, file2.length());

            image.put(MediaStore.Images.Media.DATA, file2.getAbsolutePath());

            Uri result = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);


            return file2.getPath().toString();
        } catch (NullPointerException e) {
            // TODO: handle exception
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    private int generateRandomName(int LowerLimit, int UpperLimit) {

        Random r = new Random();
        return r.nextInt((UpperLimit - LowerLimit) + 1) + LowerLimit;
    }

    ProgressDialog dia;

    public static Bitmap merge(Bitmap bitmapmain, Bitmap bitmapback) {

        Bitmap bmOverlay = Bitmap.createBitmap(bitmapback.getWidth(), bitmapback.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmOverlay);

        canvas.drawBitmap(bitmapback, new Matrix(), null);
        canvas.drawBitmap(bitmapmain, 0, 0, null);


        return bmOverlay;

    }


}

