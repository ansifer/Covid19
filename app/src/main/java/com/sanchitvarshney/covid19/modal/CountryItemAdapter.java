package com.sanchitvarshney.covid19.modal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanchitvarshney.covid19.R;

import java.util.List;

public class CountryItemAdapter extends RecyclerView.Adapter<CountryItemHolder> {

    private Context context;
    private List<CountryItem> countryItems;

    public CountryItemAdapter(Context context, List<CountryItem> countryItemList) {
        this.context = context;
        this.countryItems = countryItemList;
    }

    @NonNull
    @Override
    public CountryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_item, parent, false);
        return new CountryItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryItemHolder holder, int position) {
        holder.bindHolder(countryItems.get(position));
    }

    @Override
    public int getItemCount() {
        return countryItems.size();
    }
}
