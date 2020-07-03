package com.liangyuelong.blog.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.liangyuelong.blog.common.NoLogException;
import com.liangyuelong.blog.utils.GoogleAuthenticator;
import com.liangyuelong.blog.utils.MatrixToImageWriter;
import com.liangyuelong.blog.utils.UrlEncoder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

@RestController
public class GoogleAuthenticatorController {

    @RequestMapping("/genKey")
    public String generateKey(HttpSession session) {
        String secret = (String) session.getAttribute("secret");
        if (secret != null) {
            return secret;
        }
        secret = GoogleAuthenticator.createSecretKey();
        session.setAttribute("secret", secret);
        return secret;
    }

    @RequestMapping(value = "/qrcode")
    @ResponseBody
    public BufferedImage qrcode(HttpSession session, String account, String issuer) throws WriterException {
        String qrCodeData = "otpauth://totp/%s?secret=%s&issuer=%s";
        String secret = GoogleAuthenticator.createSecretKey();
        String content = String.format(qrCodeData, UrlEncoder.encode(issuer + ":" + account).replace("+", "%20"), UrlEncoder.encode(secret)
                .replace("+", "%20"), UrlEncoder.encode(issuer).replace("+", "%20"));
        System.out.println(content);
        session.setAttribute("secret", secret);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    @RequestMapping("/auth")
    @ResponseBody
    public Object auth(HttpSession session, String code) {
        String secret = (String) session.getAttribute("secret");
        System.out.println(secret);
        if (secret == null) {
            throw new NoLogException("generate secret");
        }
        return GoogleAuthenticator.verify(secret, code);
    }

}
