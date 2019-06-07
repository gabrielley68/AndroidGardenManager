package com.mds.gab.gardenmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mds.gab.gardenmanager.Helpers.ActivityUtils;
import com.mds.gab.gardenmanager.Manager.UserManager;
import com.mds.gab.gardenmanager.Models.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {

    EditText usernameLogin;
    EditText passwordLogin;
    EditText usernameRegister;
    EditText passwordRegister;
    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameLogin = findViewById(R.id.username_login);
        usernameRegister = findViewById(R.id.username_register);
        passwordLogin = findViewById(R.id.password_login);
        passwordRegister = findViewById(R.id.password_register);

        loginButton = findViewById(R.id.button_login);
        registerButton = findViewById(R.id.button_register);

        final Realm realm = Realm.getDefaultInstance();
        final UserManager userManager = UserManager.getInstance();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = realm.where(User.class).
                        equalTo("name", usernameLogin.getText().toString()).
                        equalTo("password", passwordLogin.getText().toString()).findFirst();

                if(user == null){
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                } else {
                    userManager.setUser(user);
                    Toast.makeText(LoginActivity.this, "Welcome " + user.getName(), Toast.LENGTH_SHORT).show();
                    ActivityUtils.launchActivity(LoginActivity.this, MainActivity.class);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = usernameRegister.getText().toString();
                String newPassword = passwordRegister.getText().toString();
                //Minimum length = 3
                if(newUsername.length() < 3 || newPassword.length() < 3){
                    Toast.makeText(LoginActivity.this, "Length of username/password must be higher than 3", Toast.LENGTH_LONG).show();
                }
                else {
                    User user = new User(newUsername, newPassword);
                    realm.beginTransaction();
                    realm.copyToRealm(user);
                    realm.commitTransaction();

                    userManager.setUser(user);

                    ActivityUtils.launchActivity(LoginActivity.this, MainActivity.class);
                }
            }
        });

    }
}
