package LoginServlet;

public class FoodItem {
    private String name;
    private double price;
    private String imageUrl;
    
    public FoodItem(String name, double price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getImageUrl() {
        return imageUrl;
    }
}
