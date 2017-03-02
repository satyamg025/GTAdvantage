package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities;

//import android.app.Fragment;
//import android.app.FragmentManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments.Fragment_sponsor;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Network.NetworkCheck;

public class Sublevels extends AppCompatActivity {
    FrameLayout fl;
    String PREF_NAME="level";
    String level,lev;
    String level_pr="level_pr";
    CardView quant,lr,verbal;
    TextView qp1,qp2,level_name;
    LinearLayout ll_quant,ll_lr,ll_verbal;
    LinearLayout quant_qp1,quant_qp2,quant_qp3;
    LinearLayout lr_qp1,lr_qp2,lr_qp3;
    LinearLayout verbal_qp1,verbal_qp2,verbal_qp3;
    SharedPreferences sharedPreferences,sharedPreferences2;
    String paperId="paperId";
    String sponsorName,sponsorInfo,sponsorLink,sponsorImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sublevels);

        quant=(CardView)findViewById(R.id.quant);
        lr=(CardView)findViewById(R.id.lr);
        verbal=(CardView)findViewById(R.id.verbal);
        ll_quant=(LinearLayout)findViewById(R.id.ll_quant);
        ll_lr=(LinearLayout)findViewById(R.id.ll_lr);
        ll_verbal=(LinearLayout)findViewById(R.id.ll_verbal);
        quant_qp1=(LinearLayout)findViewById(R.id.quant_QP1);
        quant_qp2=(LinearLayout)findViewById(R.id.quant_QP2);
        quant_qp3=(LinearLayout)findViewById(R.id.quant_QP3);
        lr_qp1=(LinearLayout)findViewById(R.id.lr_QP1);
        lr_qp2=(LinearLayout)findViewById(R.id.lr_QP2);
        lr_qp3=(LinearLayout)findViewById(R.id.lr_QP3);
        verbal_qp1=(LinearLayout)findViewById(R.id.verbal_QP1);
        verbal_qp2=(LinearLayout)findViewById(R.id.verbal_QP2);
        verbal_qp3=(LinearLayout)findViewById(R.id.verbal_QP3);
        level_name=(TextView)findViewById(R.id.level_name);
        sharedPreferences=getSharedPreferences(paperId,MODE_PRIVATE);
        sharedPreferences2=getSharedPreferences(level_pr,MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        final SharedPreferences.Editor editor2=sharedPreferences2.edit();

        if(getPrefs().getString(PREF_NAME, "").equals("1")){
            level="BASIC";
            lev="Basic";
            level_name.setText("Basic");
        }
        else if(getPrefs().getString(PREF_NAME, "").equals( "2")){
            level="INTERMEDIATE";
            lev="Intermediate";
            level_name.setText("INTERMEDIATE");
        }
        else if(getPrefs().getString(PREF_NAME, "").equals( "3")){
            level="ADVANCED";
            lev="Advanced";
            level_name.setText("ADVANCED");
        }

        quant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ll_lr.getVisibility()==View.VISIBLE){
                    ll_lr.setVisibility(View.GONE);
                }
                if(ll_verbal.getVisibility()==View.VISIBLE){
                    ll_verbal.setVisibility(View.GONE);
                }

                if(ll_quant.getVisibility()==View.VISIBLE){
                    ll_quant.setVisibility(View.GONE);
                }
                else {
                    ll_quant.setVisibility(View.VISIBLE);
                }
                quant_qp1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putString(paperId,level+"_L1QP1");
                        editor2.putString(level_pr,(lev+" Quantitative Challenge 1"));
                        editor.commit();
                        editor2.commit();
                        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                            get_sponsor(level + "_L1QP1");
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                                    .show();
                        }


                    }
                });

                quant_qp2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putString(paperId,level+"_L1QP2");
                        editor2.putString(level_pr,(lev+" Quantitative Challenge 2"));
                        editor.commit();
                        editor2.commit();
                        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                            get_sponsor(level + "_L1QP2");
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                                    .show();
                        }


                    }
                });

                quant_qp3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putString(paperId,level+"_L1QP3");
                        editor2.putString(level_pr,(lev+" Quantitative Challenge 3"));
                        editor.commit();
                        editor2.commit();
                        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                            get_sponsor(level + "_L1QP3");
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
            }
        });



        lr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ll_quant.getVisibility()==View.VISIBLE){
                    ll_quant.setVisibility(View.GONE);
                }
                if(ll_verbal.getVisibility()==View.VISIBLE){
                    ll_verbal.setVisibility(View.GONE);
                }

                if(ll_lr.getVisibility()==View.VISIBLE){
                    ll_lr.setVisibility(View.GONE);
                }
                else {
                    ll_lr.setVisibility(View.VISIBLE);
                }

                lr_qp1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putString(paperId,level+"_L2QP1");
                        editor2.putString(level_pr,(lev+" LR/DI Challenge 1"));
                        editor.commit();
                        editor2.commit();
                        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                            get_sponsor(level + "_L2QP1");
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

                lr_qp2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putString(paperId,level+"_L2QP2");
                        editor2.putString(level_pr,(lev+" LR/DI Challenge 2"));
                        editor.commit();
                        editor2.commit();
                        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                            get_sponsor(level + "_L2QP2");
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

                lr_qp3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putString(paperId,level+"_L2QP3");
                        editor2.putString(level_pr,(lev+" LR/DI Challenge 3"));
                        editor.commit();
                        editor2.commit();
                        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                            get_sponsor(level + "_L2QP3");
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
            }
        });

        verbal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ll_lr.getVisibility()==View.VISIBLE){
                    ll_lr.setVisibility(View.GONE);
                }
                if(ll_quant.getVisibility()==View.VISIBLE){
                    ll_quant.setVisibility(View.GONE);
                }

                if(ll_verbal.getVisibility()==View.VISIBLE){
                    ll_verbal.setVisibility(View.GONE);
                }
                else {
                    ll_verbal.setVisibility(View.VISIBLE);
                }

                verbal_qp1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putString(paperId,level+"_L3QP1");
                        editor2.putString(level_pr,(lev+" Verbal Challenge 1"));
                        editor.commit();
                        editor2.commit();
                        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                            get_sponsor(level + "_L3QP1");
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

                verbal_qp2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putString(paperId,level+"_L3QP2");
                        editor2.putString(level_pr,(lev+" Verbal Challenge 2"));
                        editor.commit();
                        editor2.commit();
                        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                            get_sponsor(level + "_L3QP2");
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

                verbal_qp3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putString(paperId,level+"_L3QP3");
                        editor2.putString(level_pr,(lev+" Verbal Challenge 3"));
                        editor.commit();
                        editor2.commit();
                        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                            get_sponsor(level + "_L3QP3");
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
            }
        });

    }

    public void get_sponsor(final String paperId){
        class SponsorAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loadingDialog = ProgressDialog.show(Sublevels.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String  pid=params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("paperId",pid ));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/getSponsor");
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

                Log.e("details",s);
               // loadingDialog.dismiss();
                try {
                    JSONObject jo=new JSONObject(s);

                    if(!jo.getBoolean("error")){
                        sponsorName=jo.getString("name");
                        sponsorImg=jo.getString("img");
                        sponsorInfo=jo.getString("info");
                        sponsorLink=jo.getString("link");
                    }
                    else {
                        sponsorName="";
                        sponsorImg="";
                        sponsorInfo="";
                        sponsorLink="";
                    }
                    final Fragment_sponsor fragment_sponsor = new Fragment_sponsor(sponsorImg);
                    fragment_sponsor.show(getSupportFragmentManager(), "Our Sponsors");

                    new CountDownTimer(5000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            fragment_sponsor.dismiss();
                            Intent intent=new Intent(getApplicationContext(),QuesAns.class);
                            intent.putExtra("mobile",getIntent().getExtras().getString("mobile",""));
                            intent.putExtra("name",getIntent().getExtras().getString("name",""));
                            intent.putExtra("sponName",sponsorName);
                            intent.putExtra("sponImg",sponsorImg);
                            intent.putExtra("sponInfo",sponsorInfo);
                            intent.putExtra("sponLink",sponsorLink);
                            intent.putExtra("paperId","");
                            intent.putExtra("level_pr","");
                            intent.putExtra("instruction","0");
                            intent.putExtra("level_pr","------");
                            startActivity(intent);

                        }
                    }.start();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        SponsorAsync la = new SponsorAsync();
        la.execute(paperId);

    }


    private SharedPreferences getPrefs() {
        return getSharedPreferences(PREF_NAME,MODE_PRIVATE);
    }
}
