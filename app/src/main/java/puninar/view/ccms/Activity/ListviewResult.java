package puninar.view.ccms.Activity;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kosalgeek.android.json.JsonConverter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import puninar.view.ccms.Connection.MySingleton;
import puninar.view.ccms.R;
import puninar.view.ccms.Utility.Config;
import puninar.view.ccms.Utility.SerializedList;

public class ListviewResult extends AppCompatActivity {

    final String TAG = this.getClass().getSimpleName();


    ListView lvProduct;

    SwipeRefreshLayout swiprefresh;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ListviewResult.this, MenuUtama.class);
                startActivity(in);
            }
        });*/


        lvProduct = (ListView) findViewById(R.id.list);

        readData();

        swiprefresh = (SwipeRefreshLayout) findViewById(R.id.swiper);
        swiprefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                readData();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void readData() {
        String url = Config.LOGIN_URL +"imageuploadtest/list.php";
        //String url = "http://192.168.0.102/imageuploadtest/list.php";
        //String url = "http://192.168.0.164:8090/mobile-ilham-insert-1";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String jsonText) {
                        //Log.d(TAG, jsonText);

                        final ArrayList<SerializedList> productList = new JsonConverter<SerializedList>().toArrayList(jsonText, SerializedList.class);

                        BindDictionary<SerializedList> dictionary = new BindDictionary<>();
                        dictionary.addStringField(R.id.pun_order, new StringExtractor<SerializedList>() {
                            @Override
                            public String getStringValue(SerializedList product, int position) {
                                return product.orders;
                            }
                        });

                        dictionary.addStringField(R.id.pun_checker, new StringExtractor<SerializedList>() {
                            @Override
                            public String getStringValue(SerializedList product, int position) {
                                return product.checker_name;
                            }
                        });

                        dictionary.addStringField(R.id.pun_date, new StringExtractor<SerializedList>() {
                            @Override
                            public String getStringValue(SerializedList product, int position) {
                                return "" + product.startdate;
                            }
                        });

                        dictionary.addDynamicImageField(R.id.list_image,
                                new StringExtractor<SerializedList>() {
                                    @Override
                                    public String getStringValue(SerializedList product, int position) {
                                        String path = Config.LOGIN_URL +"imageuploadtest/" + product.image + "";
                                        Log.d(TAG, path);
                                        return path;
                                    }
                                },
                                new DynamicImageLoader() {
                                    @Override
                                    public void loadImage(String url, ImageView imageView) {
                                        imageView.setMaxHeight(50);
                                        imageView.setMaxWidth(50);
                                        Picasso.with(getApplicationContext())
                                                .load(url)
                                                .placeholder(R.drawable.bglagi)
                                                .error(R.drawable.ic_launcher)
                                                .into(imageView);
                                    }
                                }
                        );

                        FunDapter<SerializedList> adapter = new FunDapter<>(
                                getApplicationContext(),
                                productList,
                                R.layout.list_row,
                                dictionary
                        );
                        lvProduct.setAdapter(adapter);
                        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent in = new Intent(ListviewResult.this, ListDetail.class);
                                SerializedList selectedProduct = productList.get(position);
                                in.putExtra("product", selectedProduct);
                                startActivity(in);
                            }
                        });

                        if (swiprefresh.isRefreshing()) {
                            swiprefresh.setRefreshing(false);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error while reading data", Toast.LENGTH_LONG).show();

                        if (swiprefresh.isRefreshing()) {
                            swiprefresh.setRefreshing(false);
                        }
                    }
                }
        );

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        showNotification();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification(){

        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // intent triggered, you can add other intent for other actions
        Intent intent = new Intent(ListviewResult.this, ListviewResult.class);
        PendingIntent pIntent = PendingIntent.getActivity(ListviewResult.this, 0, intent, 0);

        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0
        Notification mNotification = new Notification.Builder(this)

                .setContentTitle("New Post!")
                .setContentText("Here's an awesome update for you!")
                .setSmallIcon(R.drawable.truk3)
                .setContentIntent(pIntent)
                .setSound(soundUri)

                .addAction(R.drawable.truk3, "View", pIntent)
                .addAction(0, "Remind", pIntent)

                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // If you want to hide the notification after it was selected, do the code below
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, mNotification);
    }

    public void cancelNotification(int notificationId){

        if (Context.NOTIFICATION_SERVICE!=null) {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(notificationId);
        }
    }
}
