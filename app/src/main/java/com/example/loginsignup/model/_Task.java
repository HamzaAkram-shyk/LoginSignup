package com.example.loginsignup.model;

public class _Task {
private int tasKPrice,taskId;
private String taskTitle,taskDetails,taskCategory,workLocation;
private Boolean isOnline;

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    private String clientId;
private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public _Task(int tasKPrice, int taskId, String taskCategory, String taskTitle, String taskDetails, Boolean isOnline, String workerId) {
        this.tasKPrice = tasKPrice;
        this.taskId = taskId;
        this.taskCategory = taskCategory;
        this.taskTitle = taskTitle;
        this.taskDetails = taskDetails;
        this.isOnline = isOnline;
        this.clientId = workerId;
    }

    public int getTasKPrice() {
        return tasKPrice;
    }

    public void setTasKPrice(int tasKPrice) {
        this.tasKPrice = tasKPrice;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public _Task() {
    }
}
