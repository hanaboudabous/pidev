package tn.esprit.spring.services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.Taux_Interet;
import tn.esprit.spring.repository.Taux_InteretRepo;

@Service
public class Taux_InteretService {

	@Autowired
	Taux_InteretRepo taux_Interet ;
	
	
	HttpServletRequest request;
	public  String[] webscrap() throws IOException, ParseException{
		return null;    
	/*	String p2 = null ;
		String p = null;
				 System.setProperty("https.proxyHost", "127.0.0.1");
				 System.setProperty("https.proxyPort", "8182");    			   
				 Document doc = Jsoup.connect("https://www.bct.gov.tn/bct/siteprod/stat_page.jsp?id=129").userAgent("Opera").get();
				  p = doc.select("p[align = right]").first().text();
				  p2 = doc.select("p[align = center]").first().text();
				 p = p.replaceAll(",",".");
				 p2 = p2.replaceAll("[A-Z][a-z]","");
				 p2 = p2.replaceAll(" ","");
				// date1 = new SimpleDateFormat("dd/MM/yyyy").parse(p2);
				 //System.out.println(date1); 
				// d=Double.valueOf(p); 	   
				 String[ ] s = new String[2];
				 s[0] = p2 ;
				 s[1] = p ;
				 return s ;  
	*/		
		
		
		
				 }    

	   public void executewebscrup() throws ParseException{
		   
		   Double taux = null ;
		   Date date = null ;
		   Taux_Interet t = new Taux_Interet();
		   try {
			String[] l = webscrap();
			date = new SimpleDateFormat("dd/MM/yyyy").parse(l[0]);
			taux=Double.valueOf(l[1]);
			double db = taux_Interet.findFirstByOrderByIdDesc().getTauxInteret();
			if(date != null && taux != null && db != taux ){
				t.setTauxInteret(taux);
				t.setDate(date);
				taux_Interet.save(t); }
			else { System.out.println(" no need to more interest rate ");}
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	

}
