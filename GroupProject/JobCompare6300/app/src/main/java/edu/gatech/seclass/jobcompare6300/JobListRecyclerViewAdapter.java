package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.gatech.seclass.jobcompare6300.models.DataSelectModel;

import java.util.List;

/*
Citation: overall structure of this custom recyclerview adapter was
created from reference to:
https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
 */
public class JobListRecyclerViewAdapter extends RecyclerView.Adapter<JobListRecyclerViewAdapter.ViewHolder> {

    private int colorSelected;
    private int colorDefault;
    private LayoutInflater inflater;

    // store the data in a list
    private List<DataSelectModel> dataModelList;

    private int numSelected;

    JobListRecyclerViewAdapter(Context context, List<DataSelectModel> dataModelList) {
        this.inflater = LayoutInflater.from(context);
        this.dataModelList = dataModelList;
        numSelected = 0;

        colorSelected = Color.rgb(180, 255, 180);
        colorDefault = Color.WHITE;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataSelectModel dataModel = dataModelList.get(position);
        String textData = dataModel.getTextData();


        if(textData.contains("CURRENT JOB")){
            textData = textData.substring(0, textData.length() - 13);
            holder.textView.setTypeface(null, Typeface.BOLD);
        }
        holder.textView.setText(textData);
        holder.itemView.setBackgroundColor(dataModel.getIsActive() ? colorSelected : colorDefault);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!dataModel.getIsActive() && numSelected >=2) {
                    // only allow selection of up to two items.
                    return;
                }
                if(dataModel.getIsActive())
                    numSelected--;
                else
                    numSelected++;
                // update the highlighted background color based on selected status
                dataModel.setIsActive(!dataModel.getIsActive());
                holder.itemView.setBackgroundColor(dataModel.getIsActive() ? colorSelected : colorDefault);
            }
        });
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    String getItem(int posId) {
        // returns id at click position
        return dataModelList.get(posId).getTextData();
    }

    // ViewHolder implementation, required by RecycleView override
    // sets up a click listener for each item in the list of rows
    // here, that is a TextView widget.
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.jobListJobDescription);
        }
    }
}