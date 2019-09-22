package net.thumbtack.school.file;

import net.thumbtack.school.ttschool.Trainee;
import net.thumbtack.school.ttschool.TrainingException;
import net.thumbtack.school.windows.v4.Point;
import net.thumbtack.school.windows.v4.RectButton;
import net.thumbtack.school.windows.v4.base.WindowException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestFileService {
    @TempDir
    public Path tempDir;


    @Test
    public void testFileReadWriteByteArray1() throws IOException {
        byte[] arrayToWrite = {0, 1, 5, -34, 67, -123};
        File file = Files.createFile(tempDir.resolve("test.dat")).toFile();
        FileService.writeByteArrayToBinaryFile(file, arrayToWrite);
        assertTrue(file.exists());
        assertEquals(arrayToWrite.length, file.length());
        byte[] arrayRead = FileService.readByteArrayFromBinaryFile(file);
        assertArrayEquals(arrayToWrite, arrayRead);
    }

    @Test
    public void testWriteByteArrayToBinaryFileException() {
        assertThrows(IOException.class, () -> FileService.writeByteArrayToBinaryFile("", null));
    }

    @Test
    public void testFileReadWriteByteArray2() throws IOException {
        byte[] arrayToWrite = {0, 1, 5, -34, 67, -123};
        String fileName = Files.createFile(tempDir.resolve("test.dat")).toFile().getAbsolutePath();
        FileService.writeByteArrayToBinaryFile(fileName, arrayToWrite);
        File file = new File(fileName);
        assertTrue(file.exists());
        assertEquals(arrayToWrite.length, file.length());
        byte[] arrayRead = FileService.readByteArrayFromBinaryFile(fileName);
        assertArrayEquals(arrayToWrite, arrayRead);
    }

    @Test
    public void testByteStreamReadWriteByteArray() throws IOException {
        byte[] arrayToWrite = {0, 1, 5, -34, 67, -123};
        byte[] result = FileService.writeAndReadByteArrayUsingByteStream(arrayToWrite);
        assertArrayEquals(new byte[]{0, 5, 67}, result);
    }

    @Test
    public void testFileReadWriteByteArray1Buffered() throws IOException {
        byte[] arrayToWrite = {0, 1, 5, -34, 67, -123};
        File file = Files.createFile(tempDir.resolve("test.dat")).toFile();
        FileService.writeByteArrayToBinaryFileBuffered(file, arrayToWrite);
        assertTrue(file.exists());
        assertEquals(arrayToWrite.length, file.length());
        byte[] arrayRead = FileService.readByteArrayFromBinaryFileBuffered(file);
        assertArrayEquals(arrayToWrite, arrayRead);
    }

    @Test
    public void testFileReadWriteByteArray2Buffered() throws IOException {
        byte[] arrayToWrite = {0, 1, 5, -34, 67, -123};
        String fileName = Files.createFile(tempDir.resolve("test.dat")).toFile().getAbsolutePath();
        FileService.writeByteArrayToBinaryFileBuffered(fileName, arrayToWrite);
        File file = new File(fileName);
        assertTrue(file.exists());
        assertEquals(arrayToWrite.length, file.length());
        byte[] arrayRead = FileService.readByteArrayFromBinaryFileBuffered(fileName);
        assertArrayEquals(arrayToWrite, arrayRead);
    }

    @Test
    public void testFileReadWriteRectButtonToBinaryFile() throws WindowException, IOException {
        Point topLeft1 = new Point(10, 20);
        Point bottomRight1 = new Point(30, 40);
        RectButton rectButton = new RectButton(topLeft1, bottomRight1, "OK");
        File file = Files.createFile(tempDir.resolve("test.dat")).toFile();
        FileService.writeRectButtonToBinaryFile(file, rectButton);
        assertTrue(file.exists());
        assertEquals(28, file.length());
        RectButton rectButtonRead = FileService.readRectButtonFromBinaryFile(file);
        assertEquals(rectButton, rectButtonRead);
    }

    @Test
    public void testFileReadRectButtonArrayBinary() throws WindowException, IOException {
        int count = 5;
        RectButton[] rectButtons = new RectButton[count];
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            rectButtons[i] = new RectButton(new Point(random.nextInt(), random.nextInt()), new Point(random.nextInt(), random.nextInt()),
                    "OK");
        }
        File file = Files.createFile(tempDir.resolve("test.dat")).toFile();
        FileService.writeRectButtonArrayToBinaryFile(file, rectButtons);
        assertTrue(file.exists());
        assertEquals(count * 16, file.length());
        FileService.modifyRectButtonArrayInBinaryFile(file);
        RectButton[] rectsRead = FileService.readRectButtonArrayFromBinaryFile(file);
        for (RectButton rectButton : rectsRead) {
            rectButton.moveRel(-1, 0);
        }
        assertArrayEquals(rectButtons, rectsRead);
    }

    @Test
    public void testFileReadWriteRectButtonTextOneLine() throws WindowException, IOException {
        RectButton rectButton = new RectButton(10000, 10000, 20000, 20000, "OK");
        File file = Files.createFile(tempDir.resolve("test.txt")).toFile();
        FileService.writeRectButtonToTextFileOneLine(file, rectButton);
        assertTrue(file.exists());
        assertEquals(1, Files.readAllLines(file.toPath()).size());
        RectButton rectButtonRead = FileService.readRectButtonFromTextFileOneLine(file);
        assertEquals(rectButton, rectButtonRead);
    }

    @Test
    public void testFileReadWriteRectButtonTextSixLines() throws WindowException, IOException {
        RectButton rectButton = new RectButton(10000, 10000, 20000, 20000, "OK");
        File file = Files.createFile(tempDir.resolve("test.txt")).toFile();
        FileService.writeRectButtonToTextFileSixLines(file, rectButton);
        assertTrue(file.exists());
        assertEquals(6, Files.readAllLines(file.toPath()).size());
        RectButton rectRead = FileService.readRectButtonFromTextFileSixLines(file);
        assertEquals(rectButton, rectRead);
    }

    @Test
    public void testFileReadWriteTraineeTextOneLine() throws NumberFormatException, TrainingException, IOException {
        Trainee traineeToWrite = new Trainee("Иван", "Иванов", 2);
        File file = Files.createFile(tempDir.resolve("test.txt")).toFile();
        FileService.writeTraineeToTextFileOneLine(file, traineeToWrite);
        assertTrue(file.exists());
        assertEquals(1, Files.readAllLines(file.toPath()).size());
        Trainee traineeRead = FileService.readTraineeFromTextFileOneLine(file);
        assertEquals(traineeToWrite, traineeRead);
    }

    @Test
    public void testFileReadWriteTraineeTextThreeLines() throws NumberFormatException, TrainingException, IOException {
        Trainee traineeToWrite = new Trainee("Иван", "Иванов", 2);
        File file = Files.createFile(tempDir.resolve("test.txt")).toFile();
        FileService.writeTraineeToTextFileThreeLines(file, traineeToWrite);
        assertTrue(file.exists());
        assertEquals(3, Files.readAllLines(file.toPath()).size());
        Trainee traineeRead = FileService.readTraineeFromTextFileThreeLines(file);
        assertEquals(traineeToWrite, traineeRead);
    }

    @Test
    public void testFileSerializeDeserializeTraineeBinary() throws TrainingException, ClassNotFoundException, IOException {
        Trainee traineeToWrite = new Trainee("Иван", "Иванов", 2);
        File file = Files.createFile(tempDir.resolve("test.txt")).toFile();
        FileService.serializeTraineeToBinaryFile(file, traineeToWrite);
        assertTrue(file.exists());
        Trainee traineeRead = FileService.deserializeTraineeFromBinaryFile(file);
        assertEquals(traineeToWrite, traineeRead);
    }

    @Test
    public void testStringSerializeDeserializeTraineeJson() throws TrainingException {
        Trainee traineeToWrite = new Trainee("Иван", "Иванов", 2);
        String json = FileService.serializeTraineeToJsonString(traineeToWrite);
        Trainee traineeRead = FileService.deserializeTraineeFromJsonString(json);
        assertEquals(traineeToWrite, traineeRead);
    }

    @Test
    public void testFileSerializeDeserializeTraineeJson() throws TrainingException, IOException {
        Trainee traineeToWrite = new Trainee("Иван", "Иванов", 2);
        File file = Files.createFile(tempDir.resolve("test.txt")).toFile();
        FileService.serializeTraineeToJsonFile(file, traineeToWrite);
        assertTrue(file.exists());
        Trainee traineeRead = FileService.deserializeTraineeFromJsonFile(file);
        assertEquals(traineeToWrite, traineeRead);
    }

    @Test
    public void testThrowsIOException() {
        Method[] declaredMethods = FileService.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.getName().equals("serializeTraineeToJsonString") || method.getName().equals("deserializeTraineeFromJsonString")) {
                continue;
            }
            if (!Modifier.isPublic(method.getModifiers())) {
                continue;
            }
            Class<?>[] exceptionTypes = method.getExceptionTypes();
            boolean throwIOException = false;
            for (Class<?> exception : exceptionTypes) {
                if (exception == IOException.class) {
                    throwIOException = true;
                    break;
                }
            }
            if (!throwIOException) {
                fail();
            }
        }
    }

}
