package com.example.loginsignup.model;

public class _CurrentJob extends _UserJobs {
    private  String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public _CurrentJob() {
        super();
    }

    public _CurrentJob(String title, String details, int price, int taskId, String workerId, String clientId) {
        super(title, details, price, taskId, workerId, clientId);
    }
}
