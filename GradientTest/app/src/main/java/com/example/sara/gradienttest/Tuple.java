package com.example.sara.gradienttest;

/**
 * Created by Sara on 11/15/2015.
 */
public class Tuple {
    public final float x;
    public final float y;
    public int numInc;
    public int numDec;
    public Tuple(float x, float y, int inc, int dec) {
        this.x = x;
        this.y = y;
        this.numInc = inc;
        this.numDec = dec;
    }

    public void increment(boolean inc) {
        if(inc) {
            this.numInc++;
        }

        else {
            this.numDec++;
        }
    }

    public boolean checkDone() {
        return (this.numInc == 10 && this.numDec == 10);
    }

    public void reset() {
        this.numInc = 0;
        this.numDec = 0;
    }
}