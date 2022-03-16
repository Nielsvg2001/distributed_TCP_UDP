import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Scanner;


public class Client {

    public static void main(String[] args) {
        System.out.print("Choose file: ");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        File fileToSend = new File(filePath);

        try {
            byte[] buffer = new byte[25600];
            buffer = Files.readAllBytes(fileToSend.toPath());
            DatagramSocket datagramSocket = new DatagramSocket();
            DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length, InetAddress.getByName("localhost"), 1234);
            datagramSocket.send(datagramPacket);
        } catch (IOException error) {
            error.printStackTrace();
        }


    }
}
