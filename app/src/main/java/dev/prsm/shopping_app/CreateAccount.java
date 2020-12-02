package dev.prsm.shopping_app;

import android.annotation.SuppressLint;
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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

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

            LinkedBlockingQueue queue = new LinkedBlockingQueue<Runnable>();
            ThreadPoolExecutorCreateAccount taskPool = new ThreadPoolExecutorCreateAccount(
                    1,1,
                    1000, TimeUnit.MILLISECONDS, queue, this );



            if (isValidEmail(email))
            {
                if (password.equals(passwordAgain)) //TODO: check password length
                {
                    displayError("", HIDE);
                    CreateAccountTask task = new CreateAccountTask(this, email, password);
                    taskPool.execute(task);
                }

                else
                    displayError("Passwords do not match", SHOW);
            }

            else
                displayError("Invalid email address", SHOW);
        });
    }

    boolean isValidPassword(String password)
    {
        // strong
        if (password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,15}$"))
            return true;

        // moderate
        else if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})"))

        // weak
        else if (password.matches("^(?=.*[a-z])(?=.*[0-9])(?=.{8,})"))

        // poor
        else if (password.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.{8,})"))

        return false;
    }

    public static boolean isValidEmail(CharSequence target)
    {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @SuppressLint("SetTextI18n")
    public void updateView(String postReturn)
    {
        if (!postReturn.equals("1"))
            runOnUiThread(() ->
            {
                TextView errorMessage = findViewById(R.id.create_account_error);
                errorMessage.setTextColor(getResources().getColor(R.color.danger));
                errorMessage.setText("Email already taken");
            });

    }

    public void displayError(String error, int show)
    {
        TextView errorMessage = findViewById(R.id.create_account_error);
        errorMessage.setText(error);

        if (show == 1)
            errorMessage.setTextColor(getResources().getColor(R.color.danger));

        else
            errorMessage.setTextColor(getResources().getColor(R.color.secondary));
    }
}
