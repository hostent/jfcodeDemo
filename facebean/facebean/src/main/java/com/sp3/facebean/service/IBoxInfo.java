package com.sp3.facebean.service;

import com.jfcore.frame.Result;

public interface IBoxInfo {
	
	Result<String> getBoxByCode(String code);

}
