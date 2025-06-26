package br.com.dio.persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NIO2FilePersistence implements FilePersistence{

    private final String currentDir = System.getProperty("user.dir");
    private final String storedDir = "/managedFiles/NIO2/";
    private final String fileName;

    public NIO2FilePersistence(String fileName) throws IOException {
        this.fileName = fileName;
        var path = Paths.get(currentDir + storedDir);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        clearFile();
    }

    @Override
    public String write(String data) {
        var path = Paths.get(currentDir + storedDir + fileName);
        try{
            Files.write(path, data.getBytes(), StandardOpenOption.APPEND);
            Files.write(path, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public boolean remove(String setence) {
        var contentList = toListString();
        if(contentList.stream().noneMatch(c -> c.contains(setence))) return false;
        clearFile();
        contentList.stream()
                .filter(c -> !c.contains(setence))
                .forEach(this::write);
        return true;
    }

    @Override
    public String replace(String oldContent, String newContent) {
        var contentList = toListString();

        if(contentList.stream().noneMatch(c -> c.contains(oldContent))) return "";

        clearFile();
        contentList.stream()
                .map(c -> c.contains(oldContent) ? newContent : c)
                .forEach(this::write);

        return newContent;
    }

    @Override
    public String findAll() {
        var path = Paths.get(currentDir + storedDir + fileName);
        var content = "";
        try(var lines = Files.lines(path)) {
            content = lines.collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return content;
    }

    @Override
    public String findBy(String setence) {
        var content = findAll();
        return Stream.of(content.split(System.lineSeparator()))
                .filter((c -> c.contains(setence)))
                .findFirst()
                .orElse("");
    }

    private void clearFile() {
        try (OutputStream outputStream = new FileOutputStream(currentDir + storedDir + fileName)) {
//            System.out.printf("Inicilizando recursos (%s) \n", currentDir + storedDir + fileName);
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private List<String> toListString() {
        var content = findAll();
        return new ArrayList<>(Stream.of(content.split(System.lineSeparator())).toList());
    }
}
