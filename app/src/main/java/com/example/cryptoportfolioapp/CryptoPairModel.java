package com.example.cryptoportfolioapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class CryptoPairModel {

    private String symbol, price;

    public CryptoPairModel() {
    }

    public CryptoPairModel(String symbol, String price) {
        this.symbol = symbol;
        this.price = price;

    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public static CryptoPairModel parseJSONObject(JSONObject object){
        CryptoPairModel cpm = new CryptoPairModel();

        try{
            if(object.has("symbol")){
                cpm.setSymbol(object.getString("symbol"));
            }
            if(object.has("price")){
                cpm.setPrice(object.getString("price"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return cpm;
    }

    public static LinkedList<CryptoPairModel> parseJSONArray(JSONArray array){
        LinkedList<CryptoPairModel> list = new LinkedList<>();

        try{
            for(int i = 0; i < array.length(); i++){
                CryptoPairModel cpm = parseJSONObject(array.getJSONObject(i));
                list.add(cpm);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
}
