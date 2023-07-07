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

public class Coinranking implements ICoinProvider {
    String APIKEY = "";
    String APIHOST = "https://api.coinranking.com/v2";
    private String getUuid(String name) {
        String uuid = null;
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(this.APIHOST+"/search-suggestions?query="+name))
                    .header("x-access-token", this.APIKEY)
                    .build();
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObj = new JSONObject(getResponse.body());
            if (Objects.equals(jsonObj.getString("status"),"success")) {
                JSONArray objectsCryptoCoins = jsonObj.getJSONObject("data").getJSONArray("coins");
                for(int index = 0; index < objectsCryptoCoins.length(); index++){
                    String coinName = objectsCryptoCoins.getJSONObject(index).getString("name");
                    if (Objects.equals(coinName.toLowerCase(), name.toLowerCase())) {
                        uuid = objectsCryptoCoins.getJSONObject(index).getString("uuid");
                        break;
                    }
                }
                if (uuid == null && objectsCryptoCoins.length() > 0){
                    uuid = objectsCryptoCoins.getJSONObject(0).getString("uuid");
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
                    .uri(new URI(this.APIHOST + "/coin/" + uuid))
                    .header("x-access-token", this.APIKEY)
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObj = new JSONObject(getResponse.body());

            if (Objects.equals(jsonObj.getString("status"), "success")) {

                String coinName = jsonObj.getJSONObject("data").getJSONObject("coin").getString("name");
                String coinCurCurrency = "USD";
                double coinPrice = jsonObj.getJSONObject("data").getJSONObject("coin").getDouble("price");
                String coinIconUrl = jsonObj.getJSONObject("data").getJSONObject("coin").getString("iconUrl");
                double coinPriceChanged24h = jsonObj.getJSONObject("data").getJSONObject("coin").getDouble("change");

                return new Coin(uuid, coinName, coinCurCurrency, coinPrice, coinIconUrl, coinPriceChanged24h);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Coin("500");
    }
}
