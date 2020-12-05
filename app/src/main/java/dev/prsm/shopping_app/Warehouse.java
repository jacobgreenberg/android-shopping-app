package dev.prsm.shopping_app;

import java.util.ArrayList;
import java.util.List;

public class Warehouse
{
    List<Item> warehouse;

    public Warehouse()
    {
        warehouse = new ArrayList<>();
    }

    void buildWarehouse()
    {
        warehouse.add(new Item("Egyptian Hat", "hat1", 24.99));
        warehouse.add(new Item("Salamander Beenie", "hat2", 19.99));
        warehouse.add(new Item("Iron Hoodie", "hoodie1", 74.99));
        warehouse.add(new Item("Salmon Hoodie", "hoodie2", 84.99));
        warehouse.add(new Item("Obsidian Jacket", "jacket1", 2499.99));
        warehouse.add(new Item("Desire Jacket", "jacket2", 899.99));
        warehouse.add(new Item("Avocado Pants", "pants1", 57.99));
        warehouse.add(new Item("Camel Pants", "pants2", 549.99));
        warehouse.add(new Item("Cornsilk Shirt", "tshirt1", 34.99));
        warehouse.add(new Item("Rhubarb Shirt", "tshirt2", 29.99));
    }

    List<Item> getWarehouse()
    {
        return warehouse;
    }
}
