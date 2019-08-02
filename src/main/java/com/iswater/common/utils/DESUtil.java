package com.iswater.common.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author vic
 * @date 2013-5-26
 * DES对称加密工具类
 */
public class DESUtil {

    private static Key key;

    /**
     * 根据参数生成KEY
     */
    public static void setKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            _generator.init(secureRandom);
            key = _generator.generateKey();
            _generator = null;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        }
    }

    /**
     * 加密String明文输入,String密文输出
     */
    public static String getEncString(String strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        BASE64Encoder base64en = new BASE64Encoder();
        try {
            byteMing = strMing.getBytes("UTF8");
            byteMi = getEncCode(byteMing);
            strMi = base64en.encode(byteMi);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            base64en = null;
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    /**
     * 解密 以String密文输入,String明文输出
     * @param strMi
     * @return
     */
    public static String getDesString(String strMi) {
        BASE64Decoder base64De = new BASE64Decoder();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = base64De.decodeBuffer(strMi);
            byteMing = getDesCode(byteMi);
            strMing = new String(byteMing, "UTF8");
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            base64De = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     * @param byteS
     * @return
     */
    private static byte[] getEncCode(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     * @param byteD
     * @return
     */
    private static byte[] getDesCode(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    
    public static String getMd5(String input){
    		return DigestUtils.md5Hex(input) ;
    }
    
    
    public static void main(String args[]) {

       // DESUtil.setKey("szjzwl.deci.u21msax8asdj");
        DESUtil.setKey("sz7road.deci.u21msax8asdj");
//        String str1 = "pg_user";
		String str1 = "m_xyjzwl";
        //DES加密
        String str2 = DESUtil.getEncString(str1);
        String deStr = DESUtil.getDesString(str2);
        System.out.println("密文:" + str2);
        //DES解密
        System.out.println("明文:" + deStr);

        //DES加密
        String str4 = "8dfP_*qwd5dzBm";
        String str3 = DESUtil.getEncString(str4);
        String deStr2 = DESUtil.getDesString(str3);
        System.out.println("密文:" + str3);
        //DES解密
        System.out.println("明文:" + deStr2);


        String ticket = "20ffei9gicdn5sblvag2fhr0ge37clqr";
        String encTicket = DESUtil.getEncString(ticket);
        System.out.println("ticket:" + encTicket);


        System.out.println(DESUtil.getDesString("pnw+ZxojWy178TwDpysn0Q=="));
        System.out.println(DESUtil.getDesString("NjENgL6cyYhUMWJRB1IXmA=="));

        System.out.println(10 / 11);

        System.out.println(StringUtils.isNumeric("-1"));
    }
}
