package dev.prsm.shopping_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
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
        EditText editTextPassword = findViewById(R.id.create_account_password);

        editTextPassword.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            { }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable.length() < 4)
                    editTextPassword.setTextColor(getResources().getColor(R.color.danger));

                else if (editable.length() > 4 && editable.length() < 8)
                    editTextPassword.setTextColor(getResources().getColor(R.color.warning));

                else if (editable.length() > 8)
                    editTextPassword.setTextColor(getResources().getColor(R.color.success));
            }
        });

        submit.setOnClickListener((View v) ->
        {
            EditText editTextEmail = findViewById(R.id.create_account_email);
            EditText editTextPasswordAgain = findViewById(R.id.create_account_password_again);

            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String passwordAgain = editTextPasswordAgain.getText().toString();

            LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
            ThreadPoolExecutorCreateClass taskPool = new ThreadPoolExecutorCreateClass(
                    1,1,
                    1000, TimeUnit.MILLISECONDS, queue);


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

        else
            startActivity(new Intent(this, LoginSuccess.class));
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
