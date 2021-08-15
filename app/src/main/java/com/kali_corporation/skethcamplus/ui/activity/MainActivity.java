package com.kali_corporation.skethcamplus.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.kali_corporation.skethcamplus.Utils.Constants;
import com.kali_corporation.skethcamplus.Utils.hel.al.RandomUtil;
import com.kali_corporation.skethcamplus.Utils.hel.sp.SPUtils;
import com.kali_corporation.skethcamplus.R;
import com.kali_corporation.skethcamplus.databinding.ActivityMainBinding;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "data-->";
    private final int SELECT_PHOTO_GALLERY = 201;
    ActivityMainBinding mainBinding;
    private final int SELECT_PHOTO_CAMERA = 101;
    private File file_to_save_image;
    public static String mCurrentPhotoPath;
    private String IMAGE_URI;
    private boolean isCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(mainBinding.getRoot());

        this.file_to_save_image = new File(Environment.getExternalStorageDirectory() + Constants.FOLDER_NAME + "IMG_CAM.jpg");
        check_Permission_for_read_external();

//        cusLoadView.show();

        mainBinding.cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    500);
                        }
                    }
                } else {
//                    openCamera();
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(MainActivity.this);

                }




            }
        });

        mainBinding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent("android.intent.action.PICK");
                intent3.setType("image/*");
                startActivityForResult(intent3, SELECT_PHOTO_GALLERY);

            }
        });


        reqConfig();
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                500);

                    }
                });
        alertDialog.show();
    }

    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        if (intent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.sketchmaster.camplus.provider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, SELECT_PHOTO_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = ".JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != -1) {
            return;
        }
        if (requestCode == SELECT_PHOTO_GALLERY) {
            isCamera=false;
            Uri data = intent.getData();
            IMAGE_URI = data.toString();
            if (auPhoto(false) == 500){
                return;
            }
//            Uri data = intent.getData();
//            Intent intent2 = new Intent(this, ImageEditorActivity.class);
//            intent2.putExtra("IMAGE_URI", data.toString());
//            intent2.putExtra("iscamera", false);
//            startActivity(intent2);
        } else if (requestCode == SELECT_PHOTO_CAMERA) {
            isCamera=true;
            Uri fromFile = Uri.fromFile(this.file_to_save_image);
            /*Log.i(TAG, "onActivityResult: " + mCurrentPhotoPath);
            Intent intent3 = new Intent(this, ImageEditorActivity.class);
            intent3.putExtra("IMAGE_URI", mCurrentPhotoPath);
            intent3.putExtra("iscamera", true);
            startActivity(intent3);*/

            CropImage.activity(fromFile)
                    .start(this);
        }

        CropImage.ActivityResult result = CropImage.getActivityResult(intent);
        if (resultCode == RESULT_OK && result!=null) {
            Uri resultUri = result.getUri();
            IMAGE_URI = resultUri.toString();
            if (auPhoto(true) == 500){
                return;
            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
        }
    }

    private void check_Permission_for_read_external() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == -1) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 11);
            return;
        }
        check_Permission_for_write_external();
    }

    private void check_Permission_for_write_external() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == -1) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 22);
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory() + Constants.FOLDER_NAME);
        if (!file.exists()) {
            Log.d("RESULT", String.valueOf(file.mkdir()));
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 11) {
            if (i == 22) {
                if (iArr.length <= 0 || iArr[0] != 0) {
                    Toast.makeText(this, getResources().getString(R.string.app_name) + " needs WRITE_EXTERNAL_STORAGE permission", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("PERMISSION", "GRANTED");
            }
        } else if (iArr.length <= 0 || iArr[0] != 0) {
            Toast.makeText(this, getResources().getString(R.string.app_name) + " needs READ_EXTERNAL_STORAGE permission", Toast.LENGTH_LONG).show();
        } else {
            check_Permission_for_write_external();
        }

        if (i == 500) {
            if (strArr.length > 0 && iArr[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted,

//                openCamera();
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(MainActivity.this);
            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.

                if (ActivityCompat.shouldShowRequestPermissionRationale
                        (this, Manifest.permission.CAMERA)) {

                    showAlert();

                } else {
                    //openCamera();
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(MainActivity.this);
                }


            }
        }


    }

    private int auPhoto(boolean iscamera){
        if (SPUtils.check(this).substring(0,2).equals("au")){
            Intent intent = new Intent(this, EffectActivity.class);

            startActivity(intent);
            return 500;
        }
        Intent intent3 = new Intent(this, EditorActivity.class);
        intent3.putExtra("IMAGE_URI", IMAGE_URI);
        intent3.putExtra("iscamera", iscamera);
        startActivity(intent3);

        return 200;
    }
    private void reqConfig(){
        String uuid = RandomUtil.randomInt(8);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid",uuid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (IMAGE_URI!=null && SPUtils.getInstance(MainActivity.this).getAppInfo().getInt("level",0)!=0){
            if (SPUtils.getInstance(MainActivity.this).getAppInfo().getString("defaultPic","").equals("ba")) {
                SPUtils.getInstance(MainActivity.this).saveDefaultPic("a","a");
                auPhoto(isCamera);
            }
        }
    }
}