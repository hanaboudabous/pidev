package tn.esprit.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entity.LeaveTest;
import tn.esprit.spring.services.LeaveTestServiceImpl;
@RestController
public class LeaveTestController {



	//@Autowired(required=false)
	//private LeaveTestServiceImpl leaveTest ;

	@GetMapping("/leaves")
	@ResponseBody
	public List<LeaveTest> getAllLeavess(){
		LeaveTestServiceImpl leaveTest = new LeaveTestServiceImpl();
		return leaveTest.getAllLeaves();
	}



	@PostMapping("/AddLeave")
	public Long addLeave(@RequestBody LeaveTest l){
		LeaveTestServiceImpl leaveTest = new LeaveTestServiceImpl();
		leaveTest.addLeave(l);
		return l.getLeave_id();
		}


	@DeleteMapping(value = "/deleteByLeaveId/{leave_id}")
	public void deleteByLeaveId(@PathVariable("leave_id")Long leave_id) {
		LeaveTestServiceImpl leaveTest = new LeaveTestServiceImpl();
		leaveTest.deleteByLeaveId(leave_id);


	}

	@PutMapping(value = "/updateLeave")
	public LeaveTest updateLeave(@RequestBody LeaveTest L)  {
		LeaveTestServiceImpl leaveTest = new LeaveTestServiceImpl();
		return leaveTest.updateLeave(L);
	}


	@Bean
	public LeaveTestServiceImpl leaveTest() {
		return new LeaveTestServiceImpl();
	}
}
