package com.example.githubsearchrepos.activities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.githubsearchrepos.R;
import com.example.githubsearchrepos.adapter.ReposAdapter;
import com.example.githubsearchrepos.model.Item;
import com.example.githubsearchrepos.model.Repository;
import com.example.githubsearchrepos.network.GitHubApiClient;
import com.example.githubsearchrepos.network.GitHubService;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar searchToolBar;
    @BindView(R.id.searchText)
    EditText searchQuery;
    @BindView(R.id.searchBtn)
    ImageButton searchButton;

    Context context;
    GitHubService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        service = GitHubApiClient.getClient().create(GitHubService.class);
        context = getApplicationContext();
    }

    public boolean checkingInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public void sortData(List<Item> repos) {
        Collections.sort(repos, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if (o1.getWatchersCount() > o2.getWatchersCount()) {
                    return -1;
                } else if (o1.getWatchersCount() == o2.getWatchersCount()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }

    public void networkCalling(String newText) {
        Call<Repository> call = service.getReposList(newText);

        call.enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(Call<Repository> call, Response<Repository> response) {
                // System.out.println(response.body().getTotalCount());
                try {
                    if (response != null) {
                        if (response.body() != null) {
                            List<Item> repos = response.body().getItems();
                            sortData(repos);
                            Iterator iterator = repos.iterator();
                            while (iterator.hasNext()) {
                                Item i = (Item) iterator.next();
                                System.out.println(i.getWatchersCount());
                            }
                            ReposAdapter adapter = new ReposAdapter(repos, MainActivity.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                            recyclerView.setLayoutManager(linearLayoutManager);

                            recyclerView.setAdapter(adapter);
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(context, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Repository> call, Throwable t) {


            }
        });
    }

    public void searchRepos(View view) {
        String q = searchQuery.getText().toString();
        if (!q.isEmpty()) {

            if (checkingInternetConnection()) {
                recyclerView.setVisibility(View.VISIBLE);
                networkCalling(q);
            } else {
                Toast.makeText(context, "internet connection not available", Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.INVISIBLE);
            }
        }
    }
}
