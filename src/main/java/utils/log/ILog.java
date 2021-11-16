package utils.log;

public interface ILog {

    void log(String message);
    void warn(String message);
    void error(String message);
    void except(Exception e);
    void except(String message, Exception e);

}
