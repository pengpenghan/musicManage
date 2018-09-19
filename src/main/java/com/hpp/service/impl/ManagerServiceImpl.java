package com.hpp.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hpp.model.FileInfo;
import com.hpp.model.FileType;
import com.hpp.model.InfoLabel;
import com.hpp.model.InfoType;
import com.hpp.model.LabelList;
import com.hpp.model.SysUser;
import com.hpp.persistence.FileInfoMapper;
import com.hpp.persistence.FileTypeMapper;
import com.hpp.persistence.InfoLabelMapper;
import com.hpp.persistence.InfoTypeMapper;
import com.hpp.persistence.LabelListMapper;
import com.hpp.persistence.SysRoleMapper;
import com.hpp.persistence.SysUserMapper;
import com.hpp.service.IManagerService;
import com.hpp.utils.AESUtil;
import com.hpp.utils.ConfigUtil;
import com.hpp.utils.Constant;
import com.hpp.utils.DataTableModel;
import com.hpp.utils.DateUtils;
import com.hpp.utils.ResultCode;
import com.hpp.utils.UploadFileUtils;
import com.hpp.utils.UploadState;

@Service
public class ManagerServiceImpl implements IManagerService {

	@Autowired
	SysUserMapper userMapper;

	@Autowired
	SysRoleMapper roleMapper;
	
	@Autowired
	FileInfoMapper fileInfoMapper;
	
	@Autowired
	LabelListMapper labelListMapper;
	
	@Autowired
	FileTypeMapper fileTypeMapper;
	
	@Autowired
	InfoLabelMapper infoLabelMapper;
	
	@Autowired
	InfoTypeMapper infoTypeMapper;

	@Override
	public SysUser selectUserByName(String name) throws Exception {
		SysUser user = userMapper.selectUserByName(name);
		if (user != null && !StringUtils.isBlank(user.getPassword())) {
			user.setPassword(AESUtil.Decrypt(user.getPassword()));
		}
		return user;
	}

	@Override
	public boolean uploadFile(HttpServletRequest request, MultipartFile file, String fileName,Integer id) {
		//获取项目下webapp文件夹路径
		//String path = System.getProperty("evan.webapp");
		String path = ConfigUtil.get("rootPath") + "/" + DateUtils.formateDate(new Date(), DateUtils.YYYYMMDD);
		UploadState state = null;
		//文件添加日期标注
		/*int len = fileName.lastIndexOf(".");
		if (len != -1) {
			fileName = fileName.substring(0, len) + "_" + DateUtils.formateDate(new Date(), DateUtils.YYYYMMDD)
					+ fileName.substring(len);
		}*/
		try {
			state = UploadFileUtils.upload4Stream(fileName, path, file.getInputStream(),file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (state.getFlag() == UploadState.UPLOAD_SUCCSSS.getFlag()) {
			SysUser user = (SysUser)request.getSession().getAttribute(ResultCode.USER_LOGIN_INFO);
			String pathStr = path + "/" + fileName;
			//存储上传文件信息
			FileInfo info = new FileInfo();
			info.setId(id);
			info.setFileName(fileName);
			info.setFilePath(pathStr);
			info.setStatus((byte)Constant.Status.normal.getCode());
			info.setUserId(user.getId());
			info.setCtime(DateUtils.formateDate(new Date(), DateUtils.YMDHMS));
			fileInfoMapper.updateByPrimaryKeySelective(info);
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public boolean updateFile(HttpServletRequest request, MultipartFile file,
			String fileName, Integer id) {
		//获取项目下webapp文件夹路径
				//String path = System.getProperty("evan.webapp");
				String path = ConfigUtil.get("rootPath") + "/" + DateUtils.formateDate(new Date(), DateUtils.YYYYMMDD);
				UploadState state = null;
				//文件添加日期标注
				/*int len = fileName.lastIndexOf(".");
				if (len != -1) {
					fileName = fileName.substring(0, len) + "_" + DateUtils.formateDate(new Date(), DateUtils.YYYYMMDD)
							+ fileName.substring(len);
				}*/
				try {
					state = UploadFileUtils.upload4Stream(fileName, path, file.getInputStream(),file);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (state.getFlag() == UploadState.UPLOAD_SUCCSSS.getFlag()) {
					SysUser user = (SysUser)request.getSession().getAttribute(ResultCode.USER_LOGIN_INFO);
					String pathStr = path + "/" + fileName;
					//存储上传文件信息
					FileInfo info = fileInfoMapper.selectByPrimaryKey(id);
					info.setFileName(fileName);
					info.setFilePath(pathStr);
					info.setUserId(user.getId());
					info.setCtime(DateUtils.formateDate(new Date(), DateUtils.YMDHMS));
					fileInfoMapper.updateByPrimaryKeySelective(info);
					return true;
				}else {
					return false;
				}
	}

	@Override
	public DataTableModel<FileInfo> getListPageFileInfoByCondition(HashMap<String, Object> search) {
		List<FileInfo> list = fileInfoMapper.selectFileInfoList(search);
		int count = fileInfoMapper.selectFileInfoListCount(search);
		for(FileInfo fileInfo : list){
			if (StringUtils.isBlank(fileInfo.getLabels())) {
				fileInfo.setLabels("");
			}
			if(StringUtils.isBlank(fileInfo.getRemark())){
				fileInfo.setRemark("");
			}
			List<InfoLabel> labels = infoLabelMapper.getInfoLabelList(fileInfo.getId());
			String labelIds = "";
			for(InfoLabel str: labels){
				labelIds = labelIds + "," +  str.getLabelListId();
			}
			fileInfo.setLabelIds(labelIds);
		}
		return new DataTableModel<>(list,count,count);
	}

	@Override
	public boolean deleteFileInfo(HashMap<String, Object> search) {
		boolean flag = false;
		//获取文件信息
		FileInfo fileInfo = fileInfoMapper.selectFileInfo(search);
		if (fileInfo != null) {
			//设置文件状态 采用逻辑删除
			fileInfo.setStatus((byte)Constant.Status.delete.getCode());
			//更新文件
			fileInfoMapper.updateByPrimaryKeySelective(fileInfo);
			flag = true;
		}else {
			flag = false;
		}
		return flag;
	}

	@Override
	public FileInfo getFileInfo(HashMap<String, Object> search) {
		FileInfo fileInfo = fileInfoMapper.selectFileInfo(search);
		if(StringUtils.isBlank(fileInfo.getLabels())){
			fileInfo.setLabels("");
		}
		if (StringUtils.isBlank(fileInfo.getRemark())) {
			fileInfo.setRemark("");
		}
		List<InfoLabel> labels = infoLabelMapper.getInfoLabelList(fileInfo.getId());
		String labelIds = "";
		for(InfoLabel str: labels){
			labelIds = labelIds + "," +  str.getLabelListId();
		}
		fileInfo.setLabelIds(labelIds);
		return fileInfo;
	}

	@Override
	public List<LabelList> selectLabelList(Map<String, Object> map) {
		return labelListMapper.selectLabelList(map);
	}

	@Override
	public List<FileType> selectFileTypeList(Map<String, Object> map) {
		return fileTypeMapper.selectFileTypeList(map);
	}

	@Override
	public int addLabel(LabelList record) {
		return labelListMapper.insertSelective(record);
	}

	@Override
	public int addFileType(FileType record) {
		return fileTypeMapper.insertSelective(record);
	}

	@Override
	public LabelList getLabelInfo(Integer id) {
		return labelListMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteLabelById(LabelList record) {
		return labelListMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public FileType getFileTypeById(Integer id) {
		return fileTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteFileType(FileType record) {
		return fileTypeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteLabelRelation(Integer fileId) {
		return infoLabelMapper.deleteInfoLabelByFileId(fileId);
	}

	@Override
	public int addLabelRelation(InfoLabel record) {
		return infoLabelMapper.insertSelective(record);
	}

	@Override
	public int deleteTypeRelation(Integer fileId) {
		return infoTypeMapper.deleteInfoTypeByFileId(fileId);
	}

	@Override
	public int addTypeRelation(InfoType record) {
		return infoTypeMapper.insertSelective(record);
	}

	@Override
	public List<InfoLabel> getInfoLabelList(Integer fileId) {
		return infoLabelMapper.getInfoLabelList(fileId);
	}

	@Override
	public List<InfoType> getInfoTypeList(Integer fileId) {
		return infoTypeMapper.getInfoTypeList(fileId);
	}

	@Override
	public List<FileInfo> getSearchList(Map<String, Object> map) {
		List<FileInfo> list = fileInfoMapper.selectFileInfoList(map);
		for(FileInfo fl : list){
			if (StringUtils.isBlank(fl.getLabels())) {
				fl.setLabels("");
			}
			if(StringUtils.isBlank(fl.getRemark())){
				fl.setRemark("");
			}
		}
		return list;
	}

	@Override
	public int updateFileInfoById(FileInfo record) {
		return fileInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public SysUser selectUserById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysUser record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int addFileInfo(FileInfo record) {
		int i = fileInfoMapper.insertSelective(record);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", record.getUserId());
		map.put("status", Constant.Status.normal.getCode());
		List<FileInfo> fileInfos = fileInfoMapper.selectFileInfoList(map);
		int count = fileInfos.size();
		SysUser user = userMapper.selectByPrimaryKey(record.getUserId());
		user.setCount(count);
		userMapper.updateByPrimaryKeySelective(user);
		return i;
	}

	@Override
	public int addInfoLabel(InfoLabel record) {
		return infoLabelMapper.insertSelective(record);
	}

	@Override
	public List<String> getTextBookType(Map<String, Object> map) {
		return fileInfoMapper.getTextBookType(map);
	}

	@Override
	public List<String> getPressVersionType(Map<String, Object> map) {
		return fileInfoMapper.getPressVersionType(map);
	}

}
