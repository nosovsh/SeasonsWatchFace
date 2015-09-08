package com.trashgenerator.seasonswatchfaceplus;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class SeasonsWatchFaceActivity extends Activity {

    private View background;
    private TextView seasonName;
    private ImageView seasonImage;

    private final static IntentFilter INTENT_FILTER;
    static {
        INTENT_FILTER = new IntentFilter();
        INTENT_FILTER.addAction(Intent.ACTION_TIME_TICK);
        INTENT_FILTER.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        INTENT_FILTER.addAction(Intent.ACTION_TIME_CHANGED);
    }

    private static final int seasons[] = {
            0, 0, 1, 1, 1, 2,
            2, 2, 3, 3, 3, 0
    };
    private static final String colors[] = {"#61DCFB", "#5AAD6A", "#DD6951", "#F5A837"};
    private static final String seasonNames[] = {"Winter", "Spring", "Summer", "Autumn"};
    private static final String images[] = {"vodka", "cat", "ice_cream", "pushkin1"};

    private BroadcastReceiver mTimeInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context arg0, Intent intent) {
            render();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons_watch_face);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                background = (View) stub.findViewById(R.id.background);
                seasonName = (TextView) stub.findViewById(R.id.seasonName);
                seasonImage = (ImageView) stub.findViewById(R.id.seasonImage);
                registerReceiver(mTimeInfoReceiver, INTENT_FILTER);
                render();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mTimeInfoReceiver);
    }

    private void render() {
        background.setBackgroundColor(Color.parseColor("#DDDDDD"));
        seasonName.setText(seasonNames[getSeason()]);
        background.setBackgroundColor(Color.parseColor(colors[getSeason()]));
        int imageResource = this.getResources().getIdentifier(images[getSeason()], "drawable", this.getPackageName());
        seasonImage.setImageResource(imageResource);
    }

    private int getSeason() {
        return seasons[Calendar.getInstance().get(Calendar.MONTH)];
    }
}
