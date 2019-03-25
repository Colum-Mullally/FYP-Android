package mullally.colum.fyp_android_application;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Colum on 24/03/2019.
 */

public class RegisterRESTTask extends AsyncTask<String,Void,ResponseEntity>
{
    private AuthenticationService authenticationService= AuthenticationService.getInstance();
    private String authHeader;
    private String url = "https://colum-mullally-fyp-webservice.herokuapp.com/v1/register";
    private String username;
    private String password;
    private Context c;
    public RegisterRESTTask(Context applicationContext) {
        c=applicationContext;
    }

    @Override
    protected ResponseEntity doInBackground(String... strings) {
        username=strings[0];
        password=strings[1];
        url+="?username="+username+"&password="+password;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type","application/x-www-form-urlencoded");
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST,entity,String.class);
            return response;
        } catch (Exception e){
            Log.e("fucked",e.getStackTrace().toString());
            return null;
        }
    }
    protected void  onPostExecute(ResponseEntity result){
        HttpStatus status = result.getStatusCode();
        if (status == HttpStatus.OK) {
            String auth= username+":"+password;
            authHeader="Basic " + Base64.encodeToString(auth.getBytes(),Base64.NO_WRAP);
            authenticationService.validUser(authHeader);
            new UploadRESTTASK(c).execute(username,password);
        }
    }
}
