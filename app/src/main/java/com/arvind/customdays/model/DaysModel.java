package com.arvind.customdays.model;

public class DaysModel {
    String textTitle;
    boolean isSelected;

    public DaysModel(String textTitle, Boolean isSelected) {
        this.textTitle = textTitle;
        this.isSelected = isSelected;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }
}
