package mullally.colum.fyp_android_application;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;

/**
 * Created by Colum on 25/03/2019.
 */

public interface StorageAPI {
    @Multipart
    @POST("v1/storage/uploadFile")
    Call<ResponseBody> upload(@Header("Authorization")String autheader, @Part MultipartBody.Part file);

    @Streaming
    @GET("v1/storage/download/Class-Rep-form-2018-2019.pdf")
    Call<ResponseBody> download(@Header("Authorization")String autheader);
}
