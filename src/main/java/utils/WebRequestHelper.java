package utils;

import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

public class WebRequestHelper {

    public final String host;
    public final int port;

    private boolean basicAuth = false;
    private String basicAuthUser;
    private String basicAuthPassword;

    public WebRequestHelper(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public WebRequestHelper(String host, int port, String user, String password) {
        this.host = host;
        this.port = port;
        setBasicAuthParams(user, password);
    }

    public void setBasicAuthParams(String user, String password) {
        this.basicAuth = true;
        this.basicAuthUser = user;
        this.basicAuthPassword = password;
    }

    public void sendCommand(
            String cmdString
    ) {
        sendCommand(cmdString, "");
    }

    public void sendCommand(
            String cmdString,
            String payload
    ) {
        ClientResource resource = new ClientResource(
                "http://" + host + ":" + port + cmdString );
        if (basicAuth) {
            resource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, basicAuthUser, basicAuthPassword);
        }
        resource.post( payload );
    }

    public String query(
            String cmdString
    ) throws Exception
    {
        ClientResource resource = new ClientResource(
                "http://" + host + ":" + port + cmdString );
        if (basicAuth) {
            resource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, basicAuthUser, basicAuthPassword);
        }
        return resource.get().getText();
    }

}
