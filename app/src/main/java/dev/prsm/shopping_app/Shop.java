package dev.prsm.shopping_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;

public class Shop extends AppCompatActivity
{
    private String email;
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

    @SuppressLint("SetTextI18n")
    void buildGridLayout()
    {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        int halfWidth = screenWidth / 2;
        int quarterHeight = screenHeight / 4;
        GridLayout gridLayout = findViewById(R.id.shopping_grid);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        gridLayout.setColumnCount(2);
        params.width = screenWidth;
        params.height = screenHeight;

        for (Item item : rack)
        {
            LinearLayout layout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            layout.setOrientation(LinearLayout.VERTICAL);

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(getImageId(this, item.path));
            imageView.setLayoutParams(layoutParams);
            imageView.requestLayout();
            imageView.getLayoutParams().height = quarterHeight;
            imageView.getLayoutParams().width = halfWidth;
            layout.addView(imageView);

            TextView textView = new TextView(this);
            textView.setText(item.name + "\n" + nf.format(item.price));
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD_ITALIC);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            layout.addView(textView);

            Button button = new Button(this);
            button.setText("Add To Cart");
            layout.addView(button);


            gridLayout.addView(layout);
        }
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
