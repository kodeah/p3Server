package tasks;

import utils.log.ILog;

public class Task implements StatusStringUpdateDelegate {

    private static final String INITIAL_STATUS_STRING = "Initializing ...";
    private static final String FINAL_STATUS_STRING_PREFIX_FAIL = "Failed: ";

    private final String taskName;
    private final ILog log;
    private final FailableRunnable r;
    private String statusString = INITIAL_STATUS_STRING;

    private boolean executionOngoing = true;
    private boolean success = false;

    public Task(final String taskName,
                final FailableRunnable r,
                final ILog log ) {
        this.taskName = taskName;
        this.r = r;
        this.log = log;
    }

    public String getTaskName() {
        return taskName;
    }

    public synchronized String getStatusString() {
        return statusString;
    }

    public boolean executionOngoing() {
        return executionOngoing;
    }

    public boolean success() {
        return success;
    }

    @Override
    public synchronized void updateStatusString(final String statusString) {
        this.statusString = statusString;
    }

    public Object execute() {
        try {
            return r.run( this );
        } catch (Exception e) {
            log.error( e.getMessage() );
            updateStatusString( FINAL_STATUS_STRING_PREFIX_FAIL + e.getMessage() );
            e.printStackTrace();
            return null;
        }
    }
}
