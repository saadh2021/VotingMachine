package com.ic.votemachinev1.Utils;

public class Constants {
    public static final String[] AUTH_WHITELIST = {
            /*"/Admin/login",
            "/login",
            "/Voter/**",
            "/Voter/SendApprovalRequest/**",
            "/Voter/SaveCandidateApplication",
            "/Voter/SendApprovalRequest",
            "/category/get-categories",
            "/Admin/Dashboard",
            "/Admin/Voters",
            "/Admin/Candidates",
            "/Admin/Candidates",
            "/Admin/**",
            "/Voter/ApplyForCandidate",
            "/Candidate/**",*/
            "/login",
            "/Auth/Login",
            "/Auth/Login/**",
            "/VoterRegistration",
            "/SaveVoter",
            "/OTPVerification",
            "/TestData",
            "/favicon.ico",
            "/invalidateSession"


    };
    public static final String[] Voter_LIST = {
            "/Voter/**",
            "voter/VoterDashboard",
            "order/get-all-orders",
            "/category/create",
    };

    public static final String[] ADMIN_LIST = {
             "/Admin/Dashboard",
             "/Admin/**",
    };

    public static final String[] CANDIDATE_LIST = {
            //"/Candidate/**",
            "order/get-all-orders",
            "/category/create",
    };
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

}
