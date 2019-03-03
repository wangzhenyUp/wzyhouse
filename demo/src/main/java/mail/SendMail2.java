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
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
            	if(s.length()>1){
            		array = s.split(",");
            		SimpleDateFormat df = new SimpleDateFormat("MM/dd");//�������ڸ�ʽ
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
        // 1. ����һ���ʼ�
        MimeMessage message = new MimeMessage(session);
 
        // 2. From: ������
        message.setFrom(new InternetAddress(myEmailAccount, "�ǳ�", "UTF-8"));
 
        // 3. To: �ռ��ˣ��������Ӷ���ռ��ˡ����͡����ͣ�
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail,first_name, "UTF-8"));
 
        // 4. Subject: �ʼ�����
        message.setSubject("Happy birthday!", "UTF-8");
 
        // 5. Content: �ʼ�����
        message.setContent("Happy birthday,dear "+first_name, "text/html;charset=UTF-8");
            // 6. ���÷���ʱ��
        message.setSentDate(new Date());
 
        // 7. ��������
        message.saveChanges();
 
        return message;
    }
    public static void SendM(String first_name,String receiveMailAccount) throws Exception{
    	System.out.println("cehnggong");
    	// 1. ������������, ���������ʼ��������Ĳ�������
        Properties props = new Properties();                    // ��������
        props.setProperty("mail.transport.protocol", "smtp");   // ʹ�õ�Э�飨JavaMail�淶Ҫ��
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // �����˵������ SMTP ��������ַ
        props.setProperty("mail.smtp.auth", "true");            // ��Ҫ������֤
 
        // 2. �������ô����Ự����, ���ں��ʼ�����������
        Session session = Session.getInstance(props);
        // ����Ϊdebugģʽ, ���Բ鿴��ϸ�ķ��� log
        session.setDebug(true);
 
        // 3. ����һ���ʼ�
        MimeMessage message = createMimeMessage(session, receiveMailAccount,first_name);
 
        // 4. ���� Session ��ȡ�ʼ��������
        Transport transport = session.getTransport();
 
        // 5. ʹ�� �����˺� �� ���� �����ʼ�������
        transport.connect(myEmailAccount, myEmailPassword);
 
        // 6. �����ʼ�
        transport.sendMessage(message, message.getAllRecipients());
 
        // 7. �ر�����
        transport.close();
    }

    public static void main(String[] args){
        File file = new File("C:/Users/pc-1/Desktop/employee.txt");
        txt2String(file);
    }
}
