/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import encode.PlayfairCipherDecryption;
import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import jdk.jfr.DataAmount;

/**
 *
 * @author Snorlax
 */
public class TCP_Client {

    public int length = 0;
    public String[][] table;

    /**
     * @param args the command line arguments
     */
    public Point getPoint(char c) {
        Point pt = new Point(0, 0);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (c == table[i][j].charAt(0)) {
                    pt = new Point(i, j);
                }
            }
        }
        return pt;
    }

    public String[] encodeDigraph(String di[]) {
        String[] enc = new String[length];
        for (int i = 0; i < length; i++) {
            char a = di[i].charAt(0);
            char b = di[i].charAt(1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();

            // case 1: letters in digraph are of same row, shift columns to right
            if (r1 == r2) {
                c1 = (c1 + 1) % 5;
                c2 = (c2 + 1) % 5;

                // case 2: letters in digraph are of same column, shift rows down
            } else if (c1 == c2) {
                r1 = (r1 + 1) % 5;
                r2 = (r2 + 1) % 5;

                // case 3: letters in digraph form rectangle, swap first column # with second column #
            } else {
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }

            //performs the table look-up and puts those values into the encoded array
            enc[i] = table[r1][c1] + "" + table[r2][c2];
        }
        return enc;
    }

    public String cipher(String in) {
        length = (int) in.length() / 2 + in.length() % 2;

        // insert x between double-letter digraphs & redefines "length"
        for (int i = 0; i < (length - 1); i++) {
            if (in.charAt(2 * i) == in.charAt(2 * i + 1)) {
                in = new StringBuffer(in).insert(2 * i + 1, 'X').toString();
                length = (int) in.length() / 2 + in.length() % 2;
            }
        }

        // adds an x to the last digraph, if necessary
        String[] digraph = new String[length];
        for (int j = 0; j < length; j++) {
            if (j == (length - 1) && in.length() / 2 == (length - 1)) {
                in = in + "X";
            }
            digraph[j] = in.charAt(2 * j) + "" + in.charAt(2 * j + 1);
        }

        // encodes the digraphs and returns the output
        String out = "";
        String[] encDigraphs = new String[length];
        encDigraphs = encodeDigraph(digraph);
        for (int k = 0; k < length; k++) {
            out = out + encDigraphs[k];
        }
        return out;
    }

    public static void main(String[] args) throws IOException {
        //tạo socket client
        Socket client = new Socket("127.0.0.1", 9999);

        //Khai báo các đối tượng input và output
        DataInputStream din = new DataInputStream(client.getInputStream());
        DataOutputStream dout = new DataOutputStream(client.getOutputStream());

        //Nhập keyword
        System.out.println("Please input the keyword for the Playfair cipher.");
        Scanner sc = new Scanner(System.in);
        String key = sc.nextLine();
        key.toString();
        while (key.equals("")) {
            key = sc.nextLine();
        }
        key.toString();
        System.out.println();
        //Nhập nội dung
        System.out.println("Please input the message to be encoded");
        System.out.println("using the previously given keyword");
        String plaintext = sc.nextLine();
        while (plaintext.equals("")) {
            plaintext = sc.nextLine();
        }

        PlayfairCipherDecryption x = new PlayfairCipherDecryption();

        x.setKey(key);
        x.KeyGen();
        if (plaintext.length() % 2 == 0) {
            System.out.println("Encrypted text at client: " + x.encryptMessage(plaintext));
        } else {
            // nếu chuỗi không chẵn => thêm z vào cuối chuỗi
            plaintext = plaintext + 'z';
            plaintext.trim();
            System.err.println(plaintext);
            System.out.println("Encrypted text at client: " + x.encryptMessage(plaintext));
        }
        sc.close();
        //giai đoạn trao đổi

        dout.writeUTF(key);
        dout.writeUTF(x.encryptMessage(plaintext));
        //Khai báo đối tượng nhận

        String decryptedText = din.readUTF();

        System.out.println("Server return decrypted text : " + decryptedText);
        client.close();
    }

}
