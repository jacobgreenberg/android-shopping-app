package dev.prsm.shopping_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity
{
    public static final String URL =
            "http://jmgreenberg.cs.loyola.edu/shopping_app/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button submit = findViewById(R.id.login_submit);

        submit.setOnClickListener((View v) ->
        {
            EditText editTextEmail = findViewById(R.id.login_email);
            EditText editTextPassword = findViewById(R.id.login_password);
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
            ThreadPoolExecutorLogin taskPool = new ThreadPoolExecutorLogin(
                    1,1,
                    1000, TimeUnit.MILLISECONDS, queue);

            LoginTask task = new LoginTask(this, email, password);
            taskPool.execute(task);
        });
    }

    @SuppressLint("SetTextI18n")
    public void updateView(String postReturn)
    {
        Log.v("MA", "RETURN: " + postReturn);

        if (postReturn.equals("0"))
            startActivity(new Intent(this, LoginSuccess.class));

        else
        {
            runOnUiThread(() ->
            {
                TextView errorMessage = findViewById(R.id.login_error);
                errorMessage.setTextColor(getResources().getColor(R.color.danger));
                errorMessage.setText("Incorrect email or password");
            });
        }
    }
}


