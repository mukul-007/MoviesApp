package com.example.moviesapp;

import android.content.Context;
import android.media.Image;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.moviesapp.apiClient.ApiInterface;
import com.example.moviesapp.model.Result;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Result> results;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    public Adapter(List<Result> results, Context mContext, OnItemClickListener onItemClickListener) {
        this.results = results;
        this.mContext = mContext;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = results.get(position);
        String imageUrl = ApiInterface.IMAGE_BASE_URL + result.getPosterPath();
        String genre = "";
        List<Integer> genreIds = result.getGenreIds();
        if(genreIds.size() == 1){
            genre = MainActivity.genreMap.get(genreIds.get(0)) + ".";
        }else {
            for (int id : genreIds) {
                genre += MainActivity.genreMap.get(id) + ", ";
            }
            genre = genre.substring(0, genre.length() - 2) + ".";
        }
        holder.title.setText(result.getTitle());
        holder.genre.setText(genre);

        Glide.with(mContext)
                .load(imageUrl)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.releaseDate.setText(result.getReleaseDate());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public TextView title, genre, releaseDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            genre = itemView.findViewById(R.id.genre);
            releaseDate = itemView.findViewById(R.id.release_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onClick(v, getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        void onClick(View v, int position);
    }
}
