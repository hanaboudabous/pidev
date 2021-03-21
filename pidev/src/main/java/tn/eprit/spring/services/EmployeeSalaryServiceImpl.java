package tn.eprit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import tn.esprit.spring.entity.EmployeeSalary;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.EmployeeSalaryRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class EmployeeSalaryServiceImpl  implements IEmployeeSalaryService{

	@Autowired
	EmployeeSalaryRepository employeeSalaryRepository;
	@Autowired
	UserRepository userRepository;


	@Override
	public int addSalary (EmployeeSalary employeeSalary)
	{

		return employeeSalaryRepository.save(employeeSalary).getSalary_ID();
	}

	@Override
	public List<EmployeeSalary> getAllSalaries()
	{
		return (List<EmployeeSalary>) (employeeSalaryRepository.findAll());
	}

	@Override
	public void deleteBySalaryId(int salary_Id)
	{
		employeeSalaryRepository.deleteById(salary_Id);
	}

	@Override
	public EmployeeSalary updateLeave (EmployeeSalary S)
	{
		return employeeSalaryRepository.save(S);

	}

	public void affecterSalaryToUser(int Salary_ID, int User_ID) {
		User user = userRepository.findById(User_ID).get();
		EmployeeSalary employeeSalary =employeeSalaryRepository.findById(Salary_ID).get();
		if (!ObjectUtils.isEmpty(employeeSalary) && !ObjectUtils.isEmpty(user))
		employeeSalary.setUser(user);
		userRepository.save(user);
	}

}
