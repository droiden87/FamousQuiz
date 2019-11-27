package com.flat.rock.quiz.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.flat.rock.quiz.R;
import com.flat.rock.quiz.database.UserDAO;
import com.flat.rock.quiz.model.User;
import com.flat.rock.quiz.utils.InputValidation;
import com.flat.rock.quiz.utils.SessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName,textInputLayoutEmail,textInputLayoutPassword,textInputLayoutConfirmPassword;
    private TextInputEditText txtInputEdtName,txtInputEdtEmail,txtInputEdtPassword,txtInputEdtConfirmPassword;

    private Button buttonRegister;
    private InputValidation inputValidation;
    private UserDAO userDAO;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        nestedScrollView =  findViewById(R.id.nestedScrollView);

        textInputLayoutName =  findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword =  findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword =findViewById(R.id.textInputLayoutConfirmPassword);

        txtInputEdtName = findViewById(R.id.txtInputEdtName);
        txtInputEdtEmail =  findViewById(R.id.txtInputEdtEmail);
        txtInputEdtPassword = findViewById(R.id.txtInputEdtPassword);
        txtInputEdtConfirmPassword = findViewById(R.id.txtInputEdtConfirmPassword);

        buttonRegister = findViewById(R.id.appCompatButtonRegister);

        buttonRegister.setOnClickListener(this);

        inputValidation = new InputValidation(activity);
        userDAO = new UserDAO(activity);
        userDAO.open();

        user = new User();
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                saveDataToSQLite();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void saveDataToSQLite() {
        SessionManager session = new SessionManager(RegisterActivity.this);

        if (!inputValidation.isInputEditTextFilled(txtInputEdtName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(txtInputEdtEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(txtInputEdtEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(txtInputEdtPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(txtInputEdtPassword, txtInputEdtConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }

        if (!userDAO.checkUser(txtInputEdtEmail.getText().toString().trim())) {

            session.createLoginSession(userDAO.getUsernamebyEmail(txtInputEdtEmail.getText().toString().trim()),
                    txtInputEdtEmail.getText().toString().trim());
            user.setCurrentUser(true);
            user.setName(txtInputEdtName.getText().toString().trim());
            user.setEmail(txtInputEdtEmail.getText().toString().trim());
            user.setPassword(inputValidation.sha256(txtInputEdtPassword.getText().toString().trim()));

            userDAO.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
            finish();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }

        finish();

    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        txtInputEdtName.setText(null);
        txtInputEdtEmail.setText(null);
        txtInputEdtPassword.setText(null);
        txtInputEdtConfirmPassword.setText(null);
    }

    @Override
    protected void onResume() {
        userDAO.open();
        super.onResume();

    }

    @Override
    protected void onPause() {
        userDAO.close();
        super.onPause();
    }


}