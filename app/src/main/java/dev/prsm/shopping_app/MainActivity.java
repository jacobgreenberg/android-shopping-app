package dev.prsm.shopping_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        {
            startActivity(new Intent(this, Shop.class));
            overridePendingTransition(R.anim.bottom_to_top, 0);
        });

        createAccount.setOnClickListener((View v) ->
        {
            startActivity(new Intent(this, CreateAccount.class));
            overridePendingTransition(R.anim.bottom_to_top, 0);
        });

        login.setOnClickListener((View v) ->
        {
            startActivity(new Intent(this, Login.class));
            overridePendingTransition(R.anim.bottom_to_top, 0);
        });
    }
}