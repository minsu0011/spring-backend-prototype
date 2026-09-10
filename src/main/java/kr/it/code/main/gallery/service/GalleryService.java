package kr.it.code.main.gallery.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.it.code.main.common.utils.ResizeImageUtils;
import kr.it.code.main.common.vo.PageVO;
import kr.it.code.main.gallery.data.Gallery;
import kr.it.code.main.gallery.mapper.GalleryMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GalleryService {

	private final GalleryMapper  galleryMapper;
	
	@Value("${server.stored.file.path}")
	private String filePath;
	
	
	public Map<String, Object> getGalleryList(Map<String, Object> param) throws Exception {
		Map<String, Object> dataMap = new HashMap<>();

		//이동해야할 페이지 번호
		int nowPage =   Integer.parseInt( param.get("nowPage").toString() );
		int totalRows =  galleryMapper.getGalleryTotal(param);
		
		//페이지 객체 만들기
		PageVO   page = new PageVO();
		//페이지 처리를 위한 데이터 전달 
		page.setData(nowPage, totalRows);
		
		List<Gallery.Response> galleryList =  new ArrayList<>();
		
		if( totalRows  > 0 ) {
			//한 페이지에 보여줄 데이터를 출력하기 위한 조건 처리 
			param.put("offset",   page.getOffset());
			param.put("rowCount",   page.getRowCount());
			// 한페이지에 보여줄 데이터 가져오기 
			galleryList  =   galleryMapper.getGalleryList(param);
		}
		
		dataMap.put("nowPage",  nowPage);
		dataMap.put("totalRows", totalRows);
		dataMap.put("dataList",   galleryList);
		dataMap.put("pageHtml" ,  page.pageHTML());
		
		
		return dataMap;
	}
	
	public Map<String, Object>  addGallery(Gallery.ClientRequest clientRequest) throws Exception {
		 Map<String, Object> resultMap = new HashMap<>();
		//성공하면 200 을 전송   
		 resultMap.put("resultCode", 200);
		 
		 //추가할 이미지 객체 가져오기 
		 Gallery.AddRequest request =  this.makeGalleryFile(clientRequest);
		 //이미지 추가 
		 int result =  galleryMapper.addGallery(request);
		 
		 //추가 실패 시 
		 if(result < 1) {
			  throw new Exception("갤러리 추가 실패!!!");
		 }
		 
		 return resultMap;
	}
	
	
	
	private Gallery.AddRequest    makeGalleryFile(Gallery.ClientRequest  clientRequest) throws Exception {
		Map<String, Object> uploadMap =  this.uploadFile(clientRequest.getFile());
		
		if(uploadMap  == null) {
			 throw new Exception("파일 업로드 실패");
		}
		
		File  uploadFile =    (File)uploadMap.get("newFile");
		String uploadPath =   uploadMap.get("filePath").toString();
		
		String thumbFilePath  =    uploadPath + "thumb" + File.separator ;
		//썸네일 만들기
		 String thumbFileName =  ResizeImageUtils.makeThumbnailImage(280, 150, uploadFile, thumbFilePath) ;
		 
		 // 저장할 객체 만들기 
		 
		 return Gallery.AddRequest
									 .builder()
									 .title(clientRequest.getTitle())
									 .writer("admin")
									 .fileName(clientRequest.getFile().getOriginalFilename())  // 원본 파일 이름
									 .fileStoredName(uploadFile.getName())  // 물리적으로 저장된  파일 이름 
									 .filePath(uploadPath)
									 .thumbFileName(thumbFileName)
									 .thumbFilePath(thumbFilePath)
									 .build();
	}
	
	
	
	
	
	private  Map<String, Object>  uploadFile(MultipartFile file )  throws Exception {
		
		Map<String, Object> result = null;
		
		if(file == null ||  file.isEmpty()) {
			 throw new Exception("등록할 파일이 없습니다.");
		}
		
		//원본이름
		String originName = file.getOriginalFilename();
		//확장자 찾기
		String ext =     originName.substring(  originName.lastIndexOf(".") + 1  );
		String randomName = UUID.randomUUID().toString().replaceAll("-",   "");
		randomName = randomName.substring(0, 16);
		//저장을 위한 파일 이름 만들기 
		String storedName = randomName + "."  + ext;
		
		//이미지 파일 경로 만들기 
		String  galleryPath = this.filePath +"gallery"  + File.separator;
	
		//전체 경로 만들기
		String fullPath =   galleryPath  +  storedName;
		
		//파일객체는 있는 파일을 읽어서 객체화 하거나 없는 파일을 생성하기 위해 사용 
		File  newFile =   new File(fullPath);
		
		//경로를 체크해서 없으면 만들자
		if ( !newFile.getParentFile().exists() ) {
			 newFile.mkdirs();
		}
		
		//백지 파일 만들기
		newFile.createNewFile();
		
		file.transferTo(newFile); //기존파일 내용을 빈파일에 복사하여 생성 
		
		result = new HashMap<>();
		
		  result.put("newFile", newFile);
          result.put("filePath",  galleryPath);
		
		return result;
		
	}
	
}
