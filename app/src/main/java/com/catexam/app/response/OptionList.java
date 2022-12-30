package com.catexam.app.response;

public class OptionList {
    private int Icon;
    private  String paperYear;

    public OptionList(int icon) {
        Icon = icon;
    }

    public OptionList(int icon, String paperYear) {
        Icon = icon;
        this.paperYear = paperYear;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public String getPaperYear() {
        return paperYear;
    }

    public void setPaperYear(String paperYear) {
        this.paperYear = paperYear;
    }
}
