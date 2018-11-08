package com.example.viktor.math_counter.GameTypeOperation;

import java.util.List;
import java.util.Map;

public interface StrategyGameType {

    Map<String, String> getAlgoritm(int lavel  );

    List<? extends Number> random(int lavel);
}
