package com.xiaohe.mapshow.utils;

import org.apache.shiro.codec.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.activation.ActivateFailedException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件发送工具类
 *
 * @author Gmq
 * @since 2019-04-22 11:33
 **/
public class SendMailUtil {
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	/**
	 * 邮件发送的方法
	 *
	 * @param to       收件人
	 * @param subject  主题
	 * @param content  内容
	 * @param smtp     协议
	 * @param host     发送服务器服务器
	 * @param sendName 邮件发送人
	 * @param sendPort 邮件发送人端口
	 * @param userName 邮件发送人名
	 * @param userPwd  邮件发送人密码
	 * @return 成功或失败
	 */
	public static boolean send(String to, String subject, String content, String smtp, String host,
	                           String sendName, String sendPort, String userName, String userPwd) {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		// 第一步：创建Session
		Properties props = new Properties();
		// 指定邮件的传输协议，smtp(Simple Mail Transfer Protocol 简单的邮件传输协议)
		props.put("mail.transport.protocol", smtp);
		// 指定邮件发送服务器服务器 "smtp.qq.com"
		props.put("mail.host", host);
		// 指定邮件的发送人(您用来发送邮件的服务器，比如您的163\sina等邮箱)
		props.put("mail.from", sendName);
		if (true) {
			props.put("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.put("mail.smtp.socketFactory.port", sendPort);
		}
		Session session = Session.getDefaultInstance(props);

		// 开启调试模式
		session.setDebug(true);
		try {
			// 第二步：获取邮件发送对象
			Transport transport = session.getTransport();
			// 连接邮件服务器，链接您的163、sina邮箱，用户名（不带@163.com，登录邮箱的邮箱账号，不是邮箱地址）、密码
			transport.connect(userName, userPwd);
			Address toAddress = new InternetAddress(to);

			// 第三步：创建邮件消息体
			MimeMessage message = new MimeMessage(session);
			//设置自定义发件人昵称
			String nick = "";
			try {
				nick = javax.mail.internet.MimeUtility.encodeText("developer");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			message.setFrom(new InternetAddress(nick + " <" + sendName + ">"));
			//设置发信人
//			 message.setFrom(new InternetAddress(sendName));

			// 邮件的主题
			message.setSubject(subject);
			//收件人
			message.addRecipient(Message.RecipientType.TO, toAddress);
            /*//抄送人
            Address ccAddress = new InternetAddress("first.lady@whitehouse.gov");
            message.addRecipient(Message.RecipientType.CC, ccAddress);*/
			// 邮件的内容
			message.setContent(content, "text/html;charset=utf-8");
			// 邮件发送时间
			message.setSentDate(new Date());

			// 第四步：发送邮件
			// 第一个参数：邮件的消息体
			// 第二个参数：邮件的接收人，多个接收人用逗号隔开（test1@163.com,test2@sina.com）
			transport.sendMessage(message, InternetAddress.parse(to));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 以HTML格式发送邮件
	public static boolean sendHtmlMail(String to, String subject, String content, String smtp, String host,
	                                   String sendName, String sendPort, String userName, String userPwd) {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		// 第一步：创建Session
		Properties props = new Properties();
		// 指定邮件的传输协议，smtp(Simple Mail Transfer Protocol 简单的邮件传输协议)
		props.put("mail.transport.protocol", smtp);
		// 指定邮件发送服务器服务器 "smtp.qq.com"
		props.put("mail.host", host);
		// 指定邮件的发送人(您用来发送邮件的服务器，比如您的163\sina等邮箱)
		props.put("mail.from", sendName);
		if (true) {
			props.put("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.put("mail.smtp.socketFactory.port", sendPort);
		}
		Session session = Session.getDefaultInstance(props);

		// 开启调试模式
		session.setDebug(true);
		try {
			// 第二步：获取邮件发送对象
			Transport transport = session.getTransport();
			// 连接邮件服务器，链接您的163、sina邮箱，用户名（不带@163.com，登录邮箱的邮箱账号，不是邮箱地址）、密码
			transport.connect(userName, userPwd);
			Address toAddress = new InternetAddress(to);

			// 第三步：创建邮件消息体
			MimeMessage message = new MimeMessage(session);
			//设置自定义发件人昵称
			String nick = "";
			try {
				nick = javax.mail.internet.MimeUtility.encodeText("developer");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			message.setFrom(new InternetAddress(nick + " <" + sendName + ">"));
			//设置发信人
//			 message.setFrom(new InternetAddress(sendName));

			// 邮件的主题
			message.setSubject(subject);
			//收件人
			message.addRecipient(Message.RecipientType.TO, toAddress);
            /*//抄送人
            Address ccAddress = new InternetAddress("first.lady@whitehouse.gov");
            message.addRecipient(Message.RecipientType.CC, ccAddress);*/
			//++++++++++
			//MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();//创建一个包含HTML内容的MimeBodyPart
			//设置HTML内容
			messageBodyPart.setContent(content, "text/html; charset=utf-8");
			mainPart.addBodyPart(messageBodyPart);
			// 邮件的内容
			message.setContent(mainPart, "text/html;charset=utf-8");
			// 邮件发送时间
			message.setSentDate(new Date());

			// 第四步：发送邮件
			// 第一个参数：邮件的消息体
			// 第二个参数：邮件的接收人，多个接收人用逗号隔开（test1@163.com,test2@sina.com）
			transport.sendMessage(message, InternetAddress.parse(to));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//		SendMailUtil.send("772826099@qq.com.com", "注册通知", "内容", "smtp", "smtp.exmail.qq.com", "developer@he-live.com", "465", "developer@he-live.com", "XHDeveloper123");
//		SendMailUtil.sendHtmlMail("772826099@qq.com", "注册通知", "注册通知", "smtp", "smtp.exmail.qq.com", "developer@he-live.com", "465", "developer@he-live.com", "XHDeveloper123");
//.
		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		SecretKey deskey = keygen.generateKey();
		System.out.println(Base64.encodeToString(deskey.getEncoded()));

	}

	public static String getActivateContent(String email, Date deadline,String uuid) {
		StringBuilder demo = new StringBuilder();
		String format = DateUtil.format(deadline);
//		<img src="http://iotsvr.he-live.com:9192/email.jpg" height="65" width="670" alt="QQ安全中心" style="border: 0; vertical-align: middle;">
		demo.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /><title>审核成功</title><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /></head><body style=\"margin:0;padding:0;line-height:1.5;\"><style>        img {            outline: none;            text-decoration: none;            -ms-interpolation-mode: bicubic;        }                a img {            border: none;        }</style><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"670\" style=\"font-size:16px;font-family:'Microsoft YaHei';color:#4c4c4c;\"><tr><td style=\"padding-top:40px;padding-left:35px;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><thead><tr><td></td></tr></thead><tbody><tr><td style=\"padding-top:40px;padding-left:25px;\">亲爱的小荷用户：</td></tr><tr><td style=\"padding-left:55px;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\"><tbody><tr><td style=\"padding-top:15px;\">恭喜您！</td></tr><tr><td style=\"padding-top:5px;font-size:16px;\">您的小荷帐号").append(email).append("已经注册成功，请直接点击下面的链接激活账号。</td></tr><tr><td style=\"padding-top:10px;\"><a style=\"font-size:18px;font-weight:bold;color:#2f86f6;\" href=\"http://iotsvr.he-live.com:9192/#/backToLogin?email=").append(email).append("&ticket=").append(uuid).append("\">立即激活账号</a></td></tr><tr>\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top:5px;font-size:14px;\">（链接将在[").append(format).append("]之前失效）</td></tr></tbody></table></td></tr></tbody><tfoot style=\"font-size:12px;background:#f3f3f3;color:#9c9c9c;\"><tr><td align=\"right\" style=\"padding-top:15px;padding-right:15px;\">如您未做此操作，可能是他人误填，请忽略此邮件</td></tr><tr><td align=\"right\" style=\"padding-bottom:15px;padding-right:15px;\">本邮件为系统发出，请勿回复&nbsp; |&nbsp; 小荷官网<a style=\"color:#9c9c9c;\" href=\"http://www.he-live.com\" target=\"_blank\">http://www.he-live.com</a></td></tr></tfoot></table></td></tr></table></body></html>");
		String content = null;
		try {
			content = URLDecoder.decode(demo.toString(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}

	public static String getModifyPwdContent(String email, Date deadline,String uuid) {
		StringBuilder demo = new StringBuilder();
		String format = DateUtil.format(deadline);
		demo.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /><title>审核成功</title><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /></head><body style=\"margin:0;padding:0;line-height:1.5;\"><style>        img {            outline: none;            text-decoration: none;            -ms-interpolation-mode: bicubic;        }                a img {            border: none;        }</style><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"670\" style=\"font-size:16px;font-family:'Microsoft YaHei';color:#4c4c4c;\"><tr><td style=\"padding-top:40px;padding-left:35px;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><thead><tr><td></td></tr></thead><tbody><tr><td style=\"padding-top:40px;padding-left:25px;\">亲爱的小荷用户：</td></tr><tr><td style=\"padding-left:55px;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\"><tbody><tr><td style=\"padding-top:15px;\">恭喜您！</td></tr><tr><td style=\"padding-top:5px;font-size:16px;\">您的小荷帐号").append(email).append("已经通过身份验证，请直接点击下面的链接修改密码。</td></tr><tr><td style=\"padding-top:10px;\"><a style=\"font-size:18px;font-weight:bold;color:#2f86f6;\" href=\"http://iotsvr.he-live.com:9192/#/setNewPwd?email=").append(email).append("&authcode=").append(uuid).append("\">立即修改密码</a></td></tr><tr>\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top:5px;font-size:14px;\">（链接将在[").append(format).append("]之前失效）</td></tr></tbody></table></td></tr></tbody><tfoot style=\"font-size:12px;background:#f3f3f3;color:#9c9c9c;\"><tr><td align=\"right\" style=\"padding-top:15px;padding-right:15px;\">如您未做此操作，可能是他人误填，请忽略此邮件</td></tr><tr><td align=\"right\" style=\"padding-bottom:15px;padding-right:15px;\">本邮件为系统发出，请勿回复&nbsp; |&nbsp; 小荷官网<a style=\"color:#9c9c9c;\" href=\"http://www.he-live.com\" target=\"_blank\">http://www.he-live.com</a></td></tr></tfoot></table></td></tr></table></body></html>");
		String content = null;
		try {
			content = URLDecoder.decode(demo.toString(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}

}