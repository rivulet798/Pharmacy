package com.epam.service.utils;

import org.apache.log4j.Logger;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hasher {
    private static Logger logger = Logger.getLogger(Hasher.class);

    public static String hashBySha1(String input) {
        String sha1 = null;
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            logger.error(e.getMessage());        }
        return sha1;
    }

    public static String generateCsrfToken(){
        SecureRandom secureRandom = new SecureRandom();
        String csrfToken = String.valueOf(secureRandom.nextLong());
        csrfToken = Hasher.hashBySha1(csrfToken);
        return  csrfToken;
    }
}
