package tic.tac.toe.game.iti.client.ServerSide;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue; 
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import tic.tac.toe.game.iti.client.HomePageController;
import tic.tac.toe.game.iti.client.OnlineGameController;
import tic.tac.toe.game.iti.client.player.Player;

public class ServerHandler {
    public static Stage stage;
    public static DataInputStream massageIn; 
    public static DataOutputStream massageOut;
    public static Socket socket;
    public static String msg=null;
    public static boolean isFinished=false;
    public static boolean isLoggedIn = false;
    
    public static void setSocket(String ip) throws IOException{
        ServerHandler.socket=new Socket(ip, 5005);
        ServerHandler.massageIn=new DataInputStream(ServerHandler.socket.getInputStream());
        ServerHandler.massageOut=new DataOutputStream(ServerHandler.socket.getOutputStream());
        Thread listner;
        listner = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!ServerHandler.isFinished){
                    try {
                        String responseMsg=massageIn.readUTF();
                        JSONObject respone=(JSONObject) JSONValue.parse(responseMsg);
                        if(respone.get("type").equals(MassageType.SERVER_CLOSE_MSG))
                        {
                            
                        }
                        else if(respone.get("type").equals(MassageType.UPDATE_LIST_MSG) && isLoggedIn)
                        {
                            JSONArray array = (JSONArray) respone.get("data");
                            ArrayList<Player> dtoPlayers = new ArrayList<Player>();
                            for(int i = 0; i < array.size(); i++){
                                JSONObject obj = (JSONObject) JSONValue.parse((String)array.get(i));
                                dtoPlayers.add(new Player((String)obj.get("username"), "", "", ((Long)obj.get("score")).intValue()));
                            }
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    HomePageController.updateAvailablePlayers(dtoPlayers);
                                }
                            });
                        }
                        else if(respone.get("type").equals(MassageType.CHALLENGE_ACCESSEPT_MSG))
                        {
                            
                        }
                        else if(respone.get("type").equals(MassageType.UPDATE_LIST_MSG)){
                            
                        }
                        else if(respone.get("type").equals(MassageType.START_GAME_MSG)){
                            Platform.runLater(() -> {
                                OnlineGameController.navigateToGame(msg);
                            });
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
