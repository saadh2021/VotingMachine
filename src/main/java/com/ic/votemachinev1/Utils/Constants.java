package com.ic.votemachinev1.Utils;

public class Constants {
    public static final String[] AUTH_WHITELIST = {
            /*"/admin/login",
            "/login",
            "/voter/**",
            "/voter/SendApprovalRequest/**",
            "/voter/SaveCandidateApplication",
            "/voter/SendApprovalRequest",
            "/category/get-categories",
            "/admin/dashboard",
            "/admin/voters",
            "/admin/Candidates",
            "/admin/candidates",
            "/admin/**",
            "/voter/ApplyForCandidate",
            "/candidate/**",*/
            "/login",
            "/auth/login",
            "/auth/login/**",
            "/VoterRegistration",
            "/SaveVoter",


    };
    public static final String[] Voter_LIST = {
            "/voter/**",
            "voter/VoterDashboard",
            "order/get-all-orders",
            "/category/create",
    };

    public static final String[] ADMIN_LIST = {
             "/admin/dashboard",
             "/admin/**",
    };

    public static final String[] CANDIDATE_LIST = {
            //"/candidate/**",
            "order/get-all-orders",
            "/category/create",
    };
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

}
