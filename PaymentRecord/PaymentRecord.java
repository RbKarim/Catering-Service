/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaymentRecord;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Sean
 */
public class PaymentRecord extends Application{
     @Override
    public void start(Stage stage){

        Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("FXML_PaymentRecord.fxml"));
                 Scene scene = new Scene(root);
        
                 stage.setScene(scene);
                 stage.show();
            } catch (IOException ex) {
                Logger.getLogger(PaymentRecord.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Record"+ex.getMessage());
            }
        
       
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
       launch(args);
    }
}
