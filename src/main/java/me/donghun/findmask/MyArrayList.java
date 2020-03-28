package me.donghun.findmask;

import java.util.ArrayList;

public class MyArrayList<T> extends ArrayList<T> {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<super.size(); i++) {
            sb.append(super.get(i)).append("<br>");
        }
        return sb.toString();
    }
}
