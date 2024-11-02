package com.empasset.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empasset.dto.AllocatedAssetDto;
import com.empasset.dto.AssetHistoryDto;
import com.empasset.dto.AssetServiceRequestDto;
import com.empasset.dto.LoginDto;
import com.empasset.dto.SearchAssetDto;
import com.empasset.dto.ServiceRequestDto;
import com.empasset.model.Asset;
import com.empasset.model.AssetRequest;
import com.empasset.model.User;
import com.empasset.service.AdminService;
import com.empasset.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService; //for getCategory method only
	
	@PostMapping(value = "/loginUser")
	public String loginUser(@RequestBody LoginDto login) {
		return userService.loginUser(login);
	}
	
	@PostMapping(value = "/addUser")
	public String addUser(@RequestBody User user) {
		return userService.addUser(user);
	}
	
	@PutMapping(value = "/updateUser")
	public String updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}

	@GetMapping(value = "/findUserByEmail/{email}")
	public User findUserByEmail(@PathVariable String email) {
		return userService.findUserByEmail(email);
	}
	
	@GetMapping(value = "/findUserById/{id}")
	public User findUserById(@PathVariable int id) {
		return userService.findUserById(id);
	}
	
	@GetMapping(value = "/showAvailableAsset")
	public List<Asset> showAvailableAsset(){
		return userService.showAvailableAsset();
	}
	
	
	@GetMapping(value = "/myAssets/{userId}")
	public List<AllocatedAssetDto> showAssetAllocatedToUser(@PathVariable int userId){
		return userService.myAssets(userId);
	}
	
	@GetMapping(value = "/assetHistory/{userId}")
	public List<AssetHistoryDto> assetHistory(@PathVariable int userId){
		return userService.assetHistory(userId);
	}
	
	
	@PostMapping(value = "/requestAsset/{userId}/{assetId}")
	public String requestAsset(@PathVariable int userId,@PathVariable int assetId) {
		return userService.requestAsset(userId,assetId);
	}
	
	
	@GetMapping(value = "/assetRequestStatus/{userId}")
	public List<AllocatedAssetDto> assetRequestStatus(@PathVariable int userId){
		return userService.assetRequestStatus(userId);
	}
	
	@GetMapping(value = "/noOfPendingAssetRequest/{userId}")
	public List<AllocatedAssetDto> noOfPendingAssetRequest(@PathVariable int userId){
		return userService.noOfPendingAssetRequest(userId);
	}
	
	@PostMapping(value = "cancelAssetRequest/{requestId}")
	public String cancelAssetRequest(@PathVariable int requestId) {
		return userService.cancelAssetRequest(requestId);
	}

	
	@PostMapping(value = "/assetServiceRequest")
	public String assetServiceRequest(@RequestBody ServiceRequestDto serviceRequestDto) {
		return userService.assetServiceRequest(serviceRequestDto);
	}
	
	@GetMapping(value = "/serviceRequestStatus/{userId}")
	public List<AssetServiceRequestDto> serviceRequestStatus(@PathVariable int userId) {
		return userService.serviceRequestStatus(userId);
	}
	
	@GetMapping(value = "/noOfServiceRequest/{userId}")
	public List<AssetServiceRequestDto> noOfAssetServiceRequest(@PathVariable int userId){
		return userService.noOfAssetServiceRequest(userId);
	}
	
	@PostMapping(value = "assetReturnRequest/{requestId}")
	public String returnAssetRequest(@PathVariable int requestId) {
		return userService.assetReturnRequest(requestId);
	}
	
	@GetMapping(value = "/showAuditRequest/{userId}")
	public List<AllocatedAssetDto> showAuditRequest(@PathVariable int userId){
		return userService.showAuditRequest(userId);
		}
	
	@GetMapping(value = "/noOfAuditRequest/{userId}")
	public List<AllocatedAssetDto> noOfAuditRequest(@PathVariable int userId){
		return userService.noOfAuditRequest(userId);
	}
	
	@GetMapping(value = "/getAssetCategories")
	public List<String> getAssetCategories(){
		return adminService.getAssetCategories();
	}
	
	@PostMapping(value = "/searchAssetByNameAndCategory")
	public List<Asset> searchAssetByNameAndCategory(@RequestBody SearchAssetDto searchAssetDto){
		return userService.searchAssetByNameAndCategory(searchAssetDto);
	}
	
	@PostMapping(value = "/searchAssetByNameOrCategory")
	public List<Asset> searchAssetByNameOrCategory(@RequestBody SearchAssetDto searchAssetDto){
		return userService.searchAssetByNameOrCategory(searchAssetDto);
	}
	

	
}
