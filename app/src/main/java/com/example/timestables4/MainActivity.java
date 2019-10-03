package com.example.timestables4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


/*
    The app starts by asking the user for a max value of the times table
        Note: This app could do larger values but for runtime issues it is maxxed at 100
    After inputting a valid number (it only takes positive integers) and clicking start
    the text and start button become invisible and check, restart, two numbers, symbol,
    and pairs left views become visible
    The randomize() function creates a random list of Times Table pairs with no duplicates to use
    The changePair() function displays a new pair to the screen
    The two visibility functions just reverse visibility of the given states
    Each pair is a TimesPair, which contains two numbers and their product
    When a user guesses correctly a new pair is shown and the previous is removed from the deck
    Once a guess is made right/wrong a new pair is displayed
    When everything is guessed correctly a Completed msg displays

    Null input, decimals, and negative numbers are not possible as of testing

 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<TimesPair> timesTable;
    private TimesPair currentPair;
    private int currentPairIndex;
    private int maxValue = 0;


    private void randomize() {
        this.timesTable = new ArrayList<>();


        for (int i = 0; i <= this.maxValue; i++) {
            for (int j = i; j <= this.maxValue; j++) {
                TimesPair pair = new TimesPair(i, j);
                this.timesTable.add(pair);
                //System.out.println(pair);
            }
        }

        ArrayList<TimesPair> temp = new ArrayList<>();
        for (int i = 0; i < this.timesTable.size(); i++) {
            int index = (int) (Math.random() * this.timesTable.size());
            temp.add(this.timesTable.get(index));
            this.timesTable.remove(index);
            i--;
        }
        this.timesTable = temp;
        changeStartVisibility(false);
        changeGameVisibility(true);
        changePair();
    }

    public void changeGameVisibility(boolean bool) {
        int visible;
        if (bool)
            visible = View.VISIBLE;
        else
            visible = View.INVISIBLE;

        TextView textNum1 = findViewById(R.id.num1);
        TextView textNum2 = findViewById(R.id.num2);
        TextView symbol = findViewById(R.id.multiplySymbol);
        TextView remainingText = findViewById(R.id.remainingText);
        Button checkBtn = findViewById(R.id.checkBtn);
        Button restartBtn = findViewById(R.id.restartBtn);
        textNum1.setVisibility(visible);
        textNum2.setVisibility(visible);
        remainingText.setVisibility(visible);
        symbol.setVisibility(visible);
        checkBtn.setVisibility(visible);
        restartBtn.setVisibility(visible);
    }

    public void changeStartVisibility(boolean bool) {
        int visible;
        if (bool)
            visible = View.VISIBLE;
        else
            visible = View.INVISIBLE;

        TextView maxValueText = findViewById(R.id.maxValueText);
        Button startBtn = findViewById(R.id.startBtn);
        maxValueText.setVisibility(visible);
        startBtn.setVisibility(visible);

    }

    public void changePair() {
        updateRemainder();
        TextView textNum1 = findViewById(R.id.num1);
        TextView textNum2 = findViewById(R.id.num2);
        this.currentPairIndex = (int) (Math.random() * this.timesTable.size());
        this.currentPair = this.timesTable.get(this.currentPairIndex);
        textNum1.setText(Integer.toString(this.currentPair.getNum1()));
        textNum2.setText(Integer.toString(this.currentPair.getNum2()));
    }

    public void updateRemainder() {
        TextView remainingText = findViewById(R.id.remainingText);
        remainingText.setText("Pairs remaining: " + this.timesTable.size());
    }

    public void onClick(View view) {

        EditText input = findViewById(R.id.input);
        TextView completeText = findViewById(R.id.completeText);
        if (view.getId() == findViewById(R.id.checkBtn).getId()) {
            if (!input.getText().toString().equals("")) {
                if (Integer.parseInt(input.getText().toString()) == this.currentPair.getProduct()) {
                    this.timesTable.remove(this.currentPairIndex);
                }
                input.setText("");
                updateRemainder();
                if (this.timesTable.size() == 0) {
                    completeText.setVisibility(View.VISIBLE);
                    TextView text = findViewById(R.id.num1);
                    text.setText("-");
                    text = findViewById(R.id.num2);
                    text.setText("-");
                }
                else
                    changePair();
                //System.out.println(this.timesTable.size());
            }
        }

        if (view.getId() == findViewById(R.id.restartBtn).getId()) {
            //randomize();
            changeGameVisibility(false);
            changeStartVisibility(true);
            completeText.setVisibility(View.INVISIBLE);
            input.setText("");
        }

        if (view.getId() == findViewById(R.id.startBtn).getId()) {
            if (!input.getText().toString().equals("")) {
                this.maxValue = Integer.parseInt(input.getText().toString());
                if (this.maxValue < 100) {
                    randomize();
                    changeGameVisibility(true);
                    changeStartVisibility(false);
                }
                input.setText("");
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeGameVisibility(false);
        changeStartVisibility(true);


        final Button checkBtn = findViewById(R.id.checkBtn);
        Button restartBtn = findViewById(R.id.restartBtn);
        final Button startBtn = findViewById(R.id.startBtn);
        EditText input = findViewById(R.id.input);
        startBtn.setOnClickListener(this);
        checkBtn.setOnClickListener(this);
        restartBtn.setOnClickListener(this);


        // Allows the use of enter instead of clicking check for ease of use
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    //do what you want on the press of 'done'
                    if (findViewById(R.id.checkBtn).getVisibility() == View.VISIBLE)
                        checkBtn.performClick();
                    else
                        startBtn.performClick();
                }
                return false;
            }
        });

    }
}
