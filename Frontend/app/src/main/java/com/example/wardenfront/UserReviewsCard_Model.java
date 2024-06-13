package com.example.wardenfront;

/**
 * @author Ayden Boehme
 *
 * A custom CardView model made for the UserReviewActivity's
 * RecyclerView. Each new review that is updated onto the Recycler
 * gets its own UserReviewsCard_Model that contains the review's
 * title, reviewer's username, the review's rating (1-5), and the
 * full review left by the reviewer.
 */
public class UserReviewsCard_Model {

    private String reviewTitle;
    private String reviewerUsername;
    private String reviewRating;
    private String reviewMessage;

    // Constructor
    public UserReviewsCard_Model(String reviewTitle, String reviewerUsername, String reviewRating, String reviewMessage) {
        this.reviewTitle = reviewTitle;
        this.reviewerUsername = reviewerUsername;
        this.reviewRating = reviewRating;
        this.reviewMessage = reviewMessage;
    }

    // Getter and Setter
    public String getReviewTitle() {
        return reviewTitle;
    }
    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewerUsername() {
        return reviewerUsername;
    }
    public void setReviewerUsername(String reviewerUsername) {
        this.reviewerUsername = reviewerUsername;
    }

    public String getReviewRating() {
        return reviewRating;
    }
    public void setReviewRating(String reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }
    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }
}
