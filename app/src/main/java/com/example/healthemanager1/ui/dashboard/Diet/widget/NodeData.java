package com.example.healthemanager1.ui.dashboard.Diet.widget;


public class NodeData {

    private String name;
    private String quantity;
    private String name_id;
    public NodeData() {
    }

    public NodeData(String name,String quantity,String name_id) {
        this.name = name;
        this.quantity = quantity;
        this.name_id=name_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity;}

    public String getNameid() { return name_id; }
    public void setNameid(String name_id) { this.name_id = name_id;}


}


