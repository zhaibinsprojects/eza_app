package com.sanbang.utils.javaMail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 发送邮件
 * 
 * @author lfc
 */
public class MailUtils {
	
    private static Logger logger = LoggerFactory.getLogger(MailUtils.class);
	
	final static String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	public static Session createSession(String host, final String username,
			final String password) {
		Properties prop = new Properties();
		prop.put("mail.host", host);// 指定主机
		prop.put("mail.smtp.auth", true);// 指定验证为true
//		prop.put("mail.transport.protocol", "smtp");
//		prop.setProperty("mail.smtp.port", "25");
//		prop.setProperty("mail.smtp.starttls.enable", "true");
		
		//阿里云服务器禁用了25端口，修改为465
		prop.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		prop.setProperty("mail.smtp.socketFactory.fallback", "false");
		prop.setProperty("mail.smtp.socketFactory.port", "465");
		prop.setProperty("mail.smtp.port", "465");
		
		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};

		// 获取session对象
		return Session.getInstance(prop, auth);
	}

	/**
	 * 发送指定的邮件
	 * 
	 * @param mail
	 */
	public boolean send(final Mail mail) {
		boolean success = false;
		try {
			InputStream in = (InputStream) this.getClass().getClassLoader()
					.getResourceAsStream("javamail.properties");
			Properties props = new Properties();
			props.load(in);
			int number = new Random().nextInt(6);// 产生0-5之间随机数,以便选择哪个邮箱地址

			// 默认使用第一个邮箱，总共有六个邮箱供使用
			String hostName = props.getProperty("hostName");
			String mailName = props.getProperty("mailName");
			String mailPassword = props.getProperty("mailPassword");
			String mailAddress = props.getProperty("mailAddress");
			
			if (number == 0) {
				hostName = props.getProperty("hostName");
				mailName = props.getProperty("mailName");
				mailPassword = props.getProperty("mailPassword");
				mailAddress = props.getProperty("mailAddress");
			} else if (number >= 1 && number <= 5) {
				hostName = props.getProperty("hostName" + number);
				mailName = props.getProperty("mailName" + number);
				mailPassword = props.getProperty("mailPassword" + number);
				mailAddress = props.getProperty("mailAddress" + number);
			}

			Session session = MailUtils.createSession(hostName, mailName,
					mailPassword);

			final MimeMessage msg = new MimeMessage(session);// 创建邮件对象
			msg.setFrom(new InternetAddress(mailAddress));// 设置发件人
			msg.addRecipients(RecipientType.TO, mail.getToAddress());// 设置收件人
			mail.setFrom(mailAddress);
			// 设置抄送
			String cc = mail.getCcAddress();
			if (!cc.isEmpty()) {
				msg.addRecipients(RecipientType.CC, cc);
			}

			// 设置暗送
			String bcc = mail.getBccAddress();
			if (!bcc.isEmpty()) {
				msg.addRecipients(RecipientType.BCC, bcc);
			}
			msg.setSubject(mail.getSubject());// 设置主题
			MimeMultipart parts = new MimeMultipart();// 创建部件集对象
			MimeBodyPart part = new MimeBodyPart();// 创建一个部件
			part.setContent(mail.getContent(), "text/html;charset=utf-8");// 设置邮件文本内容
			parts.addBodyPart(part);// 把部件添加到部件集中
			// 添加附件
			List<AttachBean> attachBeanList = mail.getAttachs();// 获取所有附件
			if (attachBeanList != null) {
				for (AttachBean attach : attachBeanList) {
					MimeBodyPart attachPart = new MimeBodyPart();// 创建一个部件
					attachPart.attachFile(attach.getFile());// 设置附件文件
					attachPart.setFileName(MimeUtility.encodeText(attach
							.getFileName()));// 设置附件文件名
					DataHandler dh = new DataHandler(new FileDataSource(
							attach.getFile()));
					attachPart.setDataHandler(dh);
					// attachPart.setContentID("zhengshu.jpg");
					parts.addBodyPart(attachPart);
				}
			}
			parts.setSubType("mixed");
			msg.setContent(parts);// 给邮件设置内容
			msg.saveChanges(); // 保存修改
			Thread.sleep(1000);
			Transport.send(msg);
			logger.info("发送邮件成功：>>>>>>from :" + mail.getFrom() + "----to-----"
					+ mail.getToAddress());
			success = true;
		} catch (Exception e) {
			logger.error("发送邮件异常：>>>>>>from :" + mail.getFrom() + "----to-----"
					+ mail.getToAddress() + ",错误：" + e);
			e.printStackTrace();
		}
		return success;
	}
	
	
	/**
	 * 发送指定的邮件
	 * 
	 * @param mail
	 */
	public boolean sendTest(final Mail mail) {
		boolean success = false;
		try {
			InputStream in = (InputStream) this.getClass().getClassLoader()
					.getResourceAsStream("javamail.properties");
			Properties props = new Properties();
			props.load(in);
			// 默认使用第一个邮箱，总共有六个邮箱供使用
			String hostName = props.getProperty("hostName");
			String mailName = props.getProperty("mailName");
			String mailPassword = props.getProperty("mailPassword");
			String mailAddress = props.getProperty("mailAddress");

			Session session = MailUtils.createSession(hostName, mailName,
					mailPassword);

			final MimeMessage msg = new MimeMessage(session);// 创建邮件对象
			msg.setFrom(new InternetAddress(mailAddress));// 设置发件人
			msg.addRecipients(RecipientType.TO, mail.getToAddress());// 设置收件人
			mail.setFrom(mailAddress);
			// 设置抄送
			String cc = mail.getCcAddress();
			if (!cc.isEmpty()) {
				msg.addRecipients(RecipientType.CC, cc);
			}
			// 设置暗送
			String bcc = mail.getBccAddress();
			if (!bcc.isEmpty()) {
				msg.addRecipients(RecipientType.BCC, bcc);
			}
			msg.setSubject(mail.getSubject());// 设置主题
			MimeMultipart parts = new MimeMultipart();// 创建部件集对象
			MimeBodyPart part = new MimeBodyPart();// 创建一个部件
			part.setContent(mail.getContent(), "text/html;charset=utf-8");// 设置邮件文本内容
			parts.addBodyPart(part);// 把部件添加到部件集中
			// 添加附件
			List<AttachBean> attachBeanList = mail.getAttachs();// 获取所有附件
			if (attachBeanList != null) {
				for (AttachBean attach : attachBeanList) {
					MimeBodyPart attachPart = new MimeBodyPart();// 创建一个部件
					attachPart.attachFile(attach.getFile());// 设置附件文件
					attachPart.setFileName(MimeUtility.encodeText(attach
							.getFileName()));// 设置附件文件名
					DataHandler dh = new DataHandler(new FileDataSource(
							attach.getFile()));
					attachPart.setDataHandler(dh);
					// attachPart.setContentID("zhengshu.jpg");
					parts.addBodyPart(attachPart);
				}
			}
			parts.setSubType("mixed");
			msg.setContent(parts);// 给邮件设置内容
			msg.saveChanges(); // 保存修改
			Thread.sleep(1000);
			Transport.send(msg);
			logger.info("发送邮件成功：>>>>>>from :" + mail.getFrom() + "----to-----"
					+ mail.getToAddress());
			success = true;
		} catch (Exception e) {
			logger.error("发送邮件异常：>>>>>>from :" + mail.getFrom() + "----to-----"
					+ mail.getToAddress() + ",错误：" + e);
			e.printStackTrace();
		}
		return success;
	}

	public boolean sendAll(final Mail mail,String emailAddress) {
		boolean success = false;
		try {
			InputStream in = (InputStream) this.getClass().getClassLoader()
					.getResourceAsStream("javamail.properties");
			Properties props = new Properties();
			props.load(in);
			int number = new Random().nextInt(1);// 产生0-5之间随机数,以便选择哪个邮箱地址
			// 默认使用第一个邮箱，总共有六个邮箱供使用
			String hostName = props.getProperty("hostName");
			String mailName = props.getProperty("mailName");
			String mailPassword = props.getProperty("mailPassword");
			String mailAddress = props.getProperty("mailAddress");
			if (number == 0) {
				hostName = props.getProperty("hostName");
				mailName = props.getProperty("mailName");
				mailPassword = props.getProperty("mailPassword");
				mailAddress = props.getProperty("mailAddress");
			} else if (number >= 1 && number <= 3) {
				hostName = props.getProperty("hostName" + number);
				mailName = props.getProperty("mailName" + number);
				mailPassword = props.getProperty("mailPassword" + number);
				mailAddress = props.getProperty("mailAddress" + number);
			}

			Session session = MailUtils.createSession(hostName, mailName,
					mailPassword);

			final MimeMessage msg = new MimeMessage(session);// 创建邮件对象
			msg.setFrom(new InternetAddress(mailAddress));// 设置发件人
			InternetAddress[]  tos = InternetAddress.parse(emailAddress);
			// 设置抄送
			String cc = mail.getCcAddress();
			if (!cc.isEmpty()) {
				msg.addRecipients(RecipientType.CC, cc);
			}

			// 设置暗送
			String bcc = mail.getBccAddress();
			if (!bcc.isEmpty()) {
				msg.addRecipients(RecipientType.BCC, bcc);
			}
			msg.setSubject(mail.getSubject());// 设置主题
			MimeMultipart parts = new MimeMultipart();// 创建部件集对象
			MimeBodyPart part = new MimeBodyPart();// 创建一个部件
			part.setContent(mail.getContent(), "text/html;charset=utf-8");// 设置邮件文本内容
			parts.addBodyPart(part);// 把部件添加到部件集中
			// 添加附件
			List<AttachBean> attachBeanList = mail.getAttachs();// 获取所有附件
			if (attachBeanList != null) {
				for (AttachBean attach : attachBeanList) {
					MimeBodyPart attachPart = new MimeBodyPart();// 创建一个部件
					attachPart.attachFile(attach.getFile());// 设置附件文件
					attachPart.setFileName(MimeUtility.encodeText(attach
							.getFileName()));// 设置附件文件名
					DataHandler dh = new DataHandler(new FileDataSource(
							attach.getFile()));
					attachPart.setDataHandler(dh);
					// attachPart.setContentID("zhengshu.jpg");
					parts.addBodyPart(attachPart);
				}
			}
			parts.setSubType("mixed");
			msg.setContent(parts);// 给邮件设置内容
			msg.setSentDate(new Date());
			msg.saveChanges(); // 保存修改
			Transport.send(msg, tos);
			logger.info("发送邮件成功：>>>>>>from :" + msg.getFrom() + "----to-----"
					+ mail.getToAddress());
			success = true;
		} catch (Exception e) {
			logger.error("发送邮件异常：>>>>>>from :" + mail.getFrom() + "----to-----"
					+ mail.getToAddress() + ",错误：" + e);
			e.printStackTrace();
		}
		return success;
	}
	
	public static void main(String[] args) throws MessagingException,
			IOException, InterruptedException {
		
		Mail mail = new Mail("1453672473@qq.com", "Theme招标文件", "content-hahahahahah");
		//mail.addAttach(new AttachBean(new File("G:\\zhangscott\\haoyisheng\\Project\\pmphmooc.sql"), "test.sql"));
		MailUtils mailUtils = new MailUtils();
		//mailUtils.sendAll(mail,"7654321@qq.com,893274579@qq.com");
		mailUtils.sendTest(mail);
		
	}
	
}