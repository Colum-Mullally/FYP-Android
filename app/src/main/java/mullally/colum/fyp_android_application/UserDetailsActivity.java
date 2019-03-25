package mullally.colum.fyp_android_application;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class UserDetailsActivity extends AppCompatActivity {
    private AuthenticationService authenticationService= AuthenticationService.getInstance();
    private TextView votesNumber;
    private int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        x = (int)getIntent().getExtras().get("votes");
        votesNumber= (TextView)findViewById(R.id.VotesNumber);
    }
    protected void onStart() {
        super.onStart();
        votesNumber.setText(""+x);
    }
}
