package br.com.dio.persistence;

public interface FilePersistence {

    String write(String data);

    boolean remove(String setence);

    String replace(String oldContent, String newContent);

    String findAll();

    String findBy(String setence);
}
