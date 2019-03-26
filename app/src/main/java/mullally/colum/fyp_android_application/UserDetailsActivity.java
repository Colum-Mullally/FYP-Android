package mullally.colum.fyp_android_application;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDetailsActivity extends AppCompatActivity {
    private static final String TAG = UserDetailsActivity.class.getSimpleName();
    private AuthenticationService authenticationService= AuthenticationService.getInstance();
    private TextView votesNumber;
    private int x;
    private Button downloadButton;
    private Button newVoteBTn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        downloadButton = (Button)findViewById(R.id.DownloadBtn);
        newVoteBTn = (Button)findViewById(R.id.NewVoteBtn);

        x = (int)getIntent().getExtras().get("votes");
        votesNumber= (TextView)findViewById(R.id.VotesNumber);
    }
    protected void onStart() {
        super.onStart();
        votesNumber.setText(""+x);
        newVoteBTn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetailsActivity.this,VoteActivity.class);
                intent.putExtra("votes", x);
                startActivity(intent);

            }
        });
        downloadButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Retrofit.Builder builder =new Retrofit.Builder()
                        .baseUrl("https://colum-mullally-fyp-webservice.herokuapp.com/").addConverterFactory(GsonConverterFactory.create());
                final Retrofit retrofit = builder.build();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                Date today = new Date();
                String date =formatter.format(today);
                UserAPI uapi =retrofit.create(UserAPI.class);
                Call<ResponseBody> call =uapi.setField(authenticationService.getValidUser(),"Date",date);
                call.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call,Response<ResponseBody> response) {
                        StorageAPI api =retrofit.create(StorageAPI.class);
                        call =api.download(authenticationService.getValidUser());
                        call.enqueue(new Callback<ResponseBody>() {

                            @Override
                            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {

                                new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                                        runOnUiThread(new Runnable() {
                                            public void run() {

                                                Toast.makeText(UserDetailsActivity.this, "Download Complete", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        return null;
                                    }
                                }.execute();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(UserDetailsActivity.this, "Error try again", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(UserDetailsActivity.this, "Error try again", Toast.LENGTH_SHORT).show();
                    }
                });
                }
        });

    }
    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"Class-Rep-form-2018.pdf");
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                }

                outputStream.flush();
                outputStream.close();



                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
