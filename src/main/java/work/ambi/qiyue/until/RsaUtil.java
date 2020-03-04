package work.ambi.qiyue.until;

import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/3 16:07
 */
public class RsaUtil {
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Logger logger = Logger.getLogger(RsaUtil.class);
    /**
     * 获取密钥对
     *
     * @return 密钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = decoder.decode(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = decoder.decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data 待加密数据
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(encoder.encodeToString(encryptedData));
    }

    /**
     * RSA解密
     *
     * @param data 待解密数据
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = decoder.decode(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 私钥加密(未考虑明文长度超过限制的情况)
     * @param plain_text 明文
     * @param privateStr 私钥
     * @return
     */
    public static String enWithRSAPrivateKey(String plain_text,String privateStr) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(privateStr));
            return byte2hex(cipher.doFinal(plain_text.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥解密(未考虑明文长度超过限制的情况)
     * @param plain_text 密文
     * @param publicStr 公钥
     * @return
     */
    public static String deWithRSAPublicKey(String plain_text,String publicStr) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, getPublicKey(publicStr));
            byte[] plainText = cipher.doFinal(hex2byte(plain_text));
            return new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MD5withRSA签名
     *
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String signMD5withRSA(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(encoder.encodeToString(signature.sign()));
    }

    /**
     * signVerifyMD5withRSA数字签名验证.
     *
     * @param srcData 原始字符串
     * @param publicKey 公钥
     * @param sign 签名
     * @return 是否验签通过
     */
    public static boolean signVerifyMD5withRSA(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(decoder.decode(sign.getBytes()));
    }


    /**
     * SHA256withRSA签名
     *
     * @param privateKeyStr
     *            私钥
     * @param plain_text
     *            明文
     * @return
     */
    public static String signSha256withRSA(String plain_text,String privateKeyStr) {
        try {
            Signature Sign = Signature.getInstance("SHA256withRSA");
            PrivateKey privateKey = getPrivateKey(privateKeyStr);
            Sign.initSign(privateKey);
            Sign.update(plain_text.getBytes());
            byte[] signed = Sign.sign();
            return byte2hex(signed) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * SHA1WithRSA数字签名验证.
     *
     * @param data   数据
     * @param strHexPublicKey    公钥
     * @param sign   签名
     * @return true, if successful
     * @throws Exception the exception
     */
    public static boolean signVerifySHA256WithRSA(String data, String strHexPublicKey, String sign)
            throws Exception {
        try {
            PublicKey rsaPublicKey = getPublicKey(strHexPublicKey);
            //公钥解签
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(rsaPublicKey);
            sig.update(data.getBytes());
            return sig.verify(hex2byte(sign));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Description：将二进制转换成16进制字符串
     *
     * @param b
     * @return
     * @return String
     * @author name：
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * Description：将16进制字符串转换成二进制
     * @return
     * @return String
     * @author name：
     */
    public static byte[] hex2byte(String hexString) {
        if (hexString == null || "".equals(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static void main(String[] args) {
        try {
            // 生成密钥对
            KeyPair keyPair = getKeyPair();
            String privateKey = new String(encoder.encodeToString(keyPair.getPrivate().getEncoded()));
            String publicKey = new String(encoder.encodeToString(keyPair.getPublic().getEncoded()));
            System.out.println("私钥:" + privateKey);
            System.out.println("公钥:" + publicKey);
            String data = "张三丰";

            // RSA公钥加密
            String encryptData = encrypt(data, getPublicKey(publicKey));
            System.out.println("公钥加密后内容:" + encryptData);
            // RSA私钥解密
            String decryptData = decrypt(encryptData, getPrivateKey(privateKey));
            System.out.println("私钥解密后内容:" + decryptData);

            // RSA私钥加密
            String encryptData1 = enWithRSAPrivateKey(data, privateKey);
            System.out.println("私钥加密后内容:" + encryptData1);
            // RSA公钥解密
            String decryptData1 = deWithRSAPublicKey(encryptData1, publicKey);
            System.out.println("公钥解密后内容:" + decryptData1);

            // RSA签名(MD5withRSA)
            String sign = signMD5withRSA(data, getPrivateKey(privateKey));
            // RSA验签(MD5withRSA)
            boolean result = signVerifyMD5withRSA(data, getPublicKey(publicKey), sign);
            System.out.println("MD5withRSA验签结果:" + result);

            // RSA签名(SHA256withRSA)
            String sign1 = signSha256withRSA(data, privateKey);
            // RSA验签(SHA256withRSA)
            boolean result1 = signVerifySHA256WithRSA(data, publicKey, sign1);
            System.out.println("SHA256withRSA验签结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }
    }
}
