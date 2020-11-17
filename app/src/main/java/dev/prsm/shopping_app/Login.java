package dev.prsm.shopping_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button submit = findViewById(R.id.login_submit);

        submit.setOnClickListener((View v) ->
        {
            // do something here
        });
    }
}
