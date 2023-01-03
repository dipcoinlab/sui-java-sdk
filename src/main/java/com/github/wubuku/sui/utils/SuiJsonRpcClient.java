package com.github.wubuku.sui.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.wubuku.sui.bean.*;
import org.starcoin.jsonrpc.JSONRPC2Request;
import org.starcoin.jsonrpc.JSONRPC2Response;
import org.starcoin.jsonrpc.client.JSONRPC2Session;
import org.starcoin.jsonrpc.client.JSONRPC2SessionException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SuiJsonRpcClient {
    private final JSONRPC2Session jsonrpc2Session;

    /**
     * @param url The JSON RPC server URL.
     */
    public SuiJsonRpcClient(String url) throws MalformedURLException {
        this.jsonrpc2Session = new JSONRPC2Session(new URL(url));
    }

    private static void assertSuccess(JSONRPC2Response<?> response) {
        if (!response.indicatesSuccess()) {
            throw new RuntimeException(response.getError().toString());
        }
    }

    protected JSONRPC2Session getJSONRPC2Session() {
        return jsonrpc2Session;
    }

    public SuiTransactionResponse getTransaction(String digest) {
        List<Object> params = new ArrayList<>();
        params.add(digest);
        JSONRPC2Request jsonrpc2Request = new JSONRPC2Request("sui_getTransaction", params,
                System.currentTimeMillis());
        try {
            JSONRPC2Response<SuiTransactionResponse> jsonrpc2Response = getJSONRPC2Session().send(jsonrpc2Request,
                    SuiTransactionResponse.class);
            assertSuccess(jsonrpc2Response);
            return jsonrpc2Response.getResult();
        } catch (JSONRPC2SessionException e) {
            throw new RuntimeException(e);
        }
    }

    public TransactionsPage getTransactions(TransactionQuery query,
                                            String cursor,
                                            int limit,
                                            boolean descendingOrder) {

        List<Object> params = new ArrayList<>();
        params.add(query);
        params.add(cursor);
        params.add(limit);
        params.add(descendingOrder);
        JSONRPC2Request jsonrpc2Request = new JSONRPC2Request("sui_getTransactions", params,
                System.currentTimeMillis());
        try {
            JSONRPC2Response<TransactionsPage> jsonrpc2Response = getJSONRPC2Session().send(jsonrpc2Request,
                    TransactionsPage.class);
            assertSuccess(jsonrpc2Response);
            return jsonrpc2Response.getResult();
        } catch (JSONRPC2SessionException e) {
            throw new RuntimeException(e);
        }
    }

    public <F> PaginatedMoveEvents<F> getMoveEvents(String moveEvent,
                                                    EventId cursor, int limit, boolean descendingOrder,
                                                    Class<F> moveEventFieldsType) {
        List<Object> params = new ArrayList<>();
        params.add(new EventQuery.MoveEvent(moveEvent));
        params.add(cursor);
        params.add(limit);
        params.add(descendingOrder);
        JSONRPC2Request jsonrpc2Request = new JSONRPC2Request("sui_getEvents", params,
                System.currentTimeMillis());
        try {
            JSONRPC2Response<PaginatedMoveEvents<F>> jsonrpc2Response = getJSONRPC2Session()
                    .sendAndGetParametricTypeResult(jsonrpc2Request, PaginatedMoveEvents.class, moveEventFieldsType);
            assertSuccess(jsonrpc2Response);
            return jsonrpc2Response.getResult();
        } catch (JSONRPC2SessionException e) {
            throw new RuntimeException(e);
        }
    }

    public PaginatedEvents getEvents(EventQuery query, EventId cursor, int limit, boolean descendingOrder) {
        List<Object> params = new ArrayList<>();
        params.add(query);
        params.add(cursor);
        params.add(limit);
        params.add(descendingOrder);
        JSONRPC2Request jsonrpc2Request = new JSONRPC2Request("sui_getEvents", params,
                System.currentTimeMillis());
        try {
            JSONRPC2Response<PaginatedEvents> jsonrpc2Response = getJSONRPC2Session().send(jsonrpc2Request,
                    new TypeReference<PaginatedEvents>() {
                    });
            assertSuccess(jsonrpc2Response);
            return jsonrpc2Response.getResult();
        } catch (JSONRPC2SessionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SuiObjectInfo> getObjectsOwnedByAddress(String address) {
        List<Object> params = new ArrayList<>();
        params.add(address);
        return getObjects("sui_getObjectsOwnedByAddress", params);
    }

    /**
     * @param objectId the ID of the owner object
     */
    public List<SuiObjectInfo> getObjectsOwnedByObject(String objectId) {
        List<Object> params = new ArrayList<>();
        params.add(objectId);
        return getObjects("sui_getObjectsOwnedByObject", params);
    }

    private List<SuiObjectInfo> getObjects(String method, List<Object> params) {
        JSONRPC2Request jsonrpc2Request = new JSONRPC2Request(method, params,
                System.currentTimeMillis());
        try {
            JSONRPC2Response<List<SuiObjectInfo>> jsonrpc2Response = jsonrpc2Session.sendAndGetListResult(
                    jsonrpc2Request, SuiObjectInfo.class);
            assertSuccess(jsonrpc2Response);
            return jsonrpc2Response.getResult();
        } catch (JSONRPC2SessionException e) {
            throw new RuntimeException(e);
        }
    }

    public GetObjectDataResponse getObject(String objectId) {
        List<Object> params = new ArrayList<>();
        params.add(objectId);
        return getObject("sui_getObject", params);
    }

    public GetObjectDataResponse getDynamicFieldObject(String parentObjectId, String field) {
        List<Object> params = new ArrayList<>();
        params.add(parentObjectId);
        params.add(field);
        return getObject("sui_getDynamicFieldObject", params);
    }

    private GetObjectDataResponse getObject(String method, List<Object> params) {
        JSONRPC2Request jsonrpc2Request = new JSONRPC2Request(method, params, System.currentTimeMillis());
        try {
            JSONRPC2Response<GetObjectDataResponse> jsonrpc2Response = jsonrpc2Session.send(jsonrpc2Request,
                    GetObjectDataResponse.class);
            assertSuccess(jsonrpc2Response);
            return jsonrpc2Response.getResult();
        } catch (JSONRPC2SessionException e) {
            throw new RuntimeException(e);
        }
    }

    public CoinPage getCoins(String owner, String coinType, String cursor, int limit) {
        List<Object> params = new ArrayList<>();
        params.add(owner);
        params.add(coinType);
        params.add(cursor);
        params.add(limit);
        return getCoins("sui_getCoins", params);
    }

//    public CoinPage getAllCoins(String owner, String cursor, int limit) {
//        List<Object> params = new ArrayList<>();
//        params.add(owner);
//        params.add(cursor);
//        params.add(limit);
//        return getCoins("sui_getAllCoins", params);
//    }

    private CoinPage getCoins(String method, List<Object> params) {
        JSONRPC2Request jsonrpc2Request = new JSONRPC2Request(method, params, System.currentTimeMillis());
        try {
            JSONRPC2Response<CoinPage> jsonrpc2Response = jsonrpc2Session.send(jsonrpc2Request, CoinPage.class);
            assertSuccess(jsonrpc2Response);
            return jsonrpc2Response.getResult();
        } catch (JSONRPC2SessionException e) {
            throw new RuntimeException(e);
        }
    }

    public SuiCoinMetadata getCoinMetadata(String coinType) {
        List<Object> params = new ArrayList<>();
        params.add(coinType);
        JSONRPC2Request jsonrpc2Request = new JSONRPC2Request("sui_getCoinMetadata", params,
                System.currentTimeMillis());
        try {
            JSONRPC2Response<SuiCoinMetadata> jsonrpc2Response = jsonrpc2Session.send(jsonrpc2Request,
                    SuiCoinMetadata.class);
            assertSuccess(jsonrpc2Response);
            return jsonrpc2Response.getResult();
        } catch (JSONRPC2SessionException e) {
            throw new RuntimeException(e);
        }
    }

    public Supply getTotalSupply(String coinType) {
        List<Object> params = new ArrayList<>();
        params.add(coinType);
        JSONRPC2Request jsonrpc2Request = new JSONRPC2Request("sui_getTotalSupply", params, System.currentTimeMillis());
        try {
            JSONRPC2Response<Supply> jsonrpc2Response = jsonrpc2Session.send(jsonrpc2Request, Supply.class);
            assertSuccess(jsonrpc2Response);
            return jsonrpc2Response.getResult();
        } catch (JSONRPC2SessionException e) {
            throw new RuntimeException(e);
        }
    }
}
