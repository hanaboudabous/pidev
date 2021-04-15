package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Contract_Offers;
import tn.esprit.spring.entity.State_Offers;


@Repository
public interface ContractOfferRepository extends CrudRepository<Contract_Offers, Integer> {
	
	@Query("select c from Contract_Offers c where c.users.User_ID=:User_ID")
	public Contract_Offers Contract_OffersByUser(@Param ("User_ID")int User_ID );
	
	
	//@Modifying
	//@Query("update Contract_Offers c set c.state_offers = :state_offers where c.users.User_ID=:User_ID")
	//void updateContratStatusByFid(@Param("state_offers")  State_Offers s, @Param("User_ID") int User_ID);
	
	

}
