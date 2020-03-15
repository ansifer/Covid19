package com.sanchitvarshney.covid19.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sanchitvarshney.covid19.R;
import com.sanchitvarshney.covid19.modal.CountryItem;
import com.sanchitvarshney.covid19.modal.CountryItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private List<CountryItem> countryItemList;
    private List<CountryItem> originalCountryItems;
    private CountryItemAdapter adapter;
    private RecyclerView recyclerView;
    private boolean isShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView confirmedTextView = findViewById(R.id.confirmed);
        final TextView recoveredTextView = findViewById(R.id.recovered);
        final TextView deathTextView = findViewById(R.id.death);
        final ProgressBar progressBar1 = findViewById(R.id.progressBar);
        final ProgressBar progressBar2 = findViewById(R.id.progressBar2);
        final ProgressBar progressBar3 = findViewById(R.id.progressBar3);
        final ProgressBar progressBar = findViewById(R.id.progressBar4);
        final TextView countryTextView = findViewById(R.id.textView2);
        final EditText countryEditText = findViewById(R.id.countryEditText);
        final ImageView searchBtn = findViewById(R.id.searchBtn);
        final ImageView closeBtn = findViewById(R.id.closeBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isShown) {
                    countryTextView.setVisibility(View.GONE);
                    countryEditText.setVisibility(View.VISIBLE);
                    closeBtn.setVisibility(View.VISIBLE);
                }
                else {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                isShown = true;
            }
        });

        countryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")) {
                    countryItemList.clear();
                    for(CountryItem countryItem : originalCountryItems) {
                        if(!countryItem.getState().equals("null")) {
                            if(countryItem.getState().toLowerCase().contains(s.toString().toLowerCase()) || countryItem.getCountry().toLowerCase().contains(s.toString().toLowerCase())) {
                                countryItemList.add(countryItem);
                            }
                        } else {
                            if(countryItem.getCountry().toLowerCase().contains(s.toString().toLowerCase())) {
                                countryItemList.add(countryItem);
                            }
                        }
                    }
                } else {
                    countryItemList.addAll(originalCountryItems);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryTextView.setVisibility(View.VISIBLE);
                countryEditText.setVisibility(View.GONE);
                closeBtn.setVisibility(View.GONE);
                countryEditText.setText("");
                isShown = false;
                countryItemList.clear();
                countryItemList.addAll(originalCountryItems);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        countryItemList = new ArrayList<>();
        originalCountryItems = new ArrayList<>();
        confirmedTextView.setVisibility(View.GONE);
        recoveredTextView.setVisibility(View.GONE);
        deathTextView.setVisibility(View.GONE);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://covid19.mathdro.id/api";
        String url1 ="https://covid19.mathdro.id/api/confirmed";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject confirmedJSONObject = jsonObject.getJSONObject("confirmed");
                            JSONObject recoveredJSONObject = jsonObject.getJSONObject("recovered");
                            JSONObject deathsJSONObject = jsonObject.getJSONObject("deaths");
                            String confirmed = confirmedJSONObject.getString("value");
                            String recovered = recoveredJSONObject.getString("value");
                            String deaths = deathsJSONObject.getString("value");
                            confirmedTextView.setText(confirmed);
                            recoveredTextView.setText(recovered);
                            deathTextView.setText(deaths);
                            confirmedTextView.setVisibility(View.VISIBLE);
                            recoveredTextView.setVisibility(View.VISIBLE);
                            deathTextView.setVisibility(View.VISIBLE);
                            progressBar1.setVisibility(View.GONE);
                            progressBar2.setVisibility(View.GONE);
                            progressBar3.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Error: " + error.getMessage());
            }
        });

        // Request a string response from the provided URL.
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String state = jsonArray.getJSONObject(i).getString("provinceState");
                                String country = jsonArray.getJSONObject(i).getString("countryRegion");
                                String confirmed = jsonArray.getJSONObject(i).getString("confirmed");
                                String recovered = jsonArray.getJSONObject(i).getString("recovered");
                                String deaths = jsonArray.getJSONObject(i).getString("deaths");
                                CountryItem countryItem = new CountryItem(state, country, confirmed,recovered, deaths);
                                countryItemList.add(countryItem);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        originalCountryItems.addAll(countryItemList);
                        progressBar.setVisibility(View.GONE);
                        adapter = new CountryItemAdapter(MainActivity.this, countryItemList);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Error: " + error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        queue.add(stringRequest1);

    }
}
