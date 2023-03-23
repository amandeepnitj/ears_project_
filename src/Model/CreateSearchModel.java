/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javafx.scene.control.Button;

/**
 *
 * @author amandeepsingh12
 */
public class CreateSearchModel {
    private String id;
    private  String username;
    private String designation;
    public Button select_btn;

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDesignation() {
        return designation;
    }

    public Button getSelect_btn() {
        return select_btn;
    }

    

    
    
}
