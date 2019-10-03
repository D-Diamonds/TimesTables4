package com.example.timestables4;

public class TimesPair {

    private int num1;
    private int num2;
    private int product;

    public TimesPair(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
        this.product = this.num1 * this.num2;
    }

    public int getNum1() {
        return num1;
    }

    public int getNum2() {
        return num2;
    }

    public int getProduct() {
        return product;
    }

    public String toString() {
        return this.num1 + " * " + this.num2 + " = " + this.product;
    }

}
