package com.qa.automateOTP;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AuthenticationApp {

	public static void main(String[] args) {
		WebDriver d;
		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver_win32\\chromedriver.exe");
        d=new ChromeDriver();
        d.manage().window().maximize();
		d.manage().deleteAllCookies();
		d.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		String text=d.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).getText();
		System.out.println(text);

	}

}
