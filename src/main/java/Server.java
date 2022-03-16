import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Server {

    public static void main(String[] args) throws IOException {


        DatagramSocket datagramSocket = new DatagramSocket(1234);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        byte[] buffer = new byte[25600];

                        DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
                        datagramSocket.receive(datagramPacket);
                        String fileName = new String(buffer, 0, datagramPacket.getLength());
                        System.out.println("hallo1");
                        if (fileName.contains(".")) {
                            System.out.println("hallo2");
                            File fileToDownload = new File(fileName);
                            FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload);
                            System.out.print("hey");
                            datagramSocket.receive(datagramPacket);
                            System.out.println(new String(buffer, 0, datagramPacket.getLength()) + "hallo3");
                            fileOutputStream.write(Arrays.copyOfRange(buffer, 0, datagramPacket.getLength()));
                            System.out.print("bonjour");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}

