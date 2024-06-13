package com.example.wardenfront;

public class BanCard_Model {

    private String userID = "";
    private String numReports = "";
    private String reportTypes = "";
    private String name = "";

    public BanCard_Model(String UserID, String NumReports, String ReportTypes, String name){
        userID = UserID;
        numReports = NumReports;
        reportTypes = ReportTypes;
        this.name = name;
    }

    public String getUserID(){
        return userID;
    }

    public  String getNumReports(){
        return numReports;
    }

    public  String getReportTypes(){
        return reportTypes;
    }

    public String getName() { return name; }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setNumReports(String numReports) {
        this.numReports = numReports;
    }

    public void setReportTypes(String reportTypes) {
        this.reportTypes = reportTypes;
    }

    public void setName(String name) { this.name = name;}
}
