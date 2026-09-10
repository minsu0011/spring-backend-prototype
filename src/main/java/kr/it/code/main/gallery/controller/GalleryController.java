package kr.it.code.main.gallery.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.it.code.main.gallery.data.Gallery;
import kr.it.code.main.gallery.service.GalleryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gall")
public class GalleryController {

	private final GalleryService galleryService;


	
	@GetMapping("/list")
	public ModelAndView listView() {
		ModelAndView view = new ModelAndView();
		view.setViewName("views/gall/list");
		return view;  
	}
	
	
	@GetMapping("/list/data")
	public  Map<String, Object>   listViewData(@RequestParam(name="nowPage", defaultValue = "0")int  nowPage) {
		Map<String, Object>  dataMap = new HashMap<>();
		Map<String, Object>  param = new HashMap<>();
		
		try {
			
			param.put("nowPage", nowPage);
			dataMap = galleryService.getGalleryList(param);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return dataMap;  
	
	}
	
	
	@PostMapping("/add")
	public Map<String, Object> addGallery(Gallery.ClientRequest  clientRequest)  {
		Map<String, Object>  resultMap = new HashMap<>();
		
		try {
		
			resultMap = galleryService.addGallery(clientRequest);
		
		}catch(Exception e) {
			
			resultMap.put("resultCode",  500);
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	
	
	
	
	
	
	
	
	
	
}
