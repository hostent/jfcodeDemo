package com.jfcore.job;

import java.util.HashMap;
import java.util.List;

import com.jfcore.frame.Result;

public interface IFaceJob {
	
	
	Result execute(JobContext context);
	
	List<HashMap<String, Object>> getParList();

}
