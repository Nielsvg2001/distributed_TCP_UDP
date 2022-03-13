import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

     private DatagramSocket datagramSocket;
     private byte[] buffer = new byte[25600];
     private String username;

     public Client(DatagramSocket datagramSocket, String username){
         this.datagramSocket = datagramSocket;
         this.username = username;
     }
     public void sendMessage(){
         try{
             buffer = username.getBytes();
             DatagramPacket datagramPacket = new DatagramPacket(buffer,0,buffer.length, InetAddress.getByName("localhost"),1234);
             datagramSocket.send(datagramPacket);
         }catch (IOException e){
             e.printStackTrace();
         }
     }

     public void listenForMessage(){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 byte[] buffer = new byte[26500];

                 while(!datagramSocket.isConnected()) {
                     try {
                         DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
                         datagramSocket.receive(datagramPacket);
                         System.out.println(new String(datagramPacket.getData(), 0, datagramPacket.getLength()));
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
             }
         }).start();
     }

     public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
         try {
             if(bufferedReader != null){
                 bufferedReader.close();
             }
             if(bufferedWriter != null){
                 bufferedWriter.close();
             }
             if(socket != null){
                 socket.close();
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    public static void main(String[] args) {
         try {
             // Scanner scanner = new Scanner(System.in);
             System.out.print("Enter your username for the group chat: ");
             // String username = scanner.nextLine();
             DatagramSocket datagramSocket = new DatagramSocket();
             Client client = new Client(datagramSocket, "niels");
             client.listenForMessage();
             client.sendMessage();
         }catch (IOException e){
             e.printStackTrace();
         }
    }
}
