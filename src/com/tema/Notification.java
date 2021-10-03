package com.tema;

public class Notification {
    private Job job;
    private String status;
    private boolean seen;

    Notification(Job job, String status) {
        this.job = job;
        this.status = status;
        this.seen = false;
    }

    public Job getJob() {
        return job;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
