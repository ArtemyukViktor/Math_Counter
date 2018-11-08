package com.example.viktor.math_counter.GameTypeOperation;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MultiplyOperation implements StrategyGameType{
    Context context;
    int tempNumb1;
    int tempNumb2;
    int result;

    List<Integer> list;

    Map<String,String> stringIntegerMap;


    public MultiplyOperation(Context context) {
        this.context = context;
        list = new ArrayList<>();
        stringIntegerMap = new HashMap<>();

    }

    @Override
    public Map<String,String> getAlgoritm(  int lavel) {

        List<Integer> listNumber =  random(lavel);


        int val1 = listNumber.get(0);
        int val2 = listNumber.get(1);

        result = val1 * val2;

        String expression = String.valueOf(val1) + " * " + String.valueOf(val2);

        stringIntegerMap.put("keyExpression", expression);
        stringIntegerMap.put("keyResult", String.valueOf(result));

        listNumber.clear();////////////
        return stringIntegerMap;

    }

    @Override
    public List<Integer> random(int lavel) {

        Random random1 = new Random();
        Random random2 = new Random();

        if (lavel == 0){
            Toast.makeText(context, "Your static variable is null", Toast.LENGTH_SHORT).show();
            lavel = 50;
        }

        tempNumb1 = random1.nextInt(lavel);
        tempNumb2 = random1.nextInt(lavel);

        list.add(tempNumb1);
        list.add(tempNumb2);

        return list;
    }

}
