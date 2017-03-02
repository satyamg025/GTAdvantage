package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities.ApplyJobs;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities.LaunchActivity;

/**
 * Created by satyam on 1/15/17.
 */
public class Fragment_jobs extends DialogFragment{
    View view;
    TextView name,job_name,job_desc,comp_desc,salary,exp,event_date,reg_end_date,ten,twel,grad,note,website,ven,evtim;
    Button close;


    public static Fragment_jobs instance(String name, String job_name, String job_desc, String comp_desc, String salary, String exp, String ten, String twel, String grad, String note, String website, String event_date, String reg_end_date,String venue,String event_time,String no,String name2){

        Fragment_jobs fragment_jobs=new Fragment_jobs();
        Bundle b=new Bundle();
        b.putString("name",name);
        b.putString("name2",name2);
        b.putString("job_name",job_name);
        b.putString("job_desc",job_desc);
        b.putString("comp_desc",comp_desc);
        b.putString("salary",salary);
        b.putString("exp",exp);
        b.putString("ten",ten);
        b.putString("twel",twel);
        b.putString("grad",grad);
        b.putString("note",note);
        b.putString("website",website);
        b.putString("event_date",event_date);
        b.putString("reg_end_date",reg_end_date);
        b.putString("venue",venue);
        b.putString("event_time",event_time);
        b.putString("no",no);
        fragment_jobs.setArguments(b);
        return fragment_jobs;

    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_jobs, null);
        builder.setView(view);

        name=(TextView)view.findViewById(R.id.name);
        job_name=(TextView)view.findViewById(R.id.job_name);
        job_desc=(TextView)view.findViewById(R.id.job_desc);
        comp_desc=(TextView)view.findViewById(R.id.comp_desc);
        salary=(TextView)view.findViewById(R.id.salary);
        exp=(TextView)view.findViewById(R.id.experience);
        event_date=(TextView)view.findViewById(R.id.event_date);
        reg_end_date=(TextView)view.findViewById(R.id.reg_end_date);
        ten=(TextView)view.findViewById(R.id.ten);
        twel=(TextView)view.findViewById(R.id.twel);
        grad=(TextView)view.findViewById(R.id.grad);
        note=(TextView)view.findViewById(R.id.note);
        ven=(TextView)view.findViewById(R.id.venue);
        evtim=(TextView)view.findViewById(R.id.event_time);
        website=(TextView)view.findViewById(R.id.website);
        close=(Button)view.findViewById(R.id.close);

        name.setText(getArguments().getString("name",""));
        job_name.setText(getArguments().getString("job_name",""));
        job_desc.setText(getArguments().getString("job_desc",""));
        comp_desc.setText(getArguments().getString("comp_desc",""));
        salary.setText("Rs. "+getArguments().getString("salary",""));
        exp.setText(getArguments().getString("exp",""));
        event_date.setText(getArguments().getString("event_date",""));
        reg_end_date.setText(getArguments().getString("reg_end_date",""));
        ten.setText(getArguments().getString("ten",""));
        twel.setText(getArguments().getString("twel",""));
        grad.setText(getArguments().getString("grad",""));
        note.setText(getArguments().getString("note",""));
        ven.setText(getArguments().getString("venue",""));
        evtim.setText(getArguments().getString("event_time",""));
        website.setText(getArguments().getString("website",""));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getArguments().getString("no","").equals("1")) {
                    Intent intent = new Intent(getActivity(), LaunchActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    getActivity().finish();
                }
                else{
                   // Toast.makeText(getActivity(),getArguments().getString("name2",""),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ApplyJobs.class);
                    intent.putExtra("mobile",getArguments().getString("no",""));
                    intent.putExtra("reg_status",true);
                    intent.putExtra("name",getArguments().getString("name2",""));
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        return builder.create();
    }

}
