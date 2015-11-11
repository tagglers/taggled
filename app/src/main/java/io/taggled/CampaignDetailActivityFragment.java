package io.taggled;

import android.os.AsyncTask;
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
            taggleRemaining.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateTaggleCount(v);
                }
            });
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

    public void updateTaggleCount(View view){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    // Simulate network access.
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                taggleAmount.setText("$ 1.98");
                taggleCount.setText("2");
                taggleRemaining.setText(String.format(getString(R.string.can_taggle_param), 2));
            }
        }.execute();
    }


}

