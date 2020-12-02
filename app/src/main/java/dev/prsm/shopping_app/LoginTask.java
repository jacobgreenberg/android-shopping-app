package dev.prsm.shopping_app;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class LoginTask extends Thread
{
    private final Login login;
    private String email = "";
    private String password = "";
    private String result = "";

    public LoginTask(Login fromLogin, String email, String password)
    {
        login = fromLogin;
        this.email = email;
        this.password = password;
    }

    public void run()
    {
        try
        {
            URL url = new URL(Login.URL);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write("email=" + email + "&password=" + password);
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
        login.updateView(result);
    }
}
