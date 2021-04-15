package tn.esprit.spring.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import tn.esprit.spring.entity.Contract_Offers;
import tn.esprit.spring.entity.Offers;
import tn.esprit.spring.entity.State_Offers;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.ContractOfferRepository;
import tn.esprit.spring.repository.OffersRepository;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Years;

import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import org.springframework.stereotype.Service;


import tn.esprit.spring.repository.UserRepository;


@Service
public class ContractOffersService implements IContractOffersService {
	@Autowired 
	private UserRepository UserRepository;
	
	@Autowired 
	ContractOfferRepository contractofferRepository;
	@Autowired
	OffersRepository offerRepository;

	@Override
	public List<Contract_Offers> retrieveAll_Contractoffers() {
		List<Contract_Offers> Contractoffers =(List<Contract_Offers>) contractofferRepository.findAll();
		for(Contract_Offers Contractoffer: Contractoffers){
			System.out.println("Contractoffer +++ : "+ Contractoffer);
		}
		return Contractoffers;
	}

	@Override
	public Contract_Offers addContract_Offers(Contract_Offers cf) {
		Contract_Offers ContratSaved = null;
		ContratSaved =contractofferRepository.save(cf);
		return ContratSaved;
		
	}

	@Override
	public void deleteContract_Offers(int IdContractOffer) {

		contractofferRepository.deleteById(IdContractOffer);
	}

	@Override
	public Contract_Offers updateContract_Offers(Contract_Offers cf) {
		System.out.println(cf);
		Contract_Offers Contract_OffersAdded = contractofferRepository.save(cf);
		return Contract_OffersAdded;
		
	}

	@Override
	public Contract_Offers retrieveContract_Offers(int IdContractOffer) {
		System.out.println("Contractoffer id =" + IdContractOffer);
		Contract_Offers cf = contractofferRepository.findById(IdContractOffer).orElse(null);
		System.out.println("Contractoffre returned "+cf);
		return cf ;
		
	}
	
	@Override
	public Contract_Offers Contract_OffersByUser(int IdUser) {
		System.out.println("UserContractoffer id =" + IdUser);
		Contract_Offers cf = contractofferRepository.Contract_OffersByUser(IdUser);
		System.out.println("ContractoffreBy User returned "+cf);
		return cf ;
		
	}
	//partie calcul Contrat Mixte unique + periodique


    public double  Tarification_Mixte_PrimePeriodique(double vie_mixte,double dèces_mixte,int duree,int age ){
    	 double vie , deces = 0 , capit = 0,primePure,PrimeComercial ,v,puiss;
    	double [] Lx= {100.000,97.104,96.869,96.727,96.624,96.541,96.471,96.410,96.356,96.306,96.258,96.211,96.163,96.111,96.052
    	    	,95.985,95.908,95.821,95.722,95.614,95.496,95.372,95.242,95.108,94.971,94.834,94.696,94.558,94.420,94.283,94.145
    	    	,94.007,93.867,93.724,93.578,93.426,93.268,93.102,92.926,92.739,92.538,92.323,92.089,91.837,91.562,91.263,90.937
    	    	,90.580,90.190,89.764,89.297,88.786,88.226,87.614,86.944,86211,85410,84536,83582,82542,81409,80178,78.842
    	    	,77.393,75.826,74.134,72.312,70.354,68.257,66.017,63.632,61.103,58.432,55.623,52.686,49.629,46.469,43.222,39.911,36.560
    	    	,33.200,29.861,26.580,23.390,20.328,17.428,14.722,12.238,9.997,8.013,6.292,4.832,3.623,2.647,1.876,1.286,850,539
    	    	,326,187,101,51,24,10,4,1};
    	double Interet=0.01;
    	vie = vie_mixte*(Lx[age+duree]/Lx[age]) * Math.pow(1/(1+Interet), duree);
    	for (int i=0;i<duree;i++){
    		deces+=((Lx[age+i]-Lx[(age+1)+i])/Lx[age]) * Math.pow((1/(1+Interet)),i + 0.5);
    	}
    	deces = dèces_mixte * deces;
    	for (int i=0;i<duree;i++){
    		capit+=(Lx[age+i]/Lx[age]) * Math.pow((1/(1+Interet)), i);
    	}
    	primePure=(vie+deces)/capit;
    	PrimeComercial=primePure+( primePure * 0.03);
    	System.out.println("PrimeComercial "+ PrimeComercial);
    	return(Math.round(PrimeComercial));
		
	}
    public Double Tarification_Mixte_PrimeUnique(double vie_mixte,double dèces_mixte,int duree,int age ){
   	 double vie , deces = 0 ,primePure,PrimeComercial ,v,puiss;
   	double [] Lx= {100.000,97.104,96.869,96.727,96.624,96.541,96.471,96.410,96.356,96.306,96.258,96.211,96.163,96.111,96.052
	    	,95.985,95.908,95.821,95.722,95.614,95.496,95.372,95.242,95.108,94.971,94.834,94.696,94.558,94.420,94.283,94.145
	    	,94.007,93.867,93.724,93.578,93.426,93.268,93.102,92.926,92.739,92.538,92.323,92.089,91.837,91.562,91.263,90.937
	    	,90.580,90.190,89.764,89.297,88.786,88.226,87.614,86.944,86211,85410,84536,83582,82542,81409,80178,78.842
	    	,77.393,75.826,74.134,72.312,70.354,68.257,66.017,63.632,61.103,58.432,55.623,52.686,49.629,46.469,43.222,39.911,36.560
	    	,33.200,29.861,26.580,23.390,20.328,17.428,14.722,12.238,9.997,8.013,6.292,4.832,3.623,2.647,1.876,1.286,850,539
	    	,326,187,101,51,24,10,4,1};
   	double Interet=0.01;
   	vie=vie_mixte*(Lx[age+duree]/Lx[age])*Math.pow(1/(1+Interet), duree);
   	for (int i=0;i<duree;i++){
   		deces+=((Lx[age+i]-Lx[(age+1)+i])/Lx[age]) * Math.pow((1/(1+Interet)),i + 0.5);
   	}
   	deces = dèces_mixte * deces;
   	primePure=(vie+deces);
   	PrimeComercial=primePure+( primePure * 0.03);
   	 //System.out.println("PrimeComercial "+ PrimeComercial);
	return(PrimeComercial);
   	
    }
    public int age(Date birthdate){
  		Date currentDate = new Date();
  		DateFormat formatter = new SimpleDateFormat("yyyyMMdd"); 
  		int d1 = Integer.parseInt(formatter.format(birthdate));                            
  		int d2 = Integer.parseInt(formatter.format(currentDate));                          
  		int age = (d2 - d1) / 10000; 
  		return age;
  	}
    
    @Override
    public Contract_Offers  AddContractMixte(Contract_Offers contract ,int userid){
    	Offers o = offerRepository.findById(1).get();
    	User user = UserRepository.findById(1).get();
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		LocalDate dateTime = new LocalDate();
		String newDateTime = dateTime.plusYears(contract.getDuree()).toString();
		//contract.setIdContractOffer(1);
		contract.setState_offers(State_Offers.Accepted);
		contract.setUsers(user);
		int age=age(user.getBirth_date());
		contract.setTarification(Tarification_Mixte_PrimePeriodique(contract.getVie_mixte(),contract.getDèces_mixte(),contract.getDuree(), age));
		contract.setDate_debut(dateFormat.format(date));
		contract.setDate_fin(newDateTime);
		contract.setOffers(o);
		contractofferRepository.save(contract);
		return contract;}
    
   //partie pdf
     
     
        private void writeTableHeader(PdfPTable table) {
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(Color.BLUE);
            cell.setPadding(5);
             
            Font font = FontFactory.getFont(FontFactory.HELVETICA);
            font.setColor(Color.WHITE);
             
            cell.setPhrase(new Phrase("Ref", font));
             
            table.addCell(cell);
             
            cell.setPhrase(new Phrase("date debut", font));
            table.addCell(cell);
             
            cell.setPhrase(new Phrase("date fin", font));
            table.addCell(cell);
             
            cell.setPhrase(new Phrase("State Offer", font));
            table.addCell(cell);
             
            cell.setPhrase(new Phrase("Tarif", font));
            table.addCell(cell);       
        }
         
        private void writeTableData(PdfPTable table, Contract_Offers user) {
        	PdfPCell cell = new PdfPCell();
           
                
            	cell.setPhrase(new Phrase(String.valueOf(user.getIdContractOffer())));
            	table.addCell(cell);
               
                
                cell.setPhrase(new Phrase(user.getDate_debut().toString()));
                table.addCell(cell);
               
               
                cell.setPhrase(new Phrase(user.getDate_fin().toString()));
                table.addCell(cell);
                
                cell.setPhrase(new Phrase(user.getState_offers().toString()));
               
                table.addCell(cell);
                
                cell.setPhrase(new Phrase(String.valueOf(user.getTarification())+"  Dinar"));
                
                 table.addCell(cell);
                
                
            }
        
         
        public void export(Contract_Offers data) throws DocumentException, IOException {
            Document document = new Document(PageSize.A4);
            
            String File="C:\\JavaPDF\\MixteContrat"+data.getUsers().getLast_name()+data.getUsers().getFirst_name()+".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(File));
            final String USER_PWD="Mixte"+data.getUsers().getCIN();
            final String OWNER_PWD="Admin";
            writer.setEncryption(USER_PWD.getBytes(), OWNER_PWD.getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
            
             
            document.open();
            document.addTitle("MicroLeftContract");
            
            Image image = Image.getInstance("C:\\JavaPDF\\l.png");
            image.scaleToFit(150, 450);
            image.setAlignment(Image.ALIGN_MIDDLE);
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(25);
            font.setColor(Color.BLUE);
            
            document.add(image);
            Paragraph p = new Paragraph("Offer Mixte Contract ", font);
            p.setSpacingBefore(50);
            p.setAlignment(Paragraph.ALIGN_CENTER);
            
            document.add(p);
             
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100f);
            table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
            table.setSpacingBefore(50);
             
            writeTableHeader(table);
            writeTableData(table,data);
             
            document.add(table);
            Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font1.setSize(15);
            font1.setColor(Color.RED);
            Paragraph p1 = new Paragraph("Offer Mixte Contract clause", font1);
            p1.setSpacingBefore(50);
            Paragraph p2 = new Paragraph("In Case Of Death after the end of contract, The relative of the client will get : "+data.getDèces_mixte() + " Dinar");
            p1.setSpacingBefore(20);
            Paragraph p3 = new Paragraph(" * In Case Of Survival after the end of contract, The client will get : "+data.getVie_mixte()+ " Dinar");
            Paragraph p4 = new Paragraph("* The Duration of the Contract is determined to be for : "+data.getDuree()+ " Years");
            Paragraph p6 = new Paragraph("* In case of termination the offer is not refundable");
            p2.setSpacingBefore(20);
            p3.setSpacingBefore(20);
            p4.setSpacingBefore(20);
            p6.setSpacingBefore(20);
            document.add(p1);
            document.add(p2);
            document.add(p3);
            document.add(p4);
            document.add(p6);
            Image image1 = Image.getInstance("C:\\JavaPDF\\signature.jpg");
            image1.scaleToFit(200, 400);
            image1.setAlignment(Image.ALIGN_RIGHT);
            document.add(image1);
            Image QrCode = Image.getInstance("C:\\JavaPDF\\MyQRCode"+data.getUsers().getFirst_name()+".png");
            QrCode.scaleToFit(100, 300);
            QrCode.setAlignment(Image.ALIGN_LEFT);
            document.add(QrCode);
            document.close();
             
        }
        
        public int nbre_tranches_restantes(String final_date){
        	
        	LocalDate dt_final = new LocalDate(final_date);
        	LocalDate now = new LocalDate(); 
        	int monthsBetween = Years.yearsBetween(now, dt_final).getYears();
        	
        	return monthsBetween;
        }
        
        public void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        }
		
	
	
    	
    }
    
    
    
    


