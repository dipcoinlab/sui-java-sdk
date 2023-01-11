package com.github.wubuku.sui.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

/**
 * From TypeScript definition:
 * <p>
 * <pre>
 * export type ObjectOwner =
 * | { AddressOwner: SuiAddress }
 * | { ObjectOwner: SuiAddress }
 * | { Shared: { initial_shared_version: number } }
 * | 'Immutable';
 * </pre>
 */
@JsonDeserialize(using = ObjectOwnerDeserializer.class)
public interface ObjectOwner {

    class AddressOwner implements ObjectOwner {
        @JsonProperty("AddressOwner")
        private String addressOwner;

        public AddressOwner() {
        }

        public AddressOwner(String addressOwner) {
            this.addressOwner = addressOwner;
        }

        public String getAddressOwner() {
            return addressOwner;
        }

        public void setAddressOwner(String addressOwner) {
            this.addressOwner = addressOwner;
        }

        @Override
        public String toString() {
            return "ObjectOwner.AddressOwner{" +
                    "addressOwner='" + addressOwner + '\'' +
                    '}';
        }
    }

    class ObjectOwner_ implements ObjectOwner {
        @JsonProperty("ObjectOwner")
        private String objectOwner;

        public ObjectOwner_() {
        }

        public ObjectOwner_(String objectOwner) {
            this.objectOwner = objectOwner;
        }

        public String getObjectOwner() {
            return objectOwner;
        }

        public void setObjectOwner(String objectOwner) {
            this.objectOwner = objectOwner;
        }

        @Override
        public String toString() {
            return "ObjectOwner.ObjectOwner_{" +
                    "objectOwner='" + objectOwner + '\'' +
                    '}';
        }
    }

    class Shared implements ObjectOwner {
        @JsonProperty("Shared")
        private SharedProperties shared;

        public Shared() {
        }

        public Shared(SharedProperties shared) {
            this.shared = shared;
        }

        public SharedProperties getShared() {
            return shared;
        }

        public void setShared(SharedProperties shared) {
            this.shared = shared;
        }

        @Override
        public String toString() {
            return "ObjectOwner.Shared{" +
                    "shared=" + shared +
                    '}';
        }

        public static class SharedProperties {
            @JsonProperty("initial_shared_version")
            private Long initialSharedVersion;

            public SharedProperties() {
            }

            public SharedProperties(Long initialSharedVersion) {
                this.initialSharedVersion = initialSharedVersion;
            }

            public Long getInitialSharedVersion() {
                return initialSharedVersion;
            }

            public void setInitialSharedVersion(Long initialSharedVersion) {
                this.initialSharedVersion = initialSharedVersion;
            }

            @Override
            public String toString() {
                return "ObjectOwner.Shared.SharedProperties{" +
                        "initialSharedVersion=" + initialSharedVersion +
                        '}';
            }
        }
    }

    @JsonSerialize(using = ImmutableJsonSerializer.class)
    class Immutable implements ObjectOwner {
        public static Immutable INSTANCE = new Immutable();

        @Override
        public String toString() {
            return "ObjectOwner.Immutable{}";
        }
    }

    class ImmutableJsonSerializer extends JsonSerializer<Immutable> {
        @Override
        public void serialize(Immutable value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString("Immutable");
        }
    }
}
