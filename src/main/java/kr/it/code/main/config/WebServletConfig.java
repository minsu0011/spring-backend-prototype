package kr.it.code.main.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebServletConfig  implements WebMvcConfigurer {

	@Value("${server.stored.file.path}")
	private String filePath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	
		String  galleryPath = this.filePath +"gallery"  + File.separator;
		registry.addResourceHandler("/gall/img/**")
		               .addResourceLocations("file:///"  + galleryPath)
		               .setCachePeriod(0)
		               .resourceChain(true)
		               .addResolver(new PathResourceResolver());
	}
	
	
	
}
