package puninar.view.ccms.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.PhotoLoader;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import puninar.view.ccms.R;
import puninar.view.ccms.Utility.Config;
import puninar.view.ccms.Utility.GPSTracker;
import puninar.view.ccms.Utility.MyCommand;

public class InputData extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    ImageView ivGallery, ivCamera, ivDelete;


    EditText orders, mbl, hbl, aju, cust, process, cntr, checker, problems, remarks, btnDate, btnTime;

    Button ivUpload;

    GalleryPhoto galleryPhoto;

    CameraPhoto cameraPhoto;

    final int GALLERY_REQUEST = 1200;

    final int CAMERA_REQUEST = 2100;

    final String TAG = this.getClass().getSimpleName();

    LinearLayout linearMain;

    ArrayList<String> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);


        linearMain = (LinearLayout) findViewById(R.id.linearMain);

        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivDelete = (ImageView) findViewById(R.id.ivUpload);
        ivUpload = (Button) findViewById(R.id.btnRegister);

        orders = (EditText) findViewById(R.id.reg_order);
        mbl = (EditText) findViewById(R.id.reg_mbl);

        hbl = (EditText) findViewById(R.id.reg_hbl);
        hbl.setInputType(InputType.TYPE_CLASS_NUMBER);
        aju = (EditText) findViewById(R.id.reg_aju);
        cust = (EditText) findViewById(R.id.reg_cust);
        process = (EditText) findViewById(R.id.reg_process);
        cntr = (EditText) findViewById(R.id.reg_cntr);
        checker = (EditText) findViewById(R.id.reg_checkname);
        problems = (EditText) findViewById(R.id.reg_problems);
        remarks = (EditText) findViewById(R.id.reg_remarks);

        btnDate = (EditText) findViewById(R.id.reg_date);
        btnDate.setKeyListener(null);
        btnTime = (EditText) findViewById(R.id.reg_time);
        btnTime.setKeyListener(null);


        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageList.size() < 5) {
                    try {
                        Intent in = cameraPhoto.takePhotoIntent();
                        startActivityForResult(in, CAMERA_REQUEST);
                        cameraPhoto.addToGallery();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),
                                "Something Wrong while taking photos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Maximum Photo Upload", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageList.size() < 5) {
                    Intent in = galleryPhoto.openGalleryIntent();
                    startActivityForResult(in, GALLERY_REQUEST);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Maximum Photo Upload", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*imageList.clear();
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setImageBitmap (null);
                imageView.setImageResource(0);;*/



            }
        });

        ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(InputData.this);
                builder.setTitle(getString(R.string.dialog_title));
                builder.setMessage(getString(R.string.dialog_message));

                String positiveText = getString(android.R.string.ok);
                builder.setPositiveButton(positiveText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // positive button logic
                                uploadImage();

                            }
                        });

                String negativeText = getString(android.R.string.cancel);
                builder.setNegativeButton(negativeText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // negative button logic
                            }
                        });
                AlertDialog dialog = builder.create();
                // display dialog
                dialog.show();
            }
        });


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDate.setText(null);
                setDate();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTime.setText(null);
                setTime();
            }
        });
    }


    private void setDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datepickerdialog = DatePickerDialog.newInstance(
                InputData.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datepickerdialog.setThemeDark(false); //set dark them for dialog?
        datepickerdialog.vibrate(true); //vibrate on choosing date?
        datepickerdialog.dismissOnPause(true); //dismiss dialog when onPause() called?
        datepickerdialog.showYearPickerFirst(true); //choose year first?
        datepickerdialog.setAccentColor(Color.parseColor("#0099FF")); // custom accent color
        datepickerdialog.setTitle("Please select a date"); //dialog title
        datepickerdialog.show(getFragmentManager(), "Datepickerdialog"); //show dialog
    }

    private void setTime() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timepickerdialog = TimePickerDialog.newInstance(InputData.this,
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
        timepickerdialog.setThemeDark(false); //Dark Theme?
        timepickerdialog.vibrate(true); //vibrate on choosing time?
        timepickerdialog.setAccentColor(Color.parseColor("#0099FF"));
        timepickerdialog.dismissOnPause(false); //dismiss the dialog onPause() called?
        timepickerdialog.enableSeconds(true); //show seconds?

        //Handling cancel event
        timepickerdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Toast.makeText(InputData.this, "Cancel choosing time", Toast.LENGTH_SHORT).show();
            }
        });
        timepickerdialog.show(getFragmentManager(), "Timepickerdialog"); //show time picker dialog
    }


    private void uploadImage() {
        if (!validate()) {
            ivUpload.setEnabled(true);

            GPSTracker gps;
            gps = new GPSTracker(InputData.this);
            final double latitude = gps.getLatitude();
            final double longitude = gps.getLongitude();

            final MyCommand myCommand = new MyCommand(getApplicationContext());
            for (String imagePath : imageList) {
                try {
                    Bitmap bitmap = PhotoLoader.init().from(imagePath).requestSize(512, 512).getBitmap();
                    final String encodedString = ImageBase64.encode(bitmap);

                    //String url = "http://192.168.0.102/imageuploadtest/upload.php";
                    //String url = "http://192.168.43.214/imageuploadtest/upload.php";
                    //String url = "http://192.168.0.164:8090/mobile-ilham-insert-1";
                    String url = Config.LOGIN_URL + "imageuploadtest/upload.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            if (response.equals("uploaded_success")) {
                                Intent r = new Intent(getApplicationContext(), MenuUtama.class);
                                startActivity(r);
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Your Location is -\nLat: " + latitude + "\nLong: "
                                                + longitude, Toast.LENGTH_LONG).show();
                            }else {
                            Toast.makeText(InputData.this, "" + response, Toast.LENGTH_SHORT).show();
                        }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error while uploading image", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("image", encodedString);
                            params.put("orders", orders.getText().toString());
                            params.put("startdate", btnDate.getText().toString());
                            params.put("starttime", btnTime.getText().toString());
                            params.put("mbl", mbl.getText().toString());
                            params.put("hbl", hbl.getText().toString());
                            params.put("aju", aju.getText().toString());
                            params.put("cust", cust.getText().toString());
                            params.put("process", process.getText().toString());
                            params.put("cntr", cntr.getText().toString());
                            params.put("checker_name", checker.getText().toString());
                            params.put("problems", problems.getText().toString());
                            params.put("remarks", remarks.getText().toString());
                            params.put("longitude", String.valueOf(longitude));
                            params.put("latitude", String.valueOf(latitude));
                            return params;
                        }
                    };

                    myCommand.add(stringRequest);

                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error while loading image", Toast.LENGTH_SHORT).show();
                }
            }
            myCommand.execute();
            return;
        }
        ivUpload.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                galleryPhoto.setPhotoUri(data.getData());
                String photoPath = galleryPhoto.getPath();
                imageList.add(photoPath);
                Log.d(TAG, photoPath);
                try {
                    Bitmap bitmap = PhotoLoader.init().from(photoPath).requestSize(512, 512).getBitmap();

                    ImageView imageView = new ImageView(getApplicationContext());
                    LinearLayout.LayoutParams layoutParams =
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setPadding(0, 0, 0, 10);
                    imageView.setAdjustViewBounds(true);
                    imageView.setImageBitmap(bitmap);

                    linearMain.addView(imageView);

                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error while loading image", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == CAMERA_REQUEST) {
                String photoPath = cameraPhoto.getPhotoPath();
                imageList.add(photoPath);
                Log.d(TAG, photoPath);
                try {
                    Bitmap bitmap = PhotoLoader.init().from(photoPath).requestSize(512, 512).getBitmap();

                    ImageView imageView = new ImageView(getApplicationContext());
                    LinearLayout.LayoutParams layoutParams =
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setPadding(0, 0, 0, 10);
                    imageView.setAdjustViewBounds(true);
                    imageView.setImageBitmap(bitmap);

                    linearMain.addView(imageView);

                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error while loading image", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =  year + "-"  + LPad(++monthOfYear + "", "0", 2) + "-"  + LPad(dayOfMonth+ "", "0", 2);
        btnDate.setText(date);

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        btnTime.setText(hourString + ":" + minuteString + ":" + secondString);
    }

    private static String LPad(String schar, String spad, int len) {
        String sret = schar;
        for (int i = sret.length(); i < len; i++) {
            sret = spad + sret;
        }
        return new String(sret);
    }

   public boolean validate() {
        boolean valid = true;

        String ordersp = orders.getText().toString();
        String mblp = mbl.getText().toString();
        String startdatep = btnDate.getText().toString();
        String starttimep = btnTime.getText().toString();
        String hblp = hbl.getText().toString();
        String checkernamep = btnDate.getText().toString();


        if (ordersp.isEmpty()) {
            orders.setError("Please, Insert this field...");
            valid = false;
        } else {
            orders.setError(null);
        }

        if (startdatep.isEmpty()) {
            btnDate.setError("Please, Insert this field...");
            valid = false;
        } else {
            btnDate.setError(null);
        }

        if (starttimep.isEmpty()) {
            btnTime.setError("Please, Insert this field...");
            valid = false;
        } else {
            btnTime.setError(null);
        }

        if (checkernamep.isEmpty()) {
            checker.setError("Please, Insert this field...");
            valid = false;
        } else {
            checker.setError(null);
        }

        if (mblp.isEmpty()) {
            mbl.setError("Please, Insert this field...");
            valid = false;
        } else {
            mbl.setError(null);
        }

        if (hblp.isEmpty() || mblp.length() < 5) {
            hbl.setError("minimum 5 numeric characters");
            valid = false;
        } else {
            hbl.setError(null);
        }

        return valid;
    }
}
