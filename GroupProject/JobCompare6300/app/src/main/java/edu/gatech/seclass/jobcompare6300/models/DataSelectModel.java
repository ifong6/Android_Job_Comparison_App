package edu.gatech.seclass.jobcompare6300.models;

// Model for storing multi-select items in the RecyclerViewAdapter
public class DataSelectModel {
    private String textData;
    private boolean isActive;

    public DataSelectModel(String newTextData) {
        textData = newTextData;
        isActive = false;
    }

    public String getTextData() {
        return textData;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean newIsActive) {
        isActive = newIsActive;
    }
}