package com.jfcore.tools;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 

 

public class AesHelp {
	
	
	private static Logger logger = LoggerFactory.getLogger(AesHelp.class);
	
	/**
     * 加密
     * @param content 待加密内容
     * @param password  加密密钥
     * @return
     */
    public static String encrypt(String content, String password) {
        try {
           
        	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
              
              
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            
            
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(byteContent);
            return parseByte2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static String decrypt(String content, String password) {
        try {
        	if(content==null || content.isEmpty())
        	{
        		return null;
        	}
        	if(password==null || password.isEmpty())
        	{
        		return null;
        	}
        	
        	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
              
              
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            
          
            
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(parseHexStr2Byte(content));           
            return new String(result,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // 10进制转十六进制
    public static String intToHex(int n) {
        StringBuffer s = new StringBuffer();
        String a;
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            s = s.append(b[n%16]);
            n = n/16;            
        }
        a = s.reverse().toString();
        return a;
    }
    
    // 二进制转十六进制
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    // 十六进制转二进制
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    

	public static String encryptBase64(String content, String psw) {

		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);
			String algorithmstr = "AES/ECB/PKCS5Padding";
			Cipher cipher = Cipher.getInstance(algorithmstr);
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(psw.getBytes(), "AES"));

			byte[] b = cipher.doFinal(content.getBytes("utf-8"));
			
			return Base64.getEncoder().encodeToString(b);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decryptBase64(String encryptStr, String decryptKey) {
		try {
			byte[] encryptBytes = Base64.getDecoder().decode(encryptStr);

			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);
			String algorithmstr = "AES/ECB/PKCS5Padding";
			Cipher cipher = Cipher.getInstance(algorithmstr);
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
			byte[] decryptBytes = cipher.doFinal(encryptBytes);
			return new String(decryptBytes);
		} catch (Exception e) {
			String msg = String.format("encryptStr:%s,decryptKey:%s", encryptStr,decryptKey);
			
			logger.error(msg, e);
			 
		}
		return null;
	}
	
	public static void main1(String[] args)
	{
		String dbcipher = "4PDEW2RQOMKXT6OA";
		String str = encryptBase64("123456789",dbcipher);
		
		System.out.println(str);
		

		
		
		
	}


	public static void main(String[] args) {
	
	}

}