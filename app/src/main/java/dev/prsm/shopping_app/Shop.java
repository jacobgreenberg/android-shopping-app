package dev.prsm.shopping_app;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import org.w3c.dom.Text;

import java.util.List;

public class Shop extends AppCompatActivity
{
    private String email = "";
    private  List<Item> rack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);
        setTitle(getEmail());
        Warehouse warehouse = new Warehouse();
        warehouse.buildWarehouse();
        rack = warehouse.getWarehouse();
        buildGridLayout();
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

    void buildGridLayout()
    {
        GridLayout gridLayout = findViewById(R.id.shopping_grid);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.width = GridLayout.LayoutParams.MATCH_PARENT;

//        for (Item item : rack)
//        {
            TextView tv = new TextView(this);
            tv.setText("HI");
            tv.setTextSize(40f);
            tv.setTextColor(Color.RED);
            tv.setLayoutParams(params);
            gridLayout.addView(tv);



//        }
    }
}
