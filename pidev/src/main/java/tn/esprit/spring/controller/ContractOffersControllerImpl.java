package tn.esprit.spring.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.lowagie.text.DocumentException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import tn.esprit.spring.entity.Contract_Offers;
import tn.esprit.spring.entity.Offers;
import tn.esprit.spring.entity.State_Offers;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.ContractOfferRepository;
import tn.esprit.spring.services.ContractOffersService;
import tn.esprit.spring.services.EmailService;
import tn.esprit.spring.services.IContractOffersService;
@RestController
public class ContractOffersControllerImpl {
	
	@Autowired
	IContractOffersService ContractOfferService;
	@Autowired 
	 UserRepository UserRepository;
	@Autowired
   ContractOfferRepository ContractOfferRepository;
	@Autowired
	 EmailService emailService;
	@Autowired
	JavaMailSender mailSender;
	

	// http://localhost:8081/MicroAssurance/AllContractOffers
	 @GetMapping("AllContractOffers")
	 @ResponseBody
	 public List<Contract_Offers> getUsers() {
	List<Contract_Offers> listContractOffers = ContractOfferService.retrieveAll_Contractoffers();
	 return listContractOffers;
	}
	 
	
	 
	 //First Step 1
	 @PostMapping("AddContractMixte/{userid}")  
		public String AddContractMixte(@RequestBody Contract_Offers  c ,@PathVariable("userid")int userid) throws MessagingException, DocumentException, IOException   
		{  
		
		 ContractOfferService.AddContractMixte(c,userid);
		 
		 /*Twilio.init("AC7ffaee3c3d476387981efec326063046", "2c0ee980803ff501babd9ec7446b92c9");
		 Message message = Message.creator(new PhoneNumber("+216"+String.valueOf(c.getUsers().getNumber())),new PhoneNumber("16072755489"),
	        "Bienvenue chez MicroLeft ,your secretCode is :"+c.getUsers().getCIN()).create();*/
		// SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		// List<Contract_Offers> listContractOffers = ContractOfferService.retrieveAll_Contractoffers();
		 //Contract_Offers o = ContractOfferService.retrieveContract_Offers(48);
		 Contract_Offers o2 = ContractOfferService.Contract_OffersByUser(1);
		System.out.println(o2);
	        ContractOffersService exporter = new ContractOffersService();
	        System.out.println("Pdf Generated successufully ");
	        exporter.export(o2);
	        MimeMessage mimeMessage = mailSender.createMimeMessage();
	        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
	        messageHelper.setTo(c.getUsers().getEmail());
	        messageHelper.setSubject("Your Offer Contract");
	        messageHelper.setText(
					"Dear ") ;
	        FileSystemResource resource = new FileSystemResource(new File("C:\\JavaPDF\\MixteContrat"+c.getUsers().getLast_name()+c.getUsers().getFirst_name()+".pdf"));
	        messageHelper.addInline("image001", resource);
	        mailSender.send(mimeMessage);
		 return("contract Added Successufuly+client notified by SMS+email withpdf");
		}
	
	     
	 @GetMapping("ContratMixte/export/pdf")
	    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=MixteContract_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	   
	         
	        ContractOffersService exporter = new ContractOffersService();
	        System.out.println("Pdf Generated successufully ");	       
	         
	    }
	 
	 @PostMapping("ContratMixte/export/qrcode")
	    public void exportQrCode() throws DocumentException, IOException, WriterException {
		 Contract_Offers o2 = ContractOfferService.Contract_OffersByUser(1);
		 ContractOfferService.generateQRCodeImage("This is my first QR Code", 200, 200, "C:\\JavaPDF\\MyQRCode"+o2.getUsers().getFirst_name()+".png");
	        System.out.println("QRCode Generated successufully ");	       
	         
	    }
	 
	 
	 @GetMapping("ContratMixte/nbtranche/{userid}")
	    public String nbtranche(HttpServletResponse response,@PathVariable("userid")int userid ) throws DocumentException, IOException {
		 //ContractOffersService cs = new ContractOffersService();
		 Contract_Offers d= ContractOfferService.Contract_OffersByUser(userid);
		 String date = d.getDate_fin() ;
		 double prime = d.getTarification(); // get prime mel base
		 int nbr_tranche =  ContractOfferService.nbre_tranches_restantes(date);
		 
		 double montant_restant = prime * nbr_tranche;
	         
		 return ("You have " + nbr_tranche + " x " + prime + " more payments in your contract = " + montant_restant + " Dinars");
	    }
	 
	 
	 @PostMapping("ContratMixte/resilience/{userid}")
	    public Contract_Offers resilience(HttpServletResponse response,@PathVariable("userid")int userid)  {
		 
		 Contract_Offers o2 = ContractOfferService.Contract_OffersByUser(userid);
		 o2.setState_offers(State_Offers.Resillier);

		 Contract_Offers updated = ContractOfferService.updateContract_Offers(o2);
		 return updated;
	    }
	 
	 

	
}
