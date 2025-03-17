package com.github.wubuku.sui.utils;


import org.bitcoinj.core.Bech32;

import java.util.ArrayList;
import java.util.List;

public class Bech32Utils {


    public static byte[] fromBech32(String bech32) {
        if (!bech32.startsWith("suiprivkey1")) {
            throw new IllegalArgumentException("Invalid Sui private key format");
        }

        Bech32.Bech32Data decoded = Bech32.decode(bech32);
        String hrp = decoded.hrp;
        byte[] data = decoded.data; // 5位数组

        byte[] converted = convertBits(data, 5, 8, false);

        if (converted != null && converted.length == 33 && converted[0] == 0) {
            byte[] result = new byte[32];
            System.arraycopy(converted, 1, result, 0, 32);
            converted = result;
        }
        // 4. verify length
        if (converted == null || converted.length != 32) {
            throw new IllegalArgumentException("Invalid private key length");
        }

        System.out.println("Private Key Bytes: " + bytesToHex(converted));

        return converted;

        

    }

    // 将5位数组转换为8位字节数组
    private static byte[] convertBits(byte[] data, int fromBits, int toBits, boolean pad) {
        int acc = 0;
        int bits = 0;
        int maxv = (1 << toBits) - 1;
        List<Byte> ret = new ArrayList<>();

        for (byte value : data) {
            int b = value & 0xff;
            if ((b >> fromBits) != 0) {
                return null;
            }
            acc = (acc << fromBits) | b;
            bits += fromBits;
            while (bits >= toBits) {
                bits -= toBits;
                ret.add((byte) ((acc >> bits) & maxv));
            }
        }

        if (pad) {
            if (bits > 0) {
                ret.add((byte) ((acc << (toBits - bits)) & maxv));
            }
        } else if (bits >= fromBits || ((acc << (toBits - bits)) & maxv) != 0) {
            return null;
        }

        byte[] result = new byte[ret.size()];
        for (int i = 0; i < ret.size(); i++) {
            result[i] = ret.get(i);
        }
        return result;
    }

    // 字节数组转十六进制字符串
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}