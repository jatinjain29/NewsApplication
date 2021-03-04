package com.example.projectnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {
RecyclerView rv;
RvAdapter adapter;
    final static ArrayList<String> titles=new ArrayList();
    final static  ArrayList<String> urls=new ArrayList();
    final static  ArrayList<String> Imageurls=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv=findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));


        adapter=new RvAdapter(titles,MainActivity.this,Imageurls);

        String apikey="eba627b5b7294356ba5d8a24b878a109";
       // ="https://newsapi.org/v2/top-headlines?country=in&apiKey="+apikey;
        final String url="https://newsapi.org/v2/top-headlines?country=in&apiKey=eba627b5b7294356ba5d8a24b878a109";

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                   int responses= response.getInt("totalResults");

                   if(responses>0){
                       JSONArray data=response.getJSONArray("articles");
                       for(int j=1;j<data.length();j++){
                           JSONObject details= (JSONObject) data.get(j);
                           String title=details.getString("title");
                           String url= (String) details.getString("url");
                           String imgurl=details.getString("urlToImage");
                           if(title!=null && url!=null && imgurl!=null){
                               titles.add(title);
                              urls.add(url);
                              Imageurls.add(imgurl);
                           }

                       }
                   }
                 //   Toast.makeText(getApplicationContext(),titles.size(),Toast.LENGTH_LONG).show();
                    adapter.notifyDataSetChanged();
                    //rv.setAdapter(adapter);
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rv.setHasFixedSize(true);
                    rv.setAdapter(adapter);

                    //Log.i("TITLES",titles.toString());
                  //Toast.makeText(getApplicationContext(),titles.toString(),Toast.LENGTH_LONG).show();
                   //Log.i("URLS",urls.toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    Log.i("ERRROR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("User-Agent","Mozilla/5.0");
                //headers.put("key", "Value");
                return headers;
            }
        };

        queue.add(request);
      //// queue.start();
        adapter.OnItemClicked(new RvAdapter.ItemClicked() {
            @Override
            public void onNewsClicked(int position) {

                Intent intent=new Intent(MainActivity.this,NewsDetails.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

    }


}