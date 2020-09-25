<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!-- 
	이	   름 : insertHotelForm.jsp
	개  발   자 : 김 지 원
	설	   명 : 숙소 등록 폼
 -->
<%@ include file="/WEB-INF/views/top.jsp"%>

<footer class="footer-box">
		<div class="container">
			<div class="row">
				<div class="col-md-12 white_fonts">
					<div class="row">
						<div class="col-sm-6 col-md-6 col-lg-3">
							<div class="full">
								<h3>숙소 등록하기</h3>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
</footer>
<div class="section layout_padding"></div>
 <div align="center">
	<form name="f" action="insertHotel.hotel" method="post" enctype="multipart/form-data">
		<table border="solid 1" width="1000">
			<tr>
				<td align="center" colspan="2">숙소 등록하기</td>
			</tr>
			<tr>
				<th width="20%" bgcolor="orange" required>이 름</th>
				<td><input type="text" name="name" ></td>
			</tr>
			<tr>
				<th width="20%" bgcolor="orange">종 류 </th>
				<td><select name="category">
				<option value="hotel" selected>호텔</option>
				<option value="motel">모텔</option>
				<option value="guesthouse">게스트하우스</option>
				<option value="pension">펜션</option>
				</select></td>
			</tr>
			<tr>
				<th width="20%" bgcolor="orange">위치</th>
				<td><input type="text" name="location"  size="40"></td>
			</tr>
			<tr>
				<th width="20%" bgcolor="orange">전화번호</th>
				<td><input type="text" name="hp"  size="40"></td>
			</tr>
			<tr>
				<th width="20%" bgcolor="orange">체크인시간</th>
				<td>체크인 : 
				<select name="startcheckin">
				<c:forEach var="i" begin="8" end="22" step="1">
				<option value="${i}:00">${i}:00</option>
   				</c:forEach>
				</select>
					체크 아웃 :
				<select name="endcheckin">
				<c:forEach var="i" begin="8" end="22" step="1">
				<option value="${i}:00">${i}:00</option>
   				</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<th width="10%" bgcolor="orange">주차</th>
				<td>
				주차가능<input type="radio" name="parking" value="ok">
				주차불가<input type="radio" name="parking" value="no" checked>
				</td>
			</tr>
			<tr>
				<th width="40%" bgcolor="orange">내 용</th>
				<td><textarea name="content" rows="20" cols="100" ></textarea></td>
			</tr>
			<tr>
				<th width="10%" bgcolor="orange">숙소사진</th>
				<td><input type="file" name="filename" size="40"></td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="등록하기">
					<input type="reset" value="다시작성">
					<input type="button" value="목록보기" onclick="window.location.href='hotelList.hotel'">				
				</td>
			</tr>
		</table>
	</form>
	<br><br><a href="main.admin">관리자 목록으로 가기</a>
</div>
<div class="section layout_padding"></div>
<%@ include file="/WEB-INF/views/bottom.jsp"%>