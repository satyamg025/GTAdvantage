package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Adapter;

/**
 * Created by satyam on 2/2/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;

public class adapter extends RecyclerView.Adapter<adapter.view_holder>{
    List<String> name;
    List<Integer> score;
    int max;
    public adapter(List<String> name,List<Integer> score)
    {
        this.name=name;
        this.score=score;
    }
    public class view_holder extends RecyclerView.ViewHolder{
        TextView tname,tscore;
        public view_holder(View itemView) {
            super(itemView);
            tname=(TextView)itemView.findViewById(R.id.name);
            tscore=(TextView)itemView.findViewById(R.id.score);

        }
    }
    @Override
    public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_card,parent,false);
        return new view_holder(view);
    }

    @Override
    public void onBindViewHolder(adapter.view_holder holder, int position) {
        holder.tname.setText(Integer.toString(position+1)+" . "+name.get(position));
        holder.tscore.setText("Percentile : "+Integer.toString(score.get(position)));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

}
