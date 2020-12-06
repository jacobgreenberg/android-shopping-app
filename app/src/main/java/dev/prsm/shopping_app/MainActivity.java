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
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createAccount = findViewById(R.id.create_account_button);
        Button login = findViewById(R.id.login_button);
        Button gridLayout = findViewById(R.id.grid_button);

        gridLayout.setOnClickListener((View v) ->
                startActivity(new Intent(this, Shop.class)));

        createAccount.setOnClickListener((View v) ->
                startActivity(new Intent(this, CreateAccount.class)));

        login.setOnClickListener((View v) ->
                startActivity(new Intent(this, Login.class)));
    }
}