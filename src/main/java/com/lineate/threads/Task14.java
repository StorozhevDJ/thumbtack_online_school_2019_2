package com.lineate.threads;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Message {
    private String emailAddress;
    private String sender;
    private String subject;
    private String body;

    public Message(String emailAddress, String sender, String subject, String body) {
        this.emailAddress = emailAddress;
        this.sender = sender;
        this.subject = subject;
        this.body = body;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}


class Transport implements Callable {

    private final Message message;

    public Transport(Message message) {
        this.message = message;
    }

    public void send() {
        try {
            System.out.println("Start send message thread. Connect to server.");
            Thread.sleep(200);
            System.out.println("Send message to " + message.getEmailAddress());
            synchronized (message) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("sended_mail.log", true), StandardCharsets.UTF_8))) {
                    try {
                        writer.write(message.getEmailAddress() + ",");
                        writer.write(message.getSender() + ",");
                        writer.write(message.getSubject() + ",");
                        writer.write(message.getBody());
                        writer.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Thread.sleep(350);
            System.out.println("Receive answer.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object call() {
        send();
        return null;
    }
}


public class Task14 {
    /**
     * Написать класс Message, содержащий 4 текстовых поля : emailAddress, sender, subject, body, и класс Transport,
     * с методом send(Message message), отсылающий письмо на некий SMTP-сервер.
     * Реализовать массовую рассылку одного и того же текста, email адреса берутся из текстового файла.
     * Имейте в виду, что отправка одного письма требует довольно много времени (установление соединения с сервером,
     * отсылка, получение ответа), поэтому последовательная отправка писем не является хорошим решением.
     * Порядок отправки писем произвольный и не обязан совпадать с порядком email адресов в файле.
     * <p>
     * Примечание 1. Реальную отправку писем производить не надо, вместо
     * этого достаточно выводить их в некоторый файл.
     * <p>
     * Примечание 2. Если все же есть желание произвести реальную отсылку,
     * подключите javax.mail-api с помощью maven и используйте классы
     * MimeMessage и Transport. Ответственность за возможные последствия
     * целиком возлагается на Вас :-).
     */

    public static void main(String args[]) {

        ExecutorService es = Executors.newCachedThreadPool();

        try {
            Files.lines(Paths.get("mail_list.csv"), StandardCharsets.UTF_8).forEach(mail -> {
                es.submit(new Transport(new Message(mail, "from@mail.ru", "subject", "body mail")));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        es.shutdown();
        System.out.println("Main thread Done");
    }
}
