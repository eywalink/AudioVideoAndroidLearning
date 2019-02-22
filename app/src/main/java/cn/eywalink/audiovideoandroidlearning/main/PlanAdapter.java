package cn.eywalink.audiovideoandroidlearning.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.eywalink.audiovideoandroidlearning.R;
import cn.eywalink.audiovideoandroidlearning.main.entity.Plan;

/**
 * Created by lixin on 2019/2/22.
 */
public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanHolder> {

    private List<Plan> data;

    public PlanAdapter(List<Plan> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PlanHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlanHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_simple, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlanHolder planHolder, int i) {

        final Plan item = data.get(i);
        planHolder.tvPlan.setText(item.getName());
        planHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), item.getActivity());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class PlanHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cl;
        TextView tvPlan;

        public PlanHolder(@NonNull View itemView) {
            super(itemView);
            tvPlan = itemView.findViewById(R.id.tv_plan);
        }
    }
}
