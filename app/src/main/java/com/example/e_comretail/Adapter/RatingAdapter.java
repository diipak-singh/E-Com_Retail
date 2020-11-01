package com.example.e_comretail.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Details.RatingDetails;
import com.example.e_comretail.R;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {
    private ArrayList<RatingDetails> list;
    private Context context;

    public RatingAdapter(ArrayList<RatingDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.ViewHolder holder, int position) {

        String userName = list.get(position).getUserName();
        String lastName = "";
        String firstName = "";
        if (userName.split("\\w+").length > 1) {

            lastName = userName.substring(userName.lastIndexOf(" ") + 1);
            firstName = userName.substring(0, userName.lastIndexOf(' '));
        } else {
            firstName = userName;
        }

        holder.userName.setText(firstName + " on " );
        holder.reviewDate.setText(list.get(position).getReviewDate());
        holder.review.setText(list.get(position).getReviewText());
        holder.ratingBar.setRating(Float.parseFloat(list.get(position).getRating()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userName, reviewDate, review;
        private RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.textView11);
            reviewDate = itemView.findViewById(R.id.review_date);
            review = itemView.findViewById(R.id.review_text);
            ratingBar = itemView.findViewById(R.id.ratingBar2);
        }
    }
}
