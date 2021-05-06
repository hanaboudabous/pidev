package tn.esprit.spring.services;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.IUserRepo;
import tn.esprit.spring.entity.Cagnotte;
import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.Contribution;
import tn.esprit.spring.repository.ICagnotteRepo;
import tn.esprit.spring.repository.IContributionRepo;



@Service
public class ContributionService {
	@Autowired
	IContributionRepo contribution ;

	@Autowired
	ICagnotteRepo cag ;

	@Autowired
	IUserRepo user ;
		
	/*Ajout d'une contribution par un user et affectation de cette contribution au contrat le plus ancien de la cagnotte*/
	public void ajouterContribution(Contribution con , int iduser){
      User u = user.findById(iduser).get();
      System.out.println("user name "+u.getLast_name());
      int scoreinitial=u.getScoring();
      System.out.println("score initial "+scoreinitial);
      float contr=con.getNbtickets()*20;
      con.setMontant(contr); // 1 ticket = 20 dt
      con.setUsers(u);
      con.setPaye(1); // c bon khalast beha
      
      /*Si la cagnotte ne comporte pas de contrats en cours**/	
	  int empty=cag.findAllByEtat("en cours").size();
      if(empty!=0){  //cagnotte non vide
    	 // con.setPaye(1); // c bon khalast beha
    	  contribution.save(con);
    	  scoring(iduser,scoreinitial);
    	  
      /*Somme de tous les tickets non paye (paye=0)*/
     
      String state="en cours";
      List<Cagnotte> cagnotte=cag.findByEtatOrderByDateAjout(state);
      Cagnotte first= cagnotte.get(0);// si etat= en cours
      
       
       /*affectation de la contribution à la cagnotte (le contrat le plus ancien)*/
       
       while((first.getMontantactuel()+contr)>=first.getMontantfinal())
      {
    	   float reste= contr-first.getMontantfinal()+first.getMontantactuel();
    	   first.setMontantactuel(first.getMontantfinal());
    	   first.setEtat("pleine");
    	   
    	   cagnotte=cag.findByEtatOrderByDateAjout(state); // si etat= en cours
    	   first= cagnotte.get(0);// si etat = en cours
    	   contr=reste;
    	   cag.save(first);
       }
       
       if((first.getMontantactuel()+contr)<first.getMontantfinal()){
    	   float xx=first.getMontantactuel()+contr;
    	   first.setMontantactuel(xx);
    	   cag.save(first);
    	   
       }
       
       
      } 
       
    
    }
	
	/*AFFICHAGE DES CONTRIBUTIONS*/
	@Transactional
	public List<Contribution> afficherContribution(){
		return contribution.findAll();
	}
	
	
	/*Calcul de la somme de tous les tickets payés par un certain user*/
	public int calculerSommeTickets(int iduser){
		int tot=contribution.ISommeTickets(iduser);
		System.out.println("somme des tickets: "+tot);
		return tot;
		
		
	}
	
	public void scoring(int iduser,int scoreinitial){
		User u = user.findById(iduser).get();
		int nbtickets=calculerSommeTickets(iduser);
		int score= nbtickets/5;  // 5tickets = 1 point, 10 tickets= 2 pts....
		System.out.println("score "+score);
		if(score!=scoreinitial){
			//mail
			String ss = "sarah.gmiha@esprit.tn";
			mail3(u,ss);
		}
		//u.setScoring(u.getScoring()+score);
		u.setScoring(score);
		System.out.println("score de u "+u.getScoring());
		user.save(u);
		
		
		
	}
	

 	 public  void mail3(User u , String s) {

	        final String username = "pidevassurance@gmail.com";
	        final String password = "wadiebelghith1";
	    	
	        Properties prop = new Properties();
	        prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "587");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.starttls.enable", "true"); //TLS
	        
	        Session session = Session.getInstance(prop,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });

	        try {

	            Message message = new MimeMessage(session);
	            String msg = "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:v='urn:schemas-microsoft-com:vml'>"
	            		+"<head>"
	            		+"<meta content='text/html; charset=utf-8' http-equiv='Content-Type'/>"
	            		+"<meta content='width=device-width' name='viewport'/>"
	            		+"<meta content='IE=edge' http-equiv='X-UA-Compatible'/>"
	            		+"<title></title>"
	            		+"<style type='text/css'>"
	            		+"		body {"
	            		+"			margin: 0;"
	            		+"			padding: 0;"
	            		+"		}"

	            		+"		table,"
	            		+"		td,"
	            		+"		tr {"
	            		+"			vertical-align: top;"
	            		+"			border-collapse: collapse;"
	            		+"		}"

	            		+"		* {"
	            		+"			line-height: inherit;"
	            		+"		}"

	            		+"		a[x-apple-data-detectors=true] {"
	            		+"			color: inherit !important;"
	            		+"			text-decoration: none !important;"
	            		+"		}"
	            		+"	</style>"
	            		+"<style id='media-query' type='text/css'>"
	            		+"		@media (max-width: 670px) {"

	            		+"			.block-grid,"
	            		+"			.col {"
	            					+"	min-width: 320px !important;"
	            		+"				max-width: 100% !important;"
	            		+"				display: block !important;"
	            		+"			}"

	            					+".block-grid {"
	            					+"	width: 100% !important;"
	            					+"}"

	            					+".col {"
	            					+"	width: 100% !important;"
	            		+"			}"

	            		+"			.col_cont {"
	            				+"		margin: 0 auto;"
	            		+"			}"

	            					+"img.fullwidth,"
	            					+"img.fullwidthOnMobile {"
	            		+"				max-width: 100% !important;"
	            		+"			}"

	            					+".no-stack .col {"
	            		+"				min-width: 0 !important;"
	            		+"				display: table-cell !important;"
	            		+"			}"

	            					+".no-stack.two-up .col {"
	            		+"				width: 50% !important;"
	            		+"			}"

	            					+".no-stack .col.num2 {"
	            		+"				width: 16.6% !important;"
	            		+"			}"

	            				+"	.no-stack .col.num3 {"
	            		+"				width: 25% !important;"
	            		+"			}"

	            		+"			.no-stack .col.num4 {"
	            		+"			width: 33% !important;"
	            		+"		}"

	            			+"		.no-stack .col.num5 {"
	            				+"		width: 41.6% !important;"
	            				+"}"

	            					+".no-stack .col.num6 {"
	            						+"width: 50% !important;"
	            						+"}"

	            					+".no-stack .col.num7 {"
	            					+"	width: 58.3% !important;"
	            					+"}"

	            					+".no-stack .col.num8 {"
	            						+"width: 66.6% !important;"
	            						+"}"

	            					+".no-stack .col.num9 {"
	            						+"width: 75% !important;"
	            						+"}"

	            					+".no-stack .col.num10 {"
	            					+"	width: 83.3% !important;"
	            					+"}"

	            					+".video-block {"
	            					+"	max-width: none !important;"
	            					+"}"

	            					+".mobile_hide {"
	            					+"	min-height: 0px;"
	            					+"	max-height: 0px;"
	            					+"	max-width: 0px;"
	            					+"	display: none;"
	            					+"	overflow: hidden;"
	            					+"	font-size: 0px;"
	            					+"}"

	            					+".desktop_hide {"
	            					+"	display: block !important;"
	            					+"	max-height: none !important;"
	            					+"}"
	            					+"}"
	            			+"</style>"
	            		+"</head>"
	            		+"<body class='clean-body' style='margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #FFFFFF;'>"
	            		+"<!--[if IE]><div class='ie-browser'><![endif]-->"
	            		+"<table bgcolor='#FFFFFF' cellpadding='0' cellspacing='0' class='nl-container' role='presentation' style='table-layout: fixed; vertical-align: top; min-width: 320px; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF; width: 100%;' valign='top' width='100%'>"
	            		+"<tbody>"
	            		+"<tr style='vertical-align: top;' valign='top'>"
	            		+"<td style='word-break: break-word; vertical-align: top;' valign='top'>"
	            		+"<!--[if (mso)|(IE)]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td align='center' style='background-color:#FFFFFF'><![endif]-->"
	            		+"<div style='background-color:#82a1df;'>"
	            		+"<div class='block-grid mixed-two-up' style='min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;'>"
	            		+"<div style='border-collapse: collapse;display: table;width: 100%;background-color:transparent;'>"
	            		+"<div class='col num4' style='display: table-cell; vertical-align: top; max-width: 320px; min-width: 216px; width: 216px;'>"
	            		+"<div class='col_cont' style='width:100% !important;'>"
	            		+"<div style='border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;'>"
	            		+"<div align='center' class='img-container center fixedwidth' style='padding-right: 0px;padding-left: 0px;'>"
	            		+"<div style='font-size:1px;line-height:60px'> </div><img align='center' alt='I'm an image' border='0' class='center fixedwidth' src='cid:lift' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; width: 100%; max-width: 216px; display: block;' title='I'm an image' width='216'/>"
	            		+"<div style='font-size:1px;line-height:60px'> </div>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"<div class='col num8' style='display: table-cell; vertical-align: top; max-width: 320px; min-width: 432px; width: 433px;'>"
	            		+"<div class='col_cont' style='width:100% !important;'>"
	            		+"<div style='border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;'>"
	            		+"<div style='color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;'>"
	            		+"<div class='txtTinyMce-wrapper' style='line-height: 1.2; font-size: 12px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; color: #555555; mso-line-height-alt: 14px;'>"
	            		+"<p style='font-size: 34px; line-height: 1.2; text-align: left; word-break: break-word; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 41px; margin: 0;'><span style='font-size: 34px; color: #ffffff;'><em><strong>Scoring</strong></em></span></p>"
	            		+"</div>"
	            		+"</div>"
	            		+"<table border='0' cellpadding='0' cellspacing='0' class='divider' role='presentation' style='table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;' valign='top' width='100%'>"
	            		+"<tbody>"
	            		+"<tr style='vertical-align: top;' valign='top'>"
	            		+"<td class='divider_inner' style='word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;' valign='top'>"
	            		+"<table align='center' border='0' cellpadding='0' cellspacing='0' class='divider_content' role='presentation' style='table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 1px solid #BBBBBB; width: 100%;' valign='top' width='100%'>"
	            		+"<tbody>"
	            		+"<tr style='vertical-align: top;' valign='top'>"
	            		+"<td style='word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;' valign='top'><span></span></td>"
	            		+"</tr>"
	            		+"</tbody>"
	            		+"</table>"
	            		+"</td>"
	            		+"</tr>"
	            		+"</tbody>"
	            		+"</table>"
	            		+"<div style='color:#ffffff;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;'>"
	            		+"<div class='txtTinyMce-wrapper' style='font-size: 12px; line-height: 1.2; color: #ffffff; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14px;'>"
	            		+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #800080;'> Hello Mr/Mrs "+u.getFirst_name()+" "+u.getLast_name()+"Your score has increased  </span> </span></p>"
	            		+"<p style='font-size: 12px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 14px; margin: 0;'> </p>"
	            		+"</div>"
	            		+"</div>"
	            		+"<p style='font-size: 12px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 14px; margin: 0;'> </p>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"<div style='background-color:#82a1df;'>"
	            		+"<div class='block-grid' style='min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;'>"
	            		+"<div style='border-collapse: collapse;display: table;width: 100%;background-color:transparent;'>"
	            		+"<div class='col num12' style='min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 650px;'>"
	            		+"<div class='col_cont' style='width:100% !important;'>"
	            		+"<div style='border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;'>"
	            		+"<table border='0' cellpadding='0' cellspacing='0' class='divider' role='presentation' style='table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;' valign='top' width='100%'>"
	            		+"<tbody>"
	            		+"<tr style='vertical-align: top;' valign='top'>"
	            		+"<td class='divider_inner' style='word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;' valign='top'>"
	            		+"<table align='center' border='0' cellpadding='0' cellspacing='0' class='divider_content' role='presentation' style='table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 1px solid #BBBBBB; width: 100%;' valign='top' width='100%'>"
	            		+"<tbody>"
	            		+"<tr style='vertical-align: top;' valign='top'>"
	            		+"<td style='word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;' valign='top'><span></span></td>"
	            		+"</tr>"
	            		+"</tbody>"
	            		+"</table>"
	            		+"</td>"
	            		+"</tr>"
	            		+"</tbody>"
	            		+"</table>"
	            		+"<table cellpadding='0' cellspacing='0' class='social_icons' role='presentation' style='table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt;' valign='top' width='100%'>"
	            		+"<tbody>"
	            		+"<tr style='vertical-align: top;' valign='top'>"
	            		+"<td style='word-break: break-word; vertical-align: top; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;' valign='top'>"
	            		+"<table align='center' cellpadding='0' cellspacing='0' class='social_table' role='presentation' style='table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-tspace: 0; mso-table-rspace: 0; mso-table-bspace: 0; mso-table-lspace: 0;' valign='top'>"
	            		+"<tbody>"
	            		+"<tr align='center' style='vertical-align: top; display: inline-block; text-align: center;' valign='top'>"
	            		+"<td style='word-break: break-word; vertical-align: top; padding-bottom: 0; padding-right: 2.5px; padding-left: 2.5px;' valign='top'><a href='https://www.facebook.com/' target='_blank'><img alt='Facebook' height='32' src='cid:facebook' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; display: block;' title='facebook' width='32'/></a></td>"
	            		+"<td style='word-break: break-word; vertical-align: top; padding-bottom: 0; padding-right: 2.5px; padding-left: 2.5px;' valign='top'><a href='https://www.twitter.com/' target='_blank'><img alt='Twitter' height='32' src='cid:twitter' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; display: block;' title='twitter' width='32'/></a></td>"
	            		+"<td style='word-break: break-word; vertical-align: top; padding-bottom: 0; padding-right: 2.5px; padding-left: 2.5px;' valign='top'><a href='https://www.linkedin.com/' target='_blank'><img alt='Linkedin' height='32' src='cid:linkedin' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; display: block;' title='linkedin' width='32'/></a></td>"
	            		+"<td style='word-break: break-word; vertical-align: top; padding-bottom: 0; padding-right: 2.5px; padding-left: 2.5px;' valign='top'><a href='https://www.instagram.com/' target='_blank'><img alt='Instagram' height='32' src='cid:instagram' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; display: block;' title='instagram' width='32'/></a></td>"
	            		+"</tr>"
	            		+"</tbody>"
	            		+"</table>"
	            		+"</td>"
	            		+"</tr>"
	            		+"</tbody>"
	            		+"</table>"
	            		+"<div style='color:#393d47;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;'>"
	            		+"<div class='txtTinyMce-wrapper' style='line-height: 1.2; font-size: 12px; color: #393d47; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14px;'>"
	            		+"<p style='font-size: 14px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 17px; margin: 0;'>©2021 Micro-Lift. all rights reserved.</p>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"</div>"
	            		+"</td>"
	            		+"</tr>"
	            		+"</tbody>"
	            		+"</table>"
	            		+"</body>"
	            		+"</html>";
	            		
	            message.setFrom(new InternetAddress("Tarunsunny143@gmail.com"));
	            message.setRecipients(
	                    Message.RecipientType.TO,
	                    InternetAddress.parse(s)
	            );
	            message.setSubject("Congratulations");
	          //  message.setContent(msg, "text/html; charset=utf-8");
	        //    message.setText(msg, "utf-8", "html");
	            message.setContent(msg, "text/html; charset=utf-8");
	            message.saveChanges();
//	            message.setText(msg);

	            Transport.send(message);

	           

	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    }

		
		

}
