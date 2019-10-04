package com.example.timestables4;

// A class representing a times table pair

public class TimesPair {

    // Instance variables
    private int num1;
    private int num2;
    private int product;

    // Creates a TimesPair
    public TimesPair(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
        this.product = this.num1 * this.num2;
    }

    // Returns num1
    public int getNum1() {
        return num1;
    }

    // Returns num2
    public int getNum2() {
        return num2;
    }

    // Returns product of num1 * num2
    public int getProduct() {
        return product;
    }

    // For testing purposes
    public String toString() {
        return this.num1 + " * " + this.num2 + " = " + this.product;
    }

}
