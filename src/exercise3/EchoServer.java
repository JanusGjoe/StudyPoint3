/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Janus
 */
public class EchoServer implements Runnable
{
    Socket s;
    BufferedReader in;
    PrintWriter out;
    String echo;
    
    public EchoServer(Socket socket)
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
                
                String returnString = checkForCommands(echo);
                
                out.println(returnString);
            } catch (IOException ex) {
                Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String checkForCommands(String msg) {
        if (msg.contains("#"))
        {
            String splitter = "[#]";
            String[] tokens = msg.split(splitter);
            String command = tokens[0].toUpperCase();
            String returnString = tokens[1];
            
            switch (command)
            {
                case "UPPER":
                    return returnString.toUpperCase();
                case "LOWER":
                    return returnString.toLowerCase();
                case "REVERSE":
                    String temp = "";
                    
                    for (int i = (returnString.length() - 1); i > -1; i--)
                    {
                        temp = temp + returnString.charAt(i);
                    }
                    
                    return temp;
                case "TRANSLATE":
                    return translateWord(returnString);
            }
        }
        return msg;
    }
    
    private String translateWord(String word)
    {
        word = word.toUpperCase();
        
        switch(word)
        {
            case "DOG":
                return "HUND";
            case "HUND":
                return "DOG";
            case "KAT":
                return "CAT";
            case "CAT":
                return "KAT";
            case "MUS":
                return "MOUSE";
            case "MOUSE":
                return "MUS";
            case "HEST":
                return "HORSE";
            case "HORSE":
                return "HEST";
            case "KO":
                return "COW";
            case "COW":
                return "KO";
            default:
                return "NOT_FOUND";
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
            EchoServer e = new EchoServer(ss.accept());
            Thread t1 = new Thread(e);
            t1.start();
        }
    }
}