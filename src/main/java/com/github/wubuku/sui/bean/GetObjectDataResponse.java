package com.github.wubuku.sui.bean;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.io.IOException;

/**
 * From TypeScript definition:
 * <p>
 * <pre>
 * export type GetObjectDataResponse = {
 *   status: ObjectStatus;
 *   details: SuiObject | ObjectId | SuiObjectRef;
 * };
 * </pre>
 */
@JsonDeserialize(using = GetObjectDataResponseDeserializer.class)
public class GetObjectDataResponse {
    private ObjectStatus status;
    private Details details;

    public GetObjectDataResponse() {
    }

    public GetObjectDataResponse(ObjectStatus status, Details details) {
        this.status = status;
        this.details = details;
    }

    public ObjectStatus getStatus() {
        return status;
    }

    public void setStatus(ObjectStatus status) {
        this.status = status;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "GetObjectDataResponse{" +
                "status=" + status +
                ", details=" + details +
                '}';
    }

    @JsonDeserialize(using = DetailsDeserializer.class)
    public interface Details {
        class SuiObject extends com.github.wubuku.sui.bean.SuiObject implements Details {
            public SuiObject() {
            }

            public SuiObject(SuiData data, ObjectOwner owner,
                             String previousTransaction, Long storageRebate,
                             com.github.wubuku.sui.bean.SuiObjectRef reference) {
                super(data, owner, previousTransaction, storageRebate, reference);
            }
        }

        @JsonSerialize(using = ObjectIdSerializer.class)
        class ObjectId implements Details {
            private String id;

            public ObjectId() {
            }

            public ObjectId(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            @Override
            public String toString() {
                return "ObjectId{" +
                        "id='" + id + '\'' +
                        '}';
            }
        }

        class ObjectIdSerializer extends JsonSerializer<ObjectId> {
            @Override
            public void serialize(ObjectId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value.getId());
            }
        }

        class SuiObjectRef extends com.github.wubuku.sui.bean.SuiObjectRef implements Details {
            public SuiObjectRef() {
            }

            public SuiObjectRef(String digest, String objectId, Long version) {
                super(digest, objectId, version);
            }
        }
    }

    public static class DetailsDeserializer extends JsonDeserializer<Details> {
        @Override
        public Details deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (JsonToken.VALUE_STRING.equals(currentToken)) {
                return new Details.ObjectId(jsonParser.getText());
            } else if (JsonToken.VALUE_NULL.equals(currentToken)) {
                return null;
            } else if (currentToken.isScalarValue()) {
                throw new InvalidFormatException(jsonParser, "GetObjectDataResponse.DetailsDeserializer.deserialize() error.", currentToken, GetObjectDataResponse.Details.class);
            } else if (JsonToken.START_OBJECT.equals(currentToken)) {
                String fieldName = jsonParser.nextFieldName();
                /*
                 * <pre>
                 *    export type SuiObject = {
                 *         data: SuiData;
                 *         owner: ObjectOwner;
                 *         previousTransaction: TransactionDigest;
                 *         storageRebate: number;
                 *         reference: SuiObjectRef;
                 *     };
                 * </pre>
                 */
                SuiData data = null;
                ObjectOwner owner = null;
                String previousTransaction = null;
                Long storageRebate = null;
                SuiObjectRef reference = null;
                /*
                 * <pre>
                 * export type SuiObjectRef = {
                 *     digest: TransactionDigest;
                 *     objectId: string;
                 *     version: number;
                 * };
                 * </pre>
                 */
                String digest = null;
                String objectId = null;
                Long version = null;
                while (null != fieldName) {
                    if ("data".equals(fieldName)) {
                        jsonParser.nextToken();
                        data = jsonParser.readValueAs(SuiData.class);
                    } else if ("owner".equals(fieldName)) {
                        jsonParser.nextToken();
                        owner = jsonParser.readValueAs(ObjectOwner.class);
                    } else if ("previousTransaction".equals(fieldName)) {
                        jsonParser.nextToken();
                        previousTransaction = jsonParser.getValueAsString();
                    } else if ("storageRebate".equals(fieldName)) {
                        jsonParser.nextToken();
                        storageRebate = jsonParser.getLongValue();
                    } else if ("reference".equals(fieldName)) {
                        jsonParser.nextToken();
                        reference = jsonParser.readValueAs(SuiObjectRef.class);
                    } else if ("digest".equals(fieldName)) {
                        jsonParser.nextToken();
                        digest = jsonParser.getValueAsString();
                    } else if ("objectId".equals(fieldName)) {
                        jsonParser.nextToken();
                        objectId = jsonParser.getValueAsString();
                    } else if ("version".equals(fieldName)) {
                        jsonParser.nextToken();
                        version = jsonParser.getLongValue();
                    } else {
                        throw new InvalidFormatException(jsonParser, "GetObjectDataResponse.DetailsDeserializer.deserialize() error.", jsonParser.currentToken(), GetObjectDataResponse.Details.class);
                    }
                    fieldName = jsonParser.nextFieldName();
                }
                if (!JsonToken.END_OBJECT.equals(jsonParser.currentToken())) {
                    throw new InvalidFormatException(jsonParser, "GetObjectDataResponse.DetailsDeserializer.deserialize() error.", jsonParser.currentToken(), GetObjectDataResponse.Details.class);
                }
                if (data != null) {
                    return new Details.SuiObject(data, owner, previousTransaction, storageRebate, reference);
                }
                if (digest != null) {
                    return new Details.SuiObjectRef(digest, objectId, version);
                }
                throw new InvalidFormatException(jsonParser, "GetObjectDataResponse.DetailsDeserializer.deserialize() error.", jsonParser.currentToken(), GetObjectDataResponse.Details.class);
            } else if (JsonToken.START_ARRAY.equals(currentToken)) {
                throw new InvalidFormatException(jsonParser, "GetObjectDataResponse.DetailsDeserializer.deserialize() error.", jsonParser.currentToken(), GetObjectDataResponse.Details.class);
            }
            return null;
        }
    }
}
