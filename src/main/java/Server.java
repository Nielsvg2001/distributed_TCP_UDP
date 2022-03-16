import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {

    public static void main(String[] args) throws IOException {


        DatagramSocket datagramSocket = new DatagramSocket(1234);
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] buffer = new byte[25600];

                        DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
                        datagramSocket.receive(datagramPacket);

                        File fileToDownload = new File("reveived_file.txt");
                        FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload);
                        fileOutputStream.write(Arrays.copyOfRange(buffer,0,datagramPacket.getLength()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }
}
