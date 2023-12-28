package fcb8.Number.Recall;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import fcb8.Number.Recall.Repository.Animations;
import fcb8.Number.Recall.Repository.FBFetch;
import fcb8.Number.Recall.Repository.IntConnection;
import fcb8.Number.Recall.databinding.ActivitySplashBinding;

public class Splash extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private Animations TheAnimation;
    private FBFetch LoadFB;
    private IntConnection GMInternet;
    private String FSStatus, URLStatus, url;
    boolean HadInternetConnection, NoInternetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TheAnimation = new Animations(this);

        LoadFB = new FBFetch();
        GMInternet = new IntConnection(this);
        HadInternetConnection = GMInternet.getInternetConnection();
        LoadApp();
    }

    private void LoadApp() {
        LoadFB.addSnapshotListener((value, error) -> {
            if (value != null) {
                FSStatus = LoadFB.getFSStatus(value);
                URLStatus = LoadFB.getURLStatus(value);
                url = LoadFB.getUrl(value);

                if (HadInternetConnection) {
                    if (FSStatus.equals(URLStatus)) {
                        LoadT();
                    } else {
                        LoadF();
                    }
                } else {
                    NoInternetConnection = GMInternet.getNoInternetConnection();
                }
            }
        });
    }

    private void LoadT() {
        binding.SplashLogo.setVisibility(View.VISIBLE);
        TheAnimation.getScaleAnimation(binding.SplashLogo);
        Handler delayHandler = new Handler();
        delayHandler.postDelayed(() -> {
            binding.GameWeb.setVisibility(View.VISIBLE);
            DW(url);
        }, 700);
    }

    private void LoadF() {
        binding.SplashLogo.setVisibility(View.VISIBLE);
        TheAnimation.getScaleAnimation(binding.SplashLogo);
        Handler delayHandler = new Handler();
        delayHandler.postDelayed(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 700);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void DW(String url) {
        binding.GameWeb.clearHistory();
        binding.GameWeb.getSettings().setJavaScriptEnabled(true);
        binding.GameWeb.getSettings().setDomStorageEnabled(true);
        binding.GameWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        binding.GameWeb.loadUrl(url);
        binding.GameWeb.setWebViewClient(new WebViewClient());

        if (FSStatus.equals(URLStatus)) {
            binding.GameWeb.setWebViewClient(new WebViewClient());
        }
    }


    private void CustomDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Application")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (binding.GameWeb.canGoBack()) {
                binding.GameWeb.goBack();
            } else {
                CustomDialog();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    } // Exit Functions
}



