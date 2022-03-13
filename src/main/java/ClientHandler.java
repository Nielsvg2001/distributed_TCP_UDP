import javax.imageio.IIOException;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientHandler implements Runnable{

    public ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[256];
    private String clientUsername;
    private InetAddress address;
    private int port;

    public ClientHandler(DatagramSocket datagramSocket){
        try{
            this.datagramSocket = datagramSocket;
            DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
            datagramSocket.receive(datagramPacket);
            this.clientUsername = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            this.address = datagramPacket.getAddress();
            this.port = datagramPacket.getPort();
            this.clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " had entered chatroom");

        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while(!datagramSocket.isClosed()){
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
                this.datagramSocket.receive(datagramPacket); // This is a blocking line so run on separate thread
                broadcastMessage(new String(datagramPacket.getData(),0, datagramPacket.getLength()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMessage(String messageToSend){
        for(ClientHandler clientHandler: clientHandlers){
            try{
                if(!clientHandler.clientUsername.equals(clientUsername)){
                    DatagramPacket datagramPacket = new DatagramPacket(messageToSend.getBytes(),0,messageToSend.length(),clientHandler.address,clientHandler.port);
                    clientHandler.datagramSocket.send(datagramPacket);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        //broadcastMessage("SERVER: "+ clientUsername + " has left the chat!");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
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
}
