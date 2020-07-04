package com.qa.automateOTP;


import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;



public class AutomateOTPHandel {

	public static final String ACCOUNT_SID = "AC096db11c730a584b5870a454419f81ed";
	public static final String AUTH_TOKEN = "3aec5316cbabd2bc2981fd33d196082f";

	public static void main(String[] args) throws Exception{

		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver_win32\\chromedriver.exe");
       WebDriver driver=new ChromeDriver();
		driver.get("https://www.amazon.in");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//span[contains(text(),'Hello, Sign in')]")).click();
		driver.findElement(By.xpath("//a[@id='createAccountSubmit']")).click();

		driver.findElement(By.xpath("//input[@id='ap_customer_name']")).sendKeys("AbedAPITest");
		driver.findElement(By.xpath("//span[@class='a-button-text a-declarative']")).click();

		driver.findElement(By.xpath("//ul[contains(@class,'a-nostyle a-list-link')]//li//a[contains(text(),'United States +1')]")).click();
		driver.findElement(By.xpath("//input[@id='ap_phone_number']")).sendKeys("2058905151");
		driver.findElement(By.id("ap_password")).sendKeys("Testing112323123@");
		driver.findElement(By.id("continue")).click();

		// get the OTP using Twilio APIs:
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		String smsBody = getMessage();
		System.out.println(smsBody);
		String OTPNumber = smsBody.replaceAll("[^-?0-9]+", " ");
		System.out.println(OTPNumber);
		driver.findElement(By.id("auth-pv-enter-code")).sendKeys(OTPNumber);

	}

	public static String getMessage() {
		return getMessages().filter(m -> m.getDirection().compareTo(Message.Direction.INBOUND) == 0)
				.filter(m -> m.getTo().equals("+12058905151")).map(Message::getBody).findFirst()
				.orElseThrow(IllegalStateException::new);
	}

	private static Stream<Message> getMessages() {
		ResourceSet<Message> messages = Message.reader(ACCOUNT_SID).read();
		return StreamSupport.stream(messages.spliterator(), false);
	}

}