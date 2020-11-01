package com.example.e_comretail;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class chat_rec extends RecyclerView.ViewHolder {
    ConstraintLayout leftLayout, rightLayout;
    TextView leftDate, rightDate;
    TextView leftText,rightText;

    public chat_rec(View itemView){
        super(itemView);

        leftLayout = (ConstraintLayout) itemView.findViewById(R.id.left_layout);
        rightLayout = (ConstraintLayout) itemView.findViewById(R.id.right_layout);
        leftText = (TextView)itemView.findViewById(R.id.leftText);
        rightText = (TextView)itemView.findViewById(R.id.rightText);
        leftDate = (TextView)itemView.findViewById(R.id.left_date);
        rightDate =(TextView)itemView.findViewById(R.id.right_date);
    }
}
