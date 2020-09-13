package com.teamb.controller;

import java.io.File;


import java.io.IOException;
import java.text.DateFormat;




import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.teamb.model.MemberDTO;
import com.teamb.service.MemberMapper;
import com.teamb.service.MyMapper;

  

/*
이	   름 : MyController.java


개  발   자 : 원세호
설	   명 : 마이페이지 컨트롤러
*/



//rroll2
/**
 * Handles requests for the application home page.
 */
@Controller
public class MyController {
	
	@Autowired
	//private MemberMapper memberMapper;
	private MyMapper myMapper;
	
	@Resource(name="upLoadPath")
	private String upLoadPath;
	
	private static final Logger logger = LoggerFactory.getLogger(MyController.class);
	
	
	@RequestMapping("/main.my")
	public String mypageMain() {
		
		return "my/mypageMain";
		
	}
	
	@RequestMapping("/delete.my")
	public String deleteMem(HttpServletRequest req) {
		int del = 1;
		req.setAttribute("type", del);
		return "my/confirm";
	}
	
	@RequestMapping("/deletePro.my")
	public String deletePro(HttpServletRequest req,HttpSession session) {
		String id = (String)session.getAttribute("mbId");
		String passwd = (String)req.getAttribute("passwd");
		System.out.println(id+passwd);
		MemberDTO dto = myMapper.getMember(id);
		int res = myMapper.checkPassword(id,passwd);
		String msg=null,url=null;
		switch(res){
		case MemberDTO.OK:
			int re = myMapper.deleteMember(dto.getMemberNum());
				if(re>0){
					msg="탈퇴 성공!!";
					url="home.do";
				}else{
					msg="탈퇴 실패!! 관리자에게 문의하세요";
					url="home.do";
				}
		case MemberDTO.NOT_ID:
			msg="ID를 확인해주세요";
			url="delete.my";
		case MemberDTO.NOT_PW:
			msg="비밀번호를 확인해주세요";
			url="delete.my";
		case MemberDTO.ERROR:
			msg="에러!! 관리자에게 문의하세요";
			url="delete.my";
		}	
		req.setAttribute("url", url);
		req.setAttribute("msg", msg);
		return "my/alert";
	}
		
		
		/*String id =  req.getParameter("id");
		String password = req.getParameter("password");
		MemberDTO dto = myMapper.getMember(id);
		int res = myMapper.deleteMember(id,password,dto.getMemberNum());
=======
		//로그인 세션이 없어서 테스트 불가 
		//String id = String.valueOf(session.getAttribute("id"));
		//String password = String.valueOf(session.getAttribute("password"));
		String id =  req.getParameter("id");
		String password = req.getParameter("password");
		System.out.println(id);
		MemberDTO dto = myMapper.getMemberNo(id);
		
		int memberNum = dto.getMemberNum();
		System.out.println(memberNum);
		int res = myMapper.deleteMember(id,password,memberNum);
>>>>>>> branch 'junun2' of https://github.com/JIWON0813/KingGgang_Project.git
		String msg = null, url=null;
		if (res > 0) {
<<<<<<< HEAD
			if (dto.getProfile_name() == null) {
				url = "memberList.mem";
				msg = "delete successed";
			} else {
				String filename = dto.getProfile_name();
				File file = new File(upLoadPath, filename);
				if (file.delete()) {
					url = "memberList.mem";
					msg = "delete successed";
				} else {
					url = "memberList.mem";
					msg = "delete successed but image is remained";
				}
			}
		} else {
			url = "memberList.mem";
			msg = "delete failed";
		}
=======
	         if (dto.getProfile_name() == null) {
	            url = "memberList.mem";
	            msg = "delete successed";
	         } else {
	            String filename = dto.getProfile_name();
	            File file = new File(upLoadPath, filename);
	            if (file.delete()) {
	               url = "memberList.mem";
	               msg = "delete successed";
	            } else {
	               url = "memberList.mem";
	               msg = "delete successed but image is remained";
	            }
	         }
	      } else {
	         url = "memberList.mem";
	         msg = "delete failed";
	      }
>>>>>>> branch 'junun2' of https://github.com/JIWON0813/KingGgang_Project.git
		
		req.setAttribute("url", url);
		req.setAttribute("msg", msg);
<<<<<<< HEAD
		return "my/alert";
	}*/

		/*return "message";*/
	/*}*/
	
	@RequestMapping("/update.my")
	public String updateForm(HttpServletRequest req) {
		int upd = 2;
		req.setAttribute("type", upd);
		return "my/confirm";
	}
	@RequestMapping(value = "/updateForm.my")
	public ModelAndView updateMem(@RequestParam String id) {
		MemberDTO dto =  myMapper.getMember(id);
		
		ModelAndView mav = new ModelAndView("my/updateForm");
		mav.addObject("getMember", dto);
		mav.addObject("upLoadPath", upLoadPath);
		return mav;
	}
	
	@RequestMapping(value = "/updatePro.my")
	public String updatePro(HttpServletRequest req,MemberDTO dto,BindingResult result) {
		//if(result.hasErrors()) {
		//	dto.setId(null);
		//}
		String id= req.getParameter("id");
		dto.setId(id);
		
		String filename2 =  req.getParameter("filename2");
		int filesize2 =  Integer.parseInt(req.getParameter("filesize2"));
		
		String filename="";
		int filesize =0;
		
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest)req;
		MultipartFile file = mr.getFile("filename");
		File target = new File(upLoadPath,file.getOriginalFilename());
		File filedelete = new File(upLoadPath,filename2);
		if(file.getSize()>0) {
			try {
			file.transferTo(target);
			filedelete.delete();
		}catch(IOException e) {}
		filename = file.getOriginalFilename();
		filesize = (int)file.getSize();
		
	}else {
	filename=filename2;
	filesize=filesize2;
	}
		dto.setProfile_name(filename);
		dto.setProfile_size(filesize);
	
		int res = myMapper.updateMember(dto);
		String msg = null, url=null;
	if (res>0) {
		
		url = "home.my";
		msg = "update successed";
	}else {
		url = "home.my";
		msg = "update failed";
	}
	req.setAttribute("url", url);
	req.setAttribute("msg", msg);
	return "message";
	}
	
	@RequestMapping("/home.my")
	public String home() {
		
		return "home";
		
	}
	
	
	
}
