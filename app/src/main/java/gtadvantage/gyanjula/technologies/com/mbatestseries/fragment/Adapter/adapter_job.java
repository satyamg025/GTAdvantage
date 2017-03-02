package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments.Fragment_jobs;

/**
 * Created by satyam on 1/15/17.
 */
public class adapter_job extends RecyclerView.Adapter<adapter_job.ViewHolder> {

    public List<String> name=new ArrayList<String>(),job_name=new ArrayList<String>(),job_desc=new ArrayList<String>(),
            comp_desc=new ArrayList<String>(),salary,exp=new ArrayList<String>(),event_date=new ArrayList<String>(),
            reg_end_date=new ArrayList<String>(),ten=new ArrayList<String>(),twel=new ArrayList<String>(),
            grad=new ArrayList<String>(),website=new ArrayList<String>(),note=new ArrayList<String>(),
    venue=new ArrayList<String>(),event_time=new ArrayList<String>(),city=new ArrayList<String>();
    Context context;
    FragmentManager fragmentmanager;

    public adapter_job(List<String> city,List<String> na, List<String> jn, List<String> jd, List<String> cd, List<String> sal, List<String> ex, List<String> ev, List<String> reg, List<String> tn, List<String> tw, List<String> gr, List<String> web, List<String> not,List<String> ven,List<String> etim, Context context, FragmentManager supportFragmentManager) {
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
        this.venue=ven;
        this.event_time=etim;
        this.context=context;
        this.city=city;
        this.fragmentmanager=supportFragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_jobs, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.comp_name.setText(String.valueOf(name.get(position)));
            holder.job_name_text.setText(String.valueOf(city.get(position)));
            holder.salary_text.setText("Rs. "+String.valueOf(salary.get(position)));
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=holder.getAdapterPosition();

                        Fragment_jobs fragment_jobs=Fragment_jobs.instance(String.valueOf(name.get(pos)),
                                String.valueOf(job_name.get(pos)),String.valueOf(job_desc.get(pos)),String.valueOf(comp_desc.get(pos)),
                                String.valueOf(salary.get(pos)),String.valueOf(exp.get(pos)),String.valueOf(ten.get(pos)),String.valueOf(twel.get(pos)),
                                String.valueOf(grad.get(pos)),String.valueOf(note.get(pos)),String.valueOf(website.get(pos)),
                                String.valueOf(event_date.get(pos)),String.valueOf(reg_end_date.get(pos)),
                                String.valueOf(venue.get(pos)),String.valueOf(event_time.get(pos)),"1","sd");
                        fragment_jobs.show(fragmentmanager,"Jobs Available");
                }
            });
    }


    @Override
    public int getItemCount() {
        return job_name.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView comp_name,job_name_text,salary_text;
        public CardView card;
        public ViewHolder(View itemView) {
            super(itemView);
            comp_name=(TextView)itemView.findViewById(R.id.comp_name);
            job_name_text=(TextView)itemView.findViewById(R.id.job_name);
            salary_text=(TextView)itemView.findViewById(R.id.salary);
            card=(CardView)itemView.findViewById(R.id.card);

        }
    }

}
