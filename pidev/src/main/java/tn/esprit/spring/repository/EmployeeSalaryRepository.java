package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.EmployeeSalary;

@Repository
public interface EmployeeSalaryRepository extends CrudRepository<EmployeeSalary,Integer> {
	//@Query("select work_hours, year, month from EmloyeeSalary group by year, month")
   // public List<?> listworkhours();
}
