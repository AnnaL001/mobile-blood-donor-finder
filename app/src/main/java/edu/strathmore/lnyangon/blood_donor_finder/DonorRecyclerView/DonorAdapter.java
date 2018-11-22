package edu.strathmore.lnyangon.blood_donor_finder.donorRecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.strathmore.lnyangon.blood_donor_finder.R;

public class DonorAdapter extends RecyclerView.Adapter<DonorViewHolder>  {
    private List<DonorObject> itemList;
    private Context context;

    public DonorAdapter(List<DonorObject> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public DonorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_donor_list_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        DonorViewHolder dcv = new DonorViewHolder(layoutView);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DonorViewHolder donorViewHolder, int i) {
        donorViewHolder.name.setText(itemList.get(i).getName());
        donorViewHolder.phone.setText(itemList.get(i).getPhone());
        donorViewHolder.blood_group.setText(itemList.get(i).getPhone());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
