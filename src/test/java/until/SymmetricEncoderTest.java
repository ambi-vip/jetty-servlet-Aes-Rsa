package until;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/3 9:14
 */
public class SymmetricEncoderTest {


    @Test
    public void econd(){
        SymmetricEncoder se=new SymmetricEncoder();
        Scanner scanner=new Scanner(System.in);
        /*
         * 加密
         */
        System.out.println("使用AES对称加密，请输入加密的规则");
        String encodeRules="123";
        System.out.println("请输入要加密的内容:");
        String content = "Ambi";
        System.out.println("根据输入的规则"+encodeRules+"加密后的密文是:"+se.AESEncode(encodeRules, content));

        /*
         * 解密
         */
        System.out.println("使用AES对称解密，请输入加密的规则：(须与加密相同)");
        encodeRules="123";
        System.out.println("请输入要解密的内容（密文）:");
        content = "HV2fjrI3pH1Qjm3PhEOoJg==";
        System.out.println("根据输入的规则"+encodeRules+"解密后的明文是:"+se.AESDncode(encodeRules, content));
    }
}