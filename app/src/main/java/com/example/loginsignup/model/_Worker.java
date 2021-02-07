package com.example.loginsignup.model;

import java.util.List;

public class _Worker  {
    private List<Integer> applyJobId;

    private  _User user;

    public List<Integer> getApplyJobId() {
        return applyJobId;
    }

    public void setApplyJobId(List<Integer> applyJobId) {
        this.applyJobId = applyJobId;
    }

    public _User getUser() {
        return user;
    }

    public void setUser(_User user) {
        this.user = user;
    }

    public _Worker(List<Integer> jobId, _User user) {
        this.applyJobId = jobId;
        this.user = user;
    }

    public _Worker() {
    }
}
