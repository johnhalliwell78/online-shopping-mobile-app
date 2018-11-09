package day03.giangth.onlinemanagement.model;

public class Order {
    public int id;
    public String name;
    public long price;
    public String image;
    public int number;

    public Order(int id, String name, long price, String image, int number) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
