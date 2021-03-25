package com.example.healthemanager1.application;

import android.content.Context;

public class Step {
    private Context mcontext;
    private String step;
    public Step(Context context){
        mcontext=context;

    }

    public void setStep(String step)
    {
        this.step=step;
    }
    public String getStep()
    {
        return step;
    };

}
