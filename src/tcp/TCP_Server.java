/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import jdk.jfr.DataAmount;

/**
 *
 * @author Snorlax
 */
public class TCP_Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //tạo socket server
        ServerSocket server = new ServerSocket(9999);
        System.out.println("Server is running !");
        
        //Tạo kết nối server
        Socket client = server.accept();
        //Khai báo các đối tượng input và output
        DataInputStream din = new DataInputStream(client.getInputStream());
        DataOutputStream dout = new DataOutputStream(client.getOutputStream());
        System.out.println("Server connected to client !");
        //giai đoạn trao đổi
     
        
        String key = din.readUTF();
        String encryptedtext = din.readUTF();
        
        PlayfairCipherDecryption x = new PlayfairCipherDecryption();
        x.setKey(key);
        x.KeyGen();
        
        
       
        String decryptedText = x.decryptMessage(encryptedtext);
        
        
        
        dout.writeUTF(decryptedText);
        client.close();
        server.close();
    }
    
}
