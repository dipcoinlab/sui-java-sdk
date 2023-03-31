package com.github.wubuku.sui.bean;

import java.util.ArrayList;

/**
 * From Rust definition:
 * <p>
 * <pre>
 * #[derive(Eq, PartialEq, Clone, Debug, Default, Serialize, Deserialize, JsonSchema)]
 * #[serde(rename = "TransactionBlockEvents", transparent)]
 * pub struct SuiTransactionBlockEvents {
 *     pub data: Vec<SuiEvent>,
 * }
 * </pre>
 */
public class SuiTransactionBlockEvents extends ArrayList<SuiTransactionBlockEvents.SuiEvent> {

    /**
     * From Rust definition:
     * <p>
     * <pre>
     * #[serde_as]
     * #[derive(Eq, PartialEq, Clone, Debug, Serialize, Deserialize, JsonSchema)]
     * #[serde(rename = "Event", rename_all = "camelCase")]
     * pub struct SuiEvent {
     *     /// Sequential event ID, ie (transaction seq number, event seq number).
     *     /// 1) Serves as a unique event ID for each fullnode
     *     /// 2) Also serves to sequence events for the purposes of pagination and querying.
     *     ///    A higher id is an event seen later by that fullnode.
     *     /// This ID is the "cursor" for event querying.
     *     pub id: EventID,
     *     /// Move package where this event was emitted.
     *     pub package_id: ObjectID,
     *     #[schemars(with = "String")]
     *     #[serde_as(as = "DisplayFromStr")]
     *     /// Move module where this event was emitted.
     *     pub transaction_module: Identifier,
     *     /// Sender's Sui address.
     *     pub sender: SuiAddress,
     *     #[schemars(with = "String")]
     *     #[serde_as(as = "SuiStructTag")]
     *     /// Move event type.
     *     pub type_: StructTag,
     *     /// Parsed json value of the event
     *     pub parsed_json: Value,
     *     #[serde_as(as = "Base58")]
     *     #[schemars(with = "Base58")]
     *     /// Base 58 encoded bcs bytes of the move event
     *     pub bcs: Vec<u8>,
     *     /// UTC timestamp in milliseconds since epoch (1/1/1970)
     *     #[serde(skip_serializing_if = "Option::is_none")]
     *     pub timestamp_ms: Option<u64>,
     * }
     * </pre>
     */
    public static class SuiEvent {
        private EventId id;
        private String packageId;
        private String transactionModule;
        private String sender;
        private String type;
        private Object parsedJson;
        private String bcs;
        private Long timestampMs;

        public EventId getId() {
            return id;
        }

        public void setId(EventId id) {
            this.id = id;
        }

        public String getPackageId() {
            return packageId;
        }

        public void setPackageId(String packageId) {
            this.packageId = packageId;
        }

        public String getTransactionModule() {
            return transactionModule;
        }

        public void setTransactionModule(String transactionModule) {
            this.transactionModule = transactionModule;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getParsedJson() {
            return parsedJson;
        }

        public void setParsedJson(Object parsedJson) {
            this.parsedJson = parsedJson;
        }

        public String getBcs() {
            return bcs;
        }

        public void setBcs(String bcs) {
            this.bcs = bcs;
        }

        public Long getTimestampMs() {
            return timestampMs;
        }

        public void setTimestampMs(Long timestampMs) {
            this.timestampMs = timestampMs;
        }

        @Override
        public String toString() {
            return "SuiEvent{" +
                    "id=" + id +
                    ", packageId='" + packageId + '\'' +
                    ", transactionModule='" + transactionModule + '\'' +
                    ", sender='" + sender + '\'' +
                    ", type='" + type + '\'' +
                    ", parsedJson=" + parsedJson +
                    ", bcs='" + bcs + '\'' +
                    ", timestampMs=" + timestampMs +
                    '}';
        }
    }
}
