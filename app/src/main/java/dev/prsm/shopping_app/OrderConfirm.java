package dev.prsm.shopping_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OrderConfirm extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirm);

        Button button = findViewById(R.id.return_button);
        button.setOnClickListener((View v) ->
        {
            startActivity(new Intent(this, Shop.class));
            overridePendingTransition(R.anim.bottom_to_top, 0);
        });
    }
}