package edu.strathmore.lnyangon.blood_donor_finder.donorRecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import edu.strathmore.lnyangon.blood_donor_finder.DonorLocationActivity;
import edu.strathmore.lnyangon.blood_donor_finder.R;

public class DonorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView name;
    public TextView phone;
    public TextView blood_group;

    public DonorViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        name = (TextView) itemView.findViewById(R.id.donorName);
        phone = (TextView) itemView.findViewById(R.id.donorPhone);
        blood_group = (TextView) itemView.findViewById(R.id.donorBgroup);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), DonorLocationActivity.class);
        Bundle b = new Bundle();
        b.putString("name", name.getText().toString());
        intent.putExtras(b);
        v.getContext().startActivity(intent);
    }
}
