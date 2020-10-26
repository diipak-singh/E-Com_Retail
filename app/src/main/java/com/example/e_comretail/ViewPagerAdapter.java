package com.example.e_comretail;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.e_comretail.Details.ItemDetails;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private List<ItemDetails> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public ViewPagerAdapter(List<ItemDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recent_card_holder, container, false);
        ImageView itemPhoto;
        TextView itemName, measurement, finalPrice, actualPrice, discount;
        itemPhoto = view.findViewById(R.id.imageView4);
        itemName = view.findViewById(R.id.item_name);
        measurement = view.findViewById(R.id.measurement);
        finalPrice = view.findViewById(R.id.final_price);
        actualPrice = view.findViewById(R.id.actual_price);
        discount = view.findViewById(R.id.discount);

        String ItemPrice = list.get(position).getItemPrice();
        String Gst = list.get(position).getGstrate();
        String Discount = list.get(position).getDiscount();
        //some calculation
        int ItemPriceAftrGst = Integer.parseInt(ItemPrice) + (Integer.parseInt(ItemPrice) * Integer.parseInt(Gst) / 100);
        int ItemPriceAftrDiscount = ItemPriceAftrGst - (ItemPriceAftrGst * Integer.parseInt(Discount) / 100);


        Glide.with(context).load(list.get(position).getImageUrl()).into(itemPhoto);
        itemName.setText(list.get(position).getItemName());
        measurement.setText(list.get(position).getMeasurement());
        actualPrice.setText(String.valueOf(ItemPriceAftrGst));
        actualPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        finalPrice.setText("₹" + String.valueOf(ItemPriceAftrDiscount));
        discount.setText(Discount + "%");

        container.addView(view, 0);
        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
