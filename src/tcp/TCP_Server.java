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
import java.sql.*;
import java.util.Random;
import java.util.Scanner;


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
        //key test: thedieis
        //plaintext test: hellooneandall
        
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
        
        // insert id + key + cypher vừa chuyển đổi vào DB
        int i = db.update("insert into Cypher values('"+id+"','"+key+"','"+encryptedtext+"')");
        if( i != 0)
            System.out.println("insert success!");
        else
            System.out.println("insert fail!");
        
        // select * tất cả các thông tin từ bảng Cypher
        String sql = "SELECT * FROM Cypher";
        ResultSet rs = db.getAll(sql);
         while (rs.next()) {
        //lấy dữ liệu theo tên cột trong DB
        int idC = rs.getInt("id");
        String keyyC = rs.getString("keyy");
        String cypherC = rs.getString("cypher");
        
        //Hiển thị dữ liệu vừa lấy từ DB
        System.out.print("ID: " + idC);
        System.out.print(", Key: " + keyyC.trim());
        System.out.println(", CypherC: " + cypherC.trim());
        }
        
        
        
        String decryptedText = x.decryptMessage(encryptedtext);
        
        dout.writeUTF(decryptedText);
        client.close();
        server.close();
    }
    
}
