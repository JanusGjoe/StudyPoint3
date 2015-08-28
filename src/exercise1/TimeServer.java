/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise1;

import exercise3.EchoServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Janus
 */
public class TimeServer implements Runnable
{
    Socket s;
    BufferedReader in;
    PrintWriter out;
    String echo;
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    // Tue Sep 02 16:00:34 UTC 2014
    
    public TimeServer(Socket socket)
    {
        s = socket;
    }
    
    @Override
    public void run()
    {
        while (true)
        {
            try {
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                echo = in.readLine();
                out = new PrintWriter(s.getOutputStream(), true);
                
                Date date = new Date();
                String printTime = sdf.format(date);
                out.println(printTime);
            } catch (IOException ex) {
                Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        String ip = "localhost";
        int port = 4321;
        if(args.length == 2)
        {
            System.out.println("args found");
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(ip, port));
        
        while(true)
        {
            TimeServer e = new TimeServer(ss.accept());
            Thread t1 = new Thread(e);
            t1.start();
        }
    }
}
