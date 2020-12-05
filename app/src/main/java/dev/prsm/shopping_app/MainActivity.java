package dev.prsm.shopping_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
{
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createAccount = findViewById(R.id.create_account_button);
        Button login = findViewById(R.id.login_button);

        createAccount.setOnClickListener((View v) ->
        {
            intent = new Intent(this, CreateAccount.class);
            startActivity(intent);
        });

        login.setOnClickListener((View v) ->
        {
            intent = new Intent(this, Login.class);
            startActivity(intent);
        });
    }
}