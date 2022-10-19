
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Task1 {

	/*
		Task 1: 
			Go to the MMT site: https://www.makemytrip.com/flights/
			Find flights from Delhi to Mumbai
			Click to the Sorted By: Departure 
			Print the Airline name and price having 2nd lowest price. (Note: Results should be Sorted By Departure)
	*/
	
	static WebDriver driver = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		System.setProperty("webdriver.chrome.driver", "D:\\Automation\\Software\\chromedriver_v106\\chromedriver.exe");
		
	    driver = new ChromeDriver();
		
		driver.get("https://www.makemytrip.com/flights/");
		
		driver.manage().timeouts().implicitlyWait(05,TimeUnit.SECONDS);
		
		driver.manage().window().maximize();
		
		Page1();
		
		
		By Alert2 = By.xpath("//*[text()='OKAY, GOT IT!']");
		
		checkAlert(Alert2);
		
		driver.findElement(By.xpath("//*[text()='Departure']")).click();
		
		page2();
		
		driver.close();
		driver.quit();
	}
    
	
	public static void Page1()
	{
		try {
			//System.out.println("Inside Page1");
			
			By Alert1 = By.xpath("//*[@class='langCardClose']");
			
			checkAlert(Alert1);
			
			WebElement fromCity = driver.findElement(By.id("fromCity"));
			
			dropDown(fromCity , "Delhi");
			
			WebElement toCity = driver.findElement(By.xpath("//*[@id='toCity']/following::input[1]"));
			
			dropDown(toCity , "Mumbai");
			
			driver.findElement(By.xpath("(//*[contains(@class,'todayPrice')]/preceding-sibling::p)[2]")).click();
			
			driver.findElement(By.xpath("//a[text()='Search']")).click();
			
		}catch(Exception e)
		{
			System.out.println("Exception :" + e.getMessage());
		}
	}
	
	public static void page2()
	{
		try {
			//System.out.print("Inide Validation");
			
			List<WebElement> fightList = driver.findElements(By.xpath("//div[contains(@class,'fli-list') and contains(@id,'flight_list')]"));
			
			if(fightList.size()>=2)
			{
			int priceInt;
			String AirLineName,flightCode,priceText;
			
			LinkedList<String> listAirLineName = new LinkedList<String>();       
			LinkedList<Integer> listAirLinePrice = new LinkedList<Integer>();       
			
			for(int iIterator=1;iIterator<=fightList.size();iIterator++)
			{
				 AirLineName = driver.findElement(By.xpath("(//p[contains(@class,'airlineName')])[" + iIterator + "]")).getText();
				
				 flightCode = driver.findElement(By.xpath("(//p[contains(@class,'fliCode')])[" + iIterator + "]")).getText();
				
				priceText = driver.findElement(By.xpath("(//*[@class='priceSection']/descendant::p[1])[" + iIterator + "]")).getText().trim();
				
				priceText = priceText.replaceAll(",", "").trim();
				
				priceText = priceText.replace("₹", "").trim();
	            
				priceInt = Integer.parseInt(priceText);
			
				AirLineName = AirLineName + " " + flightCode ;
				
				listAirLineName.add(AirLineName);
				
				listAirLinePrice.add(priceInt);
				
			//	System.out.println(AirLineName + " " + priceInt);
				
			}
			 
			
			int temp;
			String tempAirLine;
			for (int i = 0; i < listAirLinePrice.size(); i++)   
	        {  
	            for (int j = i + 1; j < listAirLinePrice.size(); j++)   
	            {  
	                if (listAirLinePrice.get(i) > listAirLinePrice.get(j))   
	                {  
	                    temp = listAirLinePrice.get(i);  
	                    listAirLinePrice.set(i, listAirLinePrice.get(j));  
	                    listAirLinePrice.set(j, temp);
	                    
	                    
	                    tempAirLine = listAirLineName.get(i);  
	                    listAirLineName.set(i, listAirLineName.get(j));  
	                    listAirLineName.set(j, tempAirLine);
	                    
	                }  
	            }  
	        }  
			
			System.out.println("AirLine Name :" + listAirLineName.get(1) + " Price :" + listAirLinePrice.get(1));

			}else {
			    System.out.println("Only Single flight available");
			    throw new ArithmeticException("Only Single flight available");
			}
		}catch(Exception e)
		{
			System.out.println("Exception:" + e.getMessage());
		}
	}
	
	
	public static void checkAlert(By objProperty)
	{
		List<WebElement> alertVisible = driver.findElements(objProperty);
		
		if(!(alertVisible.isEmpty()))
		{
			alertVisible.get(0).click();
		}
		
	}
	
	
	public static void dropDown(WebElement wb , String sValue)
	{
		wb.sendKeys(sValue);
		
		List<WebElement> optionsDrop = driver.findElements(By.xpath("//li[contains(@id,'react')]"));

		//System.out.println("List Size :" + optionsDrop.size());

		for(int optionsIterator = 0;  optionsIterator<optionsDrop.size(); optionsIterator++)
		{
			if(optionsDrop.get(optionsIterator).getText().contains(sValue))
			{
				optionsDrop.get(optionsIterator).click();
				break;
			}
		}
		
		//System.out.println("Value Selected successfully");
	}
}
