package puninar.view.ccms.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import puninar.view.ccms.R;

    public class SpinnerActivity extends AppCompatActivity {


        String[] SPINNERLIST = {"Android Material Design", "Material Design Spinner", "Spinner Using Material Library", "Material Spinner Example"};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.spinner_tes);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
            MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                    findViewById(R.id.android_material_design_spinner);
            materialDesignSpinner.setAdapter(arrayAdapter);
        }
    }

