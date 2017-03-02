package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mobsandgeeks.saripaar.annotation.Password;

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
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities.LaunchActivity;

/**
 * Created by satyam on 1/28/17.
 */
public class Fragment_forgot_pwd extends DialogFragment {
    View view;
    EditText username,otp;
    Button btn,btn2,btn3;
    String otp1,otp2,usr;
    int flg=0;
    //TextInputLayout til,til2;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater=getActivity().getLayoutInflater();
        view=inflater.inflate(R.layout.dialog_forgot_pwd,null);
        builder.setView(view);

        username=(EditText)view.findViewById(R.id.username);
        otp=(EditText)view.findViewById(R.id.otp);
       // til=(TextInputLayout)view.findViewById(R.id.tilotp);
        //til2=(TextInputLayout)view.findViewById(R.id.tilusr);
        btn=(Button)view.findViewById(R.id.verify_otp);
        btn2=(Button)view.findViewById(R.id.verify_otp2);
        btn3=(Button)view.findViewById(R.id.verify_otp3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usr=username.getText().toString();
                if(usr.equals("")){
                    Toast.makeText(getActivity(),"Enter username",Toast.LENGTH_LONG).show();
                }
                else{
                    username(usr);
                }
            }
        });


        return builder.create();

    }


    public void username(final String user) {
        class usrAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getActivity(), "", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String user = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", user));

                String result = "null";

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/forgot_pwd");
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
                if(!result.equals("null")) {
                    String s = result.trim();

                    loadingDialog.dismiss();
                    Log.e("login", s);
                    try {
                        JSONObject jo=new JSONObject(s);
                        Boolean error=jo.getBoolean("error");
                        if(!error){
                            otp1=jo.getString("otp");
                           // til.setVisibility(View.VISIBLE);
                            //username.setFocusable(false);
                            otp.setVisibility(View.VISIBLE);
                            //btn.setText("Verify Otp");
                            btn.setVisibility(View.GONE);
                            btn2.setVisibility(View.VISIBLE);
                            btn2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    otp2=otp.getText().toString();
                                    if(otp2.equals(otp1)){

                                        //btn.setText("Change password");
                                       // username.setFocusable(true);
                                        username.setHint("Reset Password");
                                        otp.setHint("Confirm Password");
                                        username.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                        otp.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                        //username.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                        //otp.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                                        username.getText().clear();
                                        otp.getText().clear();
                                        //flg=1;
                                        btn2.setVisibility(View.GONE);
                                        btn3.setVisibility(View.VISIBLE);


                                        btn3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {


                                        if(username.getText().toString().equals("") || otp.getText().toString().equals("")){
                                            Toast.makeText(getActivity(),"Fill all entries",Toast.LENGTH_LONG).show();
                                        }
                                        else if(username.getText().toString().length()<6){
                                            Toast.makeText(getActivity(),"Password must be atleast 6 characters long",Toast.LENGTH_LONG).show();

                                        }
                                        else if(!username.getText().toString().equals(otp.getText().toString())){
                                            Toast.makeText(getActivity(),"Passwords not matching",Toast.LENGTH_LONG).show();

                                        }
                                        else{
                                            change_pwd(user,username.getText().toString());
                                        }
                                        }
                                        });
                                    }
                                    else{
                                        Toast.makeText(getActivity(),"Wrong otp entered",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        }
                        else{
                            Toast.makeText(getActivity(),"Username not found",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                else{
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Unable to connect. Try again later...", Snackbar.LENGTH_LONG)
                            .show();
                }

            }
        }

        usrAsync la = new usrAsync();
        la.execute(user);

    }


    public void change_pwd(final String user,final String pwd) {
        class chngAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loadingDialog = ProgressDialog.show(getActivity(), "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String user = params[0];
                String pwd = params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", user));
                nameValuePairs.add(new BasicNameValuePair("password", pwd));

                String result = "null";

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/change_pwd");
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
                if(!result.equals("null")) {
                    String s = result.trim();

                  //  loadingDialog.dismiss();
                    Log.e("login", s);
                    try {
                        JSONObject jo=new JSONObject(s);
                        Boolean error=jo.getBoolean("error");
                        if(!error){
                          Toast.makeText(getActivity(),"Password Changed Successfully",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getActivity(), LaunchActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getActivity(),"Some error occurred... Try again later" +
                                    "",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getActivity(), LaunchActivity.class);
                            startActivity(intent);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                else{
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Unable to connect. Try again later...", Snackbar.LENGTH_LONG)
                            .show();
                }

            }
        }

        chngAsync la = new chngAsync();
        la.execute(user,pwd);

    }

}
