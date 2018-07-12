package intern.ecollabcad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText loginUserid= (EditText) findViewById(R.id.login_userid);
        final EditText loginPassword= (EditText) findViewById(R.id.login_password);

        final Button bRegister= (Button) findViewById(R.id.login_button);
        final TextView registerLink= (TextView) findViewById(R.id.signup_text);

        sp= getSharedPreferences("LoginStatus", MODE_PRIVATE);

        SharedPreferences.Editor editor= sp.edit();
        boolean isLogin= sp.getBoolean("isLogin",false);

        if(isLogin)
        {
            Intent intent= new Intent(LoginActivity.this, UserAreaActivity.class);
            startActivity(intent);
        }

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent= new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final String userid = loginUserid.getText().toString();
                final String password = loginPassword.getText().toString();

//                Intent intent= new Intent(LoginActivity.this, UserAreaActivity.class);
//                startActivity(intent);

                if(userid.length()>0 && password.length()>0) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonreponse = new JSONObject(response);
                                boolean success = jsonreponse.getBoolean("status");

                                if (success) {

                                    Toast.makeText(LoginActivity.this, "Welcome"+ userid, Toast.LENGTH_SHORT).show();

                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putBoolean("isLogin", true);
                                    editor.putString("userId", userid);
                                    editor.commit();

                                    Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Login failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    LoginRequest loginRequest = new LoginRequest(userid, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
                else
                {
                    loginUserid.setError("Enter User Id and Password");
                }
            }
        });
    }
}
