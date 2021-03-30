package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.EmployeeSalary;
import tn.esprit.spring.entity.LeaveTest;
import tn.esprit.spring.entity.Type_Leave;
@Repository
public interface EmployeeSalaryRepository extends CrudRepository<EmployeeSalary,Integer> {
	//@Query("SELECT s FROM EmployeeSalary s WHERE work_hours= ?1")
	//List<EmployeeSalary> retrieveSalaryByWorkhours(work_hours wk);
}
