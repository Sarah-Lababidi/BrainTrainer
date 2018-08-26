package com.saralababidi.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button go;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button playAgain;
    TextView time;
    TextView operation;
    TextView resultView;
    TextView message;
    int result;
    int score = 0;
    int numOfQuestions = 0;
    int rand1, rand2, num1, num2, num3, index, i, j;
    int     [] nums = {1, 1, 1, 1};
    int     [] randomlyPositionedNums = {1, 1, 1, 1};
    Button  [] buttons = new Button[4];
    boolean timeIsUp = false;
    RelativeLayout relativeLayout;

    public void generateTurn(){
        if(!timeIsUp){
            numOfQuestions++;
            Random rand = new Random();
            rand1 = rand.nextInt(20) + 1;
            rand2 = rand.nextInt(20) + 1;
            result = rand1 + rand2;
            num1 = rand.nextInt(40) + 1;
            num2 = rand.nextInt(40) + 1;
            num3 = rand.nextInt(40) + 1;
            // the array that i want to arrange randomly
            nums [0] = num1;
            nums [1] = num2;
            nums [2] = num3;
            nums [3] = result;
            // position the values in nums randomly in a new array
            i = 0;
            while(!allIsNegOne(nums)){
                index = rand.nextInt(4);
                if(nums[index]!=-1){
                    randomlyPositionedNums[i] = nums [index];
                    i++;
                    nums[index] = -1;
                }
            }
            // set the operation to the textView
            operation.setText(rand1 + " + " + rand2);

            // set the values to the buttons
            j = 0;
            for(Button button: buttons){
                button.setText(Integer.toString(randomlyPositionedNums[j]));
                j++;
            }
        }
    }
    public boolean allIsNegOne(int [] array){
        for(int num : array){
            if(num != -1){
                return false;
            }
        }
        return true;
    }
    public void goClicked(View view) {
        go.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        buttons[0] = button1;
        buttons[1] = button2;
        buttons[2] = button3;
        buttons[3] = button4;
        generateTurn();
        startTimer();
    }
    public void buttonClicked (View view){
        Button buttonPressed = (Button) view;
        if(Integer.parseInt(buttonPressed.getText().toString()) == result){
            message.setText("Correct!");
            score++;
            resultView.setText(score + "/" + numOfQuestions);
        } else {
            message.setText("Wrong!");
            resultView.setText(score + "/" + numOfQuestions);
        }
        generateTurn();
    }
    public void playAgain (View view){
        score = 0;
        numOfQuestions = 0;
        resultView.setText("0/0");
        for(int i =0; i<4; i++){
            buttons[i].setEnabled(true);
        }
        timeIsUp = false;
        message.setText("");
        playAgain.setVisibility(View.INVISIBLE);
        nums [0] = 1;
        nums [1] = 1;
        nums [2] = 1;
        nums [3] = 1;
        generateTurn();
        startTimer();
    }
    public void startTimer(){
        new CountDownTimer(30000+100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText(millisUntilFinished/1000 + "s");
            }

            @Override
            public void onFinish() {
                time.setText("0s");
                timeIsUp = true;
                for(int i=0; i<4; i++){
                    buttons[i].setEnabled(false);
                }
                message.setText("Your score: " + score + "/" + numOfQuestions);
                playAgain.setVisibility(View.VISIBLE);
            }
        }.start();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go              = (Button)         findViewById(R.id.goButton);
        playAgain       = (Button)         findViewById(R.id.playAgain);
        time            = (TextView)       findViewById(R.id.time);
        operation       = (TextView)       findViewById(R.id.operation);
        message         = (TextView)       findViewById(R.id.message);
        resultView      = (TextView)       findViewById(R.id.resultView);
        relativeLayout  = (RelativeLayout) findViewById(R.id.relativeLayout);
    }
}
