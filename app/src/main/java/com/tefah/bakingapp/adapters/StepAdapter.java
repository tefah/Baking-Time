package com.tefah.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefah.bakingapp.R;
import com.tefah.bakingapp.pojo.Step;
import java.util.List;

/**
 * custom adapter to hold steps
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder>{

    private Context context;
    private List<Step> steps;
    private OnStepClick clickListner;

    public interface OnStepClick{
        void onClick(Step step, int position);
    }

    public StepAdapter(List<Step> steps, OnStepClick clickListner){
        this.steps = steps;
        this.clickListner = clickListner;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent    The ViewGroup that these ViewHolders are contained within.
     * @param viewType
     *
     * @return A new StepViewHolder that holds the View for each list item
     */
    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int itemId = R.layout.step_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(itemId,parent,shouldAttachToParentImmediately);
        return new StepViewHolder(view);
    }


    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (steps == null)
            return 0;
        return steps.size();
    }

    public void setSteps(List<Step> steps){
        this.steps = steps;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Step step;
        int position;

        public StepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            this.position = position;
            step = steps.get(position);
            ((TextView) itemView.findViewById(R.id.stepDescriptionTextView))
                    .setText(step.getShortDescription());
        }

        @Override
        public void onClick(View view) {
            clickListner.onClick(step, position);
        }
    }
}
