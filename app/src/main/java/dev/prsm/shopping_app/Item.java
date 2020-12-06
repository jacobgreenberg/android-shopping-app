package dev.prsm.shopping_app;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable
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

    protected Item(Parcel in)
    {
        name = in.readString();
        path = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>()
    {
        @Override
        public Item createFromParcel(Parcel in)
        {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size)
        {
            return new Item[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeString(path);
        dest.writeDouble(price);
    }
}
