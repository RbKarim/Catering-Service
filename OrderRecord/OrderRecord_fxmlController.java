/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderRecord;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author 88016
 */
public class OrderRecord_fxmlController implements Initializable {

    @FXML
    private ImageView backBtn;
    @FXML
    private TableColumn<?, ?> OrderID;
    @FXML
    private TableColumn<?, ?> ItemID;
    @FXML
    private TableColumn<?, ?> Quantity;
    @FXML
    private TableColumn<?, ?> TotalCost;
    @FXML
    private TableColumn<?, ?> OrderDate;
    @FXML
    private TableColumn<?, ?> CusotmerID;
    @FXML
    private Button SearchBtn;
    @FXML
    private JFXDatePicker Input_Date;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void BackAction(MouseEvent event) {
    }

    @FXML
    private void SearchAction(ActionEvent event) {
    }
    
}
