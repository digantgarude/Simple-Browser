package com.garude.digant.browser;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText urlText;
    Button goBtn;
    WebView webViewObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlText = findViewById(R.id.urlText);
        goBtn = findViewById(R.id.goBtn);
        webViewObj = findViewById(R.id.webViewContainer);
        goBtn.performClick();

        // On ENTER key press in Edit text;
        urlText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if((keyEvent!=null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER))|| (i == EditorInfo.IME_ACTION_DONE)){
                    // If ENTER is pressed, perform search (Press goBtn);
                    goBtn.performClick();
                }
                return false;
            }
        });
    }



    public void searchFunction(View view){
        if(isNetworkAvailable()==true){
            try{
                String url = urlText.getText().toString();
                if(url.isEmpty()==true){
                    url = "https://www.google.com/ncr";
                }
                // Find if url contains www.
                if (url.contains("www.")==false){
                    url = "www."+url;
                }
                // Find if url contains http:// or https://
                if(url.contains("http://")==false && url.contains("https://")==false){
                    url = "http://"+url;
                }

                webViewObj.setWebViewClient(new WebViewClient());

                // Get web view settings (Browser settings);
                WebSettings webSettings = webViewObj.getSettings();

                //To enable Javascript in browser;
                webSettings.setJavaScriptEnabled(true);

                //To enable zoom;
                webSettings.setBuiltInZoomControls(true);
                webSettings.supportZoom();

                //Enable app cache;
                webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                webSettings.setAppCacheEnabled(true);
                webSettings.setDomStorageEnabled(true);
                webSettings.setSaveFormData(true);

                // Load the url in webview;
                webViewObj.loadUrl(url);
                Toast.makeText(this, "Loading Please wait...", Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "Check Your Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed(){
        if (webViewObj.canGoBack()){
            // Go back;
            webViewObj.goBack();
        }else{
            // Exit app;
            super.onBackPressed();
        }
    }

    private boolean isNetworkAvailable(){
        // Make connection manager object;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get network info object;
        NetworkInfo activeNetworksInfo = cm.getActiveNetworkInfo();
        return activeNetworksInfo != null && activeNetworksInfo.isConnected();
    }
}
