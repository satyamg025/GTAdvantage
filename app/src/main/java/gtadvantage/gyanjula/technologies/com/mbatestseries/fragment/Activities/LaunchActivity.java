package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Adapter.adapter_job;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments.Fragment_forgot_pwd;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments.Fragment_otp;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments.Fragment_update;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Network.NetworkCheck;


public class LaunchActivity extends AppCompatActivity {

    Button login;
    String PREF_NAME, mobile, name;
    TextView register_here,forgot;
    EditText username, password;
    Boolean doubleBackToExitPressedOnce;
    SharedPreferences sharedPreferences, sharedPreferences2;
    RecyclerView mrecyclerView;
    Boolean update=false;
    LinearLayoutManager mlinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
            fetch_job();
        }
        else{
            Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                    .show();
        }

        login = (Button) findViewById(R.id.login);
        register_here = (TextView) findViewById(R.id.register_here);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        forgot=(TextView)findViewById(R.id.forgot);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_forgot_pwd fragment_forgot_pwd = new Fragment_forgot_pwd();
                fragment_forgot_pwd.show(getFragmentManager(),"Forgot Password");
            }
        });

        sharedPreferences2=getSharedPreferences("reg_status", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor1=sharedPreferences2.edit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {

                    if(update){
                        Fragment_update fragment_update = new Fragment_update();
                        fragment_update.show(getFragmentManager(),"Update App");
                    }
                    else {
                        login(LaunchActivity.this, user, pass);
                    }

                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                            .show();
                }

            }
        });

        register_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_otp fragment_otp = new Fragment_otp();
                fragment_otp.show(getSupportFragmentManager(), "Password Dialog");

            }
        });

    }

    public void login(final Context context, final String user, final String pwd) {
        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(LaunchActivity.this, "", "Authenticating...");
            }

            @Override
            protected String doInBackground(String... params) {
                String user = params[1];
                String pass = params[2];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email", user));
                nameValuePairs.add(new BasicNameValuePair("password", pass));

                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/login");
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
                if(result!=null) {
                String s = result.trim();

                    loadingDialog.dismiss();
                    Log.e("login", s);

                    try {
                        JSONObject jo = new JSONObject(s);
                        Boolean error = jo.getBoolean("error");
                        if (error) {
                            Snackbar.make(findViewById(android.R.id.content), "Invalid Username or Password", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(getResources().getColor(R.color.colorAccent))
                                    .show();
                        } else {
                            name = jo.getString("name");
                            mobile = jo.getString("mobile");

                            Intent intent = new Intent(getApplicationContext(), ApplyJobs.class);
                            intent.putExtra("name", name);
                            intent.putExtra("mobile", mobile);
                            intent.putExtra("reg_status", true);
                            startActivity(intent);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "Unable to connect. Try again later...", Snackbar.LENGTH_LONG)
                            .show();
                }

            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(String.valueOf(context), user, pwd);

    }

    public void fetch_job() {
        class JobAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(LaunchActivity.this, "", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {


                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/jobavail");
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
                if(result!=null) {
                String s = result.trim();

                    loadingDialog.dismiss();
                    Log.e("login", s);

                    try {
                        JSONObject jo = new JSONObject(s);
                        Boolean error = jo.getBoolean("error");
                        if (!error) {
                            update=jo.getBoolean("update");
                            if(update){
                                Fragment_update fragment_update = new Fragment_update();
                                fragment_update.show(getFragmentManager(),"Update App");
                            }
                            JSONArray name = jo.getJSONArray("name");
                            JSONArray job_name = jo.getJSONArray("job_name");
                            JSONArray job_desc = jo.getJSONArray("job_desc");
                            JSONArray comp_desc = jo.getJSONArray("comp_desc");
                            JSONArray salary = jo.getJSONArray("salary");
                            JSONArray exp = jo.getJSONArray("experience");
                            JSONArray event_date = jo.getJSONArray("event_date");
                            JSONArray reg_end_date = jo.getJSONArray("per_req");
                            JSONArray venue = jo.getJSONArray("venue");
                            JSONArray event_time = jo.getJSONArray("event_time");
                            JSONArray ten = jo.getJSONArray("10th");
                            JSONArray twel = jo.getJSONArray("12th");
                            JSONArray grad = jo.getJSONArray("grad");
                            JSONArray website = jo.getJSONArray("website");
                            JSONArray note = jo.getJSONArray("note");
                            JSONArray city=jo.getJSONArray("city");


                            List<String> city2=new ArrayList<String>(),na = new ArrayList<String>(), jn = new ArrayList<String>(), jd = new ArrayList<String>(), cd = new ArrayList<String>(), sal = new ArrayList<String>(), ex = new ArrayList<String>(),
                                    ev = new ArrayList<String>(), reg = new ArrayList<String>(), tn = new ArrayList<String>(), tw = new ArrayList<String>(),
                                    gr = new ArrayList<String>(), web = new ArrayList<String>(), not = new ArrayList<String>(), ve = new ArrayList<String>(), etim = new ArrayList<String>();

                            for (int i = 0; i < name.length(); i++) {
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
                                ve.add(String.valueOf(venue.get(i)));
                                etim.add(String.valueOf(event_time.get(i)));
                                not.add(String.valueOf(note.get(i)));
                                city2.add(city.getString(i));
                            }

                            mrecyclerView = (RecyclerView) findViewById(R.id.recycler_launch);
                            assert mrecyclerView != null;
                            mrecyclerView.setHasFixedSize(true);
                            mlinearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            mrecyclerView.setLayoutManager(mlinearLayoutManager);
                            RecyclerView.Adapter mAdapter;
                            mAdapter = new adapter_job(city2,na, jn, jd, cd, sal, ex, ev, reg, tn, tw, gr, web, not, ve, etim, getApplicationContext(), getFragmentManager());
                            mrecyclerView.setAdapter(mAdapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "Unable to connect. Try again later...", Snackbar.LENGTH_LONG)
                            .show();
                }

            }
        }

        JobAsync la = new JobAsync();
        la.execute();

    }


}
