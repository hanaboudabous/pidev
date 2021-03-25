package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entity.LeaveTest;

public interface ILeaveTestService {

		Long addLeave(LeaveTest leaveTest);

	List<LeaveTest> getAllLeaves();

	void deleteByLeaveId(Long leave_id);

	LeaveTest updateLeave(LeaveTest L);

}
