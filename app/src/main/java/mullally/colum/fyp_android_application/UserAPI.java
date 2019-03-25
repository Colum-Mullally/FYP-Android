package mullally.colum.fyp_android_application;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Colum on 25/03/2019.
 */

public interface UserAPI {
    @POST("v1/User/details/Class-Rep-form-2018-2019.pdf/addcontent/{field}")
    Call<ResponseBody> setField(@Header("Authorization")String autheader, @Path("field") String fieldname,
                                @Query("content") String responseType);

}
