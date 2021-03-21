package umn.ac.id.uts_31943;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    public Button btnLogin;
    EditText username, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login Page");
        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.pass);

        btnLogin.setOnClickListener(v -> login());//
    }

    protected void login(){
        String user = username.getText().toString().trim();
        String passLogin = pass.getText().toString().trim();
        if(user.equals("uasmobile") && passLogin.equals("uasmobilegenap")){
            Intent intentKeLagu = new Intent(Login.this, Playlist.class);
            startActivity(intentKeLagu);
        }else{
            Toast.makeText(this, "username or password doesn't matched", Toast.LENGTH_LONG).show();
        }
    }
}