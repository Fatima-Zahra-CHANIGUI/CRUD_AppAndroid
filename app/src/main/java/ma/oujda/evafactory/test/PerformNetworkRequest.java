package ma.oujda.evafactory.test;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import static ma.oujda.evafactory.test.ConnectActivity.CODE_GET_REQUEST;
import static ma.oujda.evafactory.test.ConnectActivity.CODE_POST_REQUEST;
import static ma.oujda.evafactory.test.ConnectActivity.progressBar;
import static ma.oujda.evafactory.test.CreateActivity.user;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

    String url;
    HashMap<String, String> params;
    int requestCode;
    Activity activity;
    Class next;

    PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode, Activity activity , Class next ) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
        this.activity=activity;
        this.next=next;
    }

    private void User(JSONObject objUser) throws JSONException {
        user = new UserAccount(
                objUser.getInt("id"),
                objUser.getString("FirstName"),
                objUser.getString("LastName"),
                objUser.getString("Email"),
                objUser.getString("Password")
        );
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.INVISIBLE);
        try {
            JSONObject object = new JSONObject(s);
            if (!object.getBoolean("error")) {
                Toast.makeText(activity.getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                if(!object.getBoolean("NoUser")){
                    User(object.getJSONObject("user"));

                    if(activity.getClass().equals(ConnectActivity.class)){
                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                               if(ConnectActivity.password.equals(user.getPassword())){
                                   Intent mainActivityIntent = new Intent(activity.getApplicationContext(), next);
                                   activity.startActivity(mainActivityIntent);
                                   activity.finish();
                               }else{
                                   ConnectActivity.password_editText.setError("wrong password !");
                                   ConnectActivity.password_editText.requestFocus();
                               }
                            }
                        });
                        return;
                    }
                }
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                            Intent mainActivityIntent = new Intent(activity.getApplicationContext(), next);
                            activity.startActivity(mainActivityIntent);
                            activity.finish();
                    }
                });

            }else{
                if(object.getString("message").equals("User do not exist")){
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ConnectActivity.incorrect_TextView.setText("This account doesn't exist ! please sign up !");
                            ConnectActivity.incorrect_TextView.setVisibility(View.VISIBLE);
                        }
                    });
                    return;
                }

                Toast.makeText(activity.getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        RequestHandler requestHandler = new RequestHandler();

        if (requestCode == CODE_POST_REQUEST) {
            return requestHandler.sendPostRequest(url, params);
        }

        if (requestCode == CODE_GET_REQUEST) {
            return requestHandler.sendGetRequest(url);
        }
        return null;
    }

}
