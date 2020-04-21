package com.app.mobilize.Pojo;

public class Chrono {
    long timeElapsed;
    long startTime;
    boolean is_running, is_finished, canStart;

    public Chrono(){
        is_finished = false;
        is_running = false;
        canStart = true;
    }

    public long actualChrono (long actualTime) {
        if ( is_running == true ) return timeElapsed + (actualTime-startTime);
        else return timeElapsed;
    }

    public boolean startChrono ( long startTime ) {
        if ( canStart == true && is_running == false && is_finished == false ) {
            timeElapsed = 0;
            this.startTime = startTime;
            is_running = true;
            canStart = false;
            return true;
        }
        else return false;
    }

    public void resumeChrono ( long resumeTime ){
        if ( is_running == false && is_finished == false ) {
            startTime = resumeTime;
        }
        is_running = true;
    }

    public void stopChrono ( long stopTime ) {
        timeElapsed = timeElapsed + (stopTime-startTime);
        is_running = false;
    }

    public void finishChrono ( long finishTime ){
        timeElapsed = timeElapsed + (finishTime-startTime);
        is_finished = true;
    }
}
