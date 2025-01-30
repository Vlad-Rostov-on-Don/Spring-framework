package com.example.app;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CalculatorImpl implements Calculator {

    @Override
    @Cacheable
    public List<Integer> fibonachi(int n) {
        List<Integer> result = new ArrayList<>();
        int a = 0, b = 1;
        for (int y = 0; y < n; y++) {
            result.add(a);
            int temp = a;
            a = b;
            b = temp + b;
        }
        return result;
    }
}
