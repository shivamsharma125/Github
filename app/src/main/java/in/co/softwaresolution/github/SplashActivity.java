package in.co.softwaresolution.github;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import steelkiwi.com.library.DotsLoaderView;

public class SplashActivity extends AppCompatActivity {

    public static final int SPLASH_TIME_OUT=7000;
    DotsLoaderView dotsLoaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dotsLoaderView=(DotsLoaderView)findViewById(R.id.dotsLoader);

        downloadDemo();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,StartingActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);


    }

    private void downloadDemo() {

        AsyncTask<String,String,String> demoAsync = new AsyncTask<String, String, String>() {

            @Override
            protected void onPreExecute() {
                dotsLoaderView.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "done";
            }

            @Override
            protected void onPostExecute(String s) {
                if(s.equals("done"))
                    dotsLoaderView.hide();
            }
        };
        demoAsync.execute();
    }
}
