package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Network.NetworkCheck;

public class CompanyRegister extends AppCompatActivity {

    EditText comp_name,job_name,job_desc,comp_desc,salary,exp,cand_req,note,website;
    Button reg;
    EditText username,pwd,mobi,email;
    Spinner ten_per,twel_per,grad_per;
    int k;
    Calendar myCalendar = Calendar.getInstance();
    List<String> per1=new ArrayList<String>(),per2=new ArrayList<String>(),per3=new ArrayList<String>();
    ArrayAdapter<String> adapter1,adapter2,adapter3;
    int pos1,pos2,pos3;

    String cname,jname,jdesc,cdesc,sal,ex,candi,no,web,tper,twper,gper,user,pass,mob,mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);

        comp_name=(EditText)findViewById(R.id.comp_name);
        job_name=(EditText)findViewById(R.id.job_name);
        job_desc=(EditText)findViewById(R.id.job_desc);
        comp_desc=(EditText)findViewById(R.id.comp_desc);
        salary=(EditText)findViewById(R.id.salary);
        exp=(EditText)findViewById(R.id.exp_req);
        cand_req=(EditText)findViewById(R.id.cand_req);
        username=(EditText)findViewById(R.id.username);
        pwd=(EditText)findViewById(R.id.password);
        mobi=(EditText)findViewById(R.id.mobile);
        email=(EditText)findViewById(R.id.email);
        note=(EditText)findViewById(R.id.note);
        website=(EditText)findViewById(R.id.comp_website);
        ten_per=(Spinner) findViewById(R.id.ten_per_required);
        twel_per=(Spinner) findViewById(R.id.twel_per_required);
        grad_per=(Spinner) findViewById(R.id.grad_per_required);
        reg=(Button)findViewById(R.id.comp_reg);


        per1.add("10th% required");
        per1.add("no bar");
        per1.add("60");
        per1.add("70");
        per1.add("80");
        adapter1= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,per1);
        ten_per.setAdapter(adapter1);
        ten_per.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos1=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        per2.add("12th% required");
        per2.add("no bar");
        per2.add("60");
        per2.add("70");
        per2.add("80");
        adapter2= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,per2);
        twel_per.setAdapter(adapter2);
        twel_per.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos2=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        per3.add("Graduation% required");
        per3.add("no bar");
        per3.add("60");
        per3.add("70");
        per3.add("80");
        adapter3= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,per3);
        grad_per.setAdapter(adapter3);
        grad_per.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos3=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cname=comp_name.getText().toString();
                jname=job_name.getText().toString();
                jdesc=job_desc.getText().toString();
                cdesc=comp_desc.getText().toString();
                sal=salary.getText().toString();
                pass=pwd.getText().toString();
                mob=mobi.getText().toString();
                mail=email.getText().toString();
                ex=exp.getText().toString();
                candi=cand_req.getText().toString();
                user=username.getText().toString();
                no=note.getText().toString();
                web=website.getText().toString();
                tper=per1.get(pos1);
                twper=per2.get(pos2);
                gper=per3.get(pos3);

                if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {

                    if(cname.equals("") || jname.equals("") || jdesc.equals("") || cdesc.equals("") || sal.equals("") ||
                            ex.equals("") || candi.equals("") || no.equals("") || web.equals("") ||
                            tper.equals("") || twper.equals("") || gper.equals("")||
                            pos1==0 || pos2==0 || pos3==0|| user.equals("") || pass.equals("") || mob.equals("") || mail.equals(""))
                    {
                        Snackbar.make(findViewById(android.R.id.content), "Please fill all entries", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .show();
                    }
                    else{
                    register(cname, jname, jdesc, cdesc, sal, ex,  candi, no, web, tper, twper, gper,user,pass,mob,mail);
                }
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                            .show();
                }

            }
        });

    }

    private void updateLabel(EditText edittext) {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }


    public void register(final String cname,final String jname,final String jdesc,final String cdesc,final String sal,final String ex,final String reg_en,final String no,final String web,final String tper,final String twper,final String gper,final String username,final String pwd,final String mobile,final String email){
        class RegisterAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(CompanyRegister.this, "", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {

                String cna = params[0];
                String jna=params[1];
                String jdes=params[2];
                String cdes=params[3];
                String sa=params[4];
                String ex=params[5];
                String ren=params[6];
                String note=params[7];
                String we=params[8];
                String tp=params[9];
                String twp=params[10];
                String gp=params[11];
                String user=params[12];
                String pwd=params[13];
                String mobi=params[14];
                String mail=params[15];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", cna));
                nameValuePairs.add(new BasicNameValuePair("job_post", jna));
                nameValuePairs.add(new BasicNameValuePair("job_desc", jdes));
                nameValuePairs.add(new BasicNameValuePair("comp_desc", cdes));
                nameValuePairs.add(new BasicNameValuePair("salary", sa));
                nameValuePairs.add(new BasicNameValuePair("experience", ex));
                nameValuePairs.add(new BasicNameValuePair("per_req", ren));
                nameValuePairs.add(new BasicNameValuePair("note", note));
                nameValuePairs.add(new BasicNameValuePair("website", we));
                nameValuePairs.add(new BasicNameValuePair("10th", tp));
                nameValuePairs.add(new BasicNameValuePair("12th", twp));
                nameValuePairs.add(new BasicNameValuePair("graduation", gp));
                nameValuePairs.add(new BasicNameValuePair("username",user));
                nameValuePairs.add(new BasicNameValuePair("password", pwd));
                nameValuePairs.add(new BasicNameValuePair("mobile",mobi));
                nameValuePairs.add(new BasicNameValuePair("email",mail));
                Log.e("detail",nameValuePairs.toString());


                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/company_registration");
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

                Log.e("comp_reg",s);
                loadingDialog.dismiss();
                Intent intent=new Intent(CompanyRegister.this,LaunchActivity.class);
                startActivity(intent);
                finish();

            }
        }

        RegisterAsync la = new RegisterAsync();
        la.execute(cname,jname,jdesc,cdesc,sal,ex,reg_en,no,web,tper,twper,gper,username,pwd,mobile,email);

    }

}
