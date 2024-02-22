package com.sp3.demo2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfcore.frame.Result;
import com.sp3.facebean.service.IBoxInfo;


@RestController
@RequestMapping("IBoxInfo")
public class BoxInfoController implements IBoxInfo {

	
	@RequestMapping("/getBoxByCode")
	@Override
	public Result<String> getBoxByCode(String code) {


		
		return Result.succeed("fdafdas");
	}

}
