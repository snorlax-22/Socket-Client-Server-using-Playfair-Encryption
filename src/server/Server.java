/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hesac
 */
public class Server extends Thread {

    private boolean bolIsConnected;
    private boolean bolIsError;
    private boolean bolRunning = true;
    private String strErrorMessage;
    private ServerSocket objServerSocket;

    public Server() {
        try {
            objServerSocket = new ServerSocket(9999);
            bolIsConnected = true;
            bolIsError = false;
            bolRunning = true;
        } catch (Exception ex) {
            strErrorMessage = ex.getMessage();
            bolIsConnected = false;
            bolIsError = true;
            bolRunning = false;
        }
    }

    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        while (bolRunning && !objServerSocket.isClosed()) {
            try {
                Socket objSocket = objServerSocket.accept();
                new ServerRunner(objSocket).start();

            } catch (Exception ex) {
                strErrorMessage = ex.getMessage();
                bolIsConnected = false;
                bolIsError = true;
                bolRunning = false;
                ServerRunner.log("Lỗi Server : " + strErrorMessage);
            }
        }
        
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public void stopServer() {
        bolRunning = false;
        try {
            objServerSocket.close();
        } catch (IOException ex) {
            ServerRunner.log("Lỗi không thể đóng server : " + ex.getMessage());
        }
    }

    public synchronized boolean isBolIsConnected() {
        return bolIsConnected;
    }

    public synchronized boolean isBolIsError() {
        return bolIsError;
    }

    public synchronized boolean isBolRunning() {
        return bolRunning;
    }

    public synchronized String getStrErrorMessage() {
        return strErrorMessage;
    }

}
