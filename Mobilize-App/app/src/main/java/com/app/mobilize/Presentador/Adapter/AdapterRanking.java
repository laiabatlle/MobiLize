package com.app.mobilize.Presentador.Adapter;

        import android.content.Context;
        import android.graphics.Color;
        import android.net.Uri;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;

        import com.app.mobilize.Model.Ranking;
        import com.app.mobilize.R;
        import com.google.firebase.firestore.CollectionReference;
        import com.google.firebase.firestore.FirebaseFirestore;
        import java.util.List;

public class AdapterRanking extends RecyclerView.Adapter<AdapterRanking.viewholderRanking> {

    private List<Ranking> rankList;
    private CollectionReference rank_ref;
    private Context mContext;

    public AdapterRanking(@NonNull Context context, List<Ranking> rankList){
        this.mContext = context;
        this.rankList = rankList;
        rank_ref = FirebaseFirestore.getInstance().collection("users");
    }

    @NonNull
    @Override
    public viewholderRanking onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rank, parent,false);
        return new viewholderRanking(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholderRanking holder, int position) {
        final Ranking rank = rankList.get(position);
        rank.setPosition(position+1);
        if (position == 0){
            holder.imagePosition.setImageResource(R.mipmap.ic_pos1);
            holder.position.setVisibility(View.INVISIBLE);
            holder.rankLayout.setBackgroundColor(Color.parseColor("#CFB53B"));
        }
        else {
            final Ranking rank2 = rankList.get(position-1);
            if(rank2.getPoints().equals(rank.getPoints())){
                holder.position.setText(String.valueOf(rank2.getPosition()));
                holder.user.setText(rank.getUser());
                holder.points.setText(rank.getPoints().toString());
                rank.setPosition(rank2.getPosition());
                if(rank.getPosition() == 1){
                    holder.imagePosition.setImageResource(R.mipmap.ic_pos1);
                    holder.position.setVisibility(View.INVISIBLE);
                    holder.rankLayout.setBackgroundColor(Color.parseColor("#CFB53B"));
                }
                else if(rank.getPosition() == 2){
                    holder.imagePosition.setImageResource(R.mipmap.ic_pos2);
                    holder.position.setVisibility(View.INVISIBLE);
                    holder.rankLayout.setBackgroundColor(Color.parseColor("#DBE4EB"));
                }
                else if(rank.getPosition() == 3){
                    holder.imagePosition.setImageResource(R.mipmap.ic_pos3);
                    holder.position.setVisibility(View.INVISIBLE);
                    holder.rankLayout.setBackgroundColor(Color.parseColor("#B08D57"));
                }
            }
            else if (position == 1){
                holder.imagePosition.setImageResource(R.mipmap.ic_pos2);
                holder.position.setVisibility(View.INVISIBLE);
                holder.rankLayout.setBackgroundColor(Color.parseColor("#DBE4EB"));
            } else if (position == 2){
                holder.imagePosition.setImageResource(R.mipmap.ic_pos3);
                holder.position.setVisibility(View.INVISIBLE);
                holder.rankLayout.setBackgroundColor(Color.parseColor("#B08D57"));
            }
        }
        holder.position.setText(String.valueOf(rank.getPosition()));
        holder.user.setText(rank.getUser());
        holder.points.setText(rank.getPoints().toString());
    }
    @Override
    public int getItemCount() {
        return rankList.size();
    }

    static class viewholderRanking extends RecyclerView.ViewHolder {

        TextView position, user, points;
        LinearLayout rankLayout;
        ImageView imagePosition;

        viewholderRanking(@NonNull View itemView) {
            super(itemView);

            position = (TextView) itemView.findViewById(R.id.rankPositionTV);
            user = (TextView) itemView.findViewById(R.id.rankUserTV);
            points = (TextView) itemView.findViewById(R.id.pointsTV);
            rankLayout = (LinearLayout) itemView.findViewById(R.id.rankLayout);
            imagePosition = (ImageView) itemView.findViewById(R.id.imagePosition);
        }
    }
}
