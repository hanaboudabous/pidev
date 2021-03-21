package tn.eprit.spring.services;

import java.util.List;

import tn.esprit.spring.entity.LeaveTest;

public interface ILeaveTestService {

	int addLeave(LeaveTest leaveTest);

	List<LeaveTest> getAllLeaves();

	void deleteByLeaveId(int leave_id);

	LeaveTest updateLeave(LeaveTest L);

}
