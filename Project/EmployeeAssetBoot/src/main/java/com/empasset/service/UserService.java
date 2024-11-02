package com.empasset.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.empasset.dto.AllocatedAssetDto;
import com.empasset.dto.AssetHistoryDto;
import com.empasset.dto.AssetServiceRequestDto;
import com.empasset.dto.LoginDto;
import com.empasset.dto.SearchAssetDto;
import com.empasset.dto.ServiceRequestDto;
import com.empasset.exception.InternalServerErrorException;
import com.empasset.exception.ResourceNotFoundException;
import com.empasset.jwt.JWTService;
import com.empasset.jwt.JwtFilter;
import com.empasset.model.Admin;
import com.empasset.model.Asset;
import com.empasset.model.AssetRequest;
import com.empasset.model.AssetRequest.RequestType;
import com.empasset.model.AssetRequest.Status;
import com.empasset.model.Login;
import com.empasset.model.Login.Role;
import com.empasset.model.User;
import com.empasset.model.Asset.AssetCategory;
import com.empasset.repo.AssetRequestRepo;
import com.empasset.repo.LoginRepo;
import com.empasset.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private LoginRepo loginRepo;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private AssetRequestRepo assetRequestRepo;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
	
	public String loginUser(@RequestBody LoginDto login) {
		Login credentials=loginRepo.findByEmail(login.getEmail());
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(login.getEmail(),credentials.getRole().toString());
		}
		else {
			return "Invalid Credentials";
		}
	}

	public String addUser(User user) {
		System.out.println("came to user");
		user.setPassword(encoder.encode(user.getPassword()));
		User userFound = repo.findById(user.getUserId()).orElse(null);
		if (userFound!=null) {
			throw new InternalServerErrorException("User Exists with Id " +user.getUserId());
		} else {
			System.out.println("Came to else");
			repo.save(user);
			User userCredentials = repo.findByEmail(user.getEmail());
			Login credential=new Login(userCredentials.getUserId(), userCredentials.getEmail(), userCredentials.getPassword(), Role.Employee); 
			System.out.println("came to login repo");
			loginRepo.save(credential);
			System.out.println("added to login table");
			return "User Added...";
		}
	
	}
	
	public String updateUser(User user) {
		repo.save(user);
		return "{\"res\":\" User Updated Successfully\"}";
	}
	
	public List<Asset> showAvailableAsset() {
		String status="Available";
		String cmd="select * from asset where status=?";
		List<Asset> list= jdbcTemplate.query(cmd, new Object[] {status},new RowMapper<Asset>() {

			@Override
			public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
				Asset asset=new Asset();
				asset.setAssetId(rs.getInt("asset_id"));
				asset.setAssetName(rs.getString("asset_name"));
				asset.setAssetCategory(AssetCategory.valueOf(rs.getString("asset_category")) );
				asset.setAssetModel(rs.getString("asset_model"));
				asset.setAssetDescription(rs.getString("asset_description"));
				asset.setAssetValue(rs.getDouble("asset_value"));
				asset.setManufacturingDate(rs.getDate("manufacturing_date"));
				asset.setExpiryDate(rs.getDate("expiry_date"));
				asset.setImageUrl(rs.getString("image_url"));
				asset.setStatus(com.empasset.model.Asset.Status.valueOf(rs.getString("status")));
				return asset;
			}});
		return list;
	}

//	public String loginUser(LoginDto loginDto) {
//		User user1=repo.findByEmail(loginDto.getEmail());
//		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
//		if(authentication.isAuthenticated()) {
//			return jwtService.generateToken(loginDto.getEmail(),user1.getRole().toString());
//		}
//		return "Invalid Credentials";
//		
//	}
	
	public String requestAsset(int userId, int assetId) {
		String cmd="insert into asset_request(user_id,asset_id) values (?,?)";
		jdbcTemplate.update(cmd,new Object[] {userId,assetId});
		return "Asset Request Sent";
	}
	
	public List<AllocatedAssetDto> myAssets(int userId){
		String status="Approved";
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aa.issued_date,a.image_url,a.asset_description from asset_request ar "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "join asset_allocation aa on aa.request_id=ar.request_id "
				+ "where ar.status=? and ar.request_type=? and u.user_id=?";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {status,"AssetRequest",userId}, new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setIssuedDate(rs.getDate("issued_date"));
				dto.setImageUrl(rs.getString("image_url"));
				dto.setAssetDescription(rs.getString("asset_description"));
				return dto;
			}});
		return list;
	}
	public String assetReturnRequest(int requestId) {
		String request_type="AssetRequest";
		String status="Approved";
		String cmd="select count(*) cnt from asset_request where request_id=? and request_type=? and status=?";
		List<Object> list=jdbcTemplate.query(cmd, new Object[] {requestId,request_type,status},new RowMapper<Object>() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Object ob=rs.getInt("cnt");
				return ob;
			}});
		int count=(Integer)list.get(0);
		
		
		String requestReturn="AssetReturn";
		String status1="Pending";
		if(count==1) {
			String cmd1="update asset_request set request_type=?,status=? where request_id=?";
			jdbcTemplate.update(cmd1,new Object[] {requestReturn,status1,requestId});
			return "Asset Return Request Sent";

		}
		
		return "Asset Return Request Not Sent";
	}
	public String cancelAssetRequest(int requestId) {
		String cmd="delete from asset_request where request_id=? and request_type=? and status=?";
		jdbcTemplate.update(cmd,new Object[] {requestId,"AssetRequest","Pending"});
		return "Asset Request Revoked";
		
	}
	public List<AllocatedAssetDto> assetRequestStatus(int userId) {
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,ar.request_type,ar.status,a.asset_description,a.image_url from asset_request ar "
				+ "join user u on ar.user_id=u.user_id  "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "where ar.request_type=? and u.user_id=? order by ar.status asc" ;
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {"AssetRequest",userId},new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setRequestType(rs.getString("request_type"));
				dto.setStatus(rs.getString("status"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setImageUrl(rs.getString("image_url"));
				return dto;
			}});
		return list;
	}
	
	public List<AllocatedAssetDto> noOfPendingAssetRequest(int userId) {
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,ar.request_type,ar.status from asset_request ar "
				+ "join user u on ar.user_id=u.user_id  "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "where ar.request_type=? and u.user_id=? and ar.status=?" ;
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {"AssetRequest",userId,"Pending"},new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setRequestType(rs.getString("request_type"));
				dto.setStatus(rs.getString("status"));
				return dto;
			}});
		return list;
	}
	
	public String assetServiceRequest(ServiceRequestDto serviceRequestDto) {
		String cmd="insert into asset_service(request_id,service_type,description) values (?,?,?)";
		jdbcTemplate.update(cmd,new Object[] {serviceRequestDto.getRequestId(),serviceRequestDto.getServiceType(),serviceRequestDto.getDescription()});
		return "Asset Service Request Sent";
	}

	public List<AssetHistoryDto> assetHistory(int userId) {
		String cmd="select a.asset_name,a.asset_category,aa.issued_date,aa.returned_date,ar.status,a.asset_description,a.image_url from asset a "
				+ "join asset_request ar on a.asset_id=ar.asset_id "
				+ "join user u on u.user_id=ar.user_id "
				+ "join asset_allocation aa on aa.request_id=ar.request_id "
				+ "where ar.request_type=? and u.user_id=? order by ar.status asc";
		List<AssetHistoryDto> list=jdbcTemplate.query(cmd, new Object[] {"AssetReturn",userId},new RowMapper<AssetHistoryDto>() {

			@Override
			public AssetHistoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AssetHistoryDto a=new AssetHistoryDto();
				a.setAssetName(rs.getString("asset_name"));
				a.setAssetCategory(rs.getString("asset_category"));
				a.setIssuedDate(rs.getDate("issued_date"));
				a.setReturnedDate(rs.getDate("returned_date"));
				a.setStatus(rs.getString("status"));
				a.setAssetDescription(rs.getString("asset_description"));
				a.setImageUrl(rs.getString("image_url"));
				return a;
			}});
			
		return list;
	}
	public User findUserByEmail(String email) {
		return repo.findByEmail(email);
	}
	public User findUserById(int id) {
		return repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Record Not Found with ID "+id));
	}

	public List<AllocatedAssetDto> showAuditRequest(int userId) {
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aal.issued_date,aau.audit_status,a.asset_description,a.image_url from asset_request ar "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "join asset_allocation aal on aal.request_id=ar.request_id "
				+ "join asset_audit aau on aau.request_id=ar.request_id "
				+ "where u.user_id=? order by aau.audit_status asc";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {userId}, new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setIssuedDate(rs.getDate("issued_date"));
				dto.setAuditStatus(rs.getString("audit_status"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setImageUrl(rs.getString("image_url"));
				return dto;
			}});
		return list;
	}
	
	public List<AllocatedAssetDto> noOfAuditRequest(int userId) {
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aal.issued_date,aau.audit_status from asset_request ar "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "join asset_allocation aal on aal.request_id=ar.request_id "
				+ "join asset_audit aau on aau.request_id=ar.request_id "
				+ "where u.user_id=? and aau.audit_status=?";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {userId,"Pending"}, new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setIssuedDate(rs.getDate("issued_date"));
				dto.setAuditStatus(rs.getString("audit_status"));
				return dto;
			}});
		return list;
	}
	
	public List<AssetServiceRequestDto> noOfAssetServiceRequest(int userId) {
		String cmd="select asr.service_id,u.user_id,u.first_name,a.asset_id,a.asset_name,asr.service_type,asr.description,asr.status from asset_service asr "
				+ "join asset_request ar on asr.request_id=ar.request_id "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "where asr.status=? and u.user_id=?";
		List<AssetServiceRequestDto> list=jdbcTemplate.query(cmd, new Object[] {"Pending",userId}, new RowMapper<AssetServiceRequestDto>() {

			@Override
			public AssetServiceRequestDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AssetServiceRequestDto dto=new AssetServiceRequestDto();
				dto.setServiceId(rs.getInt("service_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setServiceType(rs.getString("service_type"));
				dto.setDescription(rs.getString("description"));
				dto.setStatus(rs.getString("status"));
				return dto;
			}});
		return list;
	}

	public List<AssetServiceRequestDto> serviceRequestStatus(int userId) {
		String cmd="select asr.service_id,u.user_id,u.first_name,a.asset_id,a.asset_name,asr.service_type,asr.description,asr.status,a.asset_description,a.image_url from asset_service asr "
				+ "join asset_request ar on asr.request_id=ar.request_id "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "where u.user_id=?";
		List<AssetServiceRequestDto> list=jdbcTemplate.query(cmd, new Object[] {userId}, new RowMapper<AssetServiceRequestDto>() {

			@Override
			public AssetServiceRequestDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AssetServiceRequestDto dto=new AssetServiceRequestDto();
				dto.setServiceId(rs.getInt("service_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setServiceType(rs.getString("service_type"));
				dto.setDescription(rs.getString("description"));
				dto.setStatus(rs.getString("status"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setImageUrl(rs.getString("image_url"));
				return dto;
			}});
		return list;
	}

	public List<Asset> searchAssetByNameAndCategory(SearchAssetDto searchAssetDto) {
		String status="Available";
		String cmd="select * from asset where (asset_name like ?  and asset_category=?) and status=?";
		List<Asset> list= jdbcTemplate.query(cmd, new Object[] {"%"+searchAssetDto.getAssetName()+"%",searchAssetDto.getCategory() ,status},new RowMapper<Asset>() {

			@Override
			public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
				Asset asset=new Asset();
				asset.setAssetId(rs.getInt("asset_id"));
				asset.setAssetName(rs.getString("asset_name"));
				asset.setAssetCategory(AssetCategory.valueOf(rs.getString("asset_category")) );
				asset.setAssetModel(rs.getString("asset_model"));
				asset.setAssetDescription(rs.getString("asset_description"));
				asset.setAssetValue(rs.getDouble("asset_value"));
				asset.setImageUrl(rs.getString("image_url"));
				asset.setManufacturingDate(rs.getDate("manufacturing_date"));
				asset.setExpiryDate(rs.getDate("expiry_date"));
				asset.setStatus(com.empasset.model.Asset.Status.valueOf(rs.getString("status")));
				return asset;
			}});
		return list;
	}

	public List<Asset> searchAssetByNameOrCategory(SearchAssetDto searchAssetDto) {
		String status="Available";
		String cmd="select * from asset where (asset_name like ?  or asset_category=?) and status=?";
		List<Asset> list= jdbcTemplate.query(cmd, new Object[] {"%"+searchAssetDto.getAssetName()+"%",searchAssetDto.getCategory() ,status},new RowMapper<Asset>() {

			@Override
			public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
				Asset asset=new Asset();
				asset.setAssetId(rs.getInt("asset_id"));
				asset.setAssetName(rs.getString("asset_name"));
				asset.setAssetCategory(AssetCategory.valueOf(rs.getString("asset_category")) );
				asset.setAssetModel(rs.getString("asset_model"));
				asset.setAssetDescription(rs.getString("asset_description"));
				asset.setAssetValue(rs.getDouble("asset_value"));
				asset.setImageUrl(rs.getString("image_url"));
				asset.setManufacturingDate(rs.getDate("manufacturing_date"));
				asset.setExpiryDate(rs.getDate("expiry_date"));
				asset.setStatus(com.empasset.model.Asset.Status.valueOf(rs.getString("status")));
				return asset;
			}});
		return list;
	}






}
