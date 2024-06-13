package com.example.wardenfront;

/**
 * @author Ayden Boehme
 *
 * A custom CardView model made for the JobDiscoveryActivity's
 * RecyclerView. Each new job that is updated onto the Recycler
 * gets its own JobBoardCard_Model that contains the job's
 * title, date, and hourly pay.
 */
public class JobBoardCard_Model {

    private String jobTitle;
    private String jobDate;
    private String jobPay;
    private String ID;

    // Constructor
    public JobBoardCard_Model(String jobTitle, String jobDate, String jobPay, String ID) {
        this.jobTitle = jobTitle;
        this.jobDate = jobDate;
        this.jobPay = jobPay;
        this.ID = ID;
    }

    // Getter and Setter
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDate() {
        return jobDate;
    }
    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }

    public String getJobPay() {
        return jobPay;
    }
    public void setJobPay(String jobPay) {
        this.jobPay = jobPay;
    }

    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
}
