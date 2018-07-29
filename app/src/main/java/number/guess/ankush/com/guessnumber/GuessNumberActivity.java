package number.guess.ankush.com.guessnumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class GuessNumberActivity extends AppCompatActivity {
    public static Random random = new Random();
    public static final int MAX = 100;
    public static final int MIN = 1;
    public static int numberToBeGuessed = random.nextInt(MAX) + MIN;
    public static int trials = 0;
    public static final int MAX_TRIALS = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_number);

        final TextView trialsText = findViewById(R.id.trials);
        final TextView message = findViewById(R.id.message);
        trialsText.setText("Chances remaining : " + (MAX_TRIALS  - trials));
        final boolean[] correctGuessed = {false};

        final EditText editText = (EditText) findViewById(R.id.number);
        editText.setText(null);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                boolean handled = false;
                if ((actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
                        && (event == null || event.getAction() == KeyEvent.ACTION_DOWN)) {

                    int guessedNumber = 0;
                    String value = textView.getText().toString();
                    try {
                        guessedNumber = Integer.parseInt(value);
                    } catch (Exception e) {
                        editText.setText(null);
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();
                        handled = false;
                        return handled;

                    }
                    if (guessedNumber == numberToBeGuessed) {
                        correctGuessed[0] = true;
                        editText.setVisibility(View.GONE);
                        message.setText("Awesome! You are great.");
//                        System.out.println("Correct!");
                    } else if (guessedNumber < numberToBeGuessed) {
                        message.setText("Oops You Missed! Try higher number than this.");
//                        System.out.println("High!");
                    } else {
                        message.setText("Oops You Missed! Try smaller number than this.");
//                        System.out.println("Low!");
                    }
                    handled = true;
                    trials++;
                    trialsText.setText("Chances remaining " + (MAX_TRIALS  - trials));


                    editText.setText(null);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();

                    if (MAX_TRIALS == trials && !correctGuessed[0]) {
//                        System.out.println("game over");
                        message.setText("Game over! Correct Number was " + numberToBeGuessed);
                        editText.setVisibility(View.GONE);
                        return true;
                    }
                } else {
                    editText.setText(null);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    return true;
                }
                return handled;
            }
        });
    }

    public void sendMessage(View view) {
        // Do something in response to button click
        recreate();
        final TextView trialsText = findViewById(R.id.trials);
        trials = 0;
        trialsText.setText("Trials remaining : " + (MAX_TRIALS  - trials));
        numberToBeGuessed = random.nextInt(MAX) + MIN;
    }

    @Override
    public void recreate()
    {
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            super.recreate();
        }
        else
        {
            startActivity(getIntent());
            finish();
        }
    }
}
