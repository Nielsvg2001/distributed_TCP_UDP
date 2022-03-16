import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {


        ServerSocket serverSocket = new ServerSocket(1234);
        Thread thread = new Thread(() -> {
            while (!serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();

                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                    int fileNameLenght = dataInputStream.readInt();
                    if (fileNameLenght > 0) {
                        byte[] fileNameBytes = new byte[fileNameLenght];
                        dataInputStream.readFully(fileNameBytes, 0, fileNameBytes.length);
                        String fileName = new String(fileNameBytes);

                        int fileContentLenght = dataInputStream.readInt();
                        if (fileContentLenght > 0) {
                            byte[] fileContentBytes = new byte[fileContentLenght];
                            dataInputStream.readFully(fileContentBytes, 0, fileContentBytes.length);
                            File fileToDownload = new File(fileName);
                            FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload);
                            fileOutputStream.write(fileContentBytes);
                            fileOutputStream.close();
                        }
                    }
                } catch (IOException error) {
                    error.printStackTrace();
                }
            }
        });
        thread.start();
    }
}