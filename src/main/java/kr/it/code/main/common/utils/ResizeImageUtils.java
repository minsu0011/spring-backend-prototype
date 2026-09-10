package kr.it.code.main.common.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.MultiStepRescaleOp;

public class ResizeImageUtils {

	//썸네일 만들기 메서드
	public static  String  makeThumbnailImage(int width, int height,  File OriginFile,  String  filePath) {
		
		String thumbFileName = "";
		FileInputStream in = null;    // 파일을 읽기 위한 스트림 
		BufferedInputStream bf = null;  //성능개선을 위한 보조스트림 
		try {
		
			if(OriginFile != null) {
				//파일 이름 가져오기
				String fileName = OriginFile.getName();
				//확장자 얻기
				String ext =   fileName.substring(   fileName.lastIndexOf(".") + 1) ;
				// 파일 이름중복을 피하기 위해서 UUID 를 이용한다. 하이픈을 빈값을 변경하여 없앴다.
				String uuId  = UUID.randomUUID().toString().replaceAll("-",   "");
				//16자로 축소
				uuId = uuId.substring(0, 13);
				
				thumbFileName = uuId + "." + ext;
				
				//썸네일 이미지 만들기  시작 
				in =  new FileInputStream(OriginFile);
				bf = new BufferedInputStream(in);  //파라메터로 속도 개선할 inputStreeam 를  가져온다.
				
				// 원본파일을 줄이기 위해서  메모리에 복사
				BufferedImage originImg =  ImageIO.read(bf);
				//사이즈 축소
				MultiStepRescaleOp scaleImg = new MultiStepRescaleOp(width, height);
				// 마스킹 처리
				scaleImg.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Soft);
				//리사이즈  적용 이미지 생성 >> 메모리 
				BufferedImage  resizedImage =  scaleImg.filter(originImg,  null);
				//썸네일 저장 경로 만들기
				String thumbFilePath =   filePath  + thumbFileName;
				
				// 메모리에 있는  썸네일을 실제 저장하기 위한 파일 객체 생성
				File newThumbImage =  new File(thumbFilePath);
				
				// 경로가 없으면 만든다
				if( !newThumbImage.getParentFile().exists() ) {
					 newThumbImage.getParentFile().mkdirs();   // 하위또는 상위경로까지 포함하여 만들기 
				}
				
				   //메모리에 있는 리사이즈된 이미지를 실제 파일로 생성 
                boolean isWrite = ImageIO.write(resizedImage,  ext,  newThumbImage);

                //썸네일 생성이 오류 났을 경우 예외처리 
                if( !isWrite ) {
                    throw new Exception("Image resize is Error");
                }
                
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			//실패 시 이미지 이름 초기화  
			thumbFileName = "";
	
		}finally {
		
			 try{
	                if( bf != null){
	                    bf.close();
	                }
	                if(in != null){
	                    in.close();
	                }
	            }catch (Exception e2){
	                e2.printStackTrace();
	            }
		}
		
		return thumbFileName;
		
	}
}
