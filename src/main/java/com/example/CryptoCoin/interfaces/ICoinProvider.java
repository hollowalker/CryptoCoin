package com.example.CryptoCoin.interfaces;

import com.example.CryptoCoin.DTO.Coin;

public interface ICoinProvider {
    Coin getCoinByName(String name);
}
