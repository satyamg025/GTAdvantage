package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.io.LineNumberReader;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.POJO.analysisPOJO;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Response.analysisResponse;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Util.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    DonutProgress donutProgress1;
    TextView basic, intermediate, advance, mobile;
    String mobileNo;Boolean isHomeLocked=false;
    int progress,percentile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Progress");
        Spannable text = new SpannableString(getSupportActionBar().getTitle());
        text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        setTitleColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        donutProgress1 = (DonutProgress) findViewById(R.id.progress);
        basic = (TextView) findViewById(R.id.basic);
        intermediate = (TextView) findViewById(R.id.intermediate);
        advance = (TextView) findViewById(R.id.advance);
        //name = (TextView) findViewById(R.id.name);
        mobile = (TextView) findViewById(R.id.mobile);
        final Handler handler = new Handler();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        mobileNo=getIntent().getExtras().getString("mobile");
       // name.setText(getIntent().getExtras().getString("name"));
        progressDialog.show();
        analysisResponse response = ServiceGenerator.createService(analysisResponse.class);
        Call<analysisPOJO> call = response.requestResponse(mobileNo);
        call.enqueue(new Callback<analysisPOJO>() {
            @Override
            public void onResponse(Call<analysisPOJO> call, Response<analysisPOJO> response) {
                progressDialog.dismiss();
                analysisPOJO responseBody = response.body();
                try {
                    basic.setText("Basic : " + Integer.toString((int) Math.ceil((responseBody.getBASIC() * 100) / 90)) + "%");
                    intermediate.setText("Intermediate : " + Integer.toString((int) Math.ceil((responseBody.getINTERMEDIATE() * 100) / 90)) + "%");
                    advance.setText("Advanced : " + Integer.toString((int) Math.ceil((responseBody.getADVANCED() * 100) / 90)) + "%");
                    progress = (int) Math.ceil((((float)(responseBody.getBASIC()) + (float)(responseBody.getINTERMEDIATE()) + (float)(responseBody.getADVANCED())) * 100) / 270);
                    percentile = (int) Math.ceil((((float)(responseBody.getBASIC()) + (float)(responseBody.getINTERMEDIATE()) + (float)(responseBody.getADVANCED())) * 100) / 270);
                    Log.e(Integer.toString(percentile),Integer.toString(responseBody.getMAXTOTAL()));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(donutProgress1, "Progress", 0, progress);
                            objectAnimator.setDuration(500);
                            objectAnimator.setInterpolator(new DecelerateInterpolator());
                            objectAnimator.start();


                            int color = 0;
                            if (progress < 60) {
                                if (this != null)
                                    color = getResources().getColor(R.color.red);
                            } else if (progress < 75) {
                                if (this != null)
                                    color = getResources().getColor(R.color.orange);
                            } else {
                                if (this != null)
                                    color = getResources().getColor(R.color.green);
                            }
                            donutProgress1.setFinishedStrokeColor(color);
                            donutProgress1.setTextColor(color);
                            //Toast.makeText(MainActivity.this, String.valueOf(progress),Toast.LENGTH_SHORT).show();

                            donutProgress1.setProgress(progress);
                            donutProgress1.clearAnimation();
                            color=0;
                            if (percentile < 60) {
                                if (this != null)
                                    color = getResources().getColor(R.color.red);
                            } else if (percentile < 75) {
                                if (this != null)
                                    color = getResources().getColor(R.color.orange);
                            } else {
                                if (this != null)
                                    color = getResources().getColor(R.color.green);
                            }


                        }
                    }, 1000);


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<analysisPOJO> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.leaderboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.leader_menu) {
            startActivity(new Intent(this, leaderBoard.class).putExtra("mobile",mobileNo));
        }
        if(id==android.R.id.home)
            onBackPressed();
        return false;
    }

}
