package com.example.loginsignup.model;

public class _UserJobs {
    String Title;
    String Details;

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerMail() {
        return workerMail;
    }

    public void setWorkerMail(String workerMail) {
        this.workerMail = workerMail;
    }

    String workerName;
    String workerMail;
    int Price,taskId;
    String workerId,clientId;

    public _UserJobs() {

    }

    public _UserJobs(String title, String details, int price, int taskId, String workerId, String clientId) {
        Title = title;
        Details = details;
        Price = price;
        this.taskId = taskId;
        this.workerId = workerId;
        this.clientId = clientId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
