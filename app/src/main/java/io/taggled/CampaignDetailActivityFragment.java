package io.taggled;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class CampaignDetailActivityFragment extends Fragment {


    public CampaignDetailActivityFragment() {
    }

    @Bind(R.id.tv_campaign_taggle_amount1) TextView taggleAmount;
    @Bind(R.id.tv_campaign_taggle_nos) TextView taggleCount;
    @Bind(R.id.tv_campaign_taggle_count) TextView taggleRemaining;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_campaign_detail, container, false);

        ButterKnife.bind(this, v);

        if (getArguments() != null &&
                getArguments().containsKey(CampaignDetailActivity.EXTRA_START_TAGGLE) &&
                getArguments().getBoolean(CampaignDetailActivity.EXTRA_START_TAGGLE)) {
            taggleAmount.setText("$ 0.99");
            taggleCount.setText("1");
            taggleRemaining.setVisibility(View.VISIBLE);
        }

        return v;
    }
    public static CampaignDetailActivityFragment newInstance(String text, Bundle data) {

        CampaignDetailActivityFragment f = new CampaignDetailActivityFragment();
//        Bundle b = new Bundle();
//        b.putString("msg", text);
//
        f.setArguments(data);

        return f;
    }



}

