package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;

/**
 * Created by satyam on 1/8/17.
 */
public class adapter_correct_ans extends RecyclerView.Adapter<adapter_correct_ans.ViewHolder> {

    public List<String> ques=new ArrayList<String>();
    public String correct_ans[],user_ans[];
    Context context;
    public adapter_correct_ans( List<String> ques, String[] correct_ans, String[] user_ans, Context context) {

        this.ques=ques;
        this.correct_ans=correct_ans;
        this.user_ans=user_ans;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_correct_ans, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.qs.setText(ques.get(position));
        holder.corans.setText(correct_ans[position]);
        holder.usrans.setText(user_ans[position]);

        if(user_ans[position].equals(correct_ans[position])){
            holder.img.setImageResource(R.drawable.ic_done_black_24dp);
            holder.img.setColorFilter(context.getResources().getColor(R.color.green));
        }
         else{
            holder.img.setImageResource(R.drawable.ic_clear_black_24dp);
            holder.img.setColorFilter(context.getResources().getColor(R.color.red));
        }
    }


    @Override
    public int getItemCount() {
        return ques.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView qs,corans,usrans;
        public ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            qs=(TextView)itemView.findViewById(R.id.adapter_ques);
            corans=(TextView)itemView.findViewById(R.id.adapter_correct_ans);
            usrans=(TextView)itemView.findViewById(R.id.adapter_user_ans);
            img=(ImageView)itemView.findViewById(R.id.img_usr_ans);

        }
    }
}
