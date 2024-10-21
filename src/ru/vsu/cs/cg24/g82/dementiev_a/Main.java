package ru.vsu.cs.cg24.g82.dementiev_a;

import ru.vsu.cs.cg24.g82.dementiev_a.model.Model;
import ru.vsu.cs.cg24.g82.dementiev_a.obj_reader.ObjReader;
import ru.vsu.cs.cg24.g82.dementiev_a.obj_writer.ObjWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        String BEFORE_FOLDER = "before/";
        String AFTER_FOLDER = "after/";
        File folder = new File("models/" + BEFORE_FOLDER);
        ObjWriter objWriter = new ObjWriter();
        for (File file : folder.listFiles()) {
            Model model = read(BEFORE_FOLDER + file.getName());
            objWriter.write(model, "models/" + AFTER_FOLDER + file.getName());
            Model model2 = read(AFTER_FOLDER + file.getName());
            if (!model.equals(model2)) {
                System.out.println("Model has changed after writing: " + file.getName());
                return;
            }
        }
        System.out.println("All models stay the same after writing and reading again");
    }

    private static Model read(String filename) throws IOException {
        Path fileName = Path.of("models/" + filename);
        String fileContent = Files.readString(fileName);
        System.out.println("Loading model " + filename);
        return ObjReader.read(fileContent);
    }
}
