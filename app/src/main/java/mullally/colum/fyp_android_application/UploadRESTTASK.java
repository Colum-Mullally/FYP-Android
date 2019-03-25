package mullally.colum.fyp_android_application;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Part;

/**
 * Created by Colum on 24/03/2019.
 */

public class UploadRESTTASK extends AsyncTask<String,Void,Boolean> {
    private AuthenticationService authenticationService = AuthenticationService.getInstance();
    private boolean proceed = false;
    private Context c;
    private String username;
    private String password;
    public UploadRESTTASK(Context applicationContext) {
        c = applicationContext;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        username = strings[0];
        password = strings[1];
        Retrofit.Builder builder =new Retrofit.Builder()
                .baseUrl("https://colum-mullally-fyp-webservice.herokuapp.com/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        StorageAPI api = retrofit.create(StorageAPI.class);
        AssetManager am = c.getAssets();
        try {
            InputStream is = am.open("Class-Rep-form-2018-2019.pdf");
            File file= new File(c.getFilesDir(),"Class-Rep-form-2018-2019.pdf");
            FileUtils.copyInputStreamToFile(is, file);
            RequestBody requestFile;
            requestFile = RequestBody.create(
                    MediaType.parse("application/pdf"),
                    file
            );
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            Call<ResponseBody> call = api.upload(authenticationService.getValidUser(),body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    Intent intent = new Intent(c,InitialDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    c.startActivity(intent);
                    Log.v("Upload", "success");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("Upload error:", t.getMessage());
                }
            });

        } catch (Exception e) {
            return false;
        }
        return true;
    }

}