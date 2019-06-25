package ma.oujda.evafactory.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static ma.oujda.evafactory.test.ConnectActivity.CODE_GET_REQUEST;
import static ma.oujda.evafactory.test.ConnectActivity.progressBar;
import static ma.oujda.evafactory.test.CreateActivity.user;

public class WelcomeActivity extends AppCompatActivity {

    TextView id_TextView , firstName_textView , lastName_textView , email_textView ;
    EditText password_EditText;
    Button update_button , delete_button ;
    static String wlc_firstName , wlc_lastName , wlc_email , wlc_password;
    static int wlc_id;
    Class nextDelete = ConnectActivity.class ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        id_TextView = findViewById(R.id.wlc_id);
        firstName_textView = findViewById(R.id.wlc_firstName);
        lastName_textView = findViewById(R.id.wlc_lastName);
        email_textView = findViewById(R.id.wlc_email);
        password_EditText = findViewById(R.id.wlc_password);
        update_button = findViewById(R.id.update);
        delete_button = findViewById(R.id.delete);
        progressBar = findViewById(R.id.progressBarDelete);

        id_TextView.setText(String.valueOf(user.getId()));
        firstName_textView.setText(user.getFirstName());
        lastName_textView.setText(user.getLastName());
        email_textView.setText(user.getEmail());
        password_EditText.setText(user.getPassword());

        password_EditText.setEnabled(false);
        password_EditText.setFocusable(false);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wlc_id = Integer.parseInt(id_TextView.getText().toString());
                wlc_firstName = firstName_textView.getText().toString();
                wlc_lastName = lastName_textView.getText().toString();
                wlc_email = email_textView.getText().toString();
                wlc_password = password_EditText.getText().toString();

                Intent mainActivityIntent = new Intent(WelcomeActivity.this , UpdateActivity.class );
                startActivity(mainActivityIntent);
                finish();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wlc_id = Integer.parseInt(id_TextView.getText().toString());
                deleteUser(wlc_id);
            }
        });
    }

    private void deleteUser(final int id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete your account ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_User + id, null, CODE_GET_REQUEST , WelcomeActivity.this , nextDelete);
                        request.execute();
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

    @Override
    public void onBackPressed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to quit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        WelcomeActivity.super.onBackPressed();
                        Intent mainActivityIntent = new Intent(WelcomeActivity.this , ConnectActivity.class );
                        startActivity(mainActivityIntent);
                        finish();
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
