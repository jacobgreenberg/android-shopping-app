package dev.prsm.shopping_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class CreateAccount extends AppCompatActivity
{
    public static final String URL =
            "http://jmgreenberg.cs.loyola.edu/shopping_app/create_account.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        Button submit = findViewById(R.id.create_account_submit);

        submit.setOnClickListener((View v) -> {
            EditText editTextEmail = findViewById(R.id.create_account_email);
            EditText editTextPassword = findViewById(R.id.create_account_password);
            EditText editTextPasswordAgain = findViewById(R.id.create_account_password_again);

            String password = editTextPassword.getText().toString();
            String passwordAgain = editTextPasswordAgain.getText().toString();

            if (password.equals(passwordAgain))
            {
                CreateAccountTask task = new CreateAccountTask(
                        this, editTextEmail.getText().toString(), password);
                task.start();
            }
            else
                Log.v("MA", "THROW ERROR"); //FIXME: throw an error on xml
        });
    }

    public void updateView(String postReturn)
    {
        Log.v("MA", postReturn); //FIXME: do somethign with return
    }
}

class CreateAccountTask extends Thread
{
    private final CreateAccount createAccount;
    private String email = "";
    private String password = "";

    public CreateAccountTask(CreateAccount fromCreateAccount, String email, String password)
    {
        createAccount = fromCreateAccount;
        this.email = email;
        this.password = password;
    }

    public void run()
    {
        try
        {
            URL url = new URL(CreateAccount.URL);
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

            while ((line = bufferedReader.readLine())!= null)
                postReturn.append(line);

            createAccount.updateView(postReturn.toString());

        } catch (Exception e)
        {
            Log.v("MA", e.getMessage());
        }
    }
}
