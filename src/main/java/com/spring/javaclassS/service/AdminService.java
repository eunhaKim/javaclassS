package com.spring.javaclassS.service;

import java.util.ArrayList;

import com.spring.javaclassS.vo.GuestVO;

public interface AdminService {

	public int getMemberTotRecCnt();

	public ArrayList<GuestVO> getMemberList(int startIndexNo, int pageSize);

}
