package com.empasset.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.empasset.dto.AllocatedAssetDto;
import com.empasset.dto.AssetServiceRequestDto;
import com.empasset.dto.ForgotPasswordDto;
import com.empasset.dto.LoginDto;
import com.empasset.dto.SearchAssetAllocatedToUser;
import com.empasset.dto.SearchAssetDto;
import com.empasset.model.Admin;
import com.empasset.model.Asset;
import com.empasset.model.AssetService;
import com.empasset.model.User;
import com.empasset.service.AdminService;
import com.empasset.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService; //only for showAvailableAsset Search 

	
	@PostMapping(value = "/loginAdmin")
	public String loginAdmin(@RequestBody LoginDto login) {
		return adminService.loginAdmin(login);
	}
	
	
	
	@PostMapping(value = "/addAdmin")
	public String addAdmin(@RequestBody Admin admin) {
		return adminService.addAdmin(admin);
	}
	
	@PutMapping(value = "/updateAdmin")
	public String updateUser(@RequestBody Admin admin) {
		return adminService.updateAdmin(admin);
	}
	
	@GetMapping(value = "/showAll")
	public List<Admin> showAll(){
		return adminService.showAll();
	}
	
	@GetMapping(value = "/showAllUsers")
	public List<User> showAllUser(){
		return adminService.showAllUser();
	}
	
	@GetMapping(value = "/showAsset")
	public List<Asset> showAsset(){
		return adminService.showAsset();
	}
	
	@PostMapping(value = "/searchAllAssetByNameOrCategory")
	public List<Asset> searchAllAssetByNameOrCategory(@RequestBody SearchAssetDto searchAssetDto){
		return adminService.searchAllAssetByNameOrCategory(searchAssetDto);
	}
	
	@PostMapping(value = "/searchAllAssetByNameAndCategory")
	public List<Asset> searchAllAssetByNameAndCategory(@RequestBody SearchAssetDto searchAssetDto){
		return adminService.searchAllAssetByNameAndCategory(searchAssetDto);
	}
	
	@PostMapping(value = "/addAsset")
	public String addAsset(@RequestBody Asset asset) {
		return adminService.addAsset(asset);
	}
	
	@DeleteMapping(value = "/deleteAsset/{assetId}")
	public String deleteAsset(@PathVariable int assetId) {
		return adminService.deleteAsset(assetId);
	}
	
	@PutMapping(value = "/updateAsset")
	public String updateAsset(@RequestBody Asset asset) {
		return adminService.updateAsset(asset);
	}
	
	@GetMapping(value = "/showAvailableAsset")
	public List<Asset> showAvailableAsset(){
		return adminService.showAvailableAsset();
	}
	
	@PostMapping(value = "/searchAssetByNameAndCategory")
	public List<Asset> searchAssetByNameAndCategory(@RequestBody SearchAssetDto searchAssetDto){
		return userService.searchAssetByNameAndCategory(searchAssetDto);
	}
	
	@PostMapping(value = "/searchAssetByNameOrCategory")
	public List<Asset> searchAssetByNameOrCategory(@RequestBody SearchAssetDto searchAssetDto){
		return userService.searchAssetByNameOrCategory(searchAssetDto);
	}
	
	@GetMapping(value = "/findUserById/{id}")
	public User findUserById(@PathVariable int id) {
		return adminService.findUserById(id);
	}
	
	@GetMapping(value = "/findUserByEmail/{email}")
	public User findUserByEmail(@PathVariable String email) {
		return adminService.findUserByEmail(email);
	}
	
	@GetMapping(value = "/findAdminById/{id}")
	public Admin findAdminById(@PathVariable int id) {
		return adminService.findAdminById(id);
	}
	
	@GetMapping(value = "/showAssetAllocatedToUser")
	public List<AllocatedAssetDto> showAssetAllocatedToUser(){
		return adminService.showAssetAllocatedToUser();
	}
	
	@PostMapping(value = "/searchshowAssetAllocatedToUserById")
	public List<AllocatedAssetDto> searchshowAssetAllocatedToUserById(@RequestBody SearchAssetAllocatedToUser assetAllocatedToUser){
		return adminService.searchshowAssetAllocatedToUserById(assetAllocatedToUser);
	}
	
	
	@PostMapping(value = "/searchshowAssetAllocatedToUserByNameAndEmail")
	public List<AllocatedAssetDto> searchshowAssetAllocatedToUserByNameAndEmail(@RequestBody SearchAssetAllocatedToUser assetAllocatedToUser){
		return adminService.searchshowAssetAllocatedToUserByNameAndEmail(assetAllocatedToUser);
	}
	
	@GetMapping(value = "/showAssetAllocationRequest")
	public List<AllocatedAssetDto> showAssetAllocationRequest() {
		return adminService.showAssetAllocationRequest();
	}
	
	@PostMapping(value = "/searchAssetAllocationRequestByUserId")
	public List<AllocatedAssetDto> searchAssetAllocationRequestByUserId(@RequestBody SearchAssetAllocatedToUser assetAllocatedToUser){
		return adminService.searchAssetAllocationRequestByUserId(assetAllocatedToUser);
	}
	
	@PostMapping(value = "/searchAssetAllocationRequestByNameAndEmail")
	public List<AllocatedAssetDto> searchAssetAllocationRequestByNameAndEmail(@RequestBody SearchAssetAllocatedToUser assetAllocatedToUser){
		return adminService.searchAssetAllocationRequestByNameAndEmail(assetAllocatedToUser);
	}
	
	@PostMapping(value = "/approveAssetRequest/{adminId}/{requestId}")
	public String approveAssetRequest(@PathVariable int adminId,@PathVariable int requestId) {
		return adminService.approveAssetRequest(adminId, requestId);
	}
	
	@GetMapping(value = "/showAssetServiceRequest")
	public List<AssetServiceRequestDto> showAssetServiceRequest(){
		return adminService.showAssetServiceRequest();
	}
	
	@PostMapping(value = "/searchAssetServiceRequestByUserId")
	public List<AssetServiceRequestDto> searchAssetServiceRequestByUserId(@RequestBody SearchAssetAllocatedToUser assetAllocatedToUser){
		return adminService.searchAssetServiceRequestByUserId(assetAllocatedToUser);
	}
	
	@PostMapping(value = "/searchAssetServiceRequestByNameAndEmail")
	public List<AssetServiceRequestDto> searchAssetServiceRequestByNameAndEmail(@RequestBody SearchAssetAllocatedToUser assetAllocatedToUser){
		return adminService.searchAssetServiceRequestByNameAndEmail(assetAllocatedToUser);
	}
	
	@PostMapping(value = "/approveAssetServiceRequest/{adminId}/{serviceId}")
	public String approveAssetServiceRequest(@PathVariable int serviceId,@PathVariable int adminId) {
		return adminService.approveAssetServiceRequest(adminId,serviceId);
	}

	
	@GetMapping(value = "/showAssetReturnRequest")
	public List<AllocatedAssetDto> showAssetReturnRequest() {
		return adminService.showAssetReturnRequest();
	}
	
	@PostMapping(value = "/searchReturnRequestByUserId")
	public List<AllocatedAssetDto> searchReturnRequest(@RequestBody SearchAssetAllocatedToUser assetAllocatedToUser){
		return adminService.searchReturnRequestByUserId(assetAllocatedToUser);
	}
	
	@PostMapping(value = "/searchReturnRequestByNameAndEmail")
	public List<AllocatedAssetDto> searchReturnRequestByNameAndEmail(@RequestBody SearchAssetAllocatedToUser assetAllocatedToUser){
		return adminService.searchReturnRequestByNameAndEmail(assetAllocatedToUser);
	}
	
	@PostMapping(value = "/approveAssetReturnRequest/{requestId}")
	public String approveAssetReturnRequest(@PathVariable int requestId) {
		return adminService.approveAssetReturnRequest(requestId);
	}
	
	@PostMapping(value = "/sendAuditRequest/{requestId}")
	public String sendAuditRequest(@PathVariable int requestId) {
		return adminService.sendAuditRequest(requestId);
	}
	
	@GetMapping(value = "/showPendingAuditRequest")
	public List<AllocatedAssetDto> showPendingAuditRequest(){
		return adminService.showPendingAuditRequest();
	}
	
	@PostMapping(value = "/searchAuditRequestByUserId")
	public List<AllocatedAssetDto> searchAuditRequestByUserId(@RequestBody SearchAssetAllocatedToUser assetAllocatedToUser){
		return adminService.searchAuditRequestByUserId(assetAllocatedToUser);
	}
	
	@PostMapping(value = "/searchAuditRequestByNameAndEmail")
	public List<AllocatedAssetDto> searchAuditRequestByNameAndEmail(@RequestBody SearchAssetAllocatedToUser assetAllocatedToUser){
		return adminService.searchAuditRequestByNameAndEmail(assetAllocatedToUser);
	}
	
	@PostMapping(value = "/verifyAuditRequest/{requestId}")
	public String verifyAuditRequest(@PathVariable int requestId) {
		return adminService.verifyAuditRequest(requestId);
	}
	
	@PostMapping(value = "/resignProtocol/{userId}")
	public String resignProtocol(@PathVariable int userId) {
		return adminService.resignProtocol(userId);
	}
	
	@GetMapping(value = "/searchAssetAllocatedByAdmin/{adminId}")
	public List<AllocatedAssetDto> showAssetAllocatedByAdmin(@PathVariable int adminId){
		return adminService.showAssetAllocatedByAdmin(adminId);
	}
	
	
	@GetMapping(value = "/getAssetCategories")
	public List<String> getAssetCategories(){
		return adminService.getAssetCategories();
	}
	
	
}
