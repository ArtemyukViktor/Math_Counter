package com.example.viktor.math_counter.Util;

import android.content.Context;
import android.content.Intent;

public class Util {

    public void openActivity(Context context, Class aClass){

        Intent intent = new Intent(context, aClass);
        context.startActivity(intent);
    }


}
