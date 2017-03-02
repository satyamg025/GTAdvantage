package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments.Fragment_comp_code;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments.Fragment_jobs;

/**
 * Created by satyam on 1/21/17.
 */
public class adapter_paper extends RecyclerView.Adapter<adapter_paper.ViewHolder> {

    public List<String> cid=new ArrayList<String>(),comp_name=new ArrayList<String>(),event_date=new ArrayList<String>(),ci2=new ArrayList<String>();
    Context context;
    public String name,mobile;
    FragmentManager fragment;
    SharedPreferences sharedPreferences;

    public adapter_paper(List<String> ci2,List<String> ci, List<String> comp_name,List<String> date,String mobile,String name, Context applicationContext,FragmentManager fragment) {
        this.cid=ci;
        this.comp_name=comp_name;
        this.event_date=date;
        this.context=applicationContext;
        this.fragment=fragment;
        this.mobile=mobile;
        this.ci2=ci2;
        this.name=name;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_paper, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.btn.setText(comp_name.get(position)+"\t"+event_date.get(position));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=holder.getAdapterPosition();
                String date=event_date.get(pos);
                SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                Date d= null;
                try {
                    d = sdf.parse(date);
                    Date d2 = new Date();
                    String dat2=sdf.format(d2);
                    Date d3=sdf.parse(dat2);

                    if(d.equals(d3)){

                        Fragment_comp_code fragment_comp=Fragment_comp_code.instance(ci2.get(pos-1),cid.get(pos),mobile,name,context);
                        fragment_comp.show(fragment,"Jobs Available");
                    }
                    else{
                        Toast.makeText(context,"Exam not started yet",Toast.LENGTH_LONG).show();
                    }
                    Log.e("Date",String.valueOf(d)+String.valueOf(d2));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return cid.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public Button btn;
        public ViewHolder(View itemView) {
            super(itemView);
            btn=(Button)itemView.findViewById(R.id.paper);

        }
    }

}
