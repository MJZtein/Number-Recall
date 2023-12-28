package fcb8.Number.Recall;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import fcb8.Number.Recall.Repository.Animations;
import fcb8.Number.Recall.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {
    private ActivityAboutBinding binding;
    private Animations TheAnimation;
    private String textToType3, textToType4;
    private int charIndex3 = 0;
    private int charIndex4 = 0;
    Handler HandlerDelay = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TheAnimation = new Animations(this);
        TheAnimation.getRotatingAnimation(binding.NavBar);

        MainMenu();
    }

    private void MainMenu() {
        TypeCredits3();
        FontStyle();
    }
    private void FontStyle() {
        Typeface customFont = Typeface.createFromAsset(getAssets(), "font.otf");
        binding.Text3.setTypeface(customFont);
        binding.Text4.setTypeface(customFont);
    }

    private void TypeCredits3() {
        textToType3 = getString(R.string.About1);
        binding.Text3.setVisibility(View.VISIBLE);

        HandlerDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (charIndex3 <= textToType3.length()) {
                    String displayedText = textToType3.substring(0, charIndex3);
                    binding.Text3.setText(displayedText);
                    charIndex3++;
                    HandlerDelay.postDelayed(this, 100);
                } else {
                    HandlerDelay.postDelayed(() -> TypeCredits4(), 500);
                }
            }
        }, 50);
    }

    private void TypeCredits4() {
        textToType4 = getString(R.string.About2);
        binding.Text4.setVisibility(View.VISIBLE);

        HandlerDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (charIndex4 <= textToType4.length()) {
                    String displayedText = textToType4.substring(0, charIndex4);
                    binding.Text4.setText(displayedText);
                    charIndex4++;
                    HandlerDelay.postDelayed(this, 20);
                }
            }
        }, 500);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    } // Exit Functions
}
