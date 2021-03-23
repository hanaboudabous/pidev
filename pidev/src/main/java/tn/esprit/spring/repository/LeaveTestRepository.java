package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.LeaveTest;


@Repository
public interface LeaveTestRepository extends CrudRepository<LeaveTest,Long> {

}
