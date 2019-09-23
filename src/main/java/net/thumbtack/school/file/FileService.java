package net.thumbtack.school.file;

import com.google.gson.Gson;
import net.thumbtack.school.ttschool.Trainee;
import net.thumbtack.school.ttschool.TrainingException;
import net.thumbtack.school.windows.v4.Point;
import net.thumbtack.school.windows.v4.RectButton;
import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class FileService {

    /**
     * Записывает массив байтов в двоичный файл, имя файла задается текстовой
     * строкой.
     *
     * @throws IOException
     */
    public static void writeByteArrayToBinaryFile(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFile(new File(fileName), array);
    }

    /**
     * Записывает массив байтов в двоичный файл, имя файла задается экземпляром
     * класса File.
     *
     * @throws IOException
     */
    public static void writeByteArrayToBinaryFile(File file, byte[] array) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            bos.write(array);
        }
    }

    /**
     * Читает массив байтов из двоичного файла, имя файла задается текстовой
     * строкой.
     *
     * @throws IOException
     */
    public static byte[] readByteArrayFromBinaryFile(String fileName) throws IOException {
        return readByteArrayFromBinaryFile(new File(fileName));
    }

    /**
     * Читает массив байтов из двоичного файла, имя файла задается экземпляром
     * класса File.
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] readByteArrayFromBinaryFile(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    /**
     * Записывает массив байтов в ByteArrayOutputStream, создает на основе данных в
     * полученном ByteArrayOutputStream экземпляр ByteArrayInputStream и читает из
     * ByteArrayInputStream байты с четными номерами. Возвращает массив прочитанных
     * байтов.
     *
     * @param array
     * @return
     * @throws IOException
     */
    public static byte[] writeAndReadByteArrayUsingByteStream(byte[] array) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(array);
        array = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(array);
        byte[] ret = new byte[array.length / 2];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (byte) bais.read();
            bais.skip(1);
        }
        return ret;
    }

    /**
     * Записывает массив байтов в двоичный файл, используя буферизованный вывод, имя
     * файла задается текстовой строкой.
     *
     * @param fileName
     * @param array
     * @throws IOException
     */
    public static void writeByteArrayToBinaryFileBuffered(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFileBuffered(new File(fileName), array);
    }

    /**
     * Записывает массив байтов в двоичный файл, используя буферизованный вывод, имя
     * файла задается экземпляром класса File.
     *
     * @param file
     * @param array
     * @throws IOException
     */
    public static void writeByteArrayToBinaryFileBuffered(File file, byte[] array) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            bos.write(array);
        }
    }

    /**
     * Читает массив байтов из двоичного файла, используя буферизованный ввод, имя
     * файла задается текстовой строкой.
     *
     * @param fileName
     * @return
     */
    public static byte[] readByteArrayFromBinaryFileBuffered(String fileName) throws IOException {
        return readByteArrayFromBinaryFileBuffered(new File(fileName));
    }

    /**
     * Читает массив байтов из двоичного файла, используя буферизованный ввод, имя
     * файла задается экземпляром класса File.
     *
     * @param file
     * @return
     */
    public static byte[] readByteArrayFromBinaryFileBuffered(File file) throws IOException {
        byte[] ret = null;
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            ret = new byte[bis.available()];
            bis.read(ret, 0, ret.length);
        }
        return ret;
    }

    /**
     * Записывает RectButton в двоичный файл, имя файла задается экземпляром класса
     * File. Состояние окна записывается в виде текстовой строки в формате UTF.
     * Используйте DataOutputStream.
     *
     * @param file
     * @param rectButton
     */
    public static void writeRectButtonToBinaryFile(File file, RectButton rectButton) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeInt(rectButton.getTopLeft().getX());
            dos.writeInt(rectButton.getTopLeft().getY());
            dos.writeInt(rectButton.getBottomRight().getX());
            dos.writeInt(rectButton.getBottomRight().getY());
            dos.writeUTF(rectButton.getState().name());
            dos.writeUTF(rectButton.getText());
        }
    }

    /**
     * Читает данные для RectButton из двоичного файла и создает на их основе
     * экземпляр RectButton, имя файла задается экземпляром класса File.
     * Предполагается, что данные в файл записаны в формате предыдущего упражнения.
     * Используйте DataInputStream.
     *
     * @param file
     * @return
     */
    public static RectButton readRectButtonFromBinaryFile(File file) throws IOException, WindowException {
        RectButton ret = null;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            ret = new RectButton(new Point(dis.readInt(), dis.readInt()), new Point(dis.readInt(), dis.readInt()),
                    WindowState.valueOf(dis.readUTF()), dis.readUTF());
        }
        return ret;
    }

    /**
     * Записывает массив из RectButton в двоичный файл, имя файла задается
     * экземпляром класса File. Поле состояния и текст не записываются.
     *
     * @param file
     * @param rects
     */
    public static void writeRectButtonArrayToBinaryFile(File file, RectButton[] rects) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            for (RectButton rb : rects) {
                dos.writeInt(rb.getTopLeft().getX());
                dos.writeInt(rb.getTopLeft().getY());
                dos.writeInt(rb.getBottomRight().getX());
                dos.writeInt(rb.getBottomRight().getY());
            }
        }
    }

    /**
     * В файле массива данных RectButton из предыдущего упражнения увеличивает на 1
     * значение x каждой точки каждого RectButton. Имя файла задается экземпляром
     * класса File.
     *
     * @param file
     */
    public static void modifyRectButtonArrayInBinaryFile(File file) throws IOException, WindowException {
        RectButton[] rb = readRectButtonArrayFromBinaryFile(file);
        for (RectButton rectButton : rb) {
            rectButton.moveRel(+1, 0);
        }
        writeRectButtonArrayToBinaryFile(file, rb);
    }

    /**
     * Читает данные, записанные в формате предыдущего упражнения и создает на их
     * основе массив RectButton c с состоянием “ACTIVE” и текстом “OK”.
     *
     * @param file
     * @return
     */
    public static RectButton[] readRectButtonArrayFromBinaryFile(File file) throws IOException, WindowException {
        ArrayList<RectButton> rbal = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            while (dis.available() > 0) {
                rbal.add(new RectButton(new Point(dis.readInt(), dis.readInt()), new Point(dis.readInt(), dis.readInt()), "OK"));
            }
        }
        return rbal.toArray(new RectButton[rbal.size()]);
    }

    /**
     * Записывает RectButton в текстовый файл в одну строку, имя файла задается
     * экземпляром класса File. Поля в файле разделяются пробелами.
     *
     * @param file
     * @param rectButton
     */
    public static void writeRectButtonToTextFileOneLine(File file, RectButton rectButton) throws IOException {
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.format("%d %d %d %d %s %s", rectButton.getTopLeft().getX(), rectButton.getTopLeft().getY(),
                    rectButton.getBottomRight().getX(), rectButton.getBottomRight().getY(),
                    rectButton.getState().name(), rectButton.getText());
        }
    }

    /**
     * Читает данные для RectButton из текстового файла и создает на их основе
     * экземпляр RectButton, имя файла задается экземпляром класса File.
     * Предполагается, что данные в файл записаны в формате предыдущего упражнения.
     *
     * @param file
     * @return
     */
    public static RectButton readRectButtonFromTextFileOneLine(File file) throws IOException, WindowException {
        RectButton rb = null;
        try (Scanner scanner = new Scanner(new FileReader(file))) {
            scanner.useDelimiter(" ");
            scanner.hasNext();
            rb = new RectButton(new Point(scanner.nextInt(), scanner.nextInt()),
                    new Point(scanner.nextInt(), scanner.nextInt()), WindowState.valueOf(scanner.next()),
                    scanner.next());
        }
        return rb;
    }

    /**
     * Записывает RectButton в текстовый файл В первые 4 строки записываются
     * координаты (каждое число в отдельной строке) , в следующие 2 - состояние и
     * текст. Имя файла задается экземпляром класса File.
     *
     * @param file
     * @param rectButton
     */
    public static void writeRectButtonToTextFileSixLines(File file, RectButton rectButton) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.append(String.valueOf(rectButton.getTopLeft().getX()));
            bw.newLine();
            bw.append(String.valueOf(rectButton.getTopLeft().getY()));
            bw.newLine();
            bw.append(String.valueOf(rectButton.getBottomRight().getX()));
            bw.newLine();
            bw.append(String.valueOf(rectButton.getBottomRight().getY()));
            bw.newLine();
            bw.append(rectButton.getState().name());
            bw.newLine();
            bw.append(rectButton.getText());
            bw.newLine();
        }

    }

    /**
     * Читает данные для RectButton из текстового файла и создает на их основе
     * экземпляр RectButton, имя файла задается экземпляром класса File.
     * Предполагается, что данные в файл записаны в формате предыдущего упражнения.
     *
     * @param file
     * @return
     */
    public static RectButton readRectButtonFromTextFileSixLines(File file) throws IOException, WindowException {
        RectButton rb = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            rb = new RectButton(new Point(Integer.parseInt(reader.readLine()), Integer.parseInt(reader.readLine())),
                    new Point(Integer.parseInt(reader.readLine()), Integer.parseInt(reader.readLine())),
                    WindowState.valueOf(reader.readLine()), reader.readLine());
        }
        return rb;
    }

    /**
     * Записывает Trainee в текстовый файл в одну строку в кодировке UTF-8, имя
     * файла задается экземпляром класса File. Имя, фамилия и оценка в файле
     * разделяются пробелами.
     *
     * @param file
     * @param trainee
     */
    public static void writeTraineeToTextFileOneLine(File file, Trainee trainee) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write(String.format("%s %d", trainee.getFullName(), trainee.getRating()));
        }
    }

    /**
     * Читает данные для Trainee из текстового файла и создает на их основе
     * экземпляр Trainee, имя файла задается экземпляром класса File.
     * Предполагается, что данные в файл записаны в формате предыдущего упражнения.
     *
     * @param file
     * @return
     */
    public static Trainee readTraineeFromTextFileOneLine(File file) throws IOException, TrainingException {
        Trainee ret = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            //ret = new Trainee(reader., reader.readLine(), Integer.valueOf(reader.readLine()));

            String[] str = reader.readLine().split(" ");
            ret = new Trainee(str[0], str[1], Integer.parseInt(str[2]));
        }
        return ret;
    }

    /**
     * Записывает Trainee в текстовый файл в кодировке UTF-8, каждое поле в
     * отдельной строке, имя файла задается экземпляром класса File.
     *
     * @param file
     * @param trainee
     */
    public static void writeTraineeToTextFileThreeLines(File file, Trainee trainee) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write(trainee.getFirstName());
            writer.newLine();
            writer.write(trainee.getLastName());
            writer.newLine();
            writer.write(String.valueOf(trainee.getRating()));
        }
    }

    /**
     * Читает данные для Trainee из текстового файла и создает на их основе
     * экземпляр Trainee, имя файла задается экземпляром класса File.
     * Предполагается, что данные в файл записаны в формате предыдущего упражнения.
     *
     * @param file
     * @return
     */
    public static Trainee readTraineeFromTextFileThreeLines(File file) throws IOException, TrainingException {
        Trainee ret = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            ret = new Trainee(reader.readLine(), reader.readLine(), Integer.valueOf(reader.readLine()));
            String currentLine = reader.readLine();
        }
        return ret;
    }

    /**
     * Сериализует Trainee в двоичный файл, имя файла задается экземпляром класса
     * File.
     *
     * @param file
     * @param trainee
     */
    public static void serializeTraineeToBinaryFile(File file, Trainee trainee) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            objectOutputStream.writeObject(trainee);
        }
    }

    /**
     * Десериализует Trainee из двоичного файла, имя файла задается экземпляром
     * класса File. Предполагается, что данные в файл записаны в формате предыдущего
     * упражнения.
     *
     * @param file
     * @return
     */
    public static Trainee deserializeTraineeFromBinaryFile(File file) throws IOException, ClassNotFoundException {
        Trainee ret = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            ret = (Trainee) objectInputStream.readObject();
        }
        return ret;
    }

    /**
     * Сериализует Trainee в формате Json в текстовую строку.
     *
     * @param trainee
     * @return
     */
    public static String serializeTraineeToJsonString(Trainee trainee) {
        Gson gson = new Gson();
        return gson.toJson(trainee);
    }

    /**
     * Десериализует Trainee из текстовой строки с Json-представлением Trainee.
     *
     * @param json
     * @return
     */
    public static Trainee deserializeTraineeFromJsonString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Trainee.class);
    }

    /**
     * Сериализует Trainee в формате Json в файл, имя файла задается экземпляром
     * класса File.
     *
     * @param file
     * @param trainee
     */
    public static void serializeTraineeToJsonFile(File file, Trainee trainee) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeUTF(serializeTraineeToJsonString(trainee));
        }
    }

    /**
     * Десериализует Trainee из файла с Json-представлением Trainee, имя файла
     * задается экземпляром класса File.
     *
     * @param file
     * @return
     */
    public static Trainee deserializeTraineeFromJsonFile(File file) throws IOException {
        Trainee ret = null;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            ret = deserializeTraineeFromJsonString(dis.readUTF());
        }
        return ret;
    }

}
