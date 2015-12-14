package com.pje.def.wikibook.bdd;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Sim on 03/12/2015.
 */
public class GenderDetails {

    @DatabaseField(id = true, generatedId = false, columnName = "gender_id")
    protected int genderId;

    @DatabaseField(columnName = "gender_title")
    protected String genderTitle;

    public GenderDetails(){

    }

    public GenderDetails(int genderId, String genderTitle) {
        this.genderId = genderId;
        this.genderTitle = genderTitle;
    }

    public String getGenderTitle() {
        return genderTitle;
    }

    public void setGenderTitle(String genderTitle) {
        this.genderTitle = genderTitle;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }
}
