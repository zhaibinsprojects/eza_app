package com.sanbang.utils;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public class RandImgCodeUtil {

	private static Logger log=Logger.getLogger(RandImgCodeUtil.class.getName());
	// 验证码图片的宽度。        
    private static int width = 102;        
    // 验证码图片的高度。        
    private static int height = 35;        
    // 验证码字符个数        
    private static int codeCount = 4;        
    private static int x = width / (codeCount+1);        
    // 字体高度        
    private static int fontHeight=height - 2;        
    private static int codeY=height - 4;        
    private static Random random = new Random();        
    private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',        
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',        
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };        
    public static void service(HttpServletRequest req, HttpServletResponse resp,String USERIDCARD,Integer USERIDCARDEXPIR,Long redisloginrandcodeexpir)        
            throws ServletException, java.io.IOException {
        // 定义图像buffer        
        BufferedImage buffImg = new BufferedImage(width, height,        
                BufferedImage.TYPE_INT_RGB);        
        Graphics2D g = buffImg.createGraphics(); 
        // 创建一个随机数生成器类        
        // 将图像填充为白色        
        g.setColor(Color.WHITE);        
        g.fillRect(0, 0, width, height);        
        // 创建字体，字体的大小应该根据图片的高度来定。        
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);        
        // 设置字体。        
        g.setFont(font);        
        // 画边框。        
        g.setColor(Color.BLACK);        
        g.drawRect(0, 0, width - 1, height - 1);        
        // 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。       
        g.setColor(Color.BLACK);        
        for (int i = 0; i < 10; i++) {        
            int x = random.nextInt(width);        
            int y = random.nextInt(height);        
            int xl = random.nextInt(12);        
            int yl = random.nextInt(12);        
            g.drawLine(x, y, x + xl, y + yl);        
        }        
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。        
        StringBuffer randomCode = new StringBuffer();        
        int red = 0, green = 0, blue = 0;        
        // 随机产生codeCount数字的验证码。        
        for (int i = 0; i < codeCount; i++) {    
            // 得到随机产生的验证码数字。        
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);        
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。        
            red = random.nextInt(1);        
            green = random.nextInt(255);        
            blue = random.nextInt(1);        
            // 用随机产生的颜色将验证码绘制到图像中。        
            g.setColor(new Color(red, green, blue));        
            g.drawString(strRand, (i) * x+x/3, codeY);        
            // 将产生的四个随机数组合在一起。        
            randomCode.append(strRand);   
        }        
        // 将四位数字的验证码保存到Session中。        
        HttpSession session = req.getSession();        
//        session.setAttribute("validateCode", randomCode.toString());   
        try {
        	//生成用于标识用户的cookie
        	String randCodeKey=RedisUserSession.getUserKey(USERIDCARD, req);
        	if(randCodeKey==null){
        		String temKey=RandomStr32.getStr32();
        		Cookie cookie=new Cookie(USERIDCARD, temKey);
				cookie.setMaxAge(USERIDCARDEXPIR);
				cookie.setPath("/");
				randCodeKey=temKey;
				resp.addCookie(cookie);
        	}
        	log.info("用户标识 "+randCodeKey+" 的图片验证码是："+randomCode.toString());
			RedisUtils.set(randCodeKey+"validatecode", randomCode.toString(), redisloginrandcodeexpir);
//			RedisUtils.set(randCodeKey+"validatetime", new Date().getTime(), 10*60l);
		} catch (Exception e) {
			e.printStackTrace();
		}
        // 禁止图像缓存。        
        resp.setHeader("Pragma", "no-cache");        
        resp.setHeader("Cache-Control", "no-cache");        
        resp.setDateHeader("Expires", 0);        
        resp.setContentType("image/jpeg");        
        // 将图像输出到Servlet输出流中。        
        ServletOutputStream sos = resp.getOutputStream();        
        ImageIO.write(buffImg, "jpeg", sos);        
        sos.close();        
    }         
}
