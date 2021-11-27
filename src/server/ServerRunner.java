/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dbconnection.dbaccess;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;
import encode.Playfair;
import javax.swing.JTextArea;
import tcp.PlayfairCipherDecryption;

/**
 *
 * @author Snorlax
 */
public class ServerRunner extends Thread {

    /**
     * @param args the command line arguments
     */
    private Socket objSocket;
    private DataInputStream objDataInputStream;
    private DataOutputStream objDataOutputStream;
    public static JTextArea objJTextArea = null;
    private PlayfairCipherDecryption objPlayfair;
    private String keyword;

    public ServerRunner(Socket objSocket) {
        this.objSocket = objSocket;
        try {
            objPlayfair = new PlayfairCipherDecryption();
            objDataInputStream = new DataInputStream(objSocket.getInputStream());
            objDataOutputStream = new DataOutputStream(objSocket.getOutputStream());
            log(this.objSocket.getInetAddress().toString() + " vừa được kết nối!");
        } catch (Exception ex) {
            log("Lỗi lấy luồng dữ liệu : " + ex.getMessage());
        }
    }

    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        try {
            String strRecive = objDataInputStream.readUTF();
            String[] arrRecive = strRecive.split(";");
            String strPlainText = "";
            String keyword = "";
            String strCipher = "";
            String key = "";

            if (arrRecive.length == 3) {//Kiểm dữ liệu có chứa đủ cipher , key , keyword
                strCipher = arrRecive[0]; //0-Cipher
                key = arrRecive[1];//1-key
                keyword = arrRecive[2];//2-Keyword
                objPlayfair.setKey(key);
                String[] arrCipher = strCipher.split(" "); //tách cách từ của câu thành các chuỗi

                for (String strCipherWord : arrCipher) {
                    if (!strCipherWord.equalsIgnoreCase("")) {
                        strCipherWord = strCipherWord.trim();
                        strPlainText += objPlayfair.decryptMessage(strCipherWord);
                        strPlainText += " ";
                    }
                }
            } else {
                log("Lỗi nhận dữ liệu không đầy đủ: " + strRecive);
            }

            objDataOutputStream.writeInt(strPlainText.indexOf(keyword));
            objDataInputStream.close();
            objDataOutputStream.close();
            log(this.objSocket.getInetAddress().toString() + " ngắt kết nối!");
            objSocket.close();
        } catch (Exception ex) {
            log("Lỗi " + ex.getMessage());
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public static void log(String error) {
        if (objJTextArea != null) {
            objJTextArea.append(error + "\n");
            objJTextArea.setCaretPosition(objJTextArea.getDocument().getLength());
        }
    }

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
        int i = db.update("insert into Cypher values('" + id + "','" + key + "','" + encryptedtext + "')");
        if (i != 0) {
            System.out.println("insert success!");
        } else {
            System.out.println("insert fail!");
        }

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
