package mullally.colum.fyp_android_application;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
        if (result !=null) {
            HttpStatus status = result.getStatusCode();
            JSONObject json;
            JSONArray pdf;
            JSONArray attributes;
            String name;
            String content;
            int count= 0;
            try {
                json = new JSONObject(result.getBody().toString());
                pdf = new JSONArray(json.get("pdf").toString());
                JSONObject y;
                y = pdf.getJSONObject(0);
                attributes =new JSONArray(y.get("attributes").toString());
                for(int x =0; x<attributes.length();x++){
                    if(attributes.getJSONObject(x).get("name").toString().matches("Voter.*Name")&&attributes.getJSONObject(x).get("content").toString()!="null")
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (status == HttpStatus.OK) {
                authenticationService.validUser(authHeader);
                Intent intent = new Intent(con, UserDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("votes", count);
                con.startActivity(intent);
            }
        }
        Toast.makeText(con, "Error try again", Toast.LENGTH_SHORT).show();
    }
}
