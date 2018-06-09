package com.firestarterstagss.Models;

public class CoinsModel {

    String planID,numbarOFCoins,amount;

    public CoinsModel(String planID, String numbarOFCoins, String amount) {
        this.planID = planID;
        this.numbarOFCoins = numbarOFCoins;
        this.amount = amount;
    }

    public CoinsModel(String numbarOFCoins, String amount) {
        this.numbarOFCoins = numbarOFCoins;
        this.amount = amount;
    }

    public CoinsModel() {
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public String getNumbarOFCoins() {
        return numbarOFCoins;
    }

    public void setNumbarOFCoins(String numbarOFCoins) {
        this.numbarOFCoins = numbarOFCoins;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
