/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Janus
 */
public class TimeClient implements Runnable
{
    private Socket socket;
    BufferedReader in;
    private Scanner input = new Scanner(System.in);
    private PrintWriter output;
    
    public TimeClient(Socket soc)
    {
        socket = soc;
    }
    
    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inMSG = in.readLine();
                System.out.println(inMSG);
                String msg = input.nextLine();
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println(msg);
            } catch (IOException ex)
            {
                Logger.getLogger(TimeClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        String ip = "localhost";
        int port = 4321;
        
        Socket ss = new Socket(ip, port);
        TimeClient tc = new TimeClient(ss);
        
        Thread t1 = new Thread(tc);
        t1.start();
    }
}
