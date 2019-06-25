package ma.oujda.evafactory.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ConnectActivity extends AppCompatActivity {

    static final int CODE_GET_REQUEST = 1024;
    static final int CODE_POST_REQUEST = 1025;
    static ProgressBar progressBar;
    static EditText email_editText , password_editText ;
    Button signIn_Button ;
    TextView signUp_TextView ;
    static TextView incorrect_TextView ;
    Class nextRead = WelcomeActivity.class;
    static String email , password ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        email_editText = findViewById(R.id.email);
        password_editText = findViewById(R.id.password);
        signIn_Button = findViewById(R.id.signIn);
        signUp_TextView = findViewById(R.id.signUp);
        incorrect_TextView = findViewById(R.id.incorrect);
        progressBar =findViewById(R.id.progressBarLogin);


        String text = "New User ? Sign up";

        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent mainActivityIntent = new Intent(ConnectActivity.this, CreateActivity.class);
                startActivity(mainActivityIntent);
                finish();
            }
        };

        ss.setSpan(clickableSpan1, 11, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signUp_TextView.setText(ss);
        signUp_TextView.setMovementMethod(LinkMovementMethod.getInstance());

        signIn_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = email_editText.getText().toString();
                password = password_editText.getText().toString();

                if(TextUtils.isEmpty(password)){
                    password_editText.setError("Please enter your password !");
                    password_editText.requestFocus();
                }
                if(TextUtils.isEmpty(email)){
                    email_editText.setError("Please enter your email !");
                    email_editText.requestFocus();
                }
                else if(!TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)){
                    readUser(email);
                }
            }
        });
    }


    private void readUser(String email) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_User + email, null, CODE_GET_REQUEST , ConnectActivity.this , nextRead);
        request.execute();
    }

    @Override
    public void onBackPressed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to quit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ConnectActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
