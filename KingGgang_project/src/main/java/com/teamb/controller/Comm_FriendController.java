package com.teamb.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teamb.model.Comm_FriendDTO;
import com.teamb.model.Comm_MemberDTO;
import com.teamb.service.Comm_FriendMapper;
import com.teamb.service.Comm_MemberMapper;


/*
이      름 : Comm_MemberController
개  발   자 : 황지은
성      명 : 커뮤니티 친구 컨트롤러
*/
@Controller
public class Comm_FriendController {

   @Autowired
   private Comm_FriendMapper friendMapper;
   
   @Autowired
   private Comm_MemberMapper memberMapper;

   @Resource(name = "upLoadPath")
   private String upLoadPath;

	@RequestMapping("/comm_friend_insert.do")
	public String insertFriend(HttpServletRequest req, HttpSession session, 
							Comm_FriendDTO dto) {
		
		int login_comm_memberNum = (int) session.getAttribute("login_comm_memberNum");
		int comm_memberNum=dto.getComm_memberNum();
		String msg = null, url = null;
		
		if(login_comm_memberNum==comm_memberNum){
			msg="본인 자신은 친구 추가가 되지 않습니다.";
			url = "comm_memberList.do";
		}
		else{
			int res1= friendMapper.check_insertFriend(login_comm_memberNum,comm_memberNum);
			if(res1 == 0){
				int res = friendMapper.insertFriend(dto);
				if (res > 0) {
					msg = "친구 추가 성공. 친구목록 페이지로 이동";
					url = "comm_friendAll.do?comm_memberNum="+login_comm_memberNum;
				} else {
					msg = "친구 추가 실패. 메인으로 이동";
					url = "commhome.comm";
				}
			}
			else{
				msg="이미 등록된 친구입니다.";
				url="comm_friendAll.do?comm_memberNum="+login_comm_memberNum;
			}
		}
		req.setAttribute("msg", msg);
		req.setAttribute("url", url);
		return "message";
	}
		
	@RequestMapping(value = "/comm_friendAll.do")
	public String listFriend(HttpServletRequest req, Comm_FriendDTO dto,
				HttpSession session) {
		// Comm_MemberDTO login = (Comm_MemberDTO) session.getAttribute("comm_login");
		int comm_memberNum= Integer.parseInt(req.getParameter("comm_memberNum"));
		int login_comm_memberNum=(Integer)session.getAttribute("login_comm_memberNum");
		
		List<Comm_FriendDTO> list = null;
		if(login_comm_memberNum==comm_memberNum){
			list = friendMapper.listFriend(login_comm_memberNum);
		}
		else{
			list = friendMapper.other_listFriend(comm_memberNum);
		}
		for(Comm_FriendDTO dto2: list){
			int m=dto2.getComm_memberNum();
			 Comm_MemberDTO mdto=memberMapper.comm_getMember(m);
			dto2.setF_comm_profilename(mdto.getComm_profilename());
			dto2.setF_name(mdto.getComm_name());
			dto2.setF_comm_nickname(mdto.getComm_nickname());
		}
		session.setAttribute("friendList", list);
		req.setAttribute("login_comm_memberNum", login_comm_memberNum);
	    req.setAttribute("comm_memberNum", comm_memberNum);
 

      return "comm/friend/friendAll";
   }

   @RequestMapping(value = "/comm_deleteFriend.do")
   	public String deleteFriend(HttpServletRequest req,HttpSession session,@RequestParam int friendNum,Comm_FriendDTO dto) {
	   int login_comm_memberNum = (int) session.getAttribute("login_comm_memberNum");
      int res = friendMapper.deleteFriend(friendNum);
      String msg = null, url = null;
      if (res > 0) {
         msg = "친구삭제 성공. 친구목록페이지로 이동";
         url = "comm_friendAll.do?comm_memberNum="+login_comm_memberNum;
      }
      req.setAttribute("msg", msg);
      req.setAttribute("url", url);
      return "message";
   }
   
   
   
}