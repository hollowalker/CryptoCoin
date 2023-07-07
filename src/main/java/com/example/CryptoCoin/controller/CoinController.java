package com.example.CryptoCoin.controller;

import com.example.CryptoCoin.api.Coingecko;
import com.example.CryptoCoin.api.Coinranking;
import com.example.CryptoCoin.exceptions.NotFoundException;
import com.example.CryptoCoin.DTO.Coin;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coin")
public class CoinController {
//    Coingecko coinApi = new Coingecko();
    Coinranking coinApi = new Coinranking();
    @PostMapping("/getCoin")
    public Coin getCoin(@RequestBody String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);
            String name = jsonObj.getString("name");
            return coinApi.getCoinByName(name);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
