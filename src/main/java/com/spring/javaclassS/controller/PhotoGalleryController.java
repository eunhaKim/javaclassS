package com.spring.javaclassS.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.pagination.PageProcess;
import com.spring.javaclassS.service.PhotoGalleryService;
import com.spring.javaclassS.vo.PhotoGalleryVO;

@Controller
@RequestMapping("/photoGallery")
public class PhotoGalleryController {
	
	@Autowired
	PhotoGalleryService photoGalleryService;
	
	@Autowired
	JavaclassProvide javaclassProvide;
	
	@Autowired
	PageProcess pageProcess;
	
	@RequestMapping(value = "/photoGalleryList", method = RequestMethod.GET)
	public String photoGalleryListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag, 
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="choice", defaultValue = "최신순", required = false) String choice
		) {
		int startIndexNo = (pag - 1) * pageSize;
		
		String imsiChoice = "";
		if(choice.equals("최신순")) imsiChoice = "idx";
		else if(choice.equals("추천순")) imsiChoice = "goodCount";
		else if(choice.equals("조회순")) imsiChoice = "readNum";
		else if(choice.equals("댓글순")) imsiChoice = "replyCnt";	
		else imsiChoice = choice;
		
		//PageVO pageVo = pageProcess.totRecCnt(pag, pageSize, "photoGallery", part, choice);
		List<PhotoGalleryVO> vos = photoGalleryService.getPhotoGalleryList(startIndexNo, pageSize, part, imsiChoice);
		model.addAttribute("vos", vos);
		return "photoGallery/photoGalleryList";
	}
	
	@RequestMapping(value = "/photoGalleryInput", method = RequestMethod.GET)
	public String photoGalleryInputGet() {
		return "photoGallery/photoGalleryInput";
	}
	
	@RequestMapping(value = "/photoGalleryInput", method = RequestMethod.POST)
	public String photoGalleryInputPost(PhotoGalleryVO vo, HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		int res = photoGalleryService.imgCheck(vo, realPath);
		if(res != 0) return "redirect:/message/photoGalleryInputOk";
		else return "redirect:/message/photoGalleryInputNo";
	}
	
	
}
