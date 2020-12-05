package dev.prsm.shopping_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class LoginSuccess extends AppCompatActivity
{
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success);
        setTitle(getEmail());


    }

    String getEmail()
    {
        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String email = preferences.getString("email", "invalid");

        if (!email.equals("invalid"))
            return email;
        else
        {
            Log.v("MA", "Error retrieving email");
            return null;
        }
    }
}
