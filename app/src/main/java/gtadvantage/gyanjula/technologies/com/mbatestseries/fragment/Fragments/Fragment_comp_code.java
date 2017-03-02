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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities.QuesAns;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Network.NetworkCheck;

/**
 * Created by satyam on 1/21/17.
 */
public class Fragment_comp_code extends DialogFragment {
    View view;
    EditText editText;
    Button btn;
    String sponsorName,sponsorImg,sponsorInfo,sponsorLink;
    SharedPreferences sharedPreferences,sharedPreferences2;
    public static Fragment_comp_code instance(String comp_id,String cid,String mobile,String name, Context context) {

        Fragment_comp_code fragment_comp_code = new Fragment_comp_code();
        Bundle b = new Bundle();
        b.putString("cid",cid);
        b.putString("name",name);
        b.putString("mobile",mobile);
        b.putString("comp_id",comp_id);
        fragment_comp_code.setArguments(b);
        return fragment_comp_code;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater=getActivity().getLayoutInflater();
        view=inflater.inflate(R.layout.dialog_comp_code,null);
        builder.setView(view);

        editText=(EditText)view.findViewById(R.id.comp_otp);
        btn=(Button)view.findViewById(R.id.verify);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp=editText.getText().toString();

                if(NetworkCheck.isNetworkAvailable(getActivity())) {

                      if(otp.equals("")){
                         Toast.makeText(getActivity(),"Please enter the required code",Toast.LENGTH_SHORT).show();
                        }
                      else {
                           verify_code(getArguments().getString("cid"), FirebaseInstanceId.getInstance().getToken(), otp);
                        }
                }
                else{
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
        return builder.create();
    }

    public void verify_code(final String cid,final String fcm,final String otp){
        class verifyAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getActivity(), "", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String cid=params[0];
                String fcm = params[1];
                String otp=params[2];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("fcm", fcm));
                nameValuePairs.add(new BasicNameValuePair("cid", cid));
                nameValuePairs.add(new BasicNameValuePair("otp", otp));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/verify_comp_code");
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
                Log.e("det5",s);
                try {
                    JSONObject jo=new JSONObject(s);
                    Boolean error=jo.getBoolean("error");
                    if(!error){

                        get_sponsor(getArguments().getString("comp_id"));
                        Intent intent=new Intent(getActivity(), QuesAns.class);
                        intent.putExtra("mobile",getArguments().getString("mobile"));
                        intent.putExtra("paperId",cid);
                        intent.putExtra("name",getArguments().getString("name"));
                        intent.putExtra("sponName","");
                        intent.putExtra("sponImg","");
                        intent.putExtra("sponInfo","");
                        intent.putExtra("sponLink","");
                        intent.putExtra("instruction","0");
                        intent.putExtra("level_pr","");
                        getActivity().startActivity(intent);

                    }
                    else{
                        Toast.makeText(getActivity(),"Wrong code entered",Toast.LENGTH_LONG).show();
                    }
                   // Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        verifyAsync la = new verifyAsync();
        la.execute(cid,fcm,otp);

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
                    Intent intent=new Intent(getActivity(), QuesAns.class);
                    intent.putExtra("mobile",getArguments().getString("mobile"));
                    intent.putExtra("paperId",getArguments().getString("cid"));
                    intent.putExtra("name",getArguments().getString("name"));
                    intent.putExtra("sponName",sponsorName);
                    intent.putExtra("sponImg",sponsorImg);
                    intent.putExtra("sponInfo",sponsorInfo);
                    intent.putExtra("sponLink",sponsorLink);
                    intent.putExtra("instruction","0");
                    intent.putExtra("level_pr","_");
                    getActivity().startActivity(intent);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        SponsorAsync la = new SponsorAsync();
        la.execute(paperId);

    }

}
