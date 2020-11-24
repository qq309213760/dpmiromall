package com.dapeng.micromall.tools;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

    static final  String TAG="LD";
    /*
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private String sKey;
    private static String ivParameter = "1234567890123456";
    private static AESUtils instance = null;
    static byte[] bytes;
    private AESUtils(String sKey) {
        this.sKey = sKey;
    }


    public static AESUtils getInstance(String sKey,byte[] bytes) throws UnsupportedEncodingException {
        if (instance == null)
            instance = new AESUtils(sKey);

        AESUtils.bytes=bytes;
        Log.d(TAG,"当期bytes长度:"+bytes.length);
        ivParameter =new String(bytes,"UTF-8");
        Log.d(TAG,"当期bytes转字符粗:"+ivParameter);

        return instance;
    }




    public static String getStr(){
     return  new ObfuscatedString(new long[] {0xC80D4373C7615841L, 0x30CAD3FD436AED07L,
                 0x1FF1E5E7D584D366L, 0x7E8373D8261448D3L, 0x40649474E8AE0A3DL, 0x868A06AB808D23D1L,
                 0x48132D884CA0B345L, 0xD6608BECEE783C54L, 0x310B4C74972DA452L}).toString();
    }


    private static final String ENCRY_ALGORITHM="AES";
    private static final String CIPHER_MODE="AES/CBC/PKCS5Padding";
    public static byte[] decrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        StringBuffer keySb=new StringBuffer();
        for (byte b : key) {
            keySb.append(b);
        }
        //Log.d("LD","keySb:"+keySb.toString());

        StringBuffer inSb=new StringBuffer();
        for (byte b : input) {
            inSb.append(b);
        }
        //Log.d("LD","inSb:"+inSb.toString());

        // 把input分割成IV和密文:
        byte[] iv = new byte[16];
        byte[] data = new byte[input.length - 16];
        System.arraycopy(input, 0, iv, 0, 16);
        System.arraycopy(input, 16, data, 0, data.length);

        StringBuffer ivSb=new StringBuffer();
        for (byte b : iv) {
            ivSb.append(b);
        }
        //Log.d("LD","ivSb:"+ivSb.toString());
        StringBuffer dataSb=new StringBuffer();
        for (byte b : data) {
            dataSb.append(b);
        }
        //Log.d("LD","dataSb:"+dataSb.toString());


        // 解密:
        Cipher cipher = Cipher.getInstance(CIPHER_MODE);
        SecretKeySpec keySpec = new SecretKeySpec(key, ENCRY_ALGORITHM);
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivps);
        return cipher.doFinal(data);
    }



    // 加密
    public static String encrypt(String encData, String secretKey, String vector) throws Exception {

        if (secretKey == null) {
            return null;
        }
        if (secretKey.length() != 16) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
        return Base64.encodeToString(encrypted, Base64.DEFAULT);// 此处使用BASE64做转码。（处于android.util包）
    }


    // 加密
    public String encrypt(String sSrc) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return Base64.encodeToString(encrypted, Base64.DEFAULT);// 此处使用BASE64做转码。
    }

    // 解密
    public String decrypt(String sSrc) throws Exception {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

    // 解密
    public String decrypt(String sSrc, String key) throws Exception {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }
}