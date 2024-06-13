package com.example.wardenfront.net_utils;

//import org.java_websocket.client.WebSocketClient;

/**
 * @author Kyle Clements and Ayden Boehme
 *
 * This holds the request addresses
 */

public class Const {
    public static final String URL_JSON_ARRAY = "https://59bb99a5-5746-4db8-96c7-4bad05ccaebf.mock.pstmn.io/jsonArrReq";
    public static final String URL_Sign_In = "http://coms-309-046.class.las.iastate.edu:8080/users/login";
    public static final String URL_Ban = "http://coms-309-046.class.las.iastate.edu:8080/users/ban";
    public static final String Url_Ban_List = "http://coms-309-046.class.las.iastate.edu:8080/users/with/reports";
    public static final String Url_planB = "http://coms-309-046.class.las.iastate.edu:8080/login/create";
    public static final String Url_planB2 = "http://coms-309-046.class.las.iastate.edu:8080/login/8";

    public static final String URL_Total_Reports_Num = "http://coms-309-046.class.las.iastate.edu:8080/reports/number";
    public static final String URL_Weekly_Reports_Num = "http://coms-309-046.class.las.iastate.edu:8080/reports/number/past/week";
    public static final String URL_Banned_List = "http://coms-309-046.class.las.iastate.edu:8080/users/banned/all";
    public static final String URL_UnBan = "http://coms-309-046.class.las.iastate.edu:8080/users/unban";

    public static final String URL_CleaReport = "http://coms-309-046.class.las.iastate.edu:8080/users/clear/reports/";

    public static final String Url_jobPost_display = "http://coms-309-046.class.las.iastate.edu:8080/jobposts/";
    public static final String Url_interested = "http://coms-309-046.class.las.iastate.edu:8080/jobposts/subscribe";

    public static final String Url_getUser = "http://coms-309-046.class.las.iastate.edu:8080/users/";

    public static final String Url_report = "http://coms-309-046.class.las.iastate.edu:8080/reports/create/";



    public static final String POSTJSON_JOB_CREATE_TEST= "http://coms-309-046.class.las.iastate.edu:8080/jobposts/create";

    public static final String URL_JSON_JOB_CREATE= "http://coms-309-046.class.las.iastate.edu:8080/jobposts/create";

    public static final String URL_JSON_JOB_LIST= "http://coms-309-046.class.las.iastate.edu:8080/jobposts/title/dogs";
                   


   // public static final String POSTJSON_JOB_CREATE_TEST= "http://coms-309-046.class.las.iastate.edu:8080/jobposts/create";

    //public static final String URL_JSON_JOB_CREATE= "http://coms-309-046.class.las.iastate.edu:8080/jobposts/create";
  
    //public static final String URL_JSON_JOB_LIST= "http://coms-309-046.class.las.iastate.edu:8080/jobposts/title/dogs";

    public static final String URL_GetAuthor = "http://coms-309-046.class.las.iastate.edu:8080/jobposts/owner/";


    //public static final String URL_JSON_JOB_CREATE = "http://coms-309-046.class.las.iastate.edu:8080/jobposts/create";
    //public static final String URL_JSON_JOB_LIST = "http://coms-309-046.class.las.iastate.edu:8080/jobposts/title/dogs";
    public static final String URL_UpdateUserProfile =  "http://coms-309-046.class.las.iastate.edu:8080/users/update/";
    public static final String URL_Websocket_Inbox = "ws://coms-309-046.class.las.iastate.edu:8080/Inbox/";
    public static final String searchJobUrl = "http://coms-309-046.class.las.iastate.edu:8080/jobposts/search/get/";//+inputString
    public static final String searchUserUrl = "http://coms-309-046.class.las.iastate.edu:8080/users/search/get/";//+inputString

    public static final String getReviewsOfUser = "http://coms-309-046.class.las.iastate.edu:8080/reviews/user/";//+userID
    public static final String URL_getJobOwner = "http://coms-309-046.class.las.iastate.edu:8080/jobposts/owner/";//+jobID
    public static final String allSubbedUsersUrl = "http://coms-309-046.class.las.iastate.edu:8080/jobposts/interested/users/";//+ jobID
    public static final String mySubscribedJobsUrl = "http://coms-309-046.class.las.iastate.edu:8080/users/interested/jobposts/";//+ userID
    public static final String myCreatedPostsUrl = "http://coms-309-046.class.las.iastate.edu:8080/users/created/jobposts/";//+ userID
    public static final String URL_jobPosts = "http://coms-309-046.class.las.iastate.edu:8080/jobposts/all/active";



    public static final String URL_Create_Account = "http://coms-309-046.class.las.iastate.edu:8080/users/createaccount";

    public static final String createEmptyReviewUrl = "http://coms-309-046.class.las.iastate.edu:8080/reviews/create/empty/";
                                                        // + userID + "/" + reviewerID
    public static final String writeToReviewUrl = "http://coms-309-046.class.las.iastate.edu:8080/reviews/create/review/";
                                                        // + getEmptyReviewID + "/" + title + "/" + rating
    public static final String getUserInboxUrl = "http://coms-309-046.class.las.iastate.edu:8080/reviews/create/review/";

    public static final String URL_Delete_Account = "http://coms-309-046.class.las.iastate.edu:8080/users/delete/post/";

}
