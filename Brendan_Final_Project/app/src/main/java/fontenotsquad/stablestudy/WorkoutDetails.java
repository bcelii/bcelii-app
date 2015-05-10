package fontenotsquad.stablestudy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import butterknife.InjectView;
import butterknife.OnClick;


public class WorkoutDetails extends ActionBarActivity {
    public static final String TAG = MainPage.class.getSimpleName();
    @InjectView(R.id.workoutType) EditText workoutType;
    @InjectView(R.id.description) EditText description;
    @InjectView(R.id.time) EditText time;
    @InjectView(R.id.location) EditText location;
    @InjectView(R.id.leader) EditText leader;

    /*Populates with Parse data with:
    *1) type of workout
    * 2) Description
    * 3) Time
    * 4) Place
    * 5) Person who's leading it
     */
    public void fillList(ParseObject object){
        /*
        * will populate table view with details of workout
         */
        Log.w(TAG,"inside fillList");
        Log.v(TAG,object.get("Type").toString());
        Log.v(TAG,object.get("Info").toString());

        TextView t=(TextView)findViewById(R.id.workoutType);
        t.setText(object.get("Type").toString());
        t=(TextView)findViewById(R.id.description);
        t.setText(object.get("Info").toString());
        t=(TextView)findViewById(R.id.time);
        t.setText(object.get("Time").toString());
        t=(TextView)findViewById(R.id.location);
        t.setText(object.get("Location").toString());
        t=(TextView)findViewById(R.id.leader);
        t.setText(object.get("Leader").toString());
    }

    @OnClick(R.id.backButton)
    void goBack(View view) {
        Log.v(TAG,"inside the back button clicked");
        Intent mainPage = new Intent(getApplicationContext(), MainPage.class);

        startActivity(mainPage);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);


        /*
        * Retrieve workout details from Parse database according to day selected
         */
        Intent intent = getIntent();

        String day = intent.getStringExtra("day");


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Workouts");
        query.whereEqualTo("Day", day);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {

                } else {
                    fillList(object);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout_details, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * returns user to main page
     */
    public void goBack2(View view) {
        Log.v(TAG,"inside the back button clicked 2");
        Intent mainPage = new Intent(getApplicationContext(), MainPage.class);

        startActivity(mainPage);

    }
}
