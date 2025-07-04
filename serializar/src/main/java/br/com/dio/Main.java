package br.com.dio;

import br.com.dio.model.Person;
import br.com.dio.model.User;
import br.com.dio.processor.SerializerProcessor;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        var processor = new SerializerProcessor();
        System.out.println(processor.serializer(new Person(1, "Joao da Silva", 26)));
        System.out.println(processor.serializer(new User(2, "Maria Domingos", 30, 3222.23)));
    }
}