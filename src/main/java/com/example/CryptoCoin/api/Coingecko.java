package com.example.CryptoCoin.api;

import com.example.CryptoCoin.DTO.Coin;
import com.example.CryptoCoin.exceptions.NotFoundException;
import com.example.CryptoCoin.interfaces.ICoinProvider;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class Coingecko implements ICoinProvider {
    String APIHOST = "https://api.coingecko.com/api/v3";
    private String getUuid(String name) {
        String uuid = null;
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(this.APIHOST+"/search?query="+name))
                    .header("x-cg-pro-api-key", "")
                    .build();
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObj = new JSONObject(getResponse.body());
            JSONArray objectsCryptoCoins = jsonObj.getJSONArray("coins");
            if (objectsCryptoCoins.length() >  0) {
                for(int index = 0; index < objectsCryptoCoins.length(); index++){
                    String coinName = objectsCryptoCoins.getJSONObject(index).getString("name");
                    if (Objects.equals(coinName.toLowerCase(), name.toLowerCase())) {
                        uuid = objectsCryptoCoins.getJSONObject(index).getString("id");
                        break;
                    }
                }
                if (uuid == null){
                    uuid = objectsCryptoCoins.getJSONObject(0).getString("id");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return uuid;
    }
    @Override
    public Coin getCoinByName(String name) {
        String uuid = getUuid(name);
        if (uuid == null) {
            throw new NotFoundException();
        }
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(this.APIHOST + "/coins/" + uuid))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObj = new JSONObject(getResponse.body());

            String coinName = jsonObj.getJSONObject("localization").getString("ru");
            String coinCurCurrency = "RUB";
            double coinPrice = jsonObj.getJSONObject("market_data").getJSONObject("current_price").getDouble("rub");
            String coinIconUrl = jsonObj.getJSONObject("image").getString("large");
            double coinPriceChanged24h = jsonObj.getJSONObject("market_data").getDouble("market_cap_change_percentage_24h");

            return new Coin(uuid, coinName, coinCurCurrency, coinPrice, coinIconUrl, coinPriceChanged24h);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Coin("500");
    }
}
