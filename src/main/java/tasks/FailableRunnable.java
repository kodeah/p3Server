package tasks;

public interface FailableRunnable {

    Object run( final StatusStringUpdateDelegate statusStringUpdateDelegate ) throws Exception;

}
