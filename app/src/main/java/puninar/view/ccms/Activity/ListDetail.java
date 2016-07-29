package puninar.view.ccms.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import puninar.view.ccms.R;
import puninar.view.ccms.Utility.Config;
import puninar.view.ccms.Utility.SerializedList;

public class ListDetail extends AppCompatActivity {

    EditText etId, etName, etDesc, etQty;
    ImageView listImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_detail);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */

        etId = (EditText)findViewById(R.id.etId);
        etName = (EditText)findViewById(R.id.etName);
        etDesc = (EditText)findViewById(R.id.etDesc);
        etQty = (EditText)findViewById(R.id.etQty);
        listImage = (ImageView)findViewById(R.id.listimage);

        if(getIntent().getSerializableExtra("product") != null){
            SerializedList product = (SerializedList) getIntent().getSerializableExtra("product");
            etId.setText("" + product.id);
            etName.setText("" + product.orders);
            etDesc.setText("" + product.checker_name);
            etQty.setText("" + product.startdate);

            String path = Config.LOGIN_URL +"imageuploadtest/" + product.image + "";
                Picasso.with(getApplicationContext()).load(path).into(listImage);
            }



        }
    }

