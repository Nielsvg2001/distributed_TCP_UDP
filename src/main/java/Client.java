import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
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
            Socket socket = new Socket("localhost", 1234);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String filename = fileToSend.getName();
            byte[] fileNameBytes = filename.getBytes();

            byte[] fileContentBytes = Files.readAllBytes(fileToSend.toPath());

            dataOutputStream.writeInt(fileNameBytes.length);
            dataOutputStream.write(fileNameBytes);

            dataOutputStream.writeInt(fileContentBytes.length);
            dataOutputStream.write(fileContentBytes);
        } catch (IOException error) {
            error.printStackTrace();
        }


    }
}
