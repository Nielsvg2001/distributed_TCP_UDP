import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args){
        try{
            ServerSocket serverSocket=new ServerSocket(6666);
            Socket socket=serverSocket.accept();//establishes connection
            DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
            String  str=(String)dataInputStream.readUTF();
            System.out.println("message= "+str);
            serverSocket.close();
        }catch(Exception e){System.out.println(e);}
    }
}
