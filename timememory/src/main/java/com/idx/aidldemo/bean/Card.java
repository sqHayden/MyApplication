package com.idx.aidldemo.bean;

/**
 * Created by hayden on 18-5-22.
 */

public class Card {
    private String cardName;
    private int cardId;

    public Card(String cardName, int cardId) {
        this.cardName = cardName;
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
