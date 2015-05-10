package fontenotsquad.stablestudy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;


public class MainPage extends ActionBarActivity {

    public static final String TAG = MainPage.class.getSimpleName();
    private String jsonData;

    private ArrayList<String> daysList;
    private ListView lv;


    private ArrayAdapter<String> dayListAdapter;
    private String username;
    private String wData;
    private String wColor;
    private String wString;

    @InjectView(R.id.roomListView)
    ListView roomView;
    //@InjectView(R.id.fab) FloatingActionButton fab;
    @InjectView(R.id.weatherBar)
    LinearLayout wBar;
    @InjectView(R.id.weatherText)
    TextView wText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ButterKnife.inject(this);
        Intent intent = getIntent();

        // 2. get message value from intent
        username = intent.getStringExtra("user");

        /*
        * populate list with all workouts from Monday to Sunday in Parse database
         */
        getWorkoutInfo();


    }


    /*
    * Will pass the day that was selected to the next page so all the data can be got from the
    * Parse database
     */
    @OnItemClick(R.id.roomListView)
    void viewRoomDetails(int position) {
        Log.v(TAG, "The position hit was = " + position);
        Log.v(TAG, "Position " + position);
        Bundle extras = new Bundle();
        extras.putString("day", Integer.toString(position));



        Intent workoutDetails = new Intent(getApplicationContext(), WorkoutDetails.class);
        workoutDetails.putExtras(extras);
        startActivity(workoutDetails);

    }

    //initiates the list population of workouts in main page
    private void getWorkoutInfo() {

        jsonData = "";
        populateList();


    }

    private void populateList() {
        //will ge the list of days of the week to populate the list

        lv = (ListView) findViewById(R.id.roomListView);
        daysList = new ArrayList<String>() {{
            add("Monday");
            add("Tuesday");
            add("Wednesday");
            add("Thursday");
            add("Friday");
            add("Saturday");
            add("Sunday");
        }};
        dayListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, daysList);
        roomView.setAdapter(dayListAdapter);
    }

    private boolean isNetWorkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.v(TAG, "NETWORK IS AVAILABLE");
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }
}


