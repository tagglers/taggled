package io.taggled;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.taggled.models.Campaign;

/**
 * Created by demouser on 11/9/15.
 * Consists of two views depending on the type of update
 */
public class RV_Campaign_Updates_Adapter extends RecyclerView.Adapter<RV_Campaign_Updates_Adapter.CampaignUpdateViewHolder> {

    private  static Context myContext;
    public static final int UPDATE_IMAGE=1;
    public static final int UPDATE_NO_IMAGE=0;

    public static CardView cv_campaign_updates;
    public static TextView tv_update_name;
    public static TextView tv_update_share;
    public static TextView tv_update_date;
    public static ImageView img_campaign;
    public static class CampaignUpdateViewHolder extends RecyclerView.ViewHolder {

        CampaignUpdateViewHolder(View itemView) {
            super(itemView);

            myContext = itemView.getContext();

        }
    }

    public class UpdateViewHolder extends CampaignUpdateViewHolder {
        TextView temp;

        public UpdateViewHolder(View v) {
            super(v);

            cv_campaign_updates = (CardView)v.findViewById(R.id.cv_campaign_updates);
            tv_update_name = (TextView)v.findViewById(R.id.tv_update_name);
            tv_update_share = (TextView)v.findViewById(R.id.tv_update_share);
            tv_update_date = (TextView)v.findViewById(R.id.tv_update_date);

        }
    }

    public class UpdateImageViewHolder extends CampaignUpdateViewHolder {
        TextView score;

        public UpdateImageViewHolder(View v) {
            super(v);
            cv_campaign_updates = (CardView)v.findViewById(R.id.cv_campaign_updates);
            img_campaign=(ImageView)v.findViewById(R.id.img_campaign);
            tv_update_name = (TextView)v.findViewById(R.id.tv_update_name);
            tv_update_share = (TextView)v.findViewById(R.id.tv_update_share);
            tv_update_date = (TextView)v.findViewById(R.id.tv_update_date);

        }
    }


    List<Campaign> campaigns;

    RV_Campaign_Updates_Adapter(List<Campaign> campaigns){
        this.campaigns = campaigns;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CampaignUpdateViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        if (viewType == UPDATE_IMAGE) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_cardview_campaign_updates_images, viewGroup, false);

            return new UpdateImageViewHolder(v);
        } else {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_cardview_campaign_updates, viewGroup, false);
            return new UpdateViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(CampaignUpdateViewHolder campaignViewHolder, int position) {
        if (campaignViewHolder.getItemViewType() == UPDATE_IMAGE) {
            UpdateImageViewHolder holder = (UpdateImageViewHolder) campaignViewHolder;

        }
        else if (campaignViewHolder.getItemViewType() == UPDATE_NO_IMAGE) {
            UpdateViewHolder holder = (UpdateViewHolder) campaignViewHolder;

        }
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }


}
