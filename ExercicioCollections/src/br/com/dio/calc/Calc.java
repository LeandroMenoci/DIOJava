package br.com.dio.calc;

@FunctionalInterface
public interface Calc {

    long exec(long... numbers);
}
