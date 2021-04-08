package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entity.EmployeeSalary;
@Repository
public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary,Integer> {
	//@Query("SELECT s FROM EmployeeSalary s WHERE work_hours= ?1")
	//List<EmployeeSalary> retrieveSalaryByWorkhours(work_hours wk);
	
	@Query(value="SELECT user_id, month, year, extra_hours, work_hours, salary FROM user inner join employee_salary on user.user_id=employee_salary.user_user_id WHERE employee_salary.user_user_id=:User_ID ",nativeQuery=true )
	public List<?> fichedepaie(@Param ("User_ID")int User_ID);
	@Transactional
	@Modifying
	@Query(value="UPDATE employee_salary set salary=(work_hours*15)+(extra_hours*20)",nativeQuery=true)
    public int salary();
}
