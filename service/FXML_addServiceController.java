package service;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class FXML_addServiceController implements Initializable {

    @FXML
    private JFXTextField itemIDtxtField;
    @FXML
    private JFXTextField addServicetxtField;
    @FXML
    private JFXTextField addPricetxtField;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXButton doneBtn;
    @FXML
    private ImageView backBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void doneBtnAction(ActionEvent event) throws IOException {

        try {
            Connection con;

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
            con = DriverManager.getConnection(connectionUrl);

            Statement stm = con.createStatement();
            if (!(itemIDtxtField.getText().isEmpty()) && !(addServicetxtField.getText().isEmpty()) && !(addPricetxtField.getText().isEmpty())) {
                String sql = "insert into CUS_SERVICES(ITEM_ID,PACKAGE_NAME,PRICE) "
                        + "VALUES(" + itemIDtxtField.getText() + ",'" + addServicetxtField.getText() + "',"
                        + addPricetxtField.getText() + ")";
                System.out.println(sql);

                stm.executeUpdate(sql);

                //back to service page
                Parent addtoService = FXMLLoader.load(getClass().getResource("/service/FXML_services.fxml"));
                Scene sc = new Scene(addtoService);
                Stage su = (Stage) ((Node) event.getSource()).getScene().getWindow();
                su.setScene(sc);
                su.setTitle("back into services");
                su.show();

            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("!!Input Data!!");
                a.show();
            }
        } catch (Exception ex) {

            System.out.println("Rifat");
        }

    }

    @FXML
    private void backBtnAction(MouseEvent event) throws IOException {

        //back to service page
        Parent services = FXMLLoader.load(getClass().getResource("/service/FXML_services.fxml"));
        Scene scr = new Scene(services);
        Stage su = (Stage) ((Node) event.getSource()).getScene().getWindow();
        su.setScene(scr);
        su.setTitle("back into services");
        su.show();
    }

}
