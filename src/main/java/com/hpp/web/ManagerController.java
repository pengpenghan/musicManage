package com.hpp.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.jpush.api.report.UsersResult.User;

import com.hpp.model.FileInfo;
import com.hpp.model.FileType;
import com.hpp.model.InfoLabel;
import com.hpp.model.InfoType;
import com.hpp.model.LabelList;
import com.hpp.model.SysUser;
import com.hpp.service.IManagerService;
import com.hpp.service.IUserService;
import com.hpp.utils.Constant;
import com.hpp.utils.DataTableModel;
import com.hpp.utils.DateUtils;
import com.hpp.utils.MyLogger;
import com.hpp.utils.RespJson;
import com.hpp.utils.ResultCode;
import com.hpp.utils.ZipUtil;
import com.sun.istack.internal.FinalArrayList;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping(value = "/manager")
public class ManagerController {

	private static final MyLogger LOGGER = new MyLogger(ManagerController.class);

	@Autowired
	IManagerService managerService;

	@Autowired
	IUserService userService;

	@ResponseBody
	@RequestMapping(value = "/login")
	public RespJson login(HttpSession session, HttpServletRequest request, String username, String password,
			Integer userRole, Model model) {
		try {
			if (StringUtils.isBlank(username) && StringUtils.isBlank(password)) {
				LOGGER.debug("login false.......登录失败，用户名不存在");
				return new RespJson(false, "", ResultCode.USER_NOT_EXIST, "");
			}
			return logining(session, request, username, password, userRole);
		} catch (Exception e) {
			LOGGER.error("未知错误", e);
			return new RespJson(false, "", ResultCode.ERROR, "");
		}
	}

	public RespJson logining(HttpSession session, HttpServletRequest request, String name, String password,
			Integer userRole) {
		try {
			SysUser user = managerService.selectUserByName(name);
			if (null == user) {
				LOGGER.error("login fail......登录失败，用户不存在");
				return new RespJson(false, "用户不存在", ResultCode.USER_NOT_EXIST, "");
			}
			if (user.getPassword().equals(password)) {
				if (user.getStatus() == 0) {
					LOGGER.error("login fail......登录失败，用户已被删除");
					return new RespJson(false, "用户已被删除", ResultCode.USER_ALREADY_DEL, "");
				} else if (user.getStatus() == 2) {
					LOGGER.error("login fail......登录失败，用户已被冻结");
					return new RespJson(false, "用户已被冻结", ResultCode.USER_ALREADY_DEL, "");
				} else if (user.getStatus() == 1) {
					if (user.getRoleId() == userRole) {
						request.getSession().setAttribute(ResultCode.USER_LOGIN_INFO, user);
						request.getSession().setAttribute("loginTime", System.currentTimeMillis());
						LOGGER.debug("login successful.......");
						String url = "";
						if (userRole == 0) {// 管理员
							url = "/manager/manageIndex";
						} else {
							url = "/manager/employeeInterface";
						}

						return new RespJson(true, "登录成功", ResultCode.SUCCESS, url);
					} else {
						LOGGER.debug("login failed........登录失败，用户无此权限");
						return new RespJson(false, "登录失败，用户无权限", ResultCode.USER_NOT_EXIST, null);
					}
				} else {
					LOGGER.debug("login failed........登录失败，用户停用或锁定");
					return new RespJson(false, "登录失败，用户停用或锁定", ResultCode.USER_NOT_EXIST, null);
				}
			} else {
				LOGGER.debug("login failed........登录失败，密码错误");
				return new RespJson(false, "密码错误", ResultCode.USER_PWD_ERROR, null);
			}
		} catch (Exception e) {
			LOGGER.debug("login failed........登录失败，未知错误");
			return new RespJson(false, "未知错误，请通知管理员", ResultCode.USER_PWD_ERROR, null);
		}
	}
	
	/**
	 * @Title addFileInfo  
	 * @Description 添加文件
	 * @author 韩鹏鹏
	 * @param request
	 * @param fileName
	 * @param textBook
	 * @param pressVersion
	 * @param pageNum
	 * @param labels
	 * @param otherInfo
	 * @param labelIds
	 * @param musicName
	 * @param remark
	 * @return RespJson
	 * @date 2018年4月2日 下午3:12:43  
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/fileInfo/add")
	public RespJson addFileInfo(final HttpServletRequest request,
			String fileName,final String textBook,final String pressVersion,
			final Integer pageNum,final String labels,final String otherInfo,
			final String labelIds,final String musicName,final String remark) {
		SysUser user = (SysUser) request.getSession().getAttribute(ResultCode.USER_LOGIN_INFO);
		FileInfo info = new FileInfo();
		info.setUserId(user.getId());
		info.setMusicName(musicName);
		info.setFileName(fileName);
		info.setTextBook(textBook);
		info.setPressVersion(pressVersion);
		if (StringUtils.isNotBlank(otherInfo)) {
			info.setOtherInfo(otherInfo);
		}
		info.setPageNum(pageNum);
		info.setStatus((byte)Constant.Status.normal.getCode());
		info.setLabels(labels);
		info.setCtime(DateUtils.formateDate(new Date(), DateUtils.YMDHMS));
		int i = managerService.addFileInfo(info);
		String[] labelId = labelIds.split(",");
		for (String lb : labelId) {
			InfoLabel label = new InfoLabel();
			label.setFileId(info.getId());
			label.setLabelListId(Integer.parseInt(lb));
			managerService.addInfoLabel(label);
		}
		if (i > 0) {
			return new RespJson(true, "添加成功", ResultCode.SUCCESS, info.getId());
		} else {
			return new RespJson(false, "添加失败", ResultCode.ERROR, "");
		}
	}
	
	/**
	 * @Title updateFileInfo  
	 * @Description 管理员修改曲目信息
	 * @author 韩鹏鹏
	 * @param request
	 * @param fileName 附件名称
	 * @param textBook 所属教材名
	 * @param pressVersion 出版社及版本
	 * @param pageNum 页码
	 * @param remark 标签是否有误备注
	 * @param labels 标签列表
	 * @param labelIds 标签id
	 * @param musicName 曲目名称
	 * @return Callable<Object>
	 * @date 2018年3月29日 下午3:27:50  
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/fileInfo/update")
	public RespJson updateFileInfo(final HttpServletRequest request,
			Integer id,final String textBook,final String pressVersion,
			final Integer pageNum,final String labels,final String otherInfo,
			final String labelIds,final String musicName,final String remark) {
		SysUser user = (SysUser) request.getSession().getAttribute(ResultCode.USER_LOGIN_INFO);
		HashMap<String, Object> search = new HashMap<String, Object>();
		search.put("id", id);
		search.put("userId", user.getId());
		FileInfo info = managerService.getFileInfo(search);
		info.setMusicName(musicName);
		info.setTextBook(textBook);
		info.setPressVersion(pressVersion);
		if (StringUtils.isNotBlank(otherInfo)) {
			info.setOtherInfo(otherInfo);
		}
		info.setPageNum(pageNum);
		info.setLabels(labels);
		info.setCtime(DateUtils.formateDate(new Date(), DateUtils.YMDHMS));
		int i = managerService.updateFileInfoById(info);
		String[] labelId = labelIds.split(",");
		managerService.deleteLabelRelation(id);
		for (String lb : labelId) {
			if (StringUtils.isNotBlank(lb)) {
				InfoLabel label = new InfoLabel();
				label.setFileId(info.getId());
				label.setLabelListId(Integer.parseInt(lb));
				managerService.addInfoLabel(label);
			}
		}
		if (i > 0) {
			return new RespJson(true, "添加成功", ResultCode.SUCCESS, info.getId());
		} else {
			return new RespJson(false, "添加失败", ResultCode.ERROR, "");
		}
	}

	/**
	 * 一般@RequestMapping会将返回值解析为跳转路径，而@ResponseBody将返回值直接写入HTTP response
	 * body中，不会被解析为路径
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 */
	@RequestMapping(value = "/uploadFile")
	public @ResponseBody Callable<Object> uploadFile(final HttpServletRequest request,
			final Integer id) {
		return new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				boolean result = false;
				// 创建一个通用的多部分解析器
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
						request.getSession().getServletContext());

				// 判断 request 是否有文件上传,即多部分请求
				if (multipartResolver.isMultipart(request)) {
					MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
					MultipartFile file = multipartRequest.getFile("file");
					String fileName = file.getOriginalFilename();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("fileName", fileName);
					if (file != null) {
						result = managerService.uploadFile(request, file, fileName,id);
					}
				}
				if (result) {
					return new RespJson(true, "上传成功", ResultCode.SUCCESS, "");
				} else {
					return new RespJson(false, "上传失败", ResultCode.ERROR, "");
				}
			}
		};
	}
	
	/**
	 * 一般@RequestMapping会将返回值解析为跳转路径，而@ResponseBody将返回值直接写入HTTP response
	 * body中，不会被解析为路径
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 */
	@RequestMapping(value = "/updateFile")
	public @ResponseBody Callable<Object> updateFile(final HttpServletRequest request,
			final Integer id) {
		return new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				boolean result = false;
				// 创建一个通用的多部分解析器
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
						request.getSession().getServletContext());

				// 判断 request 是否有文件上传,即多部分请求
				if (multipartResolver.isMultipart(request)) {
					MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
					MultipartFile file = multipartRequest.getFile("file");
					String fileName = file.getOriginalFilename();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("fileName", fileName);
					if (file != null) {
						result = managerService.updateFile(request, file, fileName,id);
					}
				}
				if (result) {
					return new RespJson(true, "上传成功", ResultCode.SUCCESS, "");
				} else {
					return new RespJson(false, "上传失败", ResultCode.ERROR, "");
				}
			}
		};
	}

	/**
	 * 获取上传文件列表
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @param session
	 * @param model
	 * @param draw
	 * @param start
	 * @param length
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fileInfo/list")
	public DataTableModel<?> getFileInfoList(HttpSession session, Model model, int draw, int start, int length,
			HttpServletRequest request, String param, Integer userId, Integer type) {
		// type 0无标记 1有标记
		HashMap<String, Object> search = new HashMap<String, Object>();
		String[] paramList = new String[] {};
		if (!StringUtils.isBlank(param)) {
			paramList = param.split(" ");
		}
		if (!StringUtils.isBlank(userId + "")) {
			search.put("userId", userId);
		}
		search.put("type", type);
		search.put("draw", draw);
		search.put("pageNo", start == 0 ? 0 : start / length + 1);
		search.put("pageSize", length);
		search.put("status", Constant.Status.normal.getCode());
		search.put("paramList", paramList);
		DataTableModel<FileInfo> result = managerService.getListPageFileInfoByCondition(search);
		result.setDraw(draw);
		return result;
	}

	/**
	 * 删除上传文件
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @param id
	 * @param fileName
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fileInfo/delete")
	public RespJson deleteFileInfo(String id, String fileName, HttpServletRequest request) {
		HashMap<String, Object> search = new HashMap<String, Object>();
		search.put("id", id);
		boolean result = managerService.deleteFileInfo(search);
		if (result) {
			return new RespJson(true, "删除成功", ResultCode.SUCCESS, "");
		} else {
			return new RespJson(false, "删除失败", ResultCode.ERROR, "");
		}
	}

	/**
	 * 获取单个文件信息
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @param id
	 * @param fileName
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fileInfo/info")
	public RespJson getFileInfo(Integer id) {
		HashMap<String, Object> search = new HashMap<String, Object>();
		search.put("id", id);
		FileInfo result = managerService.getFileInfo(search);
		if (result != null) {
			return new RespJson(true, "获取成功", ResultCode.SUCCESS, result);
		} else {
			return new RespJson(false, "获取失败", ResultCode.ERROR, "");
		}
	}

	/**
	 * @Description 添加标签备注
	 * @author 韩鹏鹏
	 * @param id
	 * @param fileName
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/remark/update")
	public RespJson updateFileInfo(String id, String remark) {
		HashMap<String, Object> search = new HashMap<String, Object>();
		search.put("id", id);
		FileInfo result = managerService.getFileInfo(search);
		result.setRemark(remark);
		int num = managerService.updateFileInfoById(result);
		if (num != 0) {
			return new RespJson(true, "添加成功", ResultCode.SUCCESS, result);
		} else {
			return new RespJson(false, "添加失败", ResultCode.ERROR, "");
		}
	}
	
	/**
	 * 下载文件
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @param request
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/download")
	public void download(HttpServletRequest request, Long id, String fileName, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/x-msdownload");
		OutputStream out = response.getOutputStream();
		HashMap<String, Object> search = new HashMap<String, Object>();
		search.put("id", id);
		search.put("fileName", fileName);
		FileInfo info = managerService.getFileInfo(search);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-disposition",
				"attachment; filename=" + new String(info.getFileName().getBytes("GBK"), "ISO8859-1"));
		File file = new File(info.getFilePath());

		FileInputStream inputStream = new FileInputStream(file);
		int b = 0;
		byte[] buffer = new byte[1024];
		while (b != -1) {
			b = inputStream.read(buffer);
			out.write(buffer, 0, b);
		}
		inputStream.close();
		out.close();
		out.flush();
	}

	/**
	 * 下载文件
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @param request
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "downloadZip")
	public void downloadZip(HttpServletRequest request, String fileIds, HttpServletResponse response) throws Exception {
		String[] fileIdArr = fileIds.split(",");
		List<File> files = new ArrayList<File>();
		for (int i = 0; i < fileIdArr.length; i++) {
			response.setContentType("application/x-msdownload");
			HashMap<String, Object> search = new HashMap<String, Object>();
			search.put("id", fileIdArr[i]);
			FileInfo info = managerService.getFileInfo(search);
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(info.getFileName().getBytes("GBK"), "ISO8859-1"));
			File file = new File(info.getFilePath());
			files.add(file);
		}
		String fileName = UUID.randomUUID().toString() + ".zip";
		// 在服务器端创建打包下载的临时文件
		String outFilePath = request.getSession().getServletContext().getRealPath("/") + "/upload/";
		ZipUtil.mkDir(outFilePath);
		File fileZip = new File(outFilePath + fileName);

		// 文件输出流
		FileOutputStream outStream = new FileOutputStream(fileZip);
		// 压缩流
		ZipOutputStream toClient = new ZipOutputStream(outStream);
		// toClient.setEncoding("gbk");
		ZipUtil.zipFile(files, toClient);
		toClient.close();
		outStream.close();
		ZipUtil.downloadFile(fileZip, response, true);
	}

	@RequestMapping(value = "/manageIndex")
	public String manageIndex(HttpSession session, Model model) {
		SysUser user = (SysUser) session.getAttribute(ResultCode.USER_LOGIN_INFO);
		model.addAttribute("userName", user.getName());
		return "manageIndex";
	}

	@RequestMapping(value = "/employeeInterface")
	public String employeeInterface(HttpSession session, Model model) {
		SysUser user = (SysUser) session.getAttribute(ResultCode.USER_LOGIN_INFO);
		HashMap<String, Object> search = new HashMap<String, Object>();
		search.put("userId", user.getId());
		List<String> textBookTypes = managerService.getTextBookType(search);
		List<String> pressVersons = managerService.getPressVersionType(search);
		model.addAttribute("textBookTypes", textBookTypes);
		model.addAttribute("pressVersons", pressVersons);
		model.addAttribute("userName", user.getName());
		model.addAttribute("userId", user.getId());
		return "employeeInterface";
	}

	@RequestMapping(value = "/uploadPage")
	public String uploadPage(HttpSession session, Model model) {
		return "uploadPage";
	}

	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		session.removeAttribute(ResultCode.ADMIN_LOGIN_INFO);
		try {
			String gourl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/index";
			System.out.println(gourl);
			response.sendRedirect(gourl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/manageStaff")
	public String manageStaff(HttpSession session, Model model) {
		SysUser user = (SysUser) session.getAttribute(ResultCode.USER_LOGIN_INFO);
		model.addAttribute("userName", user.getName());
		return "manageStaff";
	}

	@RequestMapping(value = "/manageWorkDetail")
	public String manageWorkDetail(HttpSession session, Model model, Integer id) {
		SysUser userLogin = (SysUser) session.getAttribute(ResultCode.USER_LOGIN_INFO);
		SysUser user = userService.selectByPrimaryKey(id);
		model.addAttribute("userNameLogin", userLogin.getName());
		model.addAttribute("userName", user.getName());
		model.addAttribute("userId", user.getId());
		return "manageWorkDetail";
	}

	@RequestMapping(value = "/manageFileSearch")
	public String manageFileSearch(HttpSession session, Model model) {
		SysUser user = (SysUser) session.getAttribute(ResultCode.USER_LOGIN_INFO);
		model.addAttribute("userName", user.getName());
		return "manageFileSearch";
	}

	/**
	 * 获取标签列表
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/label/list")
	public RespJson getLabelList() {
		HashMap<String, Object> search = new HashMap<String, Object>();
		search.put("status", Constant.Status.normal.getCode());
		List<LabelList> list = managerService.selectLabelList(search);
		return new RespJson(true, "获取成功", ResultCode.SUCCESS, list);
	}

	/**
	 * 添加标签
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @param labelName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/label/add")
	public RespJson addLabelList(String labelName) {
		if (!StringUtils.isBlank(labelName)) {
			LabelList record = new LabelList();
			record.setLabelName(labelName);
			record.setCtime(DateUtils.formateDate(new Date(), DateUtils.YMDHMS));
			record.setStatus((byte) Constant.Status.normal.getCode());
			managerService.addLabel(record);
			return new RespJson(true, "添加成功", ResultCode.SUCCESS, "");
		} else {
			return new RespJson(true, "添加失败", ResultCode.ERROR, "");
		}
	}

	/**
	 * 删除标签
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/label/delete")
	public RespJson deleteLabelList(Integer id) {
		LabelList record = managerService.getLabelInfo(id);
		if (record != null) {
			record.setStatus((byte) Constant.Status.delete.getCode());
			managerService.deleteLabelById(record);
			return new RespJson(true, "删除成功", ResultCode.SUCCESS, "");
		} else {
			return new RespJson(true, "删除失败", ResultCode.ERROR, "");
		}
	}

	/**
	 * 获取文件类型
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fileType/list")
	public RespJson getFileTypeList() {
		HashMap<String, Object> search = new HashMap<String, Object>();
		search.put("status", Constant.Status.normal.getCode());
		List<FileType> list = managerService.selectFileTypeList(search);
		return new RespJson(true, "获取成功", ResultCode.SUCCESS, list);
	}

	/**
	 * 添加文件类型
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @param typeName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fileType/add")
	public RespJson addFileType(String typeName) {
		if (!StringUtils.isBlank(typeName)) {
			FileType record = new FileType();
			record.setTypeName(typeName);
			record.setCtime(DateUtils.formateDate(new Date(), DateUtils.YMDHMS));
			record.setStatus((byte) Constant.Status.normal.getCode());
			managerService.addFileType(record);
			return new RespJson(true, "添加成功", ResultCode.SUCCESS, "");
		} else {
			return new RespJson(true, "添加失败", ResultCode.ERROR, "");
		}
	}

	/**
	 * 删除标签
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fileType/delete")
	public RespJson deleteFileType(Integer id) {
		FileType record = managerService.getFileTypeById(id);
		if (record != null) {
			record.setStatus((byte) Constant.Status.delete.getCode());
			managerService.deleteFileType(record);
			return new RespJson(true, "删除成功", ResultCode.SUCCESS, "");
		} else {
			return new RespJson(true, "删除失败", ResultCode.ERROR, "");
		}
	}

	/**
	 * 给文件添加标签
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @param id
	 * @param labelIds
	 * @param fileTypes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fileInfo/labelType")
	public RespJson addFileType(Integer id, String labelIds, String fileTypes, String labelType, HttpSession session) {

		String[] labelId = new String[] {};
		if (!StringUtils.isBlank(labelIds)) {
			labelId = labelIds.split(",");
		}

		boolean flag_label = false;
		boolean flag_type = false;
		List<InfoLabel> listLabel = managerService.getInfoLabelList(id);
		if (listLabel.size() != 0) {
			managerService.deleteLabelRelation(id);
		}
		for (int i = 0; i < labelId.length; i++) {
			Integer labelListId = Integer.valueOf(labelId[i]);
			InfoLabel record = new InfoLabel();
			record.setFileId(id);
			record.setLabelListId(labelListId);
			managerService.addLabelRelation(record);
		}
		List<InfoType> listType = managerService.getInfoTypeList(id);
		if (listType.size() != 0) {
			managerService.deleteTypeRelation(id);
		}
		String[] fileType = new String[] {};
		if (!StringUtils.isBlank(fileTypes)) {
			fileType = fileTypes.split(",");
		}
		for (int r = 0; r < fileType.length; r++) {
			Integer fileTypeId = Integer.valueOf(fileType[r]);
			InfoType record = new InfoType();
			record.setFileId(id);
			record.setFileTypeId(fileTypeId);
			managerService.addTypeRelation(record);
		}
		HashMap<String, Object> search = new HashMap<String, Object>();
		search.put("id", id);
		FileInfo fileInfo = managerService.getFileInfo(search);
		if (fileInfo != null) {
			fileInfo.setLabels(labelType);
		}
		// 更新文件的标签及文件列表
		managerService.updateFileInfoById(fileInfo);

		SysUser userLogin = (SysUser) session.getAttribute(ResultCode.USER_LOGIN_INFO);
		SysUser user = managerService.selectUserById(userLogin.getId());
		// 标记文件数统计
		int count = user.getCount() == null ? 0 : user.getCount();
		user.setCount(count + 1);
		managerService.updateByPrimaryKeySelective(user);
		return new RespJson(true, "添加成功", ResultCode.SUCCESS, "");
	}

	/**
	 * 获取已标注的列表
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/label/type")
	public Object getLabelType(Integer id) {
		HashMap<String, Object> map = new HashMap<>();
		List<InfoLabel> infoLabels = managerService.getInfoLabelList(id);
		List<InfoType> infoTypes = managerService.getInfoTypeList(id);
		map.put("infoLabels", infoLabels);
		map.put("infoTypes", infoTypes);
		return map;
	}

	/**
	 * 管理员搜索列表
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search/list")
	public RespJson getSearchList(String param) {
		HashMap<String, Object> search = new HashMap<String, Object>();
		String[] paramList = new String[] {};
		List<FileInfo> list = managerService.getSearchList(search);
		if (!StringUtils.isBlank(param)) {
			paramList = param.split(" ");
			for (int i = 0, len = list.size(); i < len; ++i) {
				if (nameLoop(paramList, list.get(i).getMusicName()) || typeLoop(paramList, list.get(i).getLabels())) {
					continue;
				} else {
					list.remove(i);
					--len;
					--i;
				}
			}
		}
		return new RespJson(true, "查询成功", ResultCode.SUCCESS, list);
	}

	// 循环判断数组中的字符串是否包含某个字符
	public boolean nameLoop(String[] arr, String param) {
		boolean flag = false;
		for (String str : arr) {
			if (param.contains(str) || str.contains(param)) {
				return true;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	// 循环判断数组中的字符串是否包含某个字符
	public boolean typeLoop(String[] arr, String param) {
		boolean flag = false;
		for (String str : arr) {
			if (param.contains(str)) {
				flag = true;
			} else {
				return false;
			}
		}
		return flag;
	}
}
