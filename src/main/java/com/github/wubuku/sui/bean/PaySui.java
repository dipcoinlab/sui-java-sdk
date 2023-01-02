package com.github.wubuku.sui.bean;

import java.util.Arrays;

/**
 * From TypeScript definition:
 * <p>
 * <pre>
 * export type PaySui = {
 *   coins: SuiObjectRef[];
 *   recipients: SuiAddress[];
 *   amounts: number[];
 * };
 * </pre>
 */
public class PaySui {
    private SuiObjectRef[] coins;
    private String[] recipients;
    private Long[] amounts;

    public PaySui() {
    }

    public PaySui(SuiObjectRef[] coins, String[] recipients, Long[] amounts) {
        this.coins = coins;
        this.recipients = recipients;
        this.amounts = amounts;
    }

    public SuiObjectRef[] getCoins() {
        return coins;
    }

    public void setCoins(SuiObjectRef[] coins) {
        this.coins = coins;
    }

    public String[] getRecipients() {
        return recipients;
    }

    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
    }

    public Long[] getAmounts() {
        return amounts;
    }

    public void setAmounts(Long[] amounts) {
        this.amounts = amounts;
    }

    @Override
    public String toString() {
        return "PaySui{" +
                "coins=" + Arrays.toString(coins) +
                ", recipients=" + Arrays.toString(recipients) +
                ", amounts=" + Arrays.toString(amounts) +
                '}';
    }
}
