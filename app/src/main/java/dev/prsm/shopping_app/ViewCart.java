package dev.prsm.shopping_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ViewCart extends AppCompatActivity
{
    private ArrayList<Item> cart;
    private double calculatedTotal;
    private NumberFormat nf;
    private String email;
    private LinearLayout linearLayout;
    public static final String URL =
            "http://jmgreenberg.cs.loyola.edu/shopping_app/mail_confirm.php";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_view);

        Bundle bundle = getIntent().getExtras();
        cart = bundle.getParcelableArrayList("Cart");
        email = getIntent().getStringExtra("email");
        setTitle(email);
        calculatedTotal = getTotal();
        nf = NumberFormat.getCurrencyInstance();
        Log.v("MA", "TOTAL: " + nf.format(calculatedTotal));
        buildLayout();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    double getTotal()
    {
        double total = 0.00;

        for (Item item : cart)
            total += item.price;

        return total;
    }

    @SuppressLint("SetTextI18n")
    void buildLayout()
    {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        int halfWidth = screenWidth / 2;
        linearLayout = findViewById(R.id.linear_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.requestLayout();
        linearLayout.getLayoutParams().height = screenHeight;
        linearLayout.getLayoutParams().width = halfWidth;
        linearLayout.setGravity(Gravity.CENTER);
        params.height = 300;
        params.width = screenWidth;

        for (Item item : cart)
        {
            String name = getColoredSpanned(item.name, "#172b4d");
            String price = getColoredSpanned(nf.format(item.price),"#f5365c");
            TextView itemPrice = new TextView(this);
            itemPrice.setText(Html.fromHtml(name + ":\t" + price));
            itemPrice.setTypeface(itemPrice.getTypeface(), Typeface.BOLD);
            linearLayout.addView(itemPrice);

            Button removeButton = new Button(this);
            removeButton.setText("delete");
            removeButton.setOnClickListener((View v) ->
                    {
                        cart.remove(item);
                        calculatedTotal -= item.price;
                        linearLayout.removeAllViews();
                        buildLayout();
                    });

            linearLayout.addView(removeButton);
        }

        TextView totalPrice = new TextView(this);
        String total = getColoredSpanned("Total", "#172b4d");
        String price = getColoredSpanned(nf.format(calculatedTotal), "#2dce89");
        totalPrice.setText(Html.fromHtml(total + ":\t" + price));
        totalPrice.setTypeface(totalPrice.getTypeface(), Typeface.BOLD);
        linearLayout.addView(totalPrice);

        Button goBack = new Button(this);
        goBack.setText("go back");
        goBack.setOnClickListener((View v) ->
        {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("Cart", cart);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.top_to_bottom, 0);
        });

        linearLayout.addView(goBack);

        if (calculatedTotal > 0.00)
        {
            Button purchase = new Button(this);
            purchase.setText("purchase");
            purchase.setOnClickListener((View v) ->
            {
                LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
                ThreadPoolExecutorMailConfirm taskPool = new ThreadPoolExecutorMailConfirm(
                        1, 1, 1000, TimeUnit.MILLISECONDS, queue);
                MailConfirmTask task = new MailConfirmTask(this, email, cart,
                        calculatedTotal);
                taskPool.execute(task);
                startActivity(new Intent(this, OrderConfirm.class));
                overridePendingTransition(R.anim.top_to_bottom, 0);
            });

            linearLayout.addView(purchase);
            Button clearCart = new Button(this);
            clearCart.setText("clear cart");
            clearCart.setOnClickListener((View v) -> clearCart());
            linearLayout.addView(clearCart);
        }
    }

    String getColoredSpanned(String text, String color)
    {
        return "<font color=" + color + ">" + text + "</font>";
    }

    void clearCart()
    {
        cart.clear();
        calculatedTotal = 0.00;
        linearLayout.removeAllViews();
        buildLayout();
    }

    void updateView(String result)
    {
        Log.v("MA", "RESPONSE: " + result);
    }
}
