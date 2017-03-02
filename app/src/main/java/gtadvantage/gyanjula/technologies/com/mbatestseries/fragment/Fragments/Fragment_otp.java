package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities.ApplyJobs;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities.LaunchActivity;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities.SelectLevel;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Network.NetworkCheck;

/**
 * Created by satyam on 1/4/17.
 */
public class Fragment_otp extends DialogFragment {
    
    View view;
    Button register;
   // EditText mobile_no,otp;
    EditText user,mobile_no,password,confirm_password,otp;
    String otp_no,mobile,otp2,username,pwd,otp1;
    TextView resend_otp;
    SharedPreferences sharedPreferences,sharedPreferences2;

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater=getActivity().getLayoutInflater();
        view=inflater.inflate(R.layout.dialog_otp,null);
        builder.setView(view);

        user=(EditText)view.findViewById(R.id.username);
        mobile_no=(EditText)view.findViewById(R.id.mobile_number);
        password=(EditText)view.findViewById(R.id.password);
        confirm_password=(EditText)view.findViewById(R.id.confirm_password);
        register=(Button)view.findViewById(R.id.register);
        otp=(EditText)view.findViewById(R.id.send_otp);
        resend_otp=(TextView)view.findViewById(R.id.resend_otp);

        sharedPreferences=getContext().getSharedPreferences("mobile_no", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        sharedPreferences2=getContext().getSharedPreferences("reg_status", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor1=sharedPreferences.edit();



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile=mobile_no.getText().toString();
                username=user.getText().toString();
                String con_pwd=confirm_password.getText().toString();
                pwd=password.getText().toString();

                if (NetworkCheck.isNetworkAvailable(getContext())) {
                    if (mobile.equals("") || username.equals("") || con_pwd.equals("") || pwd.equals("")) {
                        Toast.makeText(getContext(), "Please fill all entries", Toast.LENGTH_LONG).show();
                    }
                    else if(mobile.length()<10){
                        Toast.makeText(getContext(),"Invalid Mobile no",Toast.LENGTH_LONG).show();
                    }
                    else if(pwd.length()<6){
                        Toast.makeText(getContext(),"Password must be at least 6 characters long",Toast.LENGTH_LONG).show();
                    }
                    else if(!pwd.equals(con_pwd)){
                        Toast.makeText(getContext(),"Passwords not matching",Toast.LENGTH_LONG).show();
                    }
                    else{
                        getotp(FirebaseInstanceId.getInstance().getToken());
                        register.setText("Verify Otp");

                        otp.setVisibility(View.VISIBLE);
                        resend_otp.setVisibility(View.VISIBLE);
                        final String otp1=otp.getText().toString();
                        editor.putString("mobile_no",mobile);
                        editor1.commit();

                        resend_otp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getotp(FirebaseInstanceId.getInstance().getToken());
                            }
                        });

                        register.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(otp1.equals(otp2)) {
                                    mobile=mobile_no.getText().toString();
                                    username=user.getText().toString();
                                    String con_pwd=confirm_password.getText().toString();
                                    pwd=password.getText().toString();
                                    register(username, pwd, mobile);
                               }
                                else{
                                    Toast.makeText(getActivity(),"Wrong Otp entered",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        /*Intent intent = new Intent(getContext(), SelectLevel.class);
                        intent.putExtra("mobile",mobile);
                        intent.putExtra("name","");
                        intent.putExtra("reg_status",false);
                        startActivity(intent);*/
                    }
                }
                else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        return builder.create();
        
    }

    public void register(final String user,final String pwd,final String mobile){
        class RegisterAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getContext(), "", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String user = params[0];
                String pwd = params[1];
                String mobile = params[2];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", user));
                nameValuePairs.add(new BasicNameValuePair("password", pwd));
                nameValuePairs.add(new BasicNameValuePair("mobile", mobile));

                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/registration");
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

                JSONObject jo = null;
                try {
                    jo = new JSONObject(s);
                    Boolean error = jo.getBoolean("error");
                    if (error) {
                        String msg = jo.getString("msg");
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), LaunchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Intent intent = new Intent(getContext(), ApplyJobs.class);
                        intent.putExtra("mobile",mobile);
                        intent.putExtra("name","");
                        intent.putExtra("reg_status",false);
                        startActivity(intent);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }
        }
        RegisterAsync la = new RegisterAsync();
        la.execute(user,pwd,mobile);

    }


    public void getotp(final String fcmid){
        class getotpAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getContext(), "", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String fcm = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("fcmid", fcm));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/getotp");
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
                    otp2=jo.getString("otp");
                    Log.e("otp",otp2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                register.setText("Verify Otp");

                otp.setVisibility(View.VISIBLE);
                resend_otp.setVisibility(View.VISIBLE);
                //editor.putString("mobile_no",mobile);
                //editor1.commit();

                resend_otp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getotp(FirebaseInstanceId.getInstance().getToken());
                    }
                });

                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        otp1=otp.getText().toString();
                        Log.e("otp get & rec",otp1+" "+otp2);

                        if(otp1.equals(otp2)) {
                            register(username, pwd, mobile);
                        }
                        else{
                            Toast.makeText(getActivity(),"Wrong Otp entered",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        }

        getotpAsync la = new getotpAsync();
        la.execute(fcmid);

    }



}
