package puninar.view.ccms.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import puninar.view.ccms.Map.MapMainActivity;
import puninar.view.ccms.R;

/**
 * Created by think on 18/07/2016.
 */


public class MenuAwal extends Fragment {
    ImageView menu1,menu2,menu3,menu4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_krani,
                container, false);

        menu1 = (ImageView) view.findViewById(R.id.menuicon1);
        menu1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent r = new Intent(getActivity(), InputData.class);
                startActivity(r);
            }
        });
        menu2 = (ImageView) view.findViewById(R.id.menuicon2);
        menu2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent r = new Intent(getActivity(), MapsActivity.class);
                startActivity(r);
            }
        });
        menu3 = (ImageView) view.findViewById(R.id.menuicon3);
        menu3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ListviewResult.class);
                startActivity(i);
            }
        });
        menu4 = (ImageView) view.findViewById(R.id.menuicon4);
        menu4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MapMainActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}




