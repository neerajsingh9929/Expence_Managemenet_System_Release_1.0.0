package com.sts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sts.fileUploadHelper.FileUploadHelper;

@RestController
public class FileUploadController {
	
	@Autowired
	FileUploadHelper fileUploadHelper;
	
	@PostMapping("/upload_file")
	public ResponseEntity<String>uploadFile(@RequestParam("file") MultipartFile file)
	{
		boolean result=false;
		
//		file methods
		
		System.out.println(file.getSize());
		System.out.println(file.getName());
		System.out.println(file.isEmpty());
long filesize=file.getSize()/1024;
		if(file.isEmpty())
		{
			return ResponseEntity.ok("File can't be Empity");
			
		}
		else if(filesize>30000 ||filesize<3 )
		{
			return ResponseEntity.ok("Please choose file size between 10kb and 300MB");
		}
		else {
			
			result =fileUploadHelper.fileUppload(file);
			
			if(result)
			{
				String fileDownloadUri = ServletUriComponentsBuilder
				        .fromCurrentContextPath()
				        .path("/images/")
				        .path(file.getOriginalFilename())
				        .toUriString();

				return ResponseEntity.ok("Successfully uploaded  "+fileDownloadUri);
			}
			else {
				return ResponseEntity.ok("Some Error ocoured Please Contact with Admin");
			}
		}
		
	
		
		
	}

}
