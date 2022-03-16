import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.Scanner;


public class Client {

    public static void main(String[] args) {
        System.out.print("Choose file: ");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        File fileToSend = new File(filePath);

        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            String fileName = fileToSend.getName();
            // Send filename
            byte[] buffer = fileName.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length, InetAddress.getByName("localhost"), 1234);
            datagramSocket.send(datagramPacket);

            // Send document
            buffer = Files.readAllBytes(fileToSend.toPath());
            datagramPacket = new DatagramPacket(buffer, 0, buffer.length, InetAddress.getByName("localhost"), 1234);
            datagramSocket.send(datagramPacket);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
