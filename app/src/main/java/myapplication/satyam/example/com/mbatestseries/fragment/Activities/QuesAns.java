package myapplication.satyam.example.com.mbatestseries.fragment.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import myapplication.satyam.example.com.mbatestseries.R;

//import android.icu.util.Calendar;


public class QuesAns extends AppCompatActivity {

    List<String> ques = new ArrayList<String>();
    List<String> quesId = new ArrayList<String>();
    List<List<String>> options= new ArrayList<List<String>>();
    String PREF_NAME="paperId";
    RecyclerView mrecyclerView;
    LinearLayoutManager mlinearLayoutManager;
    TextView sec;

    private TextView tvDay, tvHour, tvMinute, tvSecond, tvEvent;
    private LinearLayout linearLayout1, linearLayout2;
    private Handler handler;
    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_ans);
        sec=(TextView)findViewById(R.id.seconds);

        countDownStart();
        String paperId=getPrefs().getString("paperId","");
        ques_ans(paperId);
    }

    public void countDownStart(){
        new CountDownTimer(600000, 1000) {

            public void onTick(long millisUntilFinished) {
                long s=millisUntilFinished/1000;
                String secs=String.valueOf(s/10);
                sec.setText(":"+secs);
            }

            public void onFinish() {
                sec.setText("done!");
            }
        }.start();
    }



    public void ques_ans(final String paperId){
        class QuesAnsAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(QuesAns.this, "Please wait", "Loading...");
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

                // Log.e("hello123",s);

                try
                {
                    JSONObject jo=new JSONObject(s);
                    Boolean error=jo.getBoolean("error");


                    if(!error) {
                        JSONArray qu = jo.getJSONArray("ques");
                        JSONArray qid = jo.getJSONArray("quesId");
                        JSONArray opt = jo.getJSONArray("options");


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

                        mrecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        mrecyclerView.setHasFixedSize(true);
                        mlinearLayoutManager = new LinearLayoutManager(QuesAns.this);
                        mrecyclerView.setLayoutManager(mlinearLayoutManager);
                        RecyclerView.Adapter mAdapter;
                        mAdapter=new myapplication.satyam.example.com.mbatestseries.fragment.Adapter.quesans(ques,quesId,options,QuesAns.this);
                        mrecyclerView.setAdapter(mAdapter);

                    }
                    Log.e("options",options.toString());
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        QuesAnsAsync la = new QuesAnsAsync();
        la.execute(paperId);

    }
    private SharedPreferences getPrefs() {
        return getSharedPreferences(PREF_NAME,MODE_PRIVATE);
    }


}
