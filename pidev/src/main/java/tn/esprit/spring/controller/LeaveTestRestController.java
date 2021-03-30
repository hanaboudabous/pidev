package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.esprit.spring.entity.LeaveTest;
import tn.esprit.spring.entity.Type_Leave;
import tn.esprit.spring.services.ILeaveTestService;

@Controller
public class LeaveTestRestController {
	@Autowired
	ILeaveTestService leaveTestService;


	@GetMapping("/leaves")
	@ResponseBody
	public List<LeaveTest> getLeaves() {
		List<LeaveTest> list = leaveTestService.retrieveAllLeaves();
		return list;
	}


	@GetMapping("/retrieveLeave/{LeaveId}")
	@ResponseBody
	public LeaveTest retrieveLeave(@PathVariable("LeaveId") String Id) {
		return leaveTestService.retrieveLeave(Id);
	}

	@GetMapping("/retrieveLeaveType/{type}")
	@ResponseBody
	public List<LeaveTest> retrieveLeaveByType(@PathVariable("type") Type_Leave type) {
		return leaveTestService.retrieveLeaveByType(type);
	}


	@PostMapping("/addLeave")
	@ResponseBody
	public LeaveTest addLeave(@RequestBody LeaveTest l) {
		LeaveTest leave = leaveTestService.addLeave(l);
		return leave;
	}

	@DeleteMapping("/removeLeave/{leaveId}")
	@ResponseBody
	public void removeLeave(@PathVariable("leaveId") String leaveId) {
		leaveTestService.deleteLeave(leaveId);
	}

	@PutMapping("/updateLeave")
	@ResponseBody
	public LeaveTest updateLeave(@RequestBody LeaveTest leave) {
		return leaveTestService.updateLeave(leave);
	}

	@PutMapping("/affecterUserToLeave/{Leave_ID}/{User_ID}")
	@ResponseBody
	public void affecterUserToLeave(@PathVariable("Leave_ID") int idl,@PathVariable("User_ID") int id) {
		 leaveTestService.affecterUserALeave(idl, id);
	}

}
