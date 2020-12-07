package dev.prsm.shopping_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AdFragment extends Fragment
{
    private AdView adView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        Log.v("MA", "Inside onCreateView");
        return inflater.inflate(R.layout.fragment_ad, container, false);
    }

    public void onActivityCreated(Bundle bundle)
    {
        super.onActivityCreated(bundle);
        adView = getView().findViewById(R.id.ad_view);
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        adRequestBuilder.addKeyword("workout").addKeyword("fitness");
        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        AdRequest adRequest = adRequestBuilder.build();
        adView.loadAd( adRequest );
    }

}
