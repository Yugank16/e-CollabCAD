package intern.ecollabcad;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText etuseremail= findViewById(R.id.register_email);
        final EditText etuserid= findViewById(R.id.register_userid);
        final EditText etuserpassword=findViewById(R.id.register_password);
        final Button bRegister=findViewById(R.id.reg_button);

        final TextView signinLink= (TextView) findViewById(R.id.signin_link);

        signinLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent= new Intent(RegisterActivity.this, LoginActivity.class);
                registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(registerIntent);

            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etuseremail.getText().toString();
                final String userid = etuserid.getText().toString();
                final String password = etuserpassword.getText().toString();
                //Toast.makeText(RegisterActivity.this, "Pressed"+ email, Toast.LENGTH_SHORT).show();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonresponse = new JSONObject(response);
                            boolean success = jsonresponse.getBoolean("status");

                            if (success) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Registration failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Giving Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(email, userid, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

    }
}
