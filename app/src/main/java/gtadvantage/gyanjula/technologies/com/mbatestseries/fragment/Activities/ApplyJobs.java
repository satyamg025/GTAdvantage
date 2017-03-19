package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import java.util.ArrayList;
import java.util.List;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Adapter.adapter_apply_jobs;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments.Fragment_contact;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Network.NetworkCheck;

public class ApplyJobs extends AppCompatActivity {
    RecyclerView mrecyclerView;
    LinearLayoutManager mlinearLayoutManager;
    TextView welcome;
    Button comp_profile,practice;
    String name2,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_jobs);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("DashBoard");
        Spannable text = new SpannableString(getSupportActionBar().getTitle());
        text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        setTitleColor(getResources().getColor(R.color.white));


        welcome = (TextView) findViewById(R.id.welcome);
        practice=(Button) findViewById(R.id.practice_session);
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



        if (name2.equals("")) {
            practice.setVisibility(View.GONE);
            comp_profile.setVisibility(View.VISIBLE);
            comp_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Registration.class);
                    intent.putExtra("mobile",mobile);
                    intent.putExtra("name",name2);
                    startActivity(intent);
                }
            });
        }
        else {
            practice.setVisibility(View.VISIBLE);
            LinearLayout ll = (LinearLayout) findViewById(R.id.ll_sl);
            ll.setVisibility(View.VISIBLE);
            welcome.setVisibility(View.VISIBLE);
            welcome.setText("Welcome " + name2);

            practice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(),SelectLevel.class);
                    intent.putExtra("name", name2);
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("reg_status",true);
                    startActivity(intent);
                }
            });


            if (NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                fetch_job(getIntent().getExtras().getString("mobile", ""));
            } else {
                Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                        .show();
            }
        }

    }

    public void fetch_job(final String mobile) {
        class JobAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(ApplyJobs.this, "", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String phone=params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mobile",phone));

                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/applyjobavail");
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
                    JSONObject jo = new JSONObject(s);
                    Boolean error = jo.getBoolean("error");
                    if (!error) {

                        JSONArray name=jo.getJSONArray("name");
                        JSONArray job_name=jo.getJSONArray("job_name");
                        JSONArray job_desc=jo.getJSONArray("job_desc");
                        JSONArray comp_desc=jo.getJSONArray("comp_desc");
                        JSONArray salary=jo.getJSONArray("salary");
                        JSONArray exp=jo.getJSONArray("experience");
                        JSONArray event_date=jo.getJSONArray("event_date");
                        JSONArray reg_end_date=jo.getJSONArray("per_req");
                        JSONArray ten=jo.getJSONArray("10th");
                        JSONArray twel=jo.getJSONArray("12th");
                        JSONArray grad=jo.getJSONArray("grad");
                        JSONArray website=jo.getJSONArray("website");
                        JSONArray note=jo.getJSONArray("note");
                        JSONArray ven=jo.getJSONArray("venue");
                        JSONArray event_time=jo.getJSONArray("event_time");
                        JSONArray job_reg=jo.getJSONArray("job_reg");
                        JSONArray cost=jo.getJSONArray("cost");
                        JSONArray cid=jo.getJSONArray("cid");
                        JSONArray eid=jo.getJSONArray("event_id");

                        List<String> na=new ArrayList<String>(),jn=new ArrayList<String>(),jd=new ArrayList<String>(),cd=new ArrayList<String>(),sal=new ArrayList<String>(),ex=new ArrayList<String>(),
                                ev=new ArrayList<String>(),reg=new ArrayList<String>(),tn=new ArrayList<String>(),tw=new ArrayList<String>(),
                                gr=new ArrayList<String>(),web=new ArrayList<String>(),not=new ArrayList<String>(),jr=new ArrayList<String>(),ci=new ArrayList<String>(),venu=new ArrayList<String>(),
                        etime=new ArrayList<String>(),cst=new ArrayList<String>(),event_id=new ArrayList<String>();

                        for(int i=0;i<name.length();i++){
                            na.add(String.valueOf(name.get(i)));
                            jn.add(String.valueOf(job_name.get(i)));
                            jd.add(String.valueOf(job_desc.get(i)));
                            cd.add(String.valueOf(comp_desc.get(i)));
                            sal.add(String.valueOf(salary.get(i)));
                            ex.add(String.valueOf(exp.get(i)));
                            ev.add(String.valueOf(event_date.get(i)));
                            reg.add(String.valueOf(reg_end_date.get(i)));
                            tn.add(String.valueOf(ten.get(i)));
                            tw.add(String.valueOf(twel.get(i)));
                            gr.add(String.valueOf(grad.get(i)));
                            web.add(String.valueOf(website.get(i)));
                            not.add(String.valueOf(note.get(i)));
                            venu.add(String.valueOf(ven.get(i)));
                            etime.add(String.valueOf(event_time.get(i)));
                            cst.add(String.valueOf(cost.get(i)));
                            ci.add(String.valueOf(cid.get(i)));
                            event_id.add(String.valueOf(eid.get(i)));

                        }
                        for(int i=0;i<job_reg.length();i++){
                            jr.add(String.valueOf(job_reg.get(i)));
                        }

                        mrecyclerView = (RecyclerView) findViewById(R.id.recycler_jobs);
                        assert mrecyclerView != null;
                        mrecyclerView.setHasFixedSize(true);
                        mlinearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        mrecyclerView.setLayoutManager(mlinearLayoutManager);
                        RecyclerView.Adapter mAdapter;
                        mAdapter = new adapter_apply_jobs(getIntent().getExtras().getString("name",""),getIntent().getExtras().getString("mobile",""),na,jn,jd,cd,sal,ex,ev,reg,tn,tw,gr,web,not,jr,event_id,venu,etime,cst,getApplicationContext(),getFragmentManager());
                        mrecyclerView.setAdapter(mAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        JobAsync la = new JobAsync();
        la.execute(mobile);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!name2.equals("")) {
            getMenuInflater().inflate(R.menu.list_menu2, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();

        if(id==R.id.analysis){
            Intent intent=new Intent(this,MainActivity.class);
            intent.putExtra("mobile",getIntent().getExtras().getString("mobile"));
            intent.putExtra("name",getIntent().getExtras().getString("name"));
            startActivity(intent);
        }
        else if(id==R.id.exit) {
            if (!getIntent().getExtras().getBoolean("reg_status",false)) {
                new AlertDialog.Builder(this)
                        .setMessage(Html.fromHtml("<font color='#000'>Are you sure you want to logout</font>"))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getApplicationContext(), LaunchActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                finish();
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            } else {
                new AlertDialog.Builder(this)
                        .setMessage(Html.fromHtml("<font color='#000'>Are you sure you want to logout</font>"))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getApplicationContext(), LaunchActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                finish();
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        }
        else if(id==R.id.edit_profile){
            Intent intent = new Intent(getApplicationContext(), Registration.class);
            intent.putExtra("mobile",mobile);
            intent.putExtra("name",name2);
            startActivity(intent);
        }
        else if(id==R.id.contact_us){
            Fragment_contact fragment_contact=Fragment_contact.instance(getIntent().getExtras().getString("mobile"),getIntent().getExtras().getString("name"));
            fragment_contact.show(getFragmentManager(),"Contact Us");
        }
        else if(id==R.id.rate_us){
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store"));
            startActivity(intent);
        }
        else if(id==R.id.share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out this app at: https://play.google.com/store/apps/GTAdvantage");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed(){
        if (!getIntent().getExtras().getBoolean("reg_status",false)) {
            new AlertDialog.Builder(this)
                    .setMessage(Html.fromHtml("<font color='#000'>Are you sure you want to logout</font>"))
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getApplicationContext(), LaunchActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        } else {
            new AlertDialog.Builder(this)
                    .setMessage(Html.fromHtml("<font color='#000'>Are you sure you want to logout</font>"))
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getApplicationContext(), LaunchActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        }
    }


    public void fcm(final String mobile, final String fcm) {
        class fcmAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(ApplyJobs.this, "", "Loading...");
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

            }
        }

        fcmAsync la = new fcmAsync();
        la.execute(mobile,fcm);

    }


}
