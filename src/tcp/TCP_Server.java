/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import dbconnection.dbaccess;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Random;
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
    public static void main(String[] args) throws IOException, SQLException {
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
        
        dbaccess db = new dbaccess();
        Random generator = new Random();
        int id = generator.nextInt();
        key.trim();
        encryptedtext.trim();
        int i = db.update("insert into Cypherr values('"+id+"','"+key+"','"+encryptedtext+"')");
        if( i != 0)
            System.out.println("insert thành công!");
        else
            System.out.println("insert không thành công!");
        String decryptedText = x.decryptMessage(encryptedtext);
        
        //key test: thedieis
        //plaintext test: hellooneandall
        
        dout.writeUTF(decryptedText);
        client.close();
        server.close();
    }
    
}
