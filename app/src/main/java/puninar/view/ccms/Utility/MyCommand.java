package puninar.view.ccms.Utility;

import android.content.Context;

import com.android.volley.Request;

import java.util.ArrayList;

import puninar.view.ccms.Connection.MySingleton;

public class MyCommand<T> {

    private ArrayList<Request<T>> requestList = new ArrayList<>(5);

    private Context context;

    public MyCommand(Context context){
        this.context = context;
    }

    public void add(Request<T> request){
        requestList.add(request);
    }

    public void remove(Request<T> request){
        requestList.remove(request);
    }

    public void execute(){
        for(Request<T> request: requestList){
            MySingleton.getInstance(context).addToRequestQueue(request);
        }
    }
}
