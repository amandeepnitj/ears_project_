/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ears_project_;

import javafx.scene.control.Alert;

/**
 *
 * @author amandeepsingh12
 */
public class Alertuser {
    Alert alert;
    public Alertuser(String content)
    {
        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setContentText(content);
    }
    public Alertuser()
    {
        Alert alert =new Alert(Alert.AlertType.ERROR);
        
    }
    public void show()
    {
        alert.show();
    }
    
}
