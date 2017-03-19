package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Adapter.adapter_paper;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Network.NetworkCheck;

public class SelectLevel extends AppCompatActivity {
    Button basic,intermediate,advanced;
    Button comp_profile;
    SharedPreferences sharedPreferences,sp1,sp2;
    String level="level",PREF_NAME,mb="mobile_no";
    String level_pr="level_pr",name2=null,mobile=null;
    TextView welcome;
    List<String> ci=new ArrayList<String>(),comp_name=new ArrayList<String>(),date=new ArrayList<String>(),ci2=new ArrayList<String>();
    RecyclerView mrecyclerView;
    LinearLayoutManager mlinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Practice Session");
        Spannable text = new SpannableString(getSupportActionBar().getTitle());
        text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        setTitleColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        basic = (Button) findViewById(R.id.btn_basic);
        intermediate = (Button) findViewById(R.id.btn_intermediate);
        advanced = (Button) findViewById(R.id.btn_advanced);
        welcome = (TextView) findViewById(R.id.welcome);
        comp_profile = (Button) findViewById(R.id.comp_profile);
        name2 = getIntent().getExtras().getString("name", "");
        mobile = getIntent().getExtras().getString("mobile", "");


        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
           fcm(mobile, FirebaseInstanceId.getInstance().getToken());
        }
        else{
            Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                    .show();
        }

        // if(Objects.equals(name, "")){

        // }


            sharedPreferences = getSharedPreferences(level, MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();


            basic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Sublevels.class);
                    editor.putString(level, "1");
                    editor.commit();
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("name",name2);
                    startActivity(intent);
                }
            });
            intermediate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Sublevels.class);
                    editor.putString(level, "2");
                    editor.commit();
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("name",name2);
                    startActivity(intent);
                }
            });
            advanced.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Sublevels.class);
                    editor.putString(level, "3");
                    editor.commit();
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("name",name2);
                    startActivity(intent);
                }
            });
        }



    public void fcm(final String mobile, final String fcm) {
        class fcmAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(SelectLevel.this, "", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String mob = params[0];
                String fc = params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mobile", mob));
                nameValuePairs.add(new BasicNameValuePair("fcm", fc));

                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/fcm");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }


            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                loadingDialog.dismiss();
                Log.e("login", s);

                try {
                    JSONObject jo=new JSONObject(s);
                    Boolean error=jo.getBoolean("error");
                    if(!error){
                        JSONArray cid=jo.getJSONArray("cid");
                        JSONArray name=jo.getJSONArray("name");
                        JSONArray event_date=jo.getJSONArray("event_date");
                        JSONArray cid2=jo.getJSONArray("cid2");

                        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                        for(int i=0;i<cid.length();i++) {
                            String dat = event_date.getString(i);
                            Date d = sdf.parse(dat);
                            Date d2 = new Date();
                            String dat2=sdf.format(d2);
                           // Date d3=sdf.parse(dat2);
                            //Toast.makeText(getApplicationContext(),dat2,Toast.LENGTH_SHORT).show();

                            Date d3=sdf.parse(dat2);
                            Log.e("Date", String.valueOf(d) + String.valueOf(d2));
                            if (d.after(d3)) {
                                Log.e("Date", String.valueOf(d) + String.valueOf(d2));
                                date.add(event_date.getString(i));
                                ci.add(cid.getString(i));
                                ci2.add(cid2.getString(i));
                                comp_name.add(name.getString(i));

                            } else if (d.equals(d3)) {

                                date.add(event_date.getString(i));
                                ci.add(cid.getString(i));
                                ci2.add(cid2.getString(i));
                                comp_name.add(name.getString(i));
                            }
                        }
                            Log.e("details5",ci2.toString());

                            mrecyclerView = (RecyclerView) findViewById(R.id.recycler_job_paper);
                            assert mrecyclerView != null;
                            mrecyclerView.setHasFixedSize(true);
                            mlinearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            mrecyclerView.setLayoutManager(mlinearLayoutManager);
                            RecyclerView.Adapter mAdapter;
                            mAdapter = new adapter_paper(ci2,ci,comp_name,date,mobile,name2,getApplicationContext(),getFragmentManager());
                            mrecyclerView.setAdapter(mAdapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        }

        fcmAsync la = new fcmAsync();
        la.execute(mobile,fcm);

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
