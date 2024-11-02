package com.empasset.service;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.empasset.dto.AllocatedAssetDto;
import com.empasset.dto.AssetServiceRequestDto;
import com.empasset.dto.ForgotPasswordDto;
import com.empasset.dto.LoginDto;
import com.empasset.dto.SearchAssetAllocatedToUser;
import com.empasset.dto.SearchAssetDto;
import com.empasset.exception.InternalServerErrorException;
import com.empasset.exception.ResourceNotFoundException;
import com.empasset.jwt.JWTService;
import com.empasset.model.Admin;
import com.empasset.model.Asset;
import com.empasset.model.Asset.AssetCategory;
import com.empasset.model.Asset.Status;
import com.empasset.model.AssetAllocation;
import com.empasset.model.Login;
import com.empasset.model.User;
import com.empasset.model.Login.Role;
import com.empasset.repo.AdminRepo;
import com.empasset.repo.AssetAllocationRepo;
import com.empasset.repo.AssetRepo;
import com.empasset.repo.LoginRepo;
import com.empasset.repo.UserRepo;
import com.fasterxml.jackson.databind.util.EnumValues;

@Service
public class AdminService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AdminRepo repo;
	
	@Autowired 
	private AssetRepo assetRepo;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private LoginRepo loginRepo;
	
	@Autowired
	private AssetAllocationRepo allocationRepo;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
	
	
	@Autowired
	private UserRepo userRepo;
	
	public String loginAdmin(@RequestBody LoginDto login) {
		Login credentials=loginRepo.findByEmail(login.getEmail());
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(login.getEmail(),credentials.getRole().toString());
		}
		else {
			return "Invalid Credentials";
		}
	}

	public List<Admin> showAll(){
		return repo.findAll();
	}
	public Admin findAdminById(int id) {
		return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Cant Find Admin with ID "+id));
	}
	
	public User findUserById(int id) {
		return userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Record Not Found with ID "+id));
	}
	
	public String addAdmin(Admin admin) {
		System.out.println("Came to Admin");
		admin.setPassword(encoder.encode(admin.getPassword()));
		Admin adminFound = repo.findByEmail(admin.getEmail());
		if (adminFound!=null) {
			throw new InternalServerErrorException("Admin Exists with Id " +admin.getEmail());
		} else {
			System.out.println("Came to Else");
			repo.save(admin);
			System.out.println("Admin Added");
			Admin userCredentials = repo.findByEmail(admin.getEmail());
			Login credential=new Login(userCredentials.getAdminId(), userCredentials.getEmail(), userCredentials.getPassword(), Role.Admin); 
			loginRepo.save(credential);
			return "Admin Added...";
		}
	
	}
	
	public String addAsset(Asset asset) { 
		assetRepo.save(asset);
		return "Asset Added Successfully";
	}
	
	public List<User> showAllUser(){
		return userRepo.findAll();
	}
	
	public List<Asset> showAsset(){
		return assetRepo.findAll();
	}

	
	public List<AllocatedAssetDto> showAssetAllocationRequest() {
			String status="Pending";
			String requestType="AssetRequest";
			String cmd="select ar.request_id, u.user_id,u.first_name,a.asset_id,a.asset_name,a.asset_description,a.image_url from asset_request ar join user u on ar.user_id=u.user_id\r\n"
					+ "join asset a on ar.asset_id=a.asset_id where ar.status=? and ar.request_type=?";
			List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {status,requestType}, new RowMapper<AllocatedAssetDto>() {

				@Override
				public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					AllocatedAssetDto dto=new AllocatedAssetDto();
					dto.setRequestId(rs.getInt("request_id"));
					dto.setUserId(rs.getInt("user_id"));
					dto.setFirstName(rs.getString("first_name"));
					dto.setAssetId(rs.getInt("asset_id"));
					dto.setAssetName(rs.getString("asset_name"));
					dto.setAssetDescription(rs.getString("asset_description"));
					dto.setImageUrl(rs.getString("image_url"));
					return dto;
				}});
			return list;
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
				asset.setStatus(Status.valueOf(rs.getString("status")));
				return asset;
			}});
		return list;
	}
	
	public String approveAssetRequest(int adminId,int requestId) {
		Date sqDate=new Date(System.currentTimeMillis());
		
		String cmd="select asset_id from asset_request where request_id=?";
		List<Object> list=jdbcTemplate.query(cmd, new Object[] {requestId}, new RowMapper<Object>() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Object ob=rs.getInt("asset_id");
				return ob;
			}});
		int assetId=(Integer)list.get(0);
		
		List<Asset> list1=showAvailableAsset();
		boolean isAssetAvailable=false;
		for (Asset asset : list1) {
			if(asset.getAssetId()==assetId) {
				isAssetAvailable=true;

			}
		}
		if(isAssetAvailable) {
			String cm1="insert into asset_allocation(request_id,admin_id,issued_date) values (?,?,?)";
			jdbcTemplate.update(cm1,new Object[] {requestId,adminId,sqDate});
			String cm2="update asset_request set status=? where request_id=?";
			jdbcTemplate.update(cm2,new Object[] {"Approved",requestId});
			String cm3="update asset set status=? where asset_id=?";
			jdbcTemplate.update(cm3,new Object[] {"Issued",assetId});
			return "Asset has been Approved";
		}else {
			String cm4="update asset_request set status=? where request_id=?";
			jdbcTemplate.update(cm4,new Object[] {"Rejected",requestId});
			return "AssetNotAvailable";
			}
		}
	
	
	public List<AllocatedAssetDto> showAssetAllocatedByAdmin(int adminId){
		String cmd="select ar.request_id,aa.admin_id, u.user_id,u.first_name,a.asset_id,a.asset_name from asset_request ar join user u on ar.user_id=u.user_id\r\n"
				+ "join asset a on ar.asset_id=a.asset_id join asset_allocation aa on aa.request_id=ar.request_id where admin_id=?";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {adminId}, new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setAdminId(rs.getInt("admin_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				return dto;
			}});
		
		return list;
	}
	
	public List<AllocatedAssetDto> showAssetAllocatedToUser(){
		String status="Approved";
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aa.issued_date,a.asset_description,a.image_url from asset_request ar "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "join asset_allocation aa on aa.request_id=ar.request_id "
				+ "where ar.status=? and ar.request_type=?";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {status,"AssetRequest"}, new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setIssuedDate(rs.getDate("issued_date"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setImageUrl(rs.getString("image_url"));
				return dto;
			}});
		return list;
	}
	
	public List<AllocatedAssetDto> searchshowAssetAllocatedToUserById(SearchAssetAllocatedToUser assetAllocatedToUser){
		String status="Approved";
		int userId=Integer.parseInt(assetAllocatedToUser.getInputFromAngular());
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aa.issued_date,a.asset_description,a.image_url from asset_request ar "
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
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setImageUrl(rs.getString("image_url"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setIssuedDate(rs.getDate("issued_date"));
				return dto;
			}});
		return list;
	}
	
	public List<AllocatedAssetDto> searchshowAssetAllocatedToUserByNameAndEmail(SearchAssetAllocatedToUser assetAllocatedToUser){
		String status="Approved";
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aa.issued_date,a.asset_description,a.image_url from asset_request ar "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "join asset_allocation aa on aa.request_id=ar.request_id "
				+ "where (ar.status=? and ar.request_type=?) and (u.first_name like ? or u.email like ?)";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {status,"AssetRequest","%"+assetAllocatedToUser.getInputFromAngular()+"%","%"+assetAllocatedToUser.getInputFromAngular()+"%"}, new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setImageUrl(rs.getString("image_url"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setIssuedDate(rs.getDate("issued_date"));
				return dto;
			}});
		return list;
	}
	
	
	
	public AssetAllocation searchAssetAllocatedById(int id) {
		return allocationRepo.findById(id).get();
	}
	public List<AllocatedAssetDto> showAssetReturnRequest() {
		String status="Pending";
		String requestType="AssetReturn";
		String cmd="select ar.request_id, u.user_id,u.first_name,a.asset_id,a.asset_name,aa.issued_date,a.asset_description,a.image_url from asset_request ar join user u on ar.user_id=u.user_id\r\n"
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "join asset_allocation aa on aa.request_id=ar.request_id "
				+ "where ar.status=? and ar.request_type=?";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {status,requestType}, new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setIssuedDate(rs.getDate("issued_date"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setImageUrl(rs.getString("image_url"));
				return dto;
			}});
		return list;
	}
	public String approveAssetReturnRequest(int requestId) {
		Date returnedDate=new Date(System.currentTimeMillis());
		String cmd1="update asset_request set status=? where request_id=?";
		jdbcTemplate.update(cmd1,new Object[] {"Approved",requestId});
		String cmd2="update asset_allocation set returned_date=? where request_id=?";
		jdbcTemplate.update(cmd2,new Object[] {returnedDate,requestId});
		String cmd3="update asset set status=? where asset_id=(select asset_id from asset_request where request_id=?)";
		jdbcTemplate.update(cmd3,new Object[] {"Available",requestId});
		return "{\"res\":\"Asset Return Request Approved\"}";
	}
	public List<AssetServiceRequestDto> showAssetServiceRequest() {
		String cmd="select asr.service_id,u.user_id,u.first_name,a.asset_id,a.asset_name,asr.service_type,asr.description,asr.status,a.asset_description,a.image_url from asset_service asr\r\n"
				+ "join asset_request ar on asr.request_id=ar.request_id\r\n"
				+ "join user u on ar.user_id=u.user_id\r\n"
				+ "join asset a on ar.asset_id=a.asset_id\r\n"
				+ "where asr.status=?";
		List<AssetServiceRequestDto> list=jdbcTemplate.query(cmd, new Object[] {"Pending"}, new RowMapper<AssetServiceRequestDto>() {

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
	public String approveAssetServiceRequest(int adminId, int serviceId) {
		String cmd="update asset_service set admin_id=?,status=? where service_id=?";
		jdbcTemplate.update(cmd,new Object[] {adminId,"Serviced",serviceId});
		return "Service Request Approved";
	}
	
	public String resignProtocol(int userId) {
		String cmd="select count(*) cnt from asset_allocation aa \r\n"
				+ "join asset_request ar on aa.request_id=ar.request_id\r\n"
				+ "where ar.user_id=?";
		List<Object> list=jdbcTemplate.query(cmd, new Object[] {userId}, new RowMapper<Object>() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Object ob=rs.getInt("cnt");
				return ob;
			}});
		int noOfAllocatedAsset=(Integer)list.get(0); 
		
		String cmd1="select count(*) cnt from asset_request ar where ar.user_id=? and ar.request_type=? and status=?";
		List<Object> list1=jdbcTemplate.query(cmd1, new Object[] {userId,"AssetReturn","Approved"}, new RowMapper<Object>() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Object ob=rs.getInt("cnt");
				return ob;
			}});
		int noOfAssetReturned=(Integer)list1.get(0); 
		
		if (noOfAllocatedAsset==noOfAssetReturned) {
			String cmd2="update user set status=? where user_id=?";
			jdbcTemplate.update(cmd2,new Object[] {"Inactive",userId});
			return "Employee set to Inactive";
		}
		return "Employee Needs to Return the Allocated Asset";
	}
	
	public User findUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public List<String> getAssetCategories() {
		String cmd="SELECT COLUMN_TYPE AS enum_values \r\n"
				+ "FROM INFORMATION_SCHEMA.COLUMNS \r\n"
				+ "WHERE TABLE_SCHEMA = 'employeeasset' \r\n"
				+ "  AND TABLE_NAME = 'asset' \r\n"
				+ "  AND COLUMN_NAME = 'asset_category'";
		List<String> enumValues=new ArrayList<String>();
		
		List<String> enums=jdbcTemplate.query(cmd , new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String ob=rs.getString("enum_values");
				return ob;
			}});
		String convertToValues=enums.get(0);
		convertToValues=convertToValues.substring(5, convertToValues.length()-1);
		String[] values=convertToValues.split(",");
		for (int i = 0; i < values.length; i++) {
            values[i] = values[i].trim().replace("'", "");
            enumValues.add(values[i]);
        }
		
		return enumValues;
	}

	public String updateAsset(Asset asset) {
		assetRepo.save(asset);
		return "Asset Updated Successfully";
	}

	public String deleteAsset(int assetId) {
		
		String c1="select count(*) cnt from asset where asset_id=? and status=?";
		List<Integer> assetAvailable=jdbcTemplate.query(c1, new Object[] {assetId,"Available"},new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer requestId=rs.getInt("cnt");
				return requestId;
			}});
		int count= assetAvailable.get(0);
		if(count==1) {
			String cmd="select request_id from asset_request where asset_id=?";
			List<Integer> requestIdList=jdbcTemplate.query(cmd, new Object[] {assetId},new RowMapper<Integer>() {

				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					Integer requestId=rs.getInt("request_id");
					return requestId;
				}});
			for(int i=0;i<requestIdList.size();i++) {
				String cmd1="delete from asset_allocation where request_id=?";
				jdbcTemplate.update(cmd1,new Object[] {requestIdList.get(i)});
				String cmd2="delete from asset_service where request_id=?";
				jdbcTemplate.update(cmd2,new Object[] {requestIdList.get(i)});
				String cmd3="delete from asset_audit where request_id=?";
				jdbcTemplate.update(cmd3,new Object[] {requestIdList.get(i)});
				String cmd4="delete from asset_request where request_id=?";
				jdbcTemplate.update(cmd4,new Object[] {requestIdList.get(i)});
				System.out.println(requestIdList.get(i));
			}
			String assetDelete="delete from asset where asset_id=?";
			jdbcTemplate.update(assetDelete, new Object[] {assetId});
			
			System.out.println(requestIdList);
			
			return "Asset Deleted Successfully";
		}
		else {
			return "Asset is in Use Can't Delete";
		}
		
		
	}

	public String sendAuditRequest(int requestId) {
		String cm1="insert into asset_audit(request_id) values (?)";
		jdbcTemplate.update(cm1,new Object[] {requestId});
		return "Asset Audit Request Sent Successfully";
	}

	public List<AllocatedAssetDto> showPendingAuditRequest() {
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aal.issued_date,aau.audit_status,a.asset_description,a.image_url from asset_request ar "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "join asset_allocation aal on aal.request_id=ar.request_id "
				+ "join asset_audit aau on aau.request_id=ar.request_id "
				+ "where aau.audit_status=? ";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {"Pending"}, new RowMapper<AllocatedAssetDto>() {

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

	public String verifyAuditRequest(int requestId) {
		String cm1="update asset_audit set audit_status=? where request_id=?";
		jdbcTemplate.update(cm1,new Object[] {"Verified",requestId});
		return "Asset Verified Successfully";
	}

	public List<AllocatedAssetDto> searchAssetAllocationRequestByUserId(SearchAssetAllocatedToUser assetAllocatedToUser) {
		String status="Pending";
		int userId=Integer.parseInt(assetAllocatedToUser.getInputFromAngular());
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,a.image_url,a.asset_description from asset_request ar "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "where ar.status=? and ar.request_type=? and u.user_id=?";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {status,"AssetRequest",userId}, new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setImageUrl(rs.getString("image_url"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setAssetName(rs.getString("asset_name"));
				return dto;
			}});
		return list;
	}
	
	public List<AllocatedAssetDto> searchAssetAllocationRequestByNameAndEmail(SearchAssetAllocatedToUser assetAllocatedToUser) {
		String status="Pending";
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,a.image_url,a.asset_description from asset_request ar "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "where (ar.status=? and ar.request_type=?) and (u.first_name like ? or u.email like ?)";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {status,"AssetRequest","%"+assetAllocatedToUser.getInputFromAngular()+"%","%"+assetAllocatedToUser.getInputFromAngular()+"%"}, new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setImageUrl(rs.getString("image_url"));
				dto.setAssetName(rs.getString("asset_name"));
				return dto;
			}});
		return list;
		
	}

	public List<AssetServiceRequestDto> searchAssetServiceRequestByUserId(SearchAssetAllocatedToUser assetAllocatedToUser) {
		int userId=Integer.parseInt(assetAllocatedToUser.getInputFromAngular());
		String cmd="select asr.service_id,u.user_id,u.first_name,a.asset_id,a.asset_name,asr.service_type,asr.description,asr.status,a.image_url,a.asset_description from asset_service asr\r\n"
				+ "join asset_request ar on asr.request_id=ar.request_id\r\n"
				+ "join user u on ar.user_id=u.user_id\r\n"
				+ "join asset a on ar.asset_id=a.asset_id\r\n"
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
				dto.setImageUrl(rs.getString("image_url"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setServiceType(rs.getString("service_type"));
				dto.setDescription(rs.getString("description"));
				dto.setStatus(rs.getString("status"));
				return dto;
			}});
		return list;
	}

	public List<AssetServiceRequestDto> searchAssetServiceRequestByNameAndEmail(
			SearchAssetAllocatedToUser assetAllocatedToUser) {
		String cmd="select asr.service_id,u.user_id,u.first_name,a.asset_id,a.asset_name,asr.service_type,asr.description,asr.status,a.image_url,a.asset_description from asset_service asr\r\n"
				+ "join asset_request ar on asr.request_id=ar.request_id\r\n"
				+ "join user u on ar.user_id=u.user_id\r\n"
				+ "join asset a on ar.asset_id=a.asset_id\r\n"
				+ "where asr.status=? and (u.first_name like ? or u.email like ?)";
		List<AssetServiceRequestDto> list=jdbcTemplate.query(cmd, new Object[] {"Pending","%"+assetAllocatedToUser.getInputFromAngular()+"%","%"+assetAllocatedToUser.getInputFromAngular()+"%"}, new RowMapper<AssetServiceRequestDto>() {

			@Override
			public AssetServiceRequestDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AssetServiceRequestDto dto=new AssetServiceRequestDto();
				dto.setServiceId(rs.getInt("service_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setImageUrl(rs.getString("image_url"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setServiceType(rs.getString("service_type"));
				dto.setDescription(rs.getString("description"));
				dto.setStatus(rs.getString("status"));
				return dto;
			}});
		return list;
	}

	public List<AllocatedAssetDto> searchReturnRequestByUserId(SearchAssetAllocatedToUser assetAllocatedToUser) {
		String status="Pending";
		int userId=Integer.parseInt(assetAllocatedToUser.getInputFromAngular());
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aa.issued_date,a.image_url,a.asset_description from asset_request ar "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "join asset_allocation aa on aa.request_id=ar.request_id "
				+ "where ar.status=? and ar.request_type=? and u.user_id=?";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {status,"AssetReturn",userId}, new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setImageUrl(rs.getString("image_url"));
				dto.setIssuedDate(rs.getDate("issued_date"));
				return dto;
			}});
		return list;
	}

	public List<AllocatedAssetDto> searchReturnRequestByNameAndEmail(SearchAssetAllocatedToUser assetAllocatedToUser) {
		String status="Pending";
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aa.issued_date,a.image_url,a.asset_description from asset_request ar "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "join asset_allocation aa on aa.request_id=ar.request_id "
				+ "where (ar.status=? and ar.request_type=?) and (u.first_name like ? or u.email like ?)";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {status,"AssetReturn","%"+assetAllocatedToUser.getInputFromAngular()+"%","%"+assetAllocatedToUser.getInputFromAngular()+"%"}, new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setImageUrl(rs.getString("image_url"));
				dto.setIssuedDate(rs.getDate("issued_date"));
				return dto;
			}});
		return list;
	}

	public List<AllocatedAssetDto> searchAuditRequestByUserId(SearchAssetAllocatedToUser assetAllocatedToUser) {
		int userId=Integer.parseInt(assetAllocatedToUser.getInputFromAngular());
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aal.issued_date,aau.audit_status,a.image_url,a.asset_description from asset_request ar "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "join asset_allocation aal on aal.request_id=ar.request_id "
				+ "join asset_audit aau on aau.request_id=ar.request_id "
				+ "where aau.audit_status=? and u.user_id=?";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {"Pending",userId}, new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setIssuedDate(rs.getDate("issued_date"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setImageUrl(rs.getString("image_url"));
				dto.setAuditStatus(rs.getString("audit_status"));
				return dto;
			}});
		return list;
	}

	public List<AllocatedAssetDto> searchAuditRequestByNameAndEmail(SearchAssetAllocatedToUser assetAllocatedToUser) {
		String cmd="select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aal.issued_date,aau.audit_status,a.image_url,a.asset_description from asset_request ar "
				+ "join user u on ar.user_id=u.user_id "
				+ "join asset a on ar.asset_id=a.asset_id "
				+ "join asset_allocation aal on aal.request_id=ar.request_id "
				+ "join asset_audit aau on aau.request_id=ar.request_id "
				+ "where aau.audit_status=? and (u.first_name like ? or u.email like ?)";
		List<AllocatedAssetDto> list=jdbcTemplate.query(cmd, new Object[] {"Pending","%"+assetAllocatedToUser.getInputFromAngular()+"%","%"+assetAllocatedToUser.getInputFromAngular()+"%"}, new RowMapper<AllocatedAssetDto>() {

			@Override
			public AllocatedAssetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllocatedAssetDto dto=new AllocatedAssetDto();
				dto.setRequestId(rs.getInt("request_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setAssetId(rs.getInt("asset_id"));
				dto.setAssetName(rs.getString("asset_name"));
				dto.setIssuedDate(rs.getDate("issued_date"));
				dto.setAssetDescription(rs.getString("asset_description"));
				dto.setImageUrl(rs.getString("image_url"));
				dto.setAuditStatus(rs.getString("audit_status"));
				return dto;
			}});
		return list;
	}

	public List<Asset> searchAllAssetByNameOrCategory(SearchAssetDto searchAssetDto) {
		String cmd="select * from asset where (asset_name like ?  or asset_category=?)";
		List<Asset> list= jdbcTemplate.query(cmd, new Object[] {"%"+searchAssetDto.getAssetName()+"%",searchAssetDto.getCategory()},new RowMapper<Asset>() {

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

	public List<Asset> searchAllAssetByNameAndCategory(SearchAssetDto searchAssetDto) {
		String cmd="select * from asset where (asset_name like ?  and asset_category=?)";
		List<Asset> list= jdbcTemplate.query(cmd, new Object[] {"%"+searchAssetDto.getAssetName()+"%",searchAssetDto.getCategory() },new RowMapper<Asset>() {

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
				asset.setImageUrl(rs.getString("image_url"));
				asset.setExpiryDate(rs.getDate("expiry_date"));
				asset.setStatus(com.empasset.model.Asset.Status.valueOf(rs.getString("status")));
				return asset;
			}});
		return list;
	}

	public String updateAdmin(Admin admin) {
		repo.save(admin);
		return "{\"res\":\" User Updated Successfully\"}";
	}



	
}
