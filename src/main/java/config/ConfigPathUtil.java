package config;

public class ConfigPathUtil {

    public static final String USER_HOME_PREFIX = System.getProperty("user.home");

    public static String getCompletePathFromPerhapsRelativePath( final String p ) {
        if( p.substring(0, 1).equals("/") ) {
            return p;
        }
        return USER_HOME_PREFIX + "/" + p;
    }

}
