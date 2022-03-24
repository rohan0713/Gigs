package com.example.gigs;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import android.os.Handler;
import java.util.logging.LogRecord;

public class TypeWriter extends androidx.appcompat.widget.AppCompatTextView {

    private CharSequence myText;
    private int myIndex;
    private long delay = 100;
    private int count = 0;

    public TypeWriter(Context context) {
        super(context);
    }

    public TypeWriter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler handler = new Handler();

    public Runnable characterAdder = new Runnable() {
        @Override
        public void run() {

            setText(myText.subSequence(0, myIndex++));
            if(myIndex <= myText.length()){
                handler.postDelayed(characterAdder, delay);
            }else{
                myIndex = 0;
                if(count == 0){
                    myText = "Loans";
                    delay = 300;
                    count++;
                }
                else if(count == 1){
                    myText = "Credit Cards";
                    delay = 100;
                    count++;
                }
                else{
                    myText = "Saving Accounts";
                    delay = 100;
                    count = 0;
                }
                handler.postDelayed(characterAdder, delay);
            }
        }
    };

    public void animateText(CharSequence myTxt){

        myText = myTxt;
        myIndex = 0;

        setText("");

        handler.removeCallbacks(characterAdder);
        handler.postDelayed(characterAdder, delay);
    }

    public void setCharacterDelay(long m){

        delay = m;
    }
}
