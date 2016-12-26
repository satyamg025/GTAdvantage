package myapplication.satyam.example.com.mbatestseries.fragment.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.Set;

import myapplication.satyam.example.com.mbatestseries.R;

public class QuesPaper extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String PREF_NAME="paperId";
    Button paper1,paper2,paper3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_paper);

        final String paperId=getPrefs().getString("paperId","");
        paper1=(Button)findViewById(R.id.paper1);
        paper2=(Button)findViewById(R.id.paper2);
        paper3=(Button)findViewById(R.id.paper3);

        paper1.setText(paperId+ "Ques paper 1");
        paper2.setText(paperId+ "Ques paper 2");
        paper3.setText(paperId+ "Ques paper 3");

        sharedPreferences=getSharedPreferences("paperId",MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();

        paper1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QuesPaper.this,QuesAns.class);
                editor.putString("paperId",paperId+"QP1");
                editor.commit();
                startActivity(intent);

                //ques_ans(paperId+"QP1");
            }
        });
        paper2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QuesPaper.this,QuesAns.class);
                editor.putString("paperId",paperId+"QP2");
                editor.commit();
                startActivity(intent);
                //ques_ans(paperId+"QP2");
            }
        });
        paper3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QuesPaper.this,QuesAns.class);
                editor.putString("paperId",paperId+"QP3");
                editor.commit();
                startActivity(intent);
                //ques_ans(paperId+"QP3");
            }
        });


    }


    private SharedPreferences getPrefs() {
        return getSharedPreferences(PREF_NAME,MODE_PRIVATE);
    }
}
