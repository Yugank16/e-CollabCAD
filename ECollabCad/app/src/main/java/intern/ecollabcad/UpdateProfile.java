package intern.ecollabcad;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class UpdateProfile extends AppCompatActivity {

    SharedPreferences sp;
    TextView userIdTv,nameTv, orgTv, postTv, industryTv, mobileTv;
    String name, post, userId,organization, industry, mobile;
    ImageButton nameEdit, orgEdit, postEdit, industryEdit, mobileEdit;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

         userIdTv= findViewById(R.id.user_id_tv);
         nameTv= findViewById(R.id.name_tv);
         orgTv= findViewById(R.id.org_tv);
         postTv= findViewById(R.id.post_tv);
         industryTv= findViewById(R.id.industry_tv);
         mobileTv= findViewById(R.id.mobile_tv);

         nameEdit= findViewById(R.id.name_edit);
         orgEdit= findViewById(R.id.org_edit);
         postEdit= findViewById(R.id.post_edit);
         industryEdit= findViewById(R.id.industry_edit);
         mobileEdit= findViewById(R.id.mobile_edit);

         saveButton= findViewById(R.id.save_profile);
        sp= getSharedPreferences("LoginStatus",MODE_PRIVATE);
        userId= sp.getString("userId","anonymous");
        name= sp.getString("name","(not defined)");
        post= sp.getString("post","(not defined)");
        organization= sp.getString("organization","(not defined)");
        industry= sp.getString("industry","(not defined)");
        mobile= sp.getString("mobile","(not defined)");

        userIdTv.setText(userId);
        nameTv.setText(name);
        orgTv.setText(organization);
        postTv.setText(post);
        industryTv.setText(industry);
        mobileTv.setText(mobile);

        nameEdit.setOnClickListener(onClickListener);
        orgEdit.setOnClickListener(onClickListener);
        postEdit.setOnClickListener(onClickListener);
        industryEdit.setOnClickListener(onClickListener);
        mobileEdit.setOnClickListener(onClickListener);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent= new Intent(UpdateProfile.this,UserAreaActivity.class);
                startActivity(profileIntent);
                finish();
            }
        });

    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.name_edit: {

                    AlertDialog.Builder alert = new AlertDialog.Builder(UpdateProfile.this);
                    final EditText edittext = new EditText(UpdateProfile.this);
                    alert.setMessage("Please enter your Name");
                    alert.setTitle("Name");

                    alert.setView(edittext);

                    alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            String nameTyped = edittext.getText().toString();

                            if(nameTyped.length() >0)
                            {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("name",nameTyped);
                                editor.commit();

                                nameTv.setText(nameTyped);
                            }
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });

                    alert.show();

                    break;
                }
                case R.id.org_edit:{

                    AlertDialog.Builder alert = new AlertDialog.Builder(UpdateProfile.this);
                    final EditText edittext = new EditText(UpdateProfile.this);
                    alert.setMessage("Please enter your Organiaztion");
                    alert.setTitle("Organization");

                    alert.setView(edittext);

                    alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            String Typed = edittext.getText().toString();

                            if(Typed.length() >0)
                            {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("organization",Typed);
                                editor.commit();

                                orgTv.setText(Typed);
                            }
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });

                    alert.show();
                    break;
                }
                case R.id.post_edit:
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(UpdateProfile.this);
                    final EditText edittext = new EditText(UpdateProfile.this);
                    alert.setMessage("Please enter your Post");
                    alert.setTitle("Post");

                    alert.setView(edittext);

                    alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            String Typed = edittext.getText().toString();

                            if(Typed.length() >0)
                            {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("post",Typed);
                                editor.commit();

                                postTv.setText(Typed);
                            }
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });

                    alert.show();
                    break;
                }

                case R.id.industry_edit:
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(UpdateProfile.this);
                    final EditText edittext = new EditText(UpdateProfile.this);
                    alert.setMessage("Please enter your Industry Type");
                    alert.setTitle("Indutry Type");

                    alert.setView(edittext);

                    alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            String Typed = edittext.getText().toString();

                            if(Typed.length() >0)
                            {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("industry",Typed);
                                editor.commit();

                                industryTv.setText(Typed);
                            }
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });

                    alert.show();
                    break;
                }
                case R.id.mobile_edit:
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(UpdateProfile.this);
                    final EditText edittext = new EditText(UpdateProfile.this);
                    alert.setMessage("Please enter your Mobile Number");
                    alert.setTitle("Mobile Number");

                    alert.setView(edittext);

                    alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            String Typed = edittext.getText().toString();

                            if(Typed.length() >0)
                            {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("mobile",Typed);
                                editor.commit();

                                mobileTv.setText(Typed);
                            }
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });

                    alert.show();
                    break;
                }
            }

        }
    };
}
