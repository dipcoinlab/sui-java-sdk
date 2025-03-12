package com.github.wubuku.sui.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * From TypeScript definition:
 * <p>
 * <pre>
 * export type SuiEventEnvelope = {
 *   timestamp: number;
 *   txDigest: TransactionDigest;
 *   id: EventId;  // tx_seq_num:event_seq
 *   event: SuiEvent;
 * };
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class SuiEventEnvelope extends AbstractSuiEventEnvelope<Object> {
    //todo rename to SuiEvent???
    public SuiEventEnvelope() {
    }

    @Override
    public String toString() {
        return "SuiEventEnvelope{" +
                "id=" + getId() +
                ", packageId='" + getPackageId() + '\'' +
                ", transactionModule='" + getTransactionModule() + '\'' +
                ", sender='" + getSender() + '\'' +
                ", type='" + getType() + '\'' +
                ", bcs='" + getBcs() + '\'' +
                ", timestampMs=" + getTimestampMs() +
                ", parsedJson=" + getParsedJson() +
                '}';
    }
}
