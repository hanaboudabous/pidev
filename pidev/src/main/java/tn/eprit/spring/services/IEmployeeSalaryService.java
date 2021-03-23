package tn.eprit.spring.services;

import java.util.List;

import tn.esprit.spring.entity.EmployeeSalary;

public interface IEmployeeSalaryService {

	int addSalary(EmployeeSalary employeeSalary);

	List<EmployeeSalary> getAllSalaries();

	void deleteBySalaryId(int salary_Id);

	EmployeeSalary updateLeave(EmployeeSalary S);

	//List<?> stat();

}
