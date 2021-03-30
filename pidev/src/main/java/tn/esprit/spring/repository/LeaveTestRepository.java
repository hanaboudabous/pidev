package tn.esprit.spring.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.LeaveTest;
import tn.esprit.spring.entity.Payment;
import tn.esprit.spring.entity.Type_Leave;
import tn.esprit.spring.entity.Type_Payment;


@Repository

public interface LeaveTestRepository extends CrudRepository<LeaveTest,Integer>{
	@Query("SELECT l FROM LeaveTest l WHERE l.type_leave= ?1")
	List<LeaveTest> retrieveLeaveByType(Type_Leave type);
}
