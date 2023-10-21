package com.example.horoscopeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText searchZodiac;
    TextView showDesc;
    ImageButton searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchZodiac = findViewById(R.id.searchZodiac);
        showDesc = findViewById(R.id.descBox);
        searchBtn = findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign = searchZodiac.getText().toString().toLowerCase();
                if (sign.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a Zoadic sign", Toast.LENGTH_SHORT).show();
                } else {
                    getZodiacData(sign);
                }
            }
        });
    }
    private void getZodiacData(String searchText) {
        String url = "https://turquoise-squirrel-tam.cyclic.app/horoscope?title="+searchText;
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonobjectrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("json", "jsonResponse");
                try {
                    showDesc.setText(response.getJSONObject("horoscope").getString("desc"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showDesc.setText("Invalid zodiac sign  ");
//                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonobjectrequest);
    }
}