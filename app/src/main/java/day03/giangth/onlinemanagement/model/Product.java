package day03.giangth.onlinemanagement.model;

import java.io.Serializable;

public class Product implements Serializable {
    public int ID;
    public String name;
    public Integer price;
    public String image;
    public String description;
    public int idtype;

    public Product(int ID, String name, Integer price, String image, String description, int idtype) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.idtype = idtype;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdtype() {
        return idtype;
    }

    public void setIdtype(int idtype) {
        this.idtype = idtype;
    }
}
