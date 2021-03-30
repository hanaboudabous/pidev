package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entity.EmployeeSalary;

public interface IEmployeeSalaryService {

	List<EmployeeSalary> retrieveAllSalaries();
	EmployeeSalary addSalary(EmployeeSalary s) ;
	void deleteSalary(String id);
	EmployeeSalary updateSalary(EmployeeSalary s);
	EmployeeSalary retrieveSalary(String id) ;
}
