package io.taggled;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.taggled.models.Campaign;

/**
 * A placeholder fragment containing a simple view.
 */
public class CampaignDetailActivityFragment extends Fragment {

    public CampaignDetailActivityFragment() {
    }


    private List<Campaign> campaigns;
    private RecyclerView rv_campaigns_updates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_campaign_detail, container, false);
        rv_campaigns_updates=(RecyclerView)v.findViewById(R.id.rv_campaigns_updates);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_campaigns_updates.setLayoutManager(linearLayoutManager);
        rv_campaigns_updates.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

        return v;
    }




    private void initializeData(){
        campaigns = new ArrayList<Campaign>();

        Campaign campaign = new Campaign();

        campaign.setEndDate(new Date());
        campaign.setName("Drinking Water for Africa");
        campaign.setTargetAmount(40000);
        campaigns.add(campaign);
        campaigns.add(campaign);
        campaigns.add(campaign);
        campaigns.add(campaign);
    }

    private void initializeAdapter(){
        RV_Campaign_Updates_Adapter adapter = new RV_Campaign_Updates_Adapter(campaigns);
        rv_campaigns_updates.setAdapter(adapter);
    }
}

