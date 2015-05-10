package fontenotsquad.stablestudy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends ActionBarActivity {
    public static final String TAG = Login.class.getSimpleName();




    private String jsonData;
    private boolean isValid;
    @InjectView(R.id.emailfield) EditText email;
    @InjectView(R.id.passwordfield) EditText password;
    @InjectView(R.id.loginbutton) Button login;
    @InjectView(R.id.registerbutton) Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "KCfh7XD21owedQ1Mg5JMP2cjuLdpjXXBVMngw1R4", "XkvzhgLbXfGtExt603qIFBYQXbKT32XZWvW42xX4");

        //test to see if will write to triathlon parse account
        /*ParseObject testObject = new ParseObject("BC_Object");
        testObject.put("foo", "bar");
        testObject.saveInBackground();*/

        /*ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

    }

    /*
    * Listener for the Log In user action
     */
    @OnClick(R.id.loginbutton)
    void submit(View view) {
        isValid = false;
        String emailInput = email.getText().toString();
        String pwInput = password.getText().toString();

        if (emailInput.length() > 0 && pwInput.length() > 0)
        {
            brendanLogin(emailInput, pwInput);
        }
        else
        {
            showErrorMessage();
        }
    }

    /*
    * redirects user to functional login page
    * */

     @OnClick(R.id.registerbutton)
    void goToRegister(View view) {
        Intent registerpage = new Intent(getApplicationContext(), RegisterPage.class);
        startActivity(registerpage);
    }

    private void goToMainPage() {
            Log.v(TAG, "is valid!");
            Intent mainPage = new Intent(getApplicationContext(), MainPage.class);
            mainPage.putExtra("user", "dummyUserName");
            startActivity(mainPage);
    }
    private void showErrorMessage() {
        Toast.makeText(this, getString(R.string.invalid_account_toast), Toast.LENGTH_LONG).show();
    }

    /*
    * will query Parse account for entered username and password and if valid then
    * will redirect to main page, and currently if only an invalid email then will just stay on
    * login page (no warning implemented yet)
     */
    private void brendanLogin(final String emailText, String pwText){



        if (isNetWorkAvailable()) {


            /*ParseQuery<ParseObject> query = ParseQuery.getQuery("MyClass");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        objectsWereRetrievedSuccessfully(objects);
                    } else {
                        objectRetrievalFailed();
                    }
                }
            });*/

            /*
            * reduced functionality to where will only check that email is
            * already in database and then will transfer to main page
             */
            ParseQuery<ParseObject> query = ParseQuery.getQuery("userInfo");
            query.whereEqualTo("email", emailText);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (object == null) {
                        Log.d("score", "The getFirst request failed.");
                    } else {
                        Log.d("score", "Retrieved the object.");
                        if(object.getString("email").length() > 2) {

                            goToMainPage();
                        }
                        else {
                            showErrorMessage();
                        }
                    }
                }
            });



        }
        else {
            Toast.makeText(this, getString(R.string.no_net_toast), Toast.LENGTH_LONG).show();
        }

    }





    private boolean isNetWorkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }





}
