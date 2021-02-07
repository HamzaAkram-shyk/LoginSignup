package model;

public class Data {

    String title;
    String description;
    String budget;
    String worktype;
    String category;
    String location;

    public  Data(){

    }


    public Data(String title, String description, String budget, String worktype, String category, String location) {
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.worktype = worktype;
        this.category = category;
        this.location = location;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

