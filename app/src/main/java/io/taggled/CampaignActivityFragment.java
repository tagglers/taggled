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
public class CampaignActivityFragment extends Fragment {

    public CampaignActivityFragment() {
    }


    private List<Campaign> campaigns;
    private RecyclerView rv_campaigns;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_campaign, container, false);
        rv_campaigns=(RecyclerView)v.findViewById(R.id.rv_campaigns);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_campaigns.setLayoutManager(linearLayoutManager);
        rv_campaigns.setHasFixedSize(true);

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
        RV_Campaign_Adapter adapter = new RV_Campaign_Adapter(campaigns);
        rv_campaigns.setAdapter(adapter);
    }
}
