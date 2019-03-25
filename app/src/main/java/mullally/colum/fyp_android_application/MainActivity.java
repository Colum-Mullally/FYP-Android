package mullally.colum.fyp_android_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.googlecode.tesseract.android.TessBaseAPI;

public class MainActivity extends AppCompatActivity {
    private AuthenticationService authenticationService= AuthenticationService.getInstance();
    private TessBaseAPI tessBaseApi;
    EditText usernameInput;
    EditText passwordInput;
    Button registerButton;
    Button loginButton;
    private String credentials;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button)findViewById(R.id.loginButton);
        registerButton = (Button)findViewById(R.id.registerButton);
        passwordInput = (EditText)findViewById(R.id.passwordInput);
        usernameInput = (EditText)findViewById(R.id.userNameInput);
    }

    protected void onStart(){
        super.onStart();
        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               register(usernameInput.getText().toString(),passwordInput.getText().toString());
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                login(usernameInput.getText().toString(),passwordInput.getText().toString());
            }
        });
    }

    private void register( String username, String password){
        new RegisterRESTTask(getApplicationContext()).execute(username,password);
    }

    private void login(String username, String password){
        new LoginRESTTask(getApplicationContext()).execute(username,password);
    }

}
