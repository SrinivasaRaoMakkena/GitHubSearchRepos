package com.example.githubsearchrepos.network;

import com.example.githubsearchrepos.model.Item;
import com.example.githubsearchrepos.model.Repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Srinivas on 2/2/2018.
 */

public interface GitHubService {

    @GET("search/repositories?sort=stars?order=desc")
    Call<Repository> getReposList(@Query("q") String searchingParam);

}
