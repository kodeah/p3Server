package utils.log;

public class PrintLog implements ILog {


    @Override
    public void log(String message) {
        String log = "[LOG:] " + message;
        output(log);
    }

    @Override
    public void warn(String message) {
        String log = "[WARNING:] " + message;
        output(log);
    }

    @Override
    public void error(String message) {
        String log = "[ERROR:] " + message;
        output(log);
    }

    @Override
    public void except(Exception e) {
        error( e.getMessage() );
    }

    @Override
    public void except(String message, Exception e) {
        error( message + System.lineSeparator() + e.getMessage() );
    }

    private void output(String message) {
        System.out.println(message);
    }

}
