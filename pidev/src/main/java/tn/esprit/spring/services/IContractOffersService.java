package tn.esprit.spring.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.zxing.WriterException;

import tn.esprit.spring.entity.Contract_Offers;

public interface IContractOffersService {
	 List<Contract_Offers> retrieveAll_Contractoffers();
	 Contract_Offers addContract_Offers(Contract_Offers cf);
	 void deleteContract_Offers(int id);
	 Contract_Offers updateContract_Offers(Contract_Offers cf);
	 Contract_Offers retrieveContract_Offers(int id);
	 Contract_Offers Contract_OffersByUser(int id);
	 public double Tarification_Mixte_PrimePeriodique(double vie_mixte,double dèces_mixte,int duree,int age );
	 public Double Tarification_Mixte_PrimeUnique(double vie_mixte,double dèces_mixte,int duree,int age );
	 Contract_Offers AddContractMixte(Contract_Offers c , int id);
	 public void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException,IOException;
	 public int nbre_tranches_restantes(String final_date);
	 
	
	 

}
