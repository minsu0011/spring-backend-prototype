package kr.it.code.main.gallery.data;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Gallery {

	
	@Getter
	@Setter
	public static class   ClientRequest{
		
		private String title;
		private MultipartFile  file;
		
	}
	
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class  AddRequest {
		
		private String title;
		private String fileName;
		private String fileStoredName;
		private String filePath;
		private String writer;
		private String thumbFileName;
		private String thumbFilePath;
		
	}
	
	
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class  Response {
		
		private int seq;
		private String title;
		private String fileName;
		private String fileStoredName;
		private String writer;
		private String createDate;
		private String updateDate;
		
	}
	
}
