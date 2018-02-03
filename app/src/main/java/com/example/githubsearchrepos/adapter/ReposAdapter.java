package com.example.githubsearchrepos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.githubsearchrepos.R;
import com.example.githubsearchrepos.model.Item;



import java.util.List;

/**
 * Created by Srinivas on 2/2/2018.
 */

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder> {

    private List<Item> repos;
    private Context context;


    public static class ReposViewHolder extends RecyclerView.ViewHolder {

        TextView repoName;
        ImageView ownerAvtar;
        TextView repoDescription;
        TextView owner,starsCount;


        public ReposViewHolder(View v) {
            super(v);

            repoName = (TextView) v.findViewById(R.id.repo_name);
            ownerAvtar = (ImageView) v.findViewById(R.id.owner_avtar);
            repoDescription = (TextView) v.findViewById(R.id.description);
            owner = (TextView) v.findViewById(R.id.owner);
            starsCount = (TextView) v.findViewById(R.id.stars_count);

        }
    }

    public ReposAdapter(List<Item> repos, Context context) {
        this.repos = repos;
        this.context = context;
    }

    @Override
    public ReposAdapter.ReposViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_row_item, parent, false);
        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReposViewHolder holder, int position) {
        Item repoItem = repos.get(position);
        holder.repoName.setText(repoItem.getName());

        Glide.with(context).load(repoItem.getOwner().getAvatarUrl())
                .thumbnail(0.4f)
                .crossFade()
                .fitCenter()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ownerAvtar);

        holder.repoDescription.setText(repoItem.getDescription());

        holder.owner.setText(""+repoItem.getOwner().getLogin());
        holder.starsCount.setText("\u2605 "+repoItem.getWatchersCount());

    }

    @Override
    public int getItemCount() {
        return repos.size();
    }
}
