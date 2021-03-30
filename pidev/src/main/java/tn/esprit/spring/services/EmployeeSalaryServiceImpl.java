package tn.esprit.spring.services;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entity.EmployeeSalary;
import tn.esprit.spring.repository.EmployeeSalaryRepository;

	
	@Service
	public class EmployeeSalaryServiceImpl implements IEmployeeSalaryService{
		@Autowired
		EmployeeSalaryRepository salaryRepository;
		private static final Logger l = LogManager.getLogger(EmployeeSalaryServiceImpl.class);
	
		public List<EmployeeSalary> retrieveAllSalaries() {
			List<EmployeeSalary> salaries = (List<EmployeeSalary>) salaryRepository.findAll();
			for(EmployeeSalary salary : salaries)
			{
				l.info("salary ++ :"+salary);
			}
			return salaries;
	
		}
	
		public EmployeeSalary addSalary(EmployeeSalary s) {
			EmployeeSalary employeeSalarySaved = null;
			employeeSalarySaved=salaryRepository.save(s);
			return employeeSalarySaved;
	
		}
	
		public void deleteSalary(String id) {
			salaryRepository.deleteById(Integer.parseInt(id));
	
		}
	
		public EmployeeSalary updateSalary(EmployeeSalary s) {
			EmployeeSalary employeeSalaryAdded = salaryRepository.save(s);
			return employeeSalaryAdded;
		}
	
		public EmployeeSalary retrieveSalary(String id) {
	
			l.info("in retrieveSalary id= "+id);
			EmployeeSalary s = salaryRepository.findById(Integer.parseInt(id)).orElse(null);
			l.info("Salary returned : " +s);
			return s;
	
		}
	
		/*public List<EmployeeSalary> retrieveSalaryByWorkhours(Work_hours wk)
		{
			List<EmployeeSalary> salaries = (List<EmployeeSalary>) salaryRepository.retrieveSalaryByWorkhours(wk);
			for(EmployeeSalary salary : salaries)
			{
				l.info("salary ++ :"+salary);
			}
			return salaries;	
		}
	*/
	}
	
