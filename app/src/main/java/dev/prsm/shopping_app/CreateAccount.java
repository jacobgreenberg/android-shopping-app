package dev.prsm.shopping_app;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    final int SHOW = 1;
    final int HIDE = 0;
    public static final String URL =
            "http://jmgreenberg.cs.loyola.edu/shopping_app/create_account.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        Button submit = findViewById(R.id.create_account_submit);

        submit.setOnClickListener((View v) ->
        {
            EditText editTextEmail = findViewById(R.id.create_account_email);
            EditText editTextPassword = findViewById(R.id.create_account_password);
            EditText editTextPasswordAgain = findViewById(R.id.create_account_password_again);

            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String passwordAgain = editTextPasswordAgain.getText().toString();

            if (isValidEmail(email))
            {
                if (password.equals(passwordAgain)) //TODO: check password length
                {
                    displayError("", HIDE);
                    CreateAccountTask task = new CreateAccountTask(
                            this,
                            email,
                            password);
                    task.start();
                }

                else
                    displayError("Passwords do not match", SHOW);
            }

            else
                displayError("Invalid email address", SHOW);
        });
    }

    public static boolean isValidEmail(CharSequence target)
    {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void updateView(String postReturn)
    {
        if (!postReturn.equals("1"))
            displayError("Account with this email already exists", SHOW);
    }

    public void displayError(String error, int show)
    {
        TextView errorMessage = findViewById(R.id.create_account_error);
        errorMessage.setText(error);

        if (show == 1)
            errorMessage.setVisibility(View.VISIBLE);

        else
            errorMessage.setVisibility(View.INVISIBLE);
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

                while ((line = bufferedReader.readLine()) != null)
                    postReturn.append(line);

                createAccount.updateView(postReturn.toString());

            } catch (Exception e)
            {
                Log.v("MA", e.getMessage());
            }
        }
    }