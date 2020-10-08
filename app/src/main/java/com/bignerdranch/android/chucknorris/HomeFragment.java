package com.bignerdranch.android.chucknorris;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView jokeList;
    private JokesAdapter jokesAdapter;
    private Button btnReload;
    private EditText etCount;
    public static List<Value> _jokes = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        jokeList = getActivity().findViewById(R.id.rv_numbers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        jokeList.setLayoutManager(layoutManager);
        etCount = (EditText) getActivity().findViewById(R.id.et_count);
        btnReload = (Button) getActivity().findViewById(R.id.btn_reload);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countValue = etCount.getText().toString();
                if(countValue != null && !countValue.isEmpty() && !countValue.equals("") && Integer.parseInt(countValue) > 0) {
                    httpCall(Integer.parseInt(countValue));
                }
                else {
                    showAlert();
                }
            }
        });
    }

    public void showAlert() {
        int message;
        message = R.string.incorrect_input;
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(R.string.attention)
                .setMessage(message)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
        dialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (jokesAdapter == null) {
                String savedJokes = savedInstanceState.getString("jokes");
                if (savedJokes != null) {
                    Gson gson = new Gson();
                    Type outputJokesList = new TypeToken<List<Value>>() {}.getType();
                    _jokes = gson.fromJson(savedJokes, outputJokesList );
                    if (_jokes != null) {
                        jokesAdapter = new JokesAdapter(_jokes);
                        jokeList = getActivity().findViewById(R.id.rv_numbers);
                        jokeList.setAdapter(jokesAdapter);
                    }
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
        String json = gson.toJson(_jokes);
        outState.putString("jokes", json);
    }

    public void httpCall(Integer jokesCount) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="http://api.icndb.com/jokes/random/" + jokesCount.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String c) {
                        Gson gson = new Gson();
                        JokeResponse jokes = gson.fromJson(c, JokeResponse.class);
                        jokesAdapter = new JokesAdapter(jokes.value);
                        jokeList.setAdapter(jokesAdapter);
                        _jokes = jokes.value;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
}