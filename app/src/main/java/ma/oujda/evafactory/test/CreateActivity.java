package ma.oujda.evafactory.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.HashMap;
import static ma.oujda.evafactory.test.ConnectActivity.progressBar;

public class CreateActivity extends AppCompatActivity {

    EditText firstName_editText , lastName_editText , email_editText , password_editText , confirmPassword_editText;
    Button submit_button;
    Class next = WelcomeActivity.class ;
    String firstName, lastName, email, password, confirmPassword;
    static UserAccount user = new UserAccount(0 , "" , "" , "" , "") ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        firstName_editText = findViewById(R.id.create_firstName);
        lastName_editText = findViewById(R.id.create_lastName);
        email_editText = findViewById(R.id.create_email);
        password_editText = findViewById(R.id.create_password);
        confirmPassword_editText = findViewById(R.id.create_confirmPassword);
        progressBar =findViewById(R.id.progressBarCreate);
        submit_button=findViewById(R.id.submit);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = firstName_editText.getText().toString().trim();
                lastName = lastName_editText.getText().toString().trim();
                email = email_editText.getText().toString().trim();
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
                    createUser();
                }
            }
        });
    }

    private void createUser() {

        HashMap<String, String> params = new HashMap<>();
        params.put("FirstName", firstName);
        params.put("LastName", lastName);
        params.put("Email", email);
        params.put("Password", password);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_User, params, ConnectActivity.CODE_POST_REQUEST , CreateActivity.this , next );
        request.execute();
    }

    @Override
    public void onBackPressed(){
        CreateActivity.super.onBackPressed();
        Intent mainActivityIntent = new Intent(CreateActivity.this , ConnectActivity.class );
        startActivity(mainActivityIntent);
        finish();
    }
}
