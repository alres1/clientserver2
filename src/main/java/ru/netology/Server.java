package ru.netology;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {

    private static String removeWhitespace(String str) {
        return str.replaceAll(" ", "");
    }

    public static void main(String[] args) throws IOException {

        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", 23334));
        while (true) {
            try (SocketChannel socketChannel = serverChannel.accept()) {
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    int bytesCount = socketChannel.read(inputBuffer);
                    if (bytesCount == -1) break;
                    String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                    msg =  removeWhitespace(msg);
                    inputBuffer.clear();
                    socketChannel.write(ByteBuffer.wrap(("Ответ от сервера: " + msg).getBytes(StandardCharsets.UTF_8)));
                }
            } catch (IOException err) {
                System.out.println(err.getMessage());
            }
        }

    }
}
