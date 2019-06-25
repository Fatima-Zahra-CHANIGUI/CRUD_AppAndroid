package ma.oujda.evafactory.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.HashMap;
import static ma.oujda.evafactory.test.ConnectActivity.CODE_POST_REQUEST;
import static ma.oujda.evafactory.test.ConnectActivity.progressBar;
import static ma.oujda.evafactory.test.WelcomeActivity.wlc_email;
import static ma.oujda.evafactory.test.WelcomeActivity.wlc_firstName;
import static ma.oujda.evafactory.test.WelcomeActivity.wlc_lastName;
import static ma.oujda.evafactory.test.WelcomeActivity.wlc_password;
import static ma.oujda.evafactory.test.WelcomeActivity.wlc_id;

public class UpdateActivity extends AppCompatActivity {

    EditText firstName_editText , lastName_editText , email_editText , password_editText , confirmPassword_editText;
    Button update_button;
    String firstName, lastName, email, password, confirmPassword;
    Class next = WelcomeActivity.class ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        firstName_editText = findViewById(R.id.update_firstName);
        lastName_editText = findViewById(R.id.update_lastName);
        email_editText = findViewById(R.id.update_email);
        password_editText = findViewById(R.id.update_password);
        confirmPassword_editText = findViewById(R.id.update_confirmPassword);
        progressBar =findViewById(R.id.progressBarUpdate);
        update_button=findViewById(R.id.update_btn);

        firstName_editText.setText(wlc_firstName);
        lastName_editText.setText(wlc_lastName);
        email_editText.setText(wlc_email);
        password_editText.setText(wlc_password);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = firstName_editText.getText().toString();
                lastName = lastName_editText.getText().toString();
                email = email_editText.getText().toString();
                password = password_editText.getText().toString();
                confirmPassword = confirmPassword_editText.getText().toString();

                if(!TextUtils.equals(confirmPassword,password)){
                    confirmPassword_editText.setError("The passwords you entered do not match !");
                    confirmPassword_editText.requestFocus();
                    password_editText.setError("The passwords you entered do not match !");
                    password_editText.requestFocus();
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    confirmPassword_editText.setError("Please confirm your password !");
                    confirmPassword_editText.requestFocus();
                }
                if(TextUtils.isEmpty(password)){
                    password_editText.setError("Please enter your password !");
                    password_editText.requestFocus();
                }
                if(TextUtils.isEmpty(email)){
                    email_editText.setError("Please enter your email !");
                    email_editText.requestFocus();
                }
                if(TextUtils.isEmpty(lastName)){
                    lastName_editText.setError("Please enter your last name !");
                    lastName_editText.requestFocus();
                }
                if(TextUtils.isEmpty(firstName)){
                    firstName_editText.setError("Please enter your first name !");
                    firstName_editText.requestFocus();
                }


                if(!TextUtils.isEmpty(firstName) &&
                        !TextUtils.isEmpty(lastName) &&
                        !TextUtils.isEmpty(email) &&
                        !TextUtils.isEmpty(password)&&
                        !TextUtils.isEmpty(confirmPassword)&&
                        TextUtils.equals(confirmPassword,password)
                ){
                    updateUser();
                }


            }
        });
    }

    private void updateUser() {

        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(wlc_id));
        params.put("FirstName", firstName);
        params.put("LastName", lastName);
        params.put("Email", email);
        params.put("Password", password);


        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_User, params, CODE_POST_REQUEST , UpdateActivity.this , next);
        request.execute();
    }

    @Override
    public void onBackPressed(){
        UpdateActivity.super.onBackPressed();
        Intent mainActivityIntent = new Intent(UpdateActivity.this , WelcomeActivity.class );
        startActivity(mainActivityIntent);
        finish();
    }

}
