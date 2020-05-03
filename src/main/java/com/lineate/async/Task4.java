package com.lineate.async;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/*
Доработать сервер на основе селекторов, чтобы он принимал запрос http содержащий json вида "{x:10, y:20}"
и возвращал ответ с суммой двух чисел.

http://localhost:5000/?json={x:10,y:33}
*/

public class Task4 {

    private static final String HTTP_RESPONSE = "HTTP/1.1 200 OK\n" +
            "Content-Type: text/html\n" +
            "Connection: Closed\n\n";

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", 5000));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT, null);

        while (true) {

            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {
                    SocketChannel clientChannel = serverChannel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                    log("Connection Accepted: " + clientChannel.getLocalAddress() + "\n");

                } else if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int n = client.read(buffer);
                    if (n == -1) {
                        client.close();
                    }
                    String result = new String(buffer.array()).trim();
                    log("Message received: " + result);
                    result = result.split("\\r")[0];

                    java.util.regex.Pattern px = java.util.regex.Pattern.compile("^GET.+json=\\{x:(\\d+),.+\\}.+$");
                    java.util.regex.Matcher mx = px.matcher(result);
                    java.util.regex.Pattern py = java.util.regex.Pattern.compile("^GET.+json=\\{.+y:(\\d+)\\}.+$");
                    java.util.regex.Matcher my = py.matcher(result);

                    String valueX = mx.matches() ? mx.group(1) : null;
                    System.out.println("value X: " + valueX);
                    String valueY = my.matches() ? my.group(1) : null;
                    System.out.println("value Y: " + valueY);

                    key.attach(Integer.parseInt(valueX) + Integer.parseInt(valueY));

                    key.interestOps(SelectionKey.OP_WRITE);

                } else if (key.isWritable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    Integer sum = (Integer) key.attachment();
                    ByteBuffer writeBuffer = Charset.forName("UTF-8").encode(HTTP_RESPONSE);

                    client.write(writeBuffer);
                    client.write(Charset.forName("UTF-8").encode("Answer " + sum.toString()));
                    if (!writeBuffer.hasRemaining()) {
                        writeBuffer.compact();
                        key.interestOps(SelectionKey.OP_READ);
                    }
                    client.close();
                }
                iterator.remove();
            }
        }
    }

    private static void log(String str) {
        System.out.println(str);
    }


}
