package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;


public class FinishActivity extends AppCompatActivity {

    String PREF_NAME="level_pr";
    private DonutProgress donutProgress;
    ImageView sponsorImg;
    
    TextView sponInfo,sponLink;
    TextView correct_ans,wrong_ans,acknow,level;
    Button correctans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Result");
        Spannable text = new SpannableString(getSupportActionBar().getTitle());
        text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        setTitleColor(getResources().getColor(R.color.white));
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        correct_ans=(TextView)findViewById(R.id.correct_ans);
        wrong_ans=(TextView)findViewById(R.id.wrong_ans);
        
        level=(TextView)findViewById(R.id.level);
        
        acknow=(TextView)findViewById(R.id.acknow);
        sponsorImg=(ImageView)findViewById(R.id.sponsorImg);
        sponInfo=(TextView)findViewById(R.id.sponsorInfo);
        sponLink=(TextView)findViewById(R.id.sponsorLink);
        donutProgress=(DonutProgress)findViewById(R.id.Progress);
        
        correctans=(Button)findViewById(R.id.rightAns);

        if(getIntent().getExtras().getString("level_pr").equals("_")){
            correct_ans.setVisibility(View.GONE);
        }

        correctans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),CorrectAns.class);
                intent.putExtra("ques",getIntent().getExtras().getStringArrayList("ques"));
                intent.putExtra("correct_ans",getIntent().getExtras().getStringArray("correct_ans"));
                intent.putExtra("user_ans",getIntent().getExtras().getStringArray("user_ans"));
                startActivity(intent);
            }
        });

        Intent intent=getIntent();
        
        int score=intent.getIntExtra("score",0);
        int total_ques=intent.getIntExtra("total_ques",0);
        acknow.setText(intent.getStringExtra("acknow"));
        
        donutProgress.setProgress((score*100)/total_ques);
        if(getIntent().getExtras().getString("level_pr").equals("_")){
            level.setVisibility(View.GONE);
        }
        else {
            level.setText(getPrefs().getString("Level: " + PREF_NAME, ""));
        }


        Log.e("Img3",getIntent().getExtras().getString("sponInfo",""));
        sponInfo.setText(getIntent().getExtras().getString("sponInfo",""));
        sponLink.setText(getIntent().getExtras().getString("sponLink",""));
        Picasso
                .with(this)
                .load("http://gyanjulatechnologies.com/"+getIntent().getExtras().getString("sponImg",""))
                .into(sponsorImg);


        if(score<10){
            correct_ans.setText("0"+String.valueOf(score));
        }
        else{
            correct_ans.setText(String.valueOf(score));
        }
        

        if(total_ques-score<10){
            
            wrong_ans.setText("0"+String.valueOf(total_ques-score));
        }
        else{
            wrong_ans.setText(String.valueOf(total_ques-score));
        }
        

       String paper=getIntent().getExtras().getString("paperId","");
        register_score(getIntent().getExtras().getString("mobile",""),String.valueOf(score),String.valueOf(total_ques),paper);

    }

    public void register_score(final String mobile,final String score,final String max_score,final String paperId){
        class RegisterScoreAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(FinishActivity.this, "", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String mobile = params[0];
                
                String score=params[1];
                String max_score=params[2];
                
                String paperId=params[3];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                
                   nameValuePairs.add(new BasicNameValuePair("mobile_no", mobile));
                   nameValuePairs.add(new BasicNameValuePair("score", score));
                nameValuePairs.add(new BasicNameValuePair("max_score", max_score));
                
                nameValuePairs.add(new BasicNameValuePair("paperId", paperId));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                        
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/score");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }


            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();

                try {
                    JSONObject jo=new JSONObject(s);
                   if(jo.getBoolean("error")){
                       Toast.makeText(getApplicationContext(),"Failed to connect",Toast.LENGTH_LONG).show();
                   }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        RegisterScoreAsync la = new RegisterScoreAsync();
        la.execute(mobile,score,max_score,paperId);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    private SharedPreferences getPrefs() {
        return getSharedPreferences(PREF_NAME,MODE_PRIVATE);
    }
}
