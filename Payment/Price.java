
package Payment;

public class Price {
    
    private int Id;
    private String ItemName;
    private int Quantity;
    private int Price;

    public Price(int Id, String ItemName, int Quantity, int Price) {
        this.Id = Id;
        this.ItemName = ItemName;
        this.Quantity = Quantity;
        this.Price = Price;
    }

    public int getId() {
        return Id;
    }

    public String getItemName() {
        return ItemName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }
    
    
    
    
}
