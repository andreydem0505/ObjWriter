package ru.vsu.cs.cg24.g82.dementiev_a.obj_writer;

import ru.vsu.cs.cg24.g82.dementiev_a.math.Vector2f;
import ru.vsu.cs.cg24.g82.dementiev_a.math.Vector3f;
import ru.vsu.cs.cg24.g82.dementiev_a.model.Model;
import ru.vsu.cs.cg24.g82.dementiev_a.model.Polygon;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ObjWriter {
    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";

    public void write(Model model, String filename) {
        File file = new File(filename);
        try {
            if (!file.createNewFile())
                System.out.println("Warning: " + filename + " already exists");
            PrintWriter writer = new PrintWriter(file);
            for (Vector3f vector : model.vertices) {
                writer.println(vertexToString(vector));
            }
            for (Vector2f vector : model.textureVertices) {
                writer.println(textureVertexToString(vector));
            }
            for (Vector3f vector : model.normals) {
                writer.println(normalToString(vector));
            }
            for (Polygon polygon : model.polygons) {
                writer.println(polygonToString(polygon));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while creating the file");
        }
    }

    protected String vertexToString(Vector3f vector) {
        return OBJ_VERTEX_TOKEN + " " + vector.getX() + " " + vector.getY() + " " + vector.getZ();
    }

    protected String textureVertexToString(Vector2f vector) {
        return OBJ_TEXTURE_TOKEN + " " + vector.getX() + " " + vector.getY();
    }

    protected String normalToString(Vector3f vector) {
        return OBJ_NORMAL_TOKEN + " " + vector.getX() + " " + vector.getY() + " " + vector.getZ();
    }

    protected String polygonToString(Polygon polygon) {
        StringBuilder stringBuilder = new StringBuilder(OBJ_FACE_TOKEN);
        List<Integer> vertexIndices = polygon.getVertexIndices();
        List<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
        List<Integer> normalIndices = polygon.getNormalIndices();
        boolean hasTextures = textureVertexIndices.size() == vertexIndices.size();
        boolean hasNormals = normalIndices.size() == vertexIndices.size();
        for (int i = 0; i < vertexIndices.size(); i++) {
            stringBuilder.append(" ")
                    .append(getFormattedIndex(vertexIndices, i));
            if (hasNormals) {
                stringBuilder.append("/");
                if (hasTextures) {
                    stringBuilder.append(getFormattedIndex(textureVertexIndices, i))
                            .append("/")
                            .append(getFormattedIndex(normalIndices, i));
                } else {
                    stringBuilder.append("/")
                            .append(getFormattedIndex(normalIndices, i));
                }
            } else {
                if (hasTextures) {
                    stringBuilder.append("/")
                            .append(getFormattedIndex(textureVertexIndices, i));
                }
            }
        }
        return stringBuilder.toString();
    }

    private int getFormattedIndex(List<Integer> indices, int index) {
        return indices.get(index) + 1;
    }
}
