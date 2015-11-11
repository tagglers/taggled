package io.taggled;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.taggled.models.Campaign;

/**
 * Created by demouser on 11/9/15.
 */
public class RV_Campaign_Adapter extends RecyclerView.Adapter<RV_Campaign_Adapter.CampaignViewHolder> {


    OnItemClickListener mItemClickListener;
    private  static Context myContext;
    public  class CampaignViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv_campaign;
        TextView tv_campaign_name;
        TextView tv_taggle_amount;
        TextView tv_campaign_amount;
        TextView tv_taggle_nos;
        TextView tv_campaign_end_date;



        CampaignViewHolder(View itemView) {
            super(itemView);

            myContext=itemView.getContext();
            cv_campaign = (CardView)itemView.findViewById(R.id.cv_campaign);
            tv_campaign_name=(TextView)itemView.findViewById(R.id.tv_campaign_name);
            tv_taggle_amount=(TextView)itemView.findViewById(R.id.tv_taggle_amount);
            tv_campaign_amount=(TextView)itemView.findViewById(R.id.tv_campaign_amount);
            tv_taggle_nos=(TextView)itemView.findViewById(R.id.tv_taggle_nos);
            tv_campaign_end_date=(TextView)itemView.findViewById(R.id.tv_campaign_end_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(mItemClickListener!=null)
            {
                mItemClickListener.onItemClick(v,getPosition());
            }
            Intent myIntent = new Intent(myContext,CampaignDetailActivity.class);
            myContext.startActivity(myIntent);
        }
    }

    public interface OnItemClickListener{
        public void onItemClick(View view,int position);

    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener)
    {
        this.mItemClickListener=mItemClickListener;
    }

    List<Campaign> campaigns;

    RV_Campaign_Adapter(List<Campaign> campaigns){
        this.campaigns = campaigns;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CampaignViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview, viewGroup, false);

        return new CampaignViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CampaignViewHolder campaignViewHolder, int i) {
        campaignViewHolder.tv_campaign_name.setText("Drinking Water in Africa");
        campaignViewHolder.tv_campaign_amount.setText("Target Amount : $40000");
        campaignViewHolder.tv_campaign_end_date.setText("Last Date : 12th November 2015");
        campaignViewHolder.tv_taggle_amount.setText("$54");
        campaignViewHolder.tv_taggle_nos.setText("18");
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }
}
