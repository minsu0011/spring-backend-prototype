package kr.it.code.main.gallery.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.it.code.main.gallery.data.Gallery;

@Mapper
public interface GalleryMapper {
	
	 int addGallery(Gallery.AddRequest addRequest) throws Exception;

	 int getGalleryTotal(Map<String, Object> param) throws Exception;
	 
	 List<Gallery.Response> getGalleryList(Map<String, Object> param) throws Exception;

}
