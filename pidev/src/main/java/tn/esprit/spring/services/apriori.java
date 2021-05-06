package tn.esprit.spring.services;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

import org.springframework.stereotype.Service;


@Service
public class apriori {

	Vector<String> candidates=new Vector<String>();
	List<String> itemSet = new ArrayList<String>();
	List<String> finalFrequentItemSet = new ArrayList<>();
	HashMap<String,Integer> frequentItems = new HashMap<String, Integer>();
	String newLine = System.getProperty("line.separator");
	int itemsCount,countItemOccurrence=0,displayFrequentItemSetNumber=2,displayTransactionNumber=1;

	HashMap<String,Double> proposition = new HashMap<String, Double>();
	public HashMap<String, Double> getProposition() {
		return proposition;
	}
	public void setProposition(HashMap<String, Double> proposition) {
		this.proposition = proposition;
	}
	//System.out.println(apriori.getProposition().size() + "   holaaaa") ;

	public HashMap<String,Double> ExecuteApriori(String s) throws FileNotFoundException{

		int noOfTransactions,minimumSupport;
		double minimumConfidence;
		List<String> transactions = new ArrayList<String>();
		minimumSupport= 1 ;
		minimumConfidence = 0;
		File file = new File("apriori.txt");
		Scanner sc = new Scanner(file) ;

		while (sc.hasNextLine())
		{
			String str = sc.nextLine();
			transactions.add(str);
		}

		noOfTransactions = transactions.size();

		apriori a = new apriori();
		HashMap<String,Double> m= a.display(noOfTransactions, transactions, minimumSupport, minimumConfidence , s);
		

		return m;

	}


	public HashMap<String,Double> display(int noOfTransactions, List<String> transactions, int minimumSupport, double minimumConfidence , String s)
	{
		for(int i = 0; i<noOfTransactions;i++)
		{
			String str = transactions.get(i);
			String[] words = str.split(" ");
			int count = words.length;
			for(int j=0;j<count;j++)
			{
				if(i==0)
				{
					itemSet.add(words[j]);
				}
				else
				{ 
					if(!(itemSet.contains(words[j])))
					{
						itemSet.add(words[j]);
					}
				}
			}
		}

		itemsCount = itemSet.size(); 
		System.out.println(newLine+"No of Items = "+itemsCount); 
		System.out.println("No of Transactions = "+noOfTransactions); 
		System.out.println("Minimum Support = "+minimumSupport); 
		System.out.println("Minimum Confidence = "+minimumConfidence+newLine);

		System.out.println("'Items present in the Database'");
		for(String i : itemSet)
		{
			System.out.println("------> "+i);
		}

		System.out.println(newLine+"TRANSACTION ITEMSET");
		for(String i : transactions)
		{
			System.out.println("Transaction "+displayTransactionNumber+" = "+i);
			displayTransactionNumber++;
		}
		firstFrequentItemSet(noOfTransactions,transactions,minimumSupport,minimumConfidence , s);
		return this.getProposition();
	}

	public void firstFrequentItemSet(int noOfTransactions,List<String> transactions,int minimumSupport, double minimumConfidence , String s)
	{
		System.out.println();
		System.out.println("Frequent Itemset 1");
		for(int items=0;items<itemSet.size();items++)
		{
			countItemOccurrence=0;
			String itemStr = itemSet.get(items);
			for(int t=0;t<noOfTransactions;t++)
			{
				String transactionStr = transactions.get(t);
				if(transactionStr.contains(itemStr))
				{
					countItemOccurrence++;
				}
			}

			if(countItemOccurrence >= minimumSupport)
			{
				System.out.println(itemStr+" => support = "+countItemOccurrence);
				finalFrequentItemSet.add(itemStr);
				frequentItems.put(itemStr, countItemOccurrence);
			}
		}

		aprioriStart(noOfTransactions,transactions,minimumSupport,minimumConfidence , s);
	}

	public void aprioriStart(int noOfTransactions,List<String> transactions,int minimumSupport, double minimumConfidence , String s)
	{
		int itemsetNumber=1;

		for(int i=0;i<finalFrequentItemSet.size();i++)
		{
			String str = finalFrequentItemSet.get(i);
			candidates.add(str);
		} 

		do
		{
			itemsetNumber++;
			generateCombinations(itemsetNumber);
			checkFrequentItems(noOfTransactions,transactions,minimumSupport);
		}
		while(candidates.size()>1);

		System.out.println("Association Rules for Frequent Itemset"+newLine);

		generateAssociationRules(noOfTransactions, transactions, minimumConfidence , s);
	}

	private void generateCombinations(int itr)
	{
		Vector<String> candidatesTemp = new Vector<String>();
		String s1, s2;
		StringTokenizer strToken1, strToken2;
		if(itr==2)
		{
			for(int i=0; i<candidates.size(); i++)
			{
				strToken1 = new StringTokenizer(candidates.get(i));
				s1 = strToken1.nextToken();
				for(int j=i+1; j<candidates.size(); j++)
				{
					strToken2 = new StringTokenizer(candidates.elementAt(j));
					s2 = strToken2.nextToken();
					String addString = s1+" "+s2;
					candidatesTemp.add(addString);
				}
			}
		}
		else
		{
			for(int i=0; i<candidates.size(); i++)
			{
				for(int j=i+1; j<candidates.size(); j++)
				{
					s1 = new String();
					s2 = new String();

					strToken1 = new StringTokenizer(candidates.get(i));
					strToken2 = new StringTokenizer(candidates.get(j));

					for(int s=0; s<itr-2; s++)
					{
						s1 = s1 + " " + strToken1.nextToken();
						s2 = s2 + " " + strToken2.nextToken();
					}

					if(s2.compareToIgnoreCase(s1)==0)
					{
						String addString = (s1 + " " + strToken1.nextToken() + " " + strToken2.nextToken()).trim();
						candidatesTemp.add(addString);
					}
				}
			}
		}
		candidates.clear();
		candidates = new Vector<String>(candidatesTemp);
		candidatesTemp.clear();
		System.out.println();
	}

	public void checkFrequentItems(int noOfTransactions,List<String> transactions, int minimumSupport)
	{
		List<String> combList = new ArrayList<String>();
		for(int i=0;i<candidates.size();i++)
		{
			String str = candidates.get(i);
			combList.add(str);
		}
		System.out.println("Frequent Itemset "+displayFrequentItemSetNumber);
		for(int i=0;i<combList.size();i++)
		{
			String str = combList.get(i);
			String[] words = str.split(" ");
			int count = words.length;
			int flag = 0, itemSetOccurence=0;
			for(int t=0;t<noOfTransactions;t++)
			{
				String transac = transactions.get(t);
				for(int j=0;j<count;j++)
				{
					String wordStr = words[j];
					if(transac.contains(wordStr))
					{
						flag++;
					}
				}
				if(flag==count)
				{
					itemSetOccurence++;
				}
				flag=0;
			}

			if(itemSetOccurence>=minimumSupport)
			{
				System.out.println(str+" => support = "+itemSetOccurence);
				frequentItems.put(str, itemSetOccurence);
				finalFrequentItemSet.add(str);
			}
			itemSetOccurence=0;
		}
		displayFrequentItemSetNumber++;
	}


	public void generateAssociationRules(int noOfTransactions,List<String> transactions,double minimumConfidence , String chaine_mta3_choix_mte3i)
	{
		String[] word_mta3_choix_mte3i = chaine_mta3_choix_mte3i.split(" ") ;

	//	proposition = null ;
		double verifConfidence = 0 ;
		double confidence,confidence1;
		System.out.println(" - ------------- - "+finalFrequentItemSet) ;
		for(int i=0;i<finalFrequentItemSet.size();i++)
		{
			//List<> l = new ArrayList<>();



			int spring2019count=0;
			String itemSetStr = finalFrequentItemSet.get(i);
			double value = frequentItems.get(itemSetStr);
			String str = "",str1="";
			String[] words = itemSetStr.split(" ");
			int wordCountInString = words.length;
			if(wordCountInString==2) /* for FrequentItemSet = 2*/
			{
			}

			else /* for FrequentItemSet > 2 */
			{
				for(int a=0;a<wordCountInString-1;a++)
				{
					if(a==0)
					{
						str = str+words[a];
						spring2019count++;
					}
					else
					{
						str = str+" "+words[a];
						spring2019count++;
					}
					for(int j=a+1;j<wordCountInString;j++)
					{
						{
							str1=str1+" "+words[j];
							spring2019count++;
						}
					}

					//////////////////

					int compteur = 0 ;
					String[] verifword = str.split(" ") ;
					int count = verifword.length ;
					for(int j=0;j<count;j++)
					{	for(int iChoix = 0 ; iChoix < word_mta3_choix_mte3i.length ; iChoix++){

						if( verifword[j].equals(word_mta3_choix_mte3i[iChoix])){
							compteur = compteur + 1 ;
						}
					}
					}
					verifword = null ;		
					///////////////
					double s = frequentItems.get(str);
					confidence = value/s;
				//	String st = str1.trim();
				//	double s1 = frequentItems.get(st);
				//	confidence1 = value/s1;
					//System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
					if(confidence>=minimumConfidence)
					{	
						//System.out.println(compteur +"      /////////     "+ word_mta3_choix_mte3i.length);
						if(compteur == (int) word_mta3_choix_mte3i.length  && count == (int) word_mta3_choix_mte3i.length   ){
								
						if(proposition.size() == 0 ){
								proposition.put(str1,  (confidence*100)) ;
								System.out.println("1111111111111111111111") ;
								this.setProposition(proposition);
//								System.out.println(this.getProposition().size() + "holaaaa") ;
								verifConfidence = (confidence*100) ;
							}
							else{
								if(verifConfidence < (confidence*100)){
									proposition.clear();
									proposition.put(str1,  (confidence*100)) ;
									System.out.println("22222222222222222222") ;
									this.setProposition(proposition);
									verifConfidence = (confidence*100) ;	
							}
						
							System.out.println(str+" -> "+str1+" = Confidence = "+confidence*100+" and Support = "+(int)value+"");
						}					
					}
					str1="";
				}
				//str1="";
			}
			str="";str1="";
		}
	}

	}}


