package mullally.colum.fyp_android_application;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;

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

public class LoginRESTTask extends AsyncTask<String,Void,ResponseEntity>
{
    private AuthenticationService authenticationService= AuthenticationService.getInstance();
    private String authHeader;
    private String url = "https://colum-mullally-fyp-webservice.herokuapp.com/v1/User/details";
    private Context con;
    public LoginRESTTask(Context applicationContext) {
        con=applicationContext;
    }

    @Override
    protected ResponseEntity doInBackground(String... strings) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        try{
            HttpHeaders headers = new HttpHeaders();
            String auth = strings[0]+":"+strings[1];
            authHeader="Basic " + Base64.encodeToString(auth.getBytes(),Base64.NO_WRAP);
            headers.set("Authorization",authHeader);
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
            return response;
        } catch (Exception e){
            return null;
        }
    }
    protected void  onPostExecute(ResponseEntity result){
        super.onPostExecute(result);
        HttpStatus status = result.getStatusCode();
        if(status== HttpStatus.OK){
            authenticationService.validUser(authHeader);
            Intent intent = new Intent(con,UserDetailsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            con.startActivity(intent);
        }
    }
}
