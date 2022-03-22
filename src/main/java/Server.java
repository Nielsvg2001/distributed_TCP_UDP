import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Server {

    public static void main(String[] args) throws IOException {
        int i = 0;
        DatagramSocket datagramSocket = new DatagramSocket(1234);
        while (!datagramSocket.isClosed()) {
            byte[] buffer = new byte[25600];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
            datagramSocket.receive(datagramPacket);
            int finalI = i;
            Thread thread = new Thread(() -> {
                try {
                    System.out.println(finalI);
                    File fileToDownload = new File("file" + finalI + ".txt");
                    FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload);
                    fileOutputStream.write(Arrays.copyOfRange(buffer, 0, datagramPacket.getLength()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            i++;
        }
    }
}