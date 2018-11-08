package com.example.viktor.math_counter.GameTypeOperation;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomOperation implements StrategyGameType{
    Context context;
    int tempNumb1;
    int tempNumb2;
    int result;
    Random random;
    public int  tempNumbRndom =0;

    List<Integer> list;

    Map<String,String> stringIntegerMap;


    public RandomOperation(Context context) {
        this.context = context;
        list = new ArrayList<>();
        stringIntegerMap = new HashMap<>();

    }

    @Override
    public Map<String,String> getAlgoritm(  int lavel) {

        List<Integer> listNumber =  random(lavel);


        int val1 = listNumber.get(0);
        int val2 = listNumber.get(1);
        random = new Random();
        tempNumbRndom = random.nextInt(4);

        switch (tempNumbRndom ){
            case 0:{
                result = val1 + val2;
                String expression = String.valueOf(val1) + " + " + String.valueOf(val2);
                stringIntegerMap.put("keyExpression", expression);
                stringIntegerMap.put("keyResult", String.valueOf(result));
                break;
            }  case 1:{
                int a = Math.max(val1,val2);
                int b = Math.min(val1,val2);
                result = a - b;
                String expression = String.valueOf(val1) + " - " + String.valueOf(val2);
                stringIntegerMap.put("keyExpression", expression);
                stringIntegerMap.put("keyResult", String.valueOf(result));

                break;
            }  case 2:{
                result = val1 * val2;
                String expression = String.valueOf(val1) + " * " + String.valueOf(val2);
                stringIntegerMap.put("keyExpression", expression);
                stringIntegerMap.put("keyResult", String.valueOf(result));

                break;
            }  case 3:{
                result = val1 / val2;
                String expression = String.valueOf(val1) + " / " + String.valueOf(val2);
                stringIntegerMap.put("keyExpression", expression);
                stringIntegerMap.put("keyResult", String.valueOf(result));

                break;
            }default:{

            }
        }


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

        int tmp1 = tempNumb1*tempNumb2;
        if (tmp1 == 0){
            tmp1 =1;
        }
        int tmp2 = Math.min(tempNumb1, tempNumb2);

//        int a = Math.max(tmp1,tmp2);
//        int b = Math.min(tmp1,tmp2);
        if (tmp2 == 0){// is null
            tmp2=1;
        }

        list.add(tmp1);
        list.add(tmp2);

        return list;
    }
}
