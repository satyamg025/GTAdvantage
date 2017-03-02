package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities.LaunchActivity;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    protected boolean _active = true;
    protected int _splashTime = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {

                    startActivity(new Intent(Splash.this,
                            LaunchActivity.class));
                    finish();
                }
            };
        };
        splashTread.start();
    }
}
