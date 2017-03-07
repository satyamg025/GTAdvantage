package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Adapter;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities.ApplyJobs;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities.PaymentActivity;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments.Fragment_jobs;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Network.NetworkCheck;

/**
 * Created by satyam on 1/15/17.
 */
public class adapter_apply_jobs extends RecyclerView.Adapter<adapter_apply_jobs.ViewHolder> {

    public List<String> name=new ArrayList<String>(),job_name=new ArrayList<String>(),job_desc=new ArrayList<String>(),
            comp_desc=new ArrayList<String>(),salary,exp=new ArrayList<String>(),event_date=new ArrayList<String>(),
            reg_end_date=new ArrayList<String>(),ten=new ArrayList<String>(),twel=new ArrayList<String>(),venue=new ArrayList<String>(),event_time=new ArrayList<String>(),
            grad=new ArrayList<String>(),website=new ArrayList<String>(),note=new ArrayList<String>(),job_reg=new ArrayList<String>(),cid=new ArrayList<String>(),cost=new ArrayList<String>();
    Context context;
    public String mobile,name2;
    FragmentManager fragmentmanager;

    public adapter_apply_jobs(String name2, String mobile, List<String> na, List<String> jn, List<String> jd, List<String> cd, List<String> sal, List<String> ex, List<String> ev, List<String> reg, List<String> tn, List<String> tw, List<String> gr, List<String> web, List<String> not, List<String> job_reg, List<String> cid, List<String> venue, List<String> etime, List<String> cost, Context context, FragmentManager supportFragmentManager) {
        this.mobile=mobile;
        this.name2=name2;
        this.name=na;
        this.job_name=jn;
        this.job_desc=jd;
        this.comp_desc=cd;
        this.salary=sal;
        this.exp=ex;
        this.event_date=ev;
        this.reg_end_date=reg;
        this.ten=tn;
        this.twel=tw;
        this.grad=gr;
        this.website=web;
        this.note=not;
        this.job_reg=job_reg;
        this.venue=venue;
        this.event_time=etime;
        this.cid=cid;
        this.cost=cost;
        this.context=context;
        this.fragmentmanager=supportFragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_apply_jobs, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.comp_name.setText(String.valueOf(name.get(position)));
        holder.job_name_text.setText(String.valueOf(job_name.get(position)));
        holder.salary_text.setText("Rs. "+String.valueOf(salary.get(position)));

        if(job_reg.contains(cid.get(position))){
            holder.cost.setText("Applied");
            holder.cost.setClickable(false);
        }
        else{
            //holder.cost.setText(cost.get(position));
            holder.cost.setText("Apply");
            holder.cost.setClickable(true);
        }
        if(holder.cost.isClickable()) {
            holder.cost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(NetworkCheck.isNetworkAvailable(context)) {
                    apply(mobile,cid.get(position));

                       /* Intent intent=new Intent(context, PaymentActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("cost",cost.get(position));
                        context.startActivity(intent);*/
                     }
                    else{
                        Toast.makeText(context,"No Network Connection",Toast.LENGTH_LONG).show();
                    }

                }
            });
        }


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=holder.getAdapterPosition();

                Fragment_jobs fragment_jobs=Fragment_jobs.instance(String.valueOf(name.get(pos)),
                        String.valueOf(job_name.get(pos)),String.valueOf(job_desc.get(pos)),String.valueOf(comp_desc.get(pos)),
                        String.valueOf(salary.get(pos)),String.valueOf(exp.get(pos)),String.valueOf(ten.get(pos)),String.valueOf(twel.get(pos)),
                        String.valueOf(grad.get(pos)),String.valueOf(note.get(pos)),String.valueOf(website.get(pos)),
                        String.valueOf(event_date.get(pos)),String.valueOf(reg_end_date.get(pos)),String.valueOf(venue.get(pos)),String.valueOf(event_time.get(pos)),mobile,name2);
                fragment_jobs.show(fragmentmanager,"Jobs Available");


            }
        });
    }


    public void apply(final String mobile,final String comp){
        class RegisterAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loadingDialog = ProgressDialog.show(context, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String phone=params[0];
                String cmp=params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mobile", phone));
                nameValuePairs.add(new BasicNameValuePair("comp_id", cmp));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/applyjob");
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
               // loadingDialog.dismiss();
                try {
                    JSONObject jo=new JSONObject(s);
                    Toast.makeText(context,jo.getString("message"),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, ApplyJobs.class);
                intent.putExtra("mobile",mobile);
                intent.putExtra("reg_status",true);
                intent.putExtra("name",name2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        }

        RegisterAsync la = new RegisterAsync();
        la.execute(mobile,comp);

    }



    @Override
    public int getItemCount() {
        return job_name.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView comp_name,job_name_text,salary_text;
        public CardView card;
        public Button cost;
        public ViewHolder(View itemView) {
            super(itemView);
            comp_name=(TextView)itemView.findViewById(R.id.comp_name);
            job_name_text=(TextView)itemView.findViewById(R.id.job_name);
            salary_text=(TextView)itemView.findViewById(R.id.salary);
            cost=(Button)itemView.findViewById(R.id.job_cost);
            card=(CardView)itemView.findViewById(R.id.card_apply);

        }
    }

}
