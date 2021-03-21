package tn.eprit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.LeaveTest;
import tn.esprit.spring.repository.LeaveTestRepository;

@Service
public class LeaveTestServiceImpl implements ILeaveTestService{
	
	@Autowired
		LeaveTestRepository leaveTestRepository;
	
	
	@Override
	public int addLeave(LeaveTest leaveTest)
	{
		
		return leaveTestRepository.save(leaveTest).getLeave_id();
	}
	
	@Override
	public List<LeaveTest> getAllLeaves()
	{
		return (List<LeaveTest>) (leaveTestRepository.findAll());
	}
	
	@Override
	public void deleteByLeaveId(int leave_id)
	{
		leaveTestRepository.deleteById(leave_id);
	}
	
	@Override
	public LeaveTest updateLeave (LeaveTest L)
	{
		return leaveTestRepository.save(L);
		
	}
	
	
	
}
