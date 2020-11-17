package dev.prsm.shopping_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        Button submit = findViewById(R.id.create_account_submit);

        submit.setOnClickListener((View v) -> {
            // create the account
        });
    }
}
