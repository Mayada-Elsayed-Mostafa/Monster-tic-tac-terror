/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe.game.iti.client.ServerSide;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue; 
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HAZEM-LAB
 */
public class ServerHandler {
    public static DataInputStream massageIn; 
    public static DataOutputStream massageOut;
    public static Socket socket;
    public static String msg=null;
    public static boolean isFinished=false;
    
    public static void setSocket(String ip) throws IOException{
        ServerHandler.socket=new Socket(ip, 5005);
        ServerHandler.massageIn=new DataInputStream(ServerHandler.socket.getInputStream());
        ServerHandler.massageOut=new DataOutputStream(ServerHandler.socket.getOutputStream());
        Thread listner=new Thread(new Runnable() {
            @Override
            public void run() {
                while(!ServerHandler.isFinished){
                    try {
                        String responseMsg=massageIn.readUTF();
                        JSONObject respone=(JSONObject) JSONValue.parse(responseMsg);
                        if(respone.get("type").equals(MassageType.SERVER_CLOSE_MSG))
                        {
                            
                        }
                        else if(respone.get("type").equals(MassageType.UPDATE_LIST_MSG))
                        {
                            
                        }
                        else if(respone.get("type").equals(MassageType.CHALLENGE_ACCESSEPT_MSG))
                        {
                            
                        }
                        else
                            ServerHandler.msg=responseMsg;
                    } catch (IOException ex) {
                        Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        listner.start();
    }
    public static void closeSocket() throws IOException{
        JSONObject object=new JSONObject();
        object.put("type", MassageType.CLIENT_CLOSE_MSG);
        ServerHandler.massageOut.writeUTF(object.toJSONString());
        ServerHandler.isFinished=true;
        ServerHandler.massageIn.close();
        ServerHandler.massageOut.close();
        ServerHandler.socket.close();
        ServerHandler.socket=null;
    }
                   
}
