package dev.prsm.shopping_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Shop extends AppCompatActivity
{
    protected String email;
    private Warehouse warehouse;
    private ArrayList<Item> cart;
    protected NumberFormat nf;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);
        email = getEmail();
        setTitle(email);
        warehouse = new Warehouse();
        cart = new ArrayList<>();
        warehouse.buildWarehouse();
        nf = NumberFormat.getCurrencyInstance();
        buildGridLayout();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.shopping_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.cart_button)
        {
            Intent intent = new Intent(this, ViewCart.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("Cart", cart);
            intent.putExtras(bundle);
            intent.putExtra("email", email);
            startActivityForResult(intent, 1);
            overridePendingTransition(R.anim.bottom_to_top, 0);
        }

        return super.onOptionsItemSelected(item);
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
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        int halfWidth = screenWidth / 2;
        GridLayout gridLayout = findViewById(R.id.shopping_grid);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = screenWidth;
        params.height = screenHeight;

        List<Item> rack = warehouse.getWarehouse();

        for (Item item : rack)
        {
            LinearLayout layout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
            layout.setOrientation(LinearLayout.VERTICAL);

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(getImageId(this, item.path));
            imageView.setLayoutParams(layoutParams);
            imageView.requestLayout();
            imageView.getLayoutParams().height = 100;
            imageView.getLayoutParams().width = halfWidth - 25;
            layout.addView(imageView);

            TextView textView = new TextView(this);
            textView.setText(item.name + "\n" + nf.format(item.price));
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            layout.addView(textView);

            Button addButton = new Button(this);
            addButton.setText("Add To Cart");
            addButton.setOnClickListener((View v) ->
            {
                cart.add(item);
                Toast.makeText(this, item.name + " added to cart!",
                        Toast.LENGTH_SHORT).show();
            });

            layout.addView(addButton);
            gridLayout.addView(layout);
        }
    }

    public static int getImageId(Context context, String imageName)
    {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            cart = bundle.getParcelableArrayList("Cart");
        }
    }
}
