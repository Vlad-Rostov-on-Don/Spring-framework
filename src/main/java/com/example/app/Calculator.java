package com.example.app;

import java.util.List;

@Cacheable
public interface Calculator {
    List<Integer> fibonachi (int n);
}