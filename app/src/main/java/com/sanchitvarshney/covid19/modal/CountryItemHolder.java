package com.sanchitvarshney.covid19.modal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanchitvarshney.covid19.R;

public class CountryItemHolder extends RecyclerView.ViewHolder {

    private TextView country;
    private TextView confirmed;
    private TextView recovered;
    private TextView deaths;

    public CountryItemHolder(@NonNull View itemView) {
        super(itemView);
        country = itemView.findViewById(R.id.country);
        confirmed = itemView.findViewById(R.id.confirmed);
        recovered = itemView.findViewById(R.id.recovered);
        deaths = itemView.findViewById(R.id.death);
    }

    public void bindHolder(CountryItem countryItem) {
        if(countryItem.getState() != null && !countryItem.getState().equals("null")) {
            country.setText(countryItem.getState() + ", " + countryItem.getCountry());
        }
        else  {
            country.setText(countryItem.getCountry());
        }

        confirmed.setText(countryItem.getConfirmed());
        recovered.setText(countryItem.getRecovered());
        deaths.setText(countryItem.getDeaths());
    }

}
