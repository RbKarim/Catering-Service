package service;

import static service.FXML_servicesController.idNo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

public class FXML_updatePriceController implements Initializable {

    @FXML
    private JFXTextField updatePriceTxtField;
    @FXML
    private JFXButton doneUpdateBtn;
    @FXML
    private AnchorPane PriceUpdatePane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void doneUpdateBtnAction(ActionEvent event) {

        UpdatePrice();
    }

    void UpdatePrice() {

        try {
            Connection con;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
            con = DriverManager.getConnection(connectionUrl);

            if(!updatePriceTxtField.getText().isEmpty()){
            Statement stm = con.createStatement();
            String sql = "update CUS_SERVICES set PRICE=" + updatePriceTxtField.getText() + ""
                    + " where ITEM_ID=" + idNo + "";
            
            System.out.println(sql);
            
            stm.executeUpdate(sql);

                PriceUpdatePane.getScene().getWindow().hide();

            }
            else{
            Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("!!Input Data!!");
                a.show();
            }
            
        } catch (Exception ex) {

            System.out.println("Error");
        }
    }
}
