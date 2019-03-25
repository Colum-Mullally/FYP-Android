package mullally.colum.fyp_android_application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitialDetails extends AppCompatActivity {
    private AuthenticationService authenticationService = AuthenticationService.getInstance();
    private Button confirmButton;
    private EditText fullNameInput;
    private EditText studentIDInput;
    private EditText courseCodeInput;
    private EditText courseOfStudyInput;
    private EditText yearOfCourseInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_details);
        confirmButton = (Button)findViewById(R.id.ConfirmBtn);
        fullNameInput = (EditText)findViewById(R.id.FullNameInput);
        studentIDInput = (EditText)findViewById(R.id.StudentIDInput);
        courseCodeInput = (EditText)findViewById(R.id.CourseCodeInput);
        courseOfStudyInput = (EditText)findViewById(R.id.CourseOfStudyInput);
        yearOfCourseInput = (EditText)findViewById(R.id.YearOfCourseInput);
    }
    protected void onStart(){
        super.onStart();
        confirmButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Retrofit.Builder builder =new Retrofit.Builder()
                        .baseUrl("https://colum-mullally-fyp-webservice.herokuapp.com/").addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                UserAPI api =retrofit.create(UserAPI.class);
                Call<ResponseBody> call= api.setField(authenticationService.getValidUser(),"Course Code",courseCodeInput.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        Log.v("Upload", "success");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Upload error:", t.getMessage());
                    }
                });
                call= api.setField(authenticationService.getValidUser(),"Year of study",yearOfCourseInput.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        Log.v("Upload", "success");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Upload error:", t.getMessage());
                    }
                });
                call= api.setField(authenticationService.getValidUser(),"Fullname",fullNameInput.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        Log.v("Upload", "success");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Upload error:", t.getMessage());
                    }
                });
                call= api.setField(authenticationService.getValidUser(),"Course of study",courseOfStudyInput.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        Log.v("Upload", "success");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Upload error:", t.getMessage());
                    }
                });
                call= api.setField(authenticationService.getValidUser(),"Student ID",studentIDInput.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        Log.v("Upload", "success");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Upload error:", t.getMessage());
                    }
                });
                Intent intent = new Intent(InitialDetails.this,UserDetailsActivity.class);
                intent.putExtra("votes", 0);
                startActivity(intent);
            }
        });
    }
}
