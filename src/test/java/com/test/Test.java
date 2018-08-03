package com.test;

public enum Test {
    MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6) {
        @Override
        public boolean isRest() {
            return true;
        }
    },
    SUN(0) {
        @Override
        public boolean isRest() {
            return true;
        }
    };
 
    private int value;
 
    private Test(int value) {
        this.value = value;
    }
 
    public int getValue() {
        return value;
    }
 
    public boolean isRest() {
        return false;
    }
    
    public static void main(String[] args) {
    	 System.out.println("EnumTest.FRI 的 value = " + Test.FRI.getValue());//属性
    	 System.out.println("EnumTest.SUN 的 value = " + Test.SUN.isRest());//方法
	}
}
