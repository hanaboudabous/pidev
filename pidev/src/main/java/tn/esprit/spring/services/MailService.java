package tn.esprit.spring.services;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entity.User;

@Service
public class MailService {
	
	JavaMailSender javaMailSender;
	@Autowired
	IUserService userService;
	
	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendEmail(User user) throws MailException {

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("microlift88@gmail.com");
		mail.setTo(user.getEmail());
		mail.setSubject("Get your password");
		mail.setText("This is your verification code Mr."+user.getFirst_name()+": "+user.getRandom());
		javaMailSender.send(mail);
	}

	public void sendEmailForgot(User user) throws MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,"utf-8");
		String htmlMsg = "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:v='urn:schemas-microsoft-com:vml'>"
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
+"<div style='background-color:#f6c256;'>"
+"<div class='block-grid mixed-two-up' style='min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;'>"
+"<div style='border-collapse: collapse;display: table;width: 100%;background-color:transparent;'>"
+"<!--[if (mso)|(IE)]><table width='100%' cellpadding='0' cellspacing='0' border='0' style='background-color:#f6c256;'><tr><td align='center'><table cellpadding='0' cellspacing='0' border='0' style='width:650px'><tr class='layout-full-width' style='background-color:transparent'><![endif]-->"
+"<!--[if (mso)|(IE)]><td align='center' width='216' style='background-color:transparent;width:216px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;' valign='top'><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td style='padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;'><![endif]-->"
+"<div class='col num4' style='display: table-cell; vertical-align: top; max-width: 320px; min-width: 216px; width: 216px;'>"
+"<div class='col_cont' style='width:100% !important;'>"
+"<!--[if (!mso)&(!IE)]><!-->"
+"<div style='border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;'>"
+"<!--<![endif]-->"
+"<div align='center' class='img-container center fixedwidth' style='padding-right: 0px;padding-left: 5px;'>"
+"<!--[if mso]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr style='line-height:0px'><td style='padding-right: 0px;padding-left: 5px;' align='center'><![endif]--><img align='center' alt='I'm an image' border='0' class='center fixedwidth' src='cid:lift' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; width: 100%; max-width: 210px; display: block;' title='I'm an image' width='210'/>"
+"<!--[if mso]></td></tr></table><![endif]-->"
+"</div>"
+"<!--[if (!mso)&(!IE)]><!-->"
+"</div>"
+"<!--<![endif]-->"
+"</div>"
+"</div>"
+"<!--[if (mso)|(IE)]></td></tr></table><![endif]-->"
+"<!--[if (mso)|(IE)]></td><td align='center' width='433' style='background-color:transparent;width:433px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;' valign='top'><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td style='padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;'><![endif]-->"
+"<div class='col num8' style='display: table-cell; vertical-align: top; max-width: 320px; min-width: 432px; width: 433px;'>"
+"<div class='col_cont' style='width:100% !important;'>"
+"<!--[if (!mso)&(!IE)]><!-->"
+"<div style='border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;'>"
+"<!--<![endif]-->"
+"<!--[if mso]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td style='padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif'><![endif]-->"
+"<div style='color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;'>"
+"<div class='txtTinyMce-wrapper' style='line-height: 1.2; font-size: 12px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; color: #555555; mso-line-height-alt: 14px;'>"
+"<p style='font-size: 34px; line-height: 1.2; text-align: left; word-break: break-word; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 41px; margin: 0;'><span style='font-size: 34px; color: #ffffff;'><em><strong>Get your password</strong></em></span></p>"
+"</div>"
+"</div>"
+"<!--[if mso]></td></tr></table><![endif]-->"
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
+"<!--[if mso]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td style='padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif'><![endif]-->"
+"<div style='color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;'>"
+"<div class='txtTinyMce-wrapper' style='line-height: 1.2; font-size: 12px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; color: #555555; mso-line-height-alt: 14px;'>"
+"<p style='font-size: 22px; line-height: 1.2; text-align: left; word-break: break-word; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 26px; margin: 0;'><span style='font-size: 22px; color: #ffffff;'><em>This is your verification code Mr(s)."+user.getFirst_name()+":</em></span></p>"
+"</div>"
+"</div>"
+"<!--[if mso]></td></tr></table><![endif]-->"
+"<!--[if mso]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td style='padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif'><![endif]-->"
+"<div style='color:#393d47;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;'>"
+"<div class='txtTinyMce-wrapper' style='font-size: 12px; line-height: 1.2; color: #393d47; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14px;'>"
+"<p style='font-size: 46px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 55px; margin: 0;'><span style='font-size: 46px; color: #800080;'>"+user.getRandom()+"</span></p>"
+"</div>"
+"</div>"
+"<!--[if mso]></td></tr></table><![endif]-->"
+"<!--[if (!mso)&(!IE)]><!-->"
+"</div>"
+"<!--<![endif]-->"
+"</div>"
+"</div>"
+"<!--[if (mso)|(IE)]></td></tr></table><![endif]-->"
+"<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->"
+"</div>"
+"</div>"
+"</div>"
+"<div style='background-color:#f6c256;'>"
+"<div class='block-grid' style='min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;'>"
+"<div style='border-collapse: collapse;display: table;width: 100%;background-color:transparent;'>"
+"<!--[if (mso)|(IE)]><table width='100%' cellpadding='0' cellspacing='0' border='0' style='background-color:#f6c256;'><tr><td align='center'><table cellpadding='0' cellspacing='0' border='0' style='width:650px'><tr class='layout-full-width' style='background-color:transparent'><![endif]-->"
+"<!--[if (mso)|(IE)]><td align='center' width='650' style='background-color:transparent;width:650px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;' valign='top'><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td style='padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;'><![endif]-->"
+"<div class='col num12' style='min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 650px;'>"
+"<div class='col_cont' style='width:100% !important;'>"
+"<!--[if (!mso)&(!IE)]><!-->"
+"<div style='border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;'>"
+"<!--<![endif]-->"
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
+"<!--[if mso]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td style='padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif'><![endif]-->"
+"<div style='color:#393d47;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;'>"
+"<div class='txtTinyMce-wrapper' style='line-height: 1.2; font-size: 12px; color: #393d47; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14px;'>"
+"<p style='font-size: 14px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 17px; margin: 0;'>??2021 Micro-Lift. all rights reserved.</p>"
+"</div>"
+"</div>"
+"<!--[if mso]></td></tr></table><![endif]-->"
+"<!--[if (!mso)&(!IE)]><!-->"
+"</div>"
+"<!--<![endif]-->"
+"</div>"
+"</div>"
+"<!--[if (mso)|(IE)]></td></tr></table><![endif]-->"
+"<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->"
+"</div>"
+"</div>"
+"</div>"
+"<!--[if (mso)|(IE)]></td></tr></table><![endif]-->"
+"</td>"
+"</tr>"
+"</tbody>"
+"</table>"
+"<!--[if (IE)]></div><![endif]-->"
+"</body>"
+"</html>";
		
		helper.setText(htmlMsg, true);
		helper.addInline("linkedin",
                new File("C:/wamp/www/images/linkedin2x.png"));
		helper.addInline("facebook",
                new File("C:/wamp/www/images/facebook2x.png"));
		helper.addInline("twitter",
                new File("C:/wamp/www/images/twitter2x.png"));
		helper.addInline("instagram",
                new File("C:/wamp/www/images/instagram2x.png"));
		helper.addInline("lift",
                new File("C:/wamp/www/images/micro-lifttt.png"));
 
        
		helper.setTo(user.getEmail());
		helper.setSubject("Get your password !");
		javaMailSender.send(mimeMessage);
	}
	
	public void sendEmailVerify(User user) throws MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		User u1=userService.getcode(user.getEmail());
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,"utf-8");
		String htmlMsg = "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:v='urn:schemas-microsoft-com:vml'>"
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
+"<div style='font-size:1px;line-height:60px'>??</div><img align='center' alt='I'm an image' border='0' class='center fixedwidth' src='cid:lift' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; width: 100%; max-width: 216px; display: block;' title='I'm an image' width='216'/>"
+"<div style='font-size:1px;line-height:60px'>??</div>"
+"</div>"
+"</div>"
+"</div>"
+"</div>"
+"<div class='col num8' style='display: table-cell; vertical-align: top; max-width: 320px; min-width: 432px; width: 433px;'>"
+"<div class='col_cont' style='width:100% !important;'>"
+"<div style='border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;'>"
+"<div style='color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;'>"
+"<div class='txtTinyMce-wrapper' style='line-height: 1.2; font-size: 12px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; color: #555555; mso-line-height-alt: 14px;'>"
+"<p style='font-size: 34px; line-height: 1.2; text-align: left; word-break: break-word; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 41px; margin: 0;'><span style='font-size: 34px; color: #ffffff;'><em><strong>Account verification</strong></em></span></p>"
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
+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #800080;'>User data :</span>??</span></p>"
+"<p style='font-size: 12px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 14px; margin: 0;'>??</p>"
+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #000000;'>E-mail: </span>"+u1.getEmail()+"</span></p>"
+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #000000;'>First name: </span>"+u1.getFirst_name()+"</span></p>"
+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #000000;'>Last name: </span>"+u1.getLast_name()+"</span></p>"
+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #000000;'>CIN: </span>"+u1.getCIN()+"</span></p>"
+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #000000;'>Phone number: </span>"+u1.getNumber()+"</span></p>"
+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #000000;'>Status: </span>"+u1.getStatus()+"</span></p>"
+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #000000;'>Adress: </span>"+u1.getAddress()+"</span></p>"
+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #000000;'>birth_date: </span>"+u1.getBirth_date()+"</span></p>"
+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #000000;'>ZIP code: </span>"+u1.getPostal_code()+"</span></p>"
+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #000000;'>Job: </span>"+u1.getJob()+"</span></p>"
+"</div>"
+"</div>"
+"<div align='center' class='button-container' style='padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;'>"
+"<a href='http://localhost:8082/register/verifie-user/"+u1.getUser_ID()+"' target='_blank' style='-webkit-text-size-adjust: none; text-decoration: none; display: inline-block; color: #ffffff; background-color: #d2913b; border-radius: 4px; -webkit-border-radius: 4px; -moz-border-radius: 4px; width: auto; width: auto; border-top: 1px solid #8a3b8f; border-right: 1px solid #8a3b8f; border-bottom: 1px solid #8a3b8f; border-left: 1px solid #8a3b8f; padding-top: 5px; padding-bottom: 5px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; text-align: center; mso-border-alt: none; word-break: keep-all;'><span style='padding-left:20px;padding-right:20px;font-size:16px;display:inline-block;letter-spacing:undefined;'><span style='font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;'>Verify</span></span></div>"
+"<div style='color:#393d47;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;'>"
+"<div class='txtTinyMce-wrapper' style='font-size: 12px; line-height: 1.2; color: #393d47; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14px;'>"
+"<p style='font-size: 12px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 14px; margin: 0;'>??</p>"
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
+"<p style='font-size: 14px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 17px; margin: 0;'>??2021 Micro-Lift. all rights reserved.</p>"
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
		
		helper.setText(htmlMsg, true);
		helper.addInline("linkedin",
                new File("C:/wamp/www/images/linkedin2x.png"));
		helper.addInline("facebook",
                new File("C:/wamp/www/images/facebook2x.png"));
		helper.addInline("twitter",
                new File("C:/wamp/www/images/twitter2x.png"));
		helper.addInline("instagram",
                new File("C:/wamp/www/images/instagram2x.png"));
		helper.addInline("lift",
                new File("C:/wamp/www/images/micro-lifttt.png"));
 
        
		helper.setTo("mohamedamine.chourou@esprit.tn");
		helper.setSubject("Account verification");
		javaMailSender.send(mimeMessage);
	}

}
