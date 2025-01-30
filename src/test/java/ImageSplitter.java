import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class ImageSplitter {
    private static final String source = "images.sql";
    private static final String target = "src/main/resources/db/migration/V1_1_8_{n}__images.sql";

    private BufferedWriter chunk;
    private int chunkSize;
    private int chunkCount;

    @SneakyThrows
    public ImageSplitter() {
        processImageFile();
    }

    private void processImageFile() throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(source))) {
            br.lines().forEach(this::processLine);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeChunk();
        }
    }

    @SneakyThrows
    private void processLine(String line) {
        int lineSize = line.length() + 1;
        if (chunkSize + lineSize > CHUNK_SIZE) {
            closeChunk();
        }
        if (chunk == null) {
            chunk = createNewFileChunk(chunkCount);
            chunkSize = 0;
            chunkCount++;
        }
        writeToChunk(line);
        chunkSize += lineSize;
    }

    private void writeToChunk(String line) throws IOException {
        chunk.write(line);
        chunk.write("\n");
    }

    @SneakyThrows
    private void closeChunk() {
        if (chunk != null) {
            chunk.flush();
            chunk.close();
            chunk = null;
        }
    }

    private @NotNull BufferedWriter createNewFileChunk(int n) throws IOException {
        return new BufferedWriter(new FileWriter(target.replace("{n}", String.valueOf(n))));
    }

    public static void main(String[] args) {
        new ImageSplitter();
    }

    private static final int CHUNK_SIZE = 90 * 1025 * 1024; // 90MB
}