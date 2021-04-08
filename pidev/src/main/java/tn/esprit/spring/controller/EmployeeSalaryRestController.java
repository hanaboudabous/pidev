package tn.esprit.spring.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.mapstruct.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.esprit.spring.entity.EmployeeSalary;
import tn.esprit.spring.entity.LeaveTest;
import tn.esprit.spring.entity.Type_Leave;
import tn.esprit.spring.services.EmployeeSalaryServiceImpl;
import tn.esprit.spring.services.IEmployeeSalaryService;
import tn.esprit.spring.services.ILeaveTestService;
@Controller
public class EmployeeSalaryRestController {
		@Autowired
		IEmployeeSalaryService salaryService;
		EmployeeSalaryServiceImpl salaryServicee;
		@GetMapping("/salaries")
		@ResponseBody
		public List<EmployeeSalary> getSalaries() {
		List<EmployeeSalary> list = salaryService.retrieveAllSalaries();
		return list;
		}
		
		
		@GetMapping("/retrieveSalary/{salaryId}")
		@ResponseBody
		public EmployeeSalary retrieveSalary(@PathVariable("salaryId") String Id) {
		return salaryService.retrieveSalary(Id);
		}
		
		
		@PostMapping("/addSalary")
		@ResponseBody
		public EmployeeSalary addSalary(@RequestBody EmployeeSalary s) {
		EmployeeSalary salary = salaryService.addSalary(s);
		salaryService.salary();
		return salary;
		}
		
		@DeleteMapping("/removeSalary/{salaryId}")
		@ResponseBody
		public void removePayment(@PathVariable("salaryId") String salaryId) {
			salaryService.deleteSalary(salaryId);
		}
		
		@PutMapping("/updateSalary")
		@ResponseBody
		public EmployeeSalary updateSalary(@RequestBody EmployeeSalary salary) {
		return salaryService.updateSalary(salary);
		}
		
		@GetMapping("/fichesdepaie/{User_ID}")
		@ResponseBody		
		public void fichedepaie(@PathVariable("User_ID") int User_ID) {
			salaryService.fichedepaie(User_ID);
		}
		
		@PutMapping("/affecterSalaryToUser/{Salary_ID}/{User_ID}")
		@ResponseBody
		public void affecterSalaryToUser(@PathVariable("Salary_ID") int idp,@PathVariable("User_ID") int id) {
			salaryService.affecterSalaryToUser(idp, id);
		}
		
		/*@GetMapping("/users-csv")
		public void downloadUsersCSV(@Context HttpServletResponse httpServletResponse) throws IOException{
			salaryServicee.generateCsvResponse(httpServletResponse);
		}*/
}
	

