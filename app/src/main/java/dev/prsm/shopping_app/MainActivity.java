package dev.prsm.shopping_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.FileReader;

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

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.mail_button)
        {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"prsm.forms@gmail.com"});
            startActivity(Intent.createChooser(emailIntent, "Mail sent!"));
        }

        return super.onOptionsItemSelected(item);
    }
}