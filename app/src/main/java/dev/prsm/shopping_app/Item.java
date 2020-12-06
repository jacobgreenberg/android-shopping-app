package dev.prsm.shopping_app;

public class Item
{
    protected String name;
    protected String path;
    protected double price;

    public Item(String name, String path, double price)
    {
       this.name = name;
       this.path = path;
       this.price = price;
    }
}
