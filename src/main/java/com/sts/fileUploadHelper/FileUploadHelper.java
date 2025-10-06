package com.sts.fileUploadHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.constant.DynamicCallSiteDesc;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.hibernate.annotations.Comment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class FileUploadHelper {
	 //static path
//	public final String UPLOAD_DIR="C:\\Users\\neera\\Documents\\workspace-spring-tools-for-eclipse-4.31.0.RELEASE\\firststsproject\\src\\main\\resources\\images";
	
//	Dynamic path
	public final String UPLOAD_DIR=new ClassPathResource("images/").getFile().toString();
	public  FileUploadHelper()throws IOException {
		
	}
	public boolean fileUppload(MultipartFile file)
	{
		boolean f=false;
		try {
			System.out.println(UPLOAD_DIR);
			InputStream inputStream=file.getInputStream();
//Readable data
			byte data[]=new byte[inputStream.available()];
			inputStream.read(data);
//write data
//			method1
			FileOutputStream fileOutputStream=new FileOutputStream(UPLOAD_DIR+File.separator+file.getOriginalFilename());
			
//			method2
			
//			Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR+File.separator+file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			
			fileOutputStream.write(data);
			fileOutputStream.close();
			fileOutputStream.flush();
            f=true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return f;
	}
	

}
