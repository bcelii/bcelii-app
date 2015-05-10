package fontenotsquad.stablestudy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;


import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RegisterPage extends ActionBarActivity {

    public static final String TAG = RegisterPage.class.getSimpleName();
    @InjectView(R.id.fName) EditText fName;
    @InjectView(R.id.lName) EditText lName;
    @InjectView(R.id.regemailField) EditText email;
    @InjectView(R.id.regpwField) EditText pw;
    @InjectView(R.id.userField) EditText userF;
    @InjectView(R.id.regButton) Button regButton;
    @InjectView(R.id.statusMessage) TextView sMessage;
    private String jsonData;
    private boolean isWorking;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*Parse.enableLocalDatastore(this);

        Parse.initialize(this, "KCfh7XD21owedQ1Mg5JMP2cjuLdpjXXBVMngw1R4", "XkvzhgLbXfGtExt603qIFBYQXbKT32XZWvW42xX4");*/


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        ButterKnife.inject(this);
        jsonData = "";
        /*Parse.enableLocalDatastore(this);

        Parse.initialize(this, "KCfh7XD21owedQ1Mg5JMP2cjuLdpjXXBVMngw1R4", "XkvzhgLbXfGtExt603qIFBYQXbKT32XZWvW42xX4");*/
    }

    private void goToMainPage(String userText) {
        Log.v(TAG, "is valid!");
        Intent mainPage = new Intent(getApplicationContext(), MainPage.class);
        mainPage.putExtra("user", userText);
        startActivity(mainPage);
    }
    @OnClick(R.id.regButton)
    void createAccount(){
        boolean isValid = true;
        String fNameText = fName.getText().toString();
        String lNameText = lName.getText().toString();
        String emailText = email.getText().toString();
        String pwText = pw.getText().toString();
        String userText = userF.getText().toString();


        /*
        * Issues warning if any of possible inputs fields invalid
         */
        if (fNameText.length() < 1 || lNameText.length() < 1)
        {
            Toast.makeText(this, "Name Fields can't be empty", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if(!isEmailValid(emailText)) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if(pwText.length() < 6) {
            Toast.makeText(this, "Password must be at least six characters", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if(userText.length() < 4) {
            Toast.makeText(this, "Username must be at least 4 characters", Toast.LENGTH_LONG).show();
            isValid = false;
        }

        if(isValid == false)
            return;


        /*
        * Will persist following users data to Parse class:
        * 1) full name
        * 2) email
        * 3) password
        * 4) username
         */


        ParseObject infoParse = new ParseObject("userInfo");
        infoParse.put("name", fNameText + " " + lNameText);
        infoParse.put("email",emailText);
        infoParse.put("password",pwText);
        infoParse.put("username",userText);
        infoParse.saveInBackground();

        goToMainPage(userText);


    }


    private boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }








}
