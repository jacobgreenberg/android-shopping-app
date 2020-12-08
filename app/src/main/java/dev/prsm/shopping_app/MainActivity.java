package dev.prsm.shopping_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mail:
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"prsm.forms@gmail.com"});
                startActivity(Intent.createChooser(emailIntent, "mail send"));
                return true;
            case R.id.about_us:
                startActivity(new Intent(this, AboutUs.class));
                overridePendingTransition(R.anim.top_to_bottom, 0);
                return true;
            case R.id.privacy_policy:
                startActivity(new Intent(this, PrivacyPolicy.class));
                overridePendingTransition(R.anim.top_to_bottom, 0);
            default:
                return super.onContextItemSelected(item);
        }
    }
}