package fcb8.Number.Recall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import fcb8.Number.Recall.databinding.ActivityGameBinding;

public class GameActivity extends AppCompatActivity {
    private ActivityGameBinding Binding;
    private final StringBuilder inputStringBuilder = new StringBuilder();
    private int bestStreak = 0;
    private static final String BEST_STREAK_KEY = "best_streak";
    Handler HandlerDelay = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(Binding.getRoot());

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        bestStreak = preferences.getInt(BEST_STREAK_KEY, 0);

        FontStyle();
        LoadGame();
    }
    @SuppressLint("SetTextI18n")
    private void LoadGame() {
        setStart();
        setNumberButtonClickListener(Binding.n0);
        setNumberButtonClickListener(Binding.n1);
        setNumberButtonClickListener(Binding.n2);
        setNumberButtonClickListener(Binding.n3);
        setNumberButtonClickListener(Binding.n4);
        setNumberButtonClickListener(Binding.n5);
        setNumberButtonClickListener(Binding.n6);
        setNumberButtonClickListener(Binding.n7);
        setNumberButtonClickListener(Binding.n8);
        setNumberButtonClickListener(Binding.n9);
        setC();
        setCE();
        setAbout();
        RestartButton(Binding.nR);
        CheckAnswer(Binding.nS);

        Binding.Rounds.setText("");
        Binding.RecallStreak.setText("Current Round: 0");
        Binding.BestStreak.setText("Best Round: " + bestStreak);
        Binding.nS.setEnabled(false);
        Binding.nR.setEnabled(false);
    }

    private void setNewQuestion() {
        Binding.n0.setEnabled(false);
        Binding.n1.setEnabled(false);
        Binding.n2.setEnabled(false);
        Binding.n3.setEnabled(false);
        Binding.n4.setEnabled(false);
        Binding.n5.setEnabled(false);
        Binding.n6.setEnabled(false);
        Binding.n7.setEnabled(false);
        Binding.n8.setEnabled(false);
        Binding.n9.setEnabled(false);
        Binding.lC.setEnabled(false);
        Binding.lCE.setEnabled(false);
        Binding.nS.setEnabled(false);

        HandlerDelay.postDelayed(() -> {
            int currentRound = getCurrentRound();
            int randomNumber = generateRandomNumber(currentRound);
            displayDigitsOneByOne(randomNumber, 0);
        }, 1500);
    }

    private void displayDigitsOneByOne(final int number, final int index) {
        Binding.Question.setVisibility(View.VISIBLE);
        if (index < String.valueOf(number).length()) {
            Binding.Question.setText(String.valueOf(number).substring(index, index + 1));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Binding.Question.setText("");
                }
            }, 500);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayDigitsOneByOne(number, index + 1);
                }
            }, 1000);
        } else {
            Binding.Question.setText(String.valueOf(number));
            Binding.Question.setVisibility(View.INVISIBLE);
            Binding.n0.setEnabled(true);
            Binding.n1.setEnabled(true);
            Binding.n2.setEnabled(true);
            Binding.n3.setEnabled(true);
            Binding.n4.setEnabled(true);
            Binding.n5.setEnabled(true);
            Binding.n6.setEnabled(true);
            Binding.n7.setEnabled(true);
            Binding.n8.setEnabled(true);
            Binding.n9.setEnabled(true);
            Binding.lC.setEnabled(true);
            Binding.lCE.setEnabled(true);
            Binding.nS.setEnabled(true);
        }
    }

    private int getCurrentRound() {
        // Get the current rounds count from the Rounds TextView
        String currentRoundsStr = Binding.Rounds.getText().toString();

        // Extract the numeric part of the string (remove non-numeric characters)
        String numericPart = currentRoundsStr.replaceAll("[^\\d.]", "");

        // Check if the numeric part is not empty
        if (!numericPart.isEmpty()) {
            // Parse the string to get the integer value
            return Integer.parseInt(numericPart);
        }

        // Default to round 1 if parsing fails
        return 1;
    }

    private int generateRandomNumber(int length) {
        Random random = new Random();
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length) - 1;
        return min + random.nextInt(max - min + 1);
    }

    private void setNumberButtonClickListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Append the clicked number to the inputStringBuilder
                inputStringBuilder.append(button.getText());
                // Update the Answer TextView with the current input
                Binding.Answer.setText(inputStringBuilder.toString());
            }
        });
    }

    private void setCE() {
        Binding.lCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputStringBuilder.setLength(0);
                Binding.Answer.setText("");
            }
        });
    }
    private void setAbout() {
        Binding.lq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setStart() {
        Binding.StartGame.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Binding.StartGame.setVisibility(View.GONE);
                Binding.nS.setEnabled(true);
                Binding.nR.setEnabled(true);
                Restart();
            }
        });
    }

    private void setC() {
        Binding.lC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText = Binding.Answer.getText().toString();
                if (!currentText.isEmpty()) {
                    String newText = currentText.substring(0, currentText.length() - 1);
                    inputStringBuilder.setLength(newText.length());
                    Binding.Answer.setText(newText);
                }
            }
        });
    }

    private void CheckAnswer(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // Check if the current input matches the question
                String currentInput = Binding.Answer.getText().toString();
                String currentQuestion = Binding.Question.getText().toString();

                if (currentInput.equals(currentQuestion)) {
                    Binding.Question.setVisibility(View.VISIBLE);
                    Binding.InputStatus.setVisibility(View.VISIBLE);
                    Binding.InputStatus.setTextColor(Color.GREEN);
                    Binding.InputStatus.setText("Correct Input");

                    updateRounds();
                    incrementRecallStreak();
                    setNewQuestion();

                    HandlerDelay.postDelayed(() -> {
                        inputStringBuilder.setLength(0);
                        Binding.Answer.setText("");
                        Binding.InputStatus.setVisibility(View.INVISIBLE);
                    }, 1500);
                } else {
                    Binding.Question.setVisibility(View.VISIBLE);
                    Binding.InputStatus.setVisibility(View.VISIBLE);
                    Binding.InputStatus.setText("Incorrect Input");
                    Binding.InputStatus.setTextColor(Color.RED);
                    Binding.n0.setEnabled(false);
                    Binding.n1.setEnabled(false);
                    Binding.n2.setEnabled(false);
                    Binding.n3.setEnabled(false);
                    Binding.n4.setEnabled(false);
                    Binding.n5.setEnabled(false);
                    Binding.n6.setEnabled(false);
                    Binding.n7.setEnabled(false);
                    Binding.n8.setEnabled(false);
                    Binding.n9.setEnabled(false);
                    Binding.lC.setEnabled(false);
                    Binding.lCE.setEnabled(false);
                    Binding.nS.setEnabled(false);
                    HandlerDelay.postDelayed(() -> {
                        Binding.InputStatus.setText("Game Over");
                    }, 2000);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void incrementRecallStreak() {
        String currentRecallStreakStr = Binding.RecallStreak.getText().toString();
        String numericPart = currentRecallStreakStr.replaceAll("[^\\d.]", "");

        if (!numericPart.isEmpty()) {
            // Parse the string to get the integer value
            int currentRecallStreak = Integer.parseInt(numericPart);
            int newRecallStreak = currentRecallStreak + 1;
            Binding.RecallStreak.setText("Current Round: " + newRecallStreak);
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateRounds() {
        String currentRoundsStr = Binding.Rounds.getText().toString();
        String numericPart = currentRoundsStr.replaceAll("[^\\d.]", "");

        if (!numericPart.isEmpty()) {
            int currentRounds = Integer.parseInt(numericPart);
            int newRounds = currentRounds + 1;
            Binding.Rounds.setText("Round " + newRounds);

            // Update the high score whenever a round is successfully completed
            bestStreak = Math.max(bestStreak, newRounds);
            Binding.BestStreak.setText("Best Round: " + bestStreak);

            // Save the new high score to SharedPreferences
            SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(BEST_STREAK_KEY, bestStreak);
            editor.apply();
        }
    }

    private void RestartButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestartDialog();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void Restart() {
        Binding.Question.setVisibility(View.INVISIBLE);
        Binding.InputStatus.setVisibility(View.INVISIBLE);
        Binding.Rounds.setText("Round 1");
        Binding.RecallStreak.setText("Current Round: 1");
        Binding.BestStreak.setText("Best Round: " + bestStreak);
        inputStringBuilder.setLength(0);
        Binding.Answer.setText("");
        setNewQuestion();
    }

    private void FontStyle() {
        Typeface customFont = Typeface.createFromAsset(getAssets(), "font.otf");
        Binding.RecallStreak.setTypeface(customFont);
        Binding.BestStreak.setTypeface(customFont);
        Binding.Rounds.setTypeface(customFont);
        Binding.InputStatus.setTypeface(customFont);
        Binding.Question.setTypeface(customFont);
    }

    private void RestartDialog() {
        new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle)
                .setTitle("Restart")
                .setMessage("Are you sure you want to restart?")
                .setPositiveButton("Yes", (dialog, which) -> Restart())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void CustomDialog() {
        new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle)
                .setTitle("Main Menu")
                .setMessage("Are you sure you want to go back?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            CustomDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    } // Exit Functions
}