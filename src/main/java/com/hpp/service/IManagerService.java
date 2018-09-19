package com.hpp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.hpp.model.FileInfo;
import com.hpp.model.FileType;
import com.hpp.model.InfoLabel;
import com.hpp.model.InfoType;
import com.hpp.model.LabelList;
import com.hpp.model.SysUser;
import com.hpp.utils.DataTableModel;

public interface IManagerService {
	
	/**
	 * 通过用户名获取用户信息
	 * @Description 
	 * @author 韩鹏鹏
	 * @param name
	 * @return
	 * @throws Exception
	 */
	SysUser selectUserByName(String name) throws Exception;
	
	/**
	 * 上传文件
	 * @Description 
	 * @author 韩鹏鹏
	 * @param request
	 * @param file
	 * @param fileName
	 * @return
	 */
	boolean uploadFile(HttpServletRequest request,MultipartFile file,String fileName,Integer id);
	
	/**
	 * @Title updateFile  
	 * @Description 更新上传文件
	 * @author 韩鹏鹏
	 * @param request
	 * @param file
	 * @param fileName
	 * @param id
	 * @return boolean
	 * @date 2018年4月2日 下午6:21:35  
	 * @throws
	 */
	boolean updateFile(HttpServletRequest request,MultipartFile file,String fileName,Integer id);
	
	/**
	 * 查询上传文件列表
	 * @Description 
	 * @author 韩鹏鹏
	 * @param search
	 * @return
	 */
	DataTableModel<FileInfo> getListPageFileInfoByCondition(HashMap<String, Object> search);
	
	/**
	 * 删除当个文件
	 * @Description 
	 * @author 韩鹏鹏
	 * @param search
	 * @return
	 */
	boolean deleteFileInfo(HashMap<String, Object> search);
	
	/**
	 * 获取文件对象
	 * @Description 
	 * @author 韩鹏鹏
	 * @param search
	 * @return
	 */
	FileInfo getFileInfo(HashMap<String, Object> search);
	
	int updateFileInfoById(FileInfo record);
	
	/**
	 * @Title: addFileInfo  
	 * @Description:添加文件
	 * @author:韩鹏鹏
	 * @param record
	 * @return int
	 * @date 2018年4月2日 上午12:11:18  
	 * @throws
	 */
	int addFileInfo(FileInfo record);
	
	List<LabelList> selectLabelList(Map<String, Object> map);
	
	int addLabel(LabelList record);
	
	LabelList getLabelInfo(Integer id);
	
	int deleteLabelById(LabelList record);
	
	List<FileType> selectFileTypeList(Map<String, Object> map);
	
	int addFileType(FileType record);
	
	FileType getFileTypeById(Integer id);
	
	int deleteFileType(FileType record);
	
	//删除关联标签
	//先查询
	List<InfoLabel> getInfoLabelList(Integer fileId);
	
	int deleteLabelRelation(Integer fileId);
	//添加关联
	int addLabelRelation(InfoLabel record);

	//删除文件类型
	//先查询
	List<InfoType> getInfoTypeList(Integer fileId);
	int deleteTypeRelation(Integer fileId);
	//添加关联类型
	int addTypeRelation(InfoType record);
	
	//管理员搜索框
	List<FileInfo> getSearchList(Map<String, Object> map);
	
	SysUser selectUserById(Integer id);

    int updateByPrimaryKeySelective(SysUser record);
    
    /**
     * @Title insertSelective  
     * @Description 添加标签列表关联
     * @author 韩鹏鹏
     * @param record
     * @return int
     * @date 2018年4月2日 下午1:55:47  
     * @throws
     */
    int addInfoLabel(InfoLabel record);
    
    /**
     * @Title getTextBookType  
     * @Description 获取所属教材名列表
     * @author 韩鹏鹏
     * @return List<String>
     * @date 2018年4月2日 下午5:42:29  
     * @throws
     */
    List<String> getTextBookType(Map<String, Object> map);
    
    /**
     * @Title getPressVersionType  
     * @Description 获取出版社及版本
     * @author 韩鹏鹏
     * @return List<String>
     * @date 2018年4月2日 下午5:42:50  
     * @throws
     */
    List<String> getPressVersionType(Map<String, Object> map);
}
