import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import java.io.File;


import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot;


import java.util.Properties;    
import javax.mail.*;    
import javax.mail.internet.*;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Task2 {
	
		/*
		Task 2 
			Go to the site: https://temp-mail.org/en/
			Generate temporary email and copy the same using copy button.
			Go to your Gmail and send an email on this temporary email.
			Go back to the site https://temp-mail.org/en/
			Click to the newly received mail.
			Verify the Subject and Body of email (Using assertion)
			Take a screenshot of the Email.
		Note: Do not use Thread. Sleep() or Wait methods.

		*/
		
		static WebDriver driver = null;
		static JavascriptExecutor js;
		static String ScreenshotPath = "D:\\Automation\\Screenshot\\";
		static int ScreenshotNo = 1;
		
		public static void main(String[] args) throws InterruptedException, UnsupportedFlavorException, IOException {
			// TODO Auto-generated method stub
				
			System.setProperty("webdriver.chrome.driver", "D:\\Automation\\Software\\chromedriver_v106\\chromedriver.exe");
			
		    driver = new ChromeDriver();
						
			js = (JavascriptExecutor) driver;

			driver.get("https://temp-mail.org/en/");
			
			driver.manage().timeouts().implicitlyWait(05,TimeUnit.SECONDS);
			
			driver.manage().window().maximize();
			
			String actualCopedText = page1();
			
		    send("sushant.yadav0893@gmail.com","vzsjwvvyjmzlukor",actualCopedText,"Hello World..!!","How r u?"); 
		    
		    page2();
			
	}
		
		public static String page1() throws UnsupportedFlavorException, IOException{
			String actualCopedText=""; 
			try {
			
			System.out.print("Inside Page 1");
				
			List<WebElement> load = driver.findElements(By.xpath("//*[@class='emailbox-input opentip']"));
			
			while(load.isEmpty())
			{
				load = driver.findElements(By.xpath("//*[@class='emailbox-input opentip']"));
			}
			
			driver.findElement(By.id("click-to-copy")).click();
			
			Toolkit toolkit = Toolkit.getDefaultToolkit();
		    Clipboard clipboard = toolkit.getSystemClipboard();
		    
		     actualCopedText = (String) clipboard.getData(DataFlavor.stringFlavor);
		    System.out.println("String from Clipboard: " + actualCopedText);
			
			}catch(Exception e)
		    {
		    	System.out.print("Error " + e.getMessage());
		    }
		    return actualCopedText;
		}
		
		public static void page2()
		{
		 try {
			
			System.out.print("Inside Page 2");
				
			WebElement RefreshBtn =  driver.findElement(By.id("click-to-refresh"));
			
			ScrollToWebElemnt(RefreshBtn);	
			
			List<WebElement> MailRecevied = driver.findElements(By.xpath("//*[@class='inbox-dataList']/ul/li[2]"));
			
			while(MailRecevied.isEmpty())
			{
				MailRecevied = driver.findElements(By.xpath("//*[@class='inbox-dataList']/ul/li[2]"));
			}
			
			Screenshot("Receved_Mail_Page");
			
			//click on received mail
			driver.findElement(By.xpath("//*[@class='inbox-dataList']/ul/li[2]")).click();
			
			//Read Subject
			String emailSubject , emailBody ; 
			emailSubject = driver.findElement(By.xpath("//*[@class='user-data-subject']/h4")).getText();
			
			//Read Body
			emailBody = driver.findElement(By.xpath("//*[@class='inbox-data-content-intro']/div[1]")).getText();
			
			Screenshot("Receved_Mail_Page");
			}catch(Exception e)
			{
				
			}
		}
		
		public static void Screenshot(String PageName)
		{
			try {
			TakesScreenshot scrShot =((TakesScreenshot)driver);

			//Call getScreenshotAs method to create image file
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			
			PageName = ScreenshotPath + PageName + "_" + ScreenshotNo + ".png";
			//Move image file to new destination
			File DestFile=new File(PageName);

			//Copy file at destination
			FileUtils.copyFile(SrcFile, DestFile);
			
			ScreenshotNo = ScreenshotNo + 1;
			}catch(Exception e)
			{
				System.out.println("Error at time of capture screenshot");
			}
		}
		
		public static void send(String from,String password,String to,String sub,String msg){  
	          //Get properties object    
	          Properties props = new Properties();    
	          props.put("mail.smtp.host", "smtp.gmail.com");    
	          props.put("mail.smtp.socketFactory.port", "465");    
	          props.put("mail.smtp.socketFactory.class",    
	                    "javax.net.ssl.SSLSocketFactory");    
	          props.put("mail.smtp.auth", "true");    
	          props.put("mail.smtp.port", "465");    
	          
	          //get Session   
	          Session session = Session.getDefaultInstance(props,    
	           new javax.mail.Authenticator() {    
	           protected PasswordAuthentication getPasswordAuthentication() {    
	           return new PasswordAuthentication(from,password);  
	           }    
	          });    
	          
	          //compose message    
	          try {    
	           MimeMessage message = new MimeMessage(session);    
	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
	           message.setSubject(sub);    
	           message.setText(msg);    
	           //send message  
	           Transport.send(message);    
	           System.out.println("message sent successfully");    
	          } catch (MessagingException e) {throw new RuntimeException(e);}    
	             
	    }  
	 
		
		public static void ScrollToWebElemnt(WebElement wb)
		{
	        // Scrolling down the page till the element is found		
	        js.executeScript("arguments[0].scrollIntoView();", wb);
		}
}
