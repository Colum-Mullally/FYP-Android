package mullally.colum.fyp_android_application;

/**
 * Created by Colum on 24/03/2019.
 */

class AuthenticationService {
    private static final AuthenticationService ourInstance = new AuthenticationService();
    private String cred = " ";
    private boolean authenticated = false;
    static AuthenticationService getInstance() {
        return ourInstance;
    }

    private AuthenticationService() {
    }

    public String getValidUser(){
        if(authenticated){
        return cred;
        } else{
            return null;
        }
    }

    public void validUser(String cred){
        authenticated=true;
        this.cred=cred;

    }
    public void invalidate(){
        authenticated=false;
    }
    public boolean Authenticated(){
        return authenticated;
    }

}
