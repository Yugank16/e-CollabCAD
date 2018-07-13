package intern.ecollabcad;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserAreaActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    SharedPreferences sp;
    NavigationView navigationView;
    View navHeader;
    TextView useridtvNav, emailtvNav;
    String userId;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] fileNames;
    ArrayList<FileData> arrayList= new ArrayList<FileData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sp= getSharedPreferences("LoginStatus",MODE_PRIVATE);
        userId= sp.getString("userId","anonymous");

        drawer= findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView= findViewById(R.id.nav_view);
        navHeader= navigationView.getHeaderView(0);

        useridtvNav= navHeader.findViewById(R.id.user_tv_nav);
        emailtvNav= navHeader.findViewById(R.id.email_tv_nav);

        useridtvNav.setText(userId);
        emailtvNav.setText(" ");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.update_profile:{
                        Intent profileIntent= new Intent(UserAreaActivity.this,UpdateProfile.class);
                        startActivity(profileIntent);
                        break;
                    }
                    case R.id.gett_help:{
                        Intent profileIntent= new Intent(UserAreaActivity.this,Demo.class);
                        startActivity(profileIntent);
                        break;
                    }

                    case R.id.logout:{
                        Intent logoutIntent= new Intent(UserAreaActivity.this,LoginActivity.class);

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("isLogin",false);
                        editor.putString("userId"," ");
                        editor.commit();
                        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(logoutIntent);

                        break;
                    }
                    case R.id.change_password:{
                        AlertDialog.Builder alert = new AlertDialog.Builder(UserAreaActivity.this);
                        final EditText edittext = new EditText(UserAreaActivity.this);
                        alert.setMessage("Enter new password");
                        alert.setTitle("Change Password");

                        alert.setView(edittext);

                        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                String nameTyped = edittext.getText().toString();

                                if(nameTyped.length() >0)
                                {
                                    Toast.makeText(UserAreaActivity.this, "Server Busy", Toast.LENGTH_SHORT).show();
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


                return true;
            }
        });

        recyclerView= (RecyclerView) findViewById(R.id.main_recycler_view);
        fileNames= getResources().getStringArray(R.array.file_names);
        int i=0;
        for(String name: fileNames)
        {
            FileData fileData= new FileData(i,name,"JSON");
            arrayList.add(fileData);
            i++;
        }

        adapter= new RecyclerViewAdapter(arrayList,this);
        recyclerView.setHasFixedSize(true);  //this will improve the performance
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
}
