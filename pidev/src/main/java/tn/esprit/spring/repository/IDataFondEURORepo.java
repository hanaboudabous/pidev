package tn.esprit.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.DataFondEURO;

@Repository

public interface IDataFondEURORepo  extends JpaRepository<DataFondEURO, Integer>{

	
	List<DataFondEURO> findByTypBonde(String typBonde);
	
	List<DataFondEURO> findByTypBondeAndDateSouscriptionLessThanEqualAndDateEcheanceGreaterThanEqual(String typBonde , Date DateSouscription , Date ateEcheance);
	List<DataFondEURO> findByTypBondeAndDateSouscriptionGreaterThanEqualAndDateEcheanceGreaterThanEqual(String typBonde , Date DateSouscription , Date ateEcheance);


	@Query(value = "SELECT * FROM data_fondeuro WHERE extract(year from date_souscription)<=:s and extract(year from date_echeance)>=:s", nativeQuery = true)
	public List<DataFondEURO> listeParAnnee(@Param("s") int s );
	
	

	@Query(value = "SELECT * FROM data_fondeuro WHERE typ_bonde='BTA' and extract(year from date_souscription)<=:s and extract(year from date_echeance)>=:s", nativeQuery = true)
	public List<DataFondEURO> listeParAnneeBTA(@Param("s") int s );
	
	
	List<DataFondEURO> findAllByOrderByDateEcheanceAsc();
	List<DataFondEURO> findAllByOrderByDateSouscriptionDesc();
	
/*
	List<DataFondEURO> findByTypBondeByOrderByDateEcheanceAsc(String typBonde);
	List<DataFondEURO> findByTypBondeByOrderByDateEcheanceDesc(String typBonde);*/

	
}
