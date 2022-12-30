package com.catexam.app.response;

public class DatabaseModel {
    int main_cat_id;
    String main_category;

    int index;
    int main_category_id;
    int isAttempted;

    String sub_category_title;
    String question;
    String option_1;
    String option_2;
    String option_3;
    String option_4;
    int answer;
    String explaination;
    int sub_category_id;
    int que_id;
    int bookmark;

    public DatabaseModel(int drawable, String color) {
        this.drawable = drawable;
        this.color = color;
    }

    public int drawable;
    public String color;

    // default constructor
    public DatabaseModel() {

    }

// parameterized constructor


    public DatabaseModel(int main_cat_id, String main_category, int index, int main_category_id,int isAttempted, String sub_category_title, String question, String option_1, String option_2, String option_3, String option_4, int answer, String explaination, int sub_category_id, int que_id, int bookmark) {
        this.main_cat_id = main_cat_id;
        this.main_category = main_category;
        this.index = index;
        this.isAttempted = isAttempted;
        this.main_category_id = main_category_id;
        this.sub_category_title = sub_category_title;
        this.question = question;
        this.option_1 = option_1;
        this.option_2 = option_2;
        this.option_3 = option_3;
        this.option_4 = option_4;
        this.answer = answer;
        this.explaination = explaination;
        this.sub_category_id = sub_category_id;
        this.que_id = que_id;
        this.bookmark = bookmark;
    }

    public int getMain_cat_id() {
        return main_cat_id;
    }

    public void setMain_cat_id(int main_cat_id) {
        this.main_cat_id = main_cat_id;
    }

    public int getIsAttempted(){return isAttempted;}
    public void  setIsAttempted(int isAttempted){this.isAttempted = isAttempted;}

    public String getMain_category() {
        return main_category;
    }

    public void setMain_category(String main_category) {
        this.main_category = main_category;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getMain_category_id() {
        return main_category_id;
    }

    public void setMain_category_id(int main_category_id) {
        this.main_category_id = main_category_id;
    }

    public String getSub_category_title() {
        return sub_category_title;
    }

    public void setSub_category_title(String sub_category_title) {
        this.sub_category_title = sub_category_title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption_1() {
        return option_1;
    }

    public void setOption_1(String option_1) {
        this.option_1 = option_1;
    }

    public String getOption_2() {
        return option_2;
    }

    public void setOption_2(String option_2) {
        this.option_2 = option_2;
    }

    public String getOption_3() {
        return option_3;
    }

    public void setOption_3(String option_3) {
        this.option_3 = option_3;
    }

    public String getOption_4() {
        return option_4;
    }

    public void setOption_4(String option_4) {
        this.option_4 = option_4;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

    public int getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(int sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public int getQue_id() {
        return que_id;
    }

    public void setQue_id(int que_id) {
        this.que_id = que_id;
    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }
}