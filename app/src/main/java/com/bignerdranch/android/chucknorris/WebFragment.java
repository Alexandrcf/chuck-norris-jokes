package com.bignerdranch.android.chucknorris;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebFragment extends Fragment {

    private WebView webView;
    private Bundle webViewState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewState = savedInstanceState;
    }

    @Override
    public void onResume() {
        super.onResume();

        webView = (WebView) getActivity().findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if(webViewState==null){
            webView.loadUrl("http://www.icndb.com/api/");
        }else{
            webView.restoreState(webViewState);    // Restore the state
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        webView.saveState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_web, container, false);
    }

}
