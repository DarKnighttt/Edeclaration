package com.projects.darknight.edeclaration.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Worker {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("firstname")
    @Expose
    private String firstName;
    @SerializedName("lastname")
    @Expose
    private String lastName;
    @SerializedName("placeOfWork")
    @Expose
    private String workPlace;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("linkPDF")
    @Expose
    private String pdfLink;

    public Worker() {
    }

    public Worker(String firstName, String lastName, String workPlace, String position, String pdfLink) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.workPlace = workPlace;
        this.position = position;
        this.pdfLink = pdfLink;
    }

    public Worker(String id, String firstName, String lastName, String workPlace, String position, String pdfLink) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.workPlace = workPlace;
        this.position = position;
        this.pdfLink = pdfLink;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }


    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "; Workplace: " + workPlace + "; Position: " + position;
    }
}
