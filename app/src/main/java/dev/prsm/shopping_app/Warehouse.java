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
        warehouse.add(new Item("Egyptian Hat", "egyptian_hat", 24.99));
        warehouse.add(new Item("Salamander Hat", "salamander_hat", 19.99));
        warehouse.add(new Item("Iron Hoodie", "iron_hoodie", 74.99));
        warehouse.add(new Item("Salmon Hoodie", "salmon_hoodie", 84.99));
        warehouse.add(new Item("Obsidian Jacket", "obsidian_jacket", 2499.99));
        warehouse.add(new Item("Desire Jacket", "desire_jacket", 899.99));
        warehouse.add(new Item("Avocado Pants", "avocado_pants", 57.99));
        warehouse.add(new Item("Camel Pants", "camel_pants", 549.99));
        warehouse.add(new Item("Cornsilk Shirt", "cornsilk_shirt", 34.99));
        warehouse.add(new Item("Rain Shirt", "rain_shirt", 29.99));
    }

    List<Item> getWarehouse()
    {
        return warehouse;
    }
}
