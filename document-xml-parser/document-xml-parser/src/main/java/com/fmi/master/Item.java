package com.fmi.master;

public class Item {
    @Documentable
    private String name = "Book";
    @Documentable(title = "sub_title")
    private String subTitle = "The book of science";
    @Documentable
    private double price = 20.0;
}
