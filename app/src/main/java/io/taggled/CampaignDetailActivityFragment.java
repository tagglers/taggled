package io.taggled;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class CampaignDetailActivityFragment extends Fragment {

    public CampaignDetailActivityFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_campaign_detail, container, false);

        return v;
    }
    public static CampaignDetailActivityFragment newInstance(String text) {

        CampaignDetailActivityFragment f = new CampaignDetailActivityFragment();
//        Bundle b = new Bundle();
//        b.putString("msg", text);
//
//        f.setArguments(b);

        return f;
    }



}

