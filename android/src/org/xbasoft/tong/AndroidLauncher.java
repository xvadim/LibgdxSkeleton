package org.xbasoft.tong;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication
                            implements SystemServivesInterface
{

    private final int HIDE_ADS = 0;
    private final int SHOW_ADS = 1;

    private AdView adView;

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_ADS: {
                    adView.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE_ADS: {
                    adView.setVisibility(View.GONE);
                    break;
                }
            }
        }
    };

    private TongGame mTongGame;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;

        RelativeLayout layout = new RelativeLayout(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        setupMainWindowDisplayMode();

        mTongGame = new TongGame(this);

        View gameView = initializeForView(mTongGame, config);
        layout.addView(gameView);

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
        adView.setVisibility(View.GONE);

        RelativeLayout.LayoutParams adParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        layout.addView(adView, adParams);

        setContentView(layout);

        AdRequest.Builder builder = new AdRequest.Builder();
//        builder.addTestDevice(testDeviceId);
        adView.loadAd(builder.build());
	}

    @Override
    public void onResume() {
        setSystemUiVisibilityMode();

        super.onResume();
    }

    //https://stackoverflow.com/questions/21164836/immersive-mode-navigation-becomes-sticky-after-volume-press-or-minimise-restore
    private void setupMainWindowDisplayMode() {
        View decorView = setSystemUiVisibilityMode();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                setSystemUiVisibilityMode(); // Needed to avoid exiting immersive_sticky when keyboard is displayed
            }
        });
    }

    private View setSystemUiVisibilityMode() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        return decorView;
    }

    @Override
    public void showAds(boolean isShow) {
        handler.sendEmptyMessage(isShow ? SHOW_ADS : HIDE_ADS);
    }

    @Override
    public void sendEmail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String subj = "Tong feedback"; //getString(R.string.feedback_subject, getString(R.string.app_name));
                    Intent ei = new Intent(Intent.ACTION_VIEW);
                    ei.setData(Uri.parse("mailto:your.email@gmail.com"));
                    ei.putExtra(android.content.Intent.EXTRA_SUBJECT, subj);
                    startActivity(Intent.createChooser(ei, subj));
                } catch (Exception ex) {
                }
            }
        });
    }
}
