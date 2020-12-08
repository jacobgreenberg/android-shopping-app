package dev.prsm.shopping_app;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.ArrayList;


public class MailConfirmTask extends Thread
{
    private final ViewCart viewCart;
    protected String email;
    protected ArrayList<Item> cart;
    protected double calculatedTotal;
    private String data = "data=";
    private String result;
    protected NumberFormat nf;

    public MailConfirmTask(ViewCart fromViewCart, String email, ArrayList<Item> cart,
                           double calculatedTotal)
    {
        viewCart = fromViewCart;
        this.email = email;
        this.cart = cart;
        this.calculatedTotal = calculatedTotal;
        nf = NumberFormat.getCurrencyInstance();
    }

    void createDataString()
    {
        StringBuilder sb = new StringBuilder(email);

        for (Item item : cart)
            sb.append(";").append(item.name).append(": ").append(nf.format(item.price));

        sb.append(";").append("Total: ").append(nf.format(calculatedTotal));
        data += sb.toString();
    }

    public void run()
    {
        createDataString();

        try
        {
            URL url = new URL(ViewCart.URL);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            StringBuilder postReturn = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null)
                postReturn.append(line);

            result = postReturn.toString();
            
        } catch (Exception e)
        {
            Log.v("MA", e.getMessage());
        }
    }

    public void updateView()
    {
        viewCart.updateView(result);
    }
}
