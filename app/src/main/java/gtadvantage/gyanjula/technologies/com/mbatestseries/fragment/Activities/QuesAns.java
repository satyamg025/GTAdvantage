package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments.Fragment_instructions;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Network.NetworkCheck;


public class QuesAns extends AppCompatActivity {

    List<String> ques = new ArrayList<String>();
    List<String> quesId = new ArrayList<String>();
    List<String> op=new ArrayList<String>();
    String answer[],right_ans[];
    List<List<String>> options= new ArrayList<List<String>>();
    String PREF_NAME="paperId";
    TextView sec,question,ques_num,name;
    CountDownTimer timer;
    CardView card;
    int score;
    RadioButton option1,option2,option3,option4;
    RadioGroup rg;
    Button prev,next;
    String paperId,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_ans);


       // MobileAds.initialize(getApplicationContext(), "ca-app-pub-4428461208226724~4013781697");
       // AdView mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);

        /*final Fragment_sponsor fragment_sponsor = new Fragment_sponsor(getIntent().getExtras().getString("sponImg",""));
        fragment_sponsor.show(getSupportFragmentManager(), "Our Sponsors");

        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                fragment_sponsor.dismiss();
            }
        }.start();*/

        card=(CardView)findViewById(R.id.ques_ans);
        question=(TextView)findViewById(R.id.ques);
        option1=(RadioButton)findViewById(R.id.option1);
        option2=(RadioButton)findViewById(R.id.option2);
        option3=(RadioButton)findViewById(R.id.option3);
        option4=(RadioButton)findViewById(R.id.option4);
        rg=(RadioGroup)findViewById(R.id.options);
        prev=(Button)findViewById(R.id.prev);
        next=(Button)findViewById(R.id.next);
        name=(TextView)findViewById(R.id.name);
        ques_num=(TextView)findViewById(R.id.ques_number);

        prev.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        card.setVisibility(View.INVISIBLE);
        sec=(TextView)findViewById(R.id.seconds);


        name.setText(getIntent().getExtras().getString("name",""));

        if(getIntent().getExtras().getString("instruction").equals("0"))
        {
            Fragment_instructions fragment_instructions=Fragment_instructions.instance(getIntent().getExtras().getString("name"),getIntent().getExtras().getString("mobile"),
                    getIntent().getExtras().getString("sponName"),getIntent().getExtras().getString("sponImg"),getIntent().getExtras().getString("sponInfo"),getIntent().getExtras().getString("sponLink"),
                    getIntent().getExtras().getString("paperId"));
            fragment_instructions.show(getFragmentManager(),"Instructions");
        }

        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
            if (getIntent().getExtras().getString("paperId").equals("")) {
                paperId = getPrefs().getString("paperId", "");
                ques_ans(paperId);
            } else {
                paperId = getIntent().getExtras().getString("paperId", "");
                cmp_ques_ans(paperId);
            }
        }

        else{
            Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                    .show();
        }

    }

    public void setView(final int i){

        card.setVisibility(View.VISIBLE);
        rg.clearCheck();
        ques_num.setText(String.valueOf(i+1)+"/"+String.valueOf(ques.size()));
        question.setText(ques.get(i));
        op=options.get(i);
        option1.setText(op.get(0));
        option2.setText(op.get(1));
        option3.setText(op.get(2));
        option4.setText(op.get(3));

        if(answer[i]==option1.getText()){
             option1.setChecked(true);
        }
        else if(answer[i]==option2.getText()){
            option2.setChecked(true);
        }
        else if(answer[i]==option3.getText()){
            option3.setChecked(true);
        }
        else if(answer[i]==option4.getText()){
            option4.setChecked(true);
        }


        if(i==0){
            prev.setVisibility(View.INVISIBLE);
            next.setVisibility(View.VISIBLE);
        }
        else if(i==(ques.size()-1)){
            prev.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            next.setText("Finish");
        }
        else{
            prev.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
        }
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i==(ques.size()-1)){
                    next.setText("Next");
                }
                if(option1.isChecked()){
                    answer[i] = String.valueOf(option1.getText());
                }
                if(option2.isChecked()){
                    answer[i] = String.valueOf(option2.getText());
                }
                if(option3.isChecked()){
                    answer[i] = String.valueOf(option3.getText());
                }
                if(option4.isChecked()){
                    answer[i] = String.valueOf(option4.getText());
                }
                setView(i-1);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(option1.isChecked()){
                    answer[i] = String.valueOf(option1.getText());
                }
                if(option2.isChecked()){
                    answer[i] = String.valueOf(option2.getText());
                }
                if(option3.isChecked()){
                    answer[i] = String.valueOf(option3.getText());
                }
                if(option4.isChecked()){
                    answer[i] = String.valueOf(option4.getText());
                }
                if(i==(ques.size()-1)){

                    if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                        Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                        int sc = calculate_score();
                        intent.putExtra("score", sc);
                        intent.putExtra("total_ques", ques.size());
                        intent.putExtra("acknow", "Thank You...");
                        timer.cancel();
                        timer = null;
                        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        Log.e("Img2", getIntent().getExtras().getString("sponInfo", ""));
                        intent.putExtra("mobile", getIntent().getExtras().getString("mobile", ""));
                        intent.putExtra("sponName", getIntent().getExtras().getString("sponName", ""));
                        intent.putExtra("sponInfo", getIntent().getExtras().getString("sponInfo", ""));
                        intent.putExtra("sponImg", getIntent().getExtras().getString("sponImg", ""));
                        intent.putExtra("sponLink", getIntent().getExtras().getString("sponLink", ""));
                        intent.putStringArrayListExtra("ques", (ArrayList<String>) ques);
                        intent.putExtra("correct_ans", right_ans);
                        intent.putExtra("user_ans", answer);
                        intent.putExtra("paperId",paperId);
                        intent.putExtra("level_pr",getIntent().getExtras().getString("level_pr",""));
                        startActivity(intent);
                        finish();
                    }
                    else{
                        card.setVisibility(View.GONE);
                        Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                                .show();

                    }
                }
                else {
                    setView(i + 1);
                }


            }
        });

    }


    public int calculate_score(){
        score=0;
        for(int i=0;i<right_ans.length;i++){
            if(answer[i].equals( right_ans[i])){
                score++;
            }
        }

        return score;
    }

    public void countDownStart(){
        timer=new CountDownTimer(60000*Integer.valueOf(time), 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                long s=millisUntilFinished/1000;
                sec.setText("Time remaining: "+String.valueOf(s/60)+":"+String.valueOf(s%60));
            }

            public void onFinish() {

                if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                    int sc = calculate_score();
                    intent.putExtra("score", sc);
                    intent.putExtra("total_ques", ques.size());
                    intent.putExtra("acknow", "Time Up...");
                    intent.putExtra("mobile", getIntent().getExtras().getString("mobile", ""));
                    intent.putExtra("sponName", getIntent().getExtras().getString("sponName", ""));
                    intent.putExtra("sponInfo", getIntent().getExtras().getString("sponInfo", ""));
                    intent.putExtra("sponImg", getIntent().getExtras().getString("sponImg", ""));
                    intent.putExtra("sponLink", getIntent().getExtras().getString("sponLink", ""));
                    intent.putStringArrayListExtra("ques", (ArrayList<String>) ques);
                    intent.putExtra("correct_ans", right_ans);
                    intent.putExtra("user_ans", answer);
                    intent.putExtra("paperId",paperId);
                    intent.putExtra("level_pr",getIntent().getExtras().getString("level_pr","_"));
                    timer = null;
                    startActivity(intent);
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                            .show();

                }

            }
        }.start();
    }



    public void ques_ans(final String paperId){
        class QuesAnsAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(QuesAns.this, "", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String pid = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("paperId", pid));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/ques");
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

                try
                {
                    JSONObject jo=new JSONObject(s);
                    Boolean error=jo.getBoolean("error");


                    if(!error) {
                        JSONArray qu = jo.getJSONArray("ques");
                        JSONArray qid = jo.getJSONArray("quesId");
                        JSONArray opt = jo.getJSONArray("options");
                        JSONArray an = jo.getJSONArray("ans");
                        time=jo.getString("time");
                        Log.e("answer",String.valueOf(an));

                        right_ans=new String[an.length()];
                        for (int i = 0; i < qu.length(); i++) {
                            ques.add(String.valueOf(qu.get(i)));
                        }
                        for (int i = 0; i < qid.length(); i++) {
                            quesId.add(String.valueOf(qid.get(i)));
                        }
                        for(int i=0;i<opt.length();i++){
                            JSONArray op=opt.getJSONArray(i);
                            List<String> ot=new ArrayList<String>();
                            for(int j=0;j<op.length();j++){
                                ot.add(String.valueOf(op.get(j)));
                            }
                            options.add(i,ot);
                        }
                        for(int i=0;i<an.length();i++){
                            right_ans[i]=(String.valueOf(an.get(i)));
                        }
                        answer=new String[ques.size()];
                        for(int i=0;i<ques.size();i++){
                            answer[i]="----";
                        }
                        countDownStart();
                        setView(0);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        QuesAnsAsync la = new QuesAnsAsync();
        la.execute(paperId);

    }



    public void cmp_ques_ans(final String paperId){
        class QuesAnsAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(QuesAns.this, "", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String pid = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("cid", pid));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/comp_ques");
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

                try
                {
                    JSONObject jo=new JSONObject(s);
                    Boolean error=jo.getBoolean("error");


                    if(!error) {
                        JSONArray qu = jo.getJSONArray("ques");
                        JSONArray qid = jo.getJSONArray("quesId");
                        JSONArray opt = jo.getJSONArray("options");
                        JSONArray an = jo.getJSONArray("ans");
                        time=jo.getString("time");
                        Log.e("answer",String.valueOf(an));

                        right_ans=new String[an.length()];
                        for (int i = 0; i < qu.length(); i++) {
                            ques.add(String.valueOf(qu.get(i)));
                        }
                        for (int i = 0; i < qid.length(); i++) {
                            quesId.add(String.valueOf(qid.get(i)));
                        }
                        for(int i=0;i<opt.length();i++){
                            JSONArray op=opt.getJSONArray(i);
                            List<String> ot=new ArrayList<String>();
                            for(int j=0;j<op.length();j++){
                                ot.add(String.valueOf(op.get(j)));
                            }
                            options.add(i,ot);
                        }
                        for(int i=0;i<an.length();i++){
                            right_ans[i]=(String.valueOf(an.get(i)));
                        }

                        answer=new String[ques.size()];
                        for(int i=0;i<ques.size();i++){
                            answer[i]="----";
                        }
                        countDownStart();
                        setView(0);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        QuesAnsAsync la = new QuesAnsAsync();
        la.execute(paperId);

    }


    @Override
    public void onBackPressed() {
        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                            int sc = calculate_score();
                            intent.putExtra("score", sc);
                            intent.putExtra("total_ques", ques.size());
                            intent.putExtra("acknow", "Thank You...");
                            intent.putExtra("mobile", getIntent().getExtras().getString("mobile", ""));
                            intent.putExtra("sponName", getIntent().getExtras().getString("sponName", ""));
                            intent.putExtra("sponInfo", getIntent().getExtras().getString("sponInfo", ""));
                            intent.putExtra("sponImg", getIntent().getExtras().getString("sponImg", ""));
                            intent.putExtra("sponLink", getIntent().getExtras().getString("sponLink", ""));
                            intent.putStringArrayListExtra("ques", (ArrayList<String>) ques);
                            intent.putExtra("correct_ans", right_ans);
                            intent.putExtra("user_ans", answer);
                            intent.putExtra("paperId",paperId);
                            intent.putExtra("level_pr",getIntent().getExtras().getString("level_pr","_"));
                            timer.cancel();
                            timer = null;
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else{
            super.onBackPressed();
        }


    }


    private SharedPreferences getPrefs() {
        return getSharedPreferences(PREF_NAME,MODE_PRIVATE);
    }

}
