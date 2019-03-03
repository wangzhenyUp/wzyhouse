package mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail2 {
	public static String myEmailAccount = "mail_demo@126.com";
    public static String myEmailPassword = "wang123456";
    public static String myEmailSMTPHost = "smtp.126.com";


	public static String txt2String(File file){
		String[] array = new String[4];
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	if(s.length()>1){
            		array = s.split(",");
            		SimpleDateFormat df = new SimpleDateFormat("MM/dd");//设置日期格式
                    if(df.format(new Date()).toString().equals(array[2].substring(5))){
                    	SendM(array[1],array[3]);
                    }    
            	}
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return "";
    }
    public static MimeMessage createMimeMessage(Session session,String receiveMail,String first_name) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
 
        // 2. From: 发件人
        message.setFrom(new InternetAddress(myEmailAccount, "昵称", "UTF-8"));
 
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail,first_name, "UTF-8"));
 
        // 4. Subject: 邮件主题
        message.setSubject("Happy birthday!", "UTF-8");
 
        // 5. Content: 邮件正文
        message.setContent("Happy birthday,dear "+first_name, "text/html;charset=UTF-8");
            // 6. 设置发件时间
        message.setSentDate(new Date());
 
        // 7. 保存设置
        message.saveChanges();
 
        return message;
    }
    public static void SendM(String first_name,String receiveMailAccount) throws Exception{
    	System.out.println("cehnggong");
    	// 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
 
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);
 
        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, receiveMailAccount,first_name);
 
        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
 
        // 5. 使用 邮箱账号 和 密码 连接邮件服务器
        transport.connect(myEmailAccount, myEmailPassword);
 
        // 6. 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
 
        // 7. 关闭连接
        transport.close();
    }

    public static void main(String[] args){
        File file = new File("C:/Users/pc-1/Desktop/employee.txt");
        txt2String(file);
    }
}
