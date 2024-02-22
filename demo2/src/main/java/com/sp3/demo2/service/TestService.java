package com.sp3.demo2.service;

 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sp3.demo2.dataAccess._db;
import com.sp3.demo2.dataAccess.tables.Box;

public class TestService {

	static Logger logger = LoggerFactory.getLogger(TestService.class);

	public static void main(String[] args) {

		logger.info("开始");
		Box box = null;

		for (int i = 0; i < 10000; i++) {
			box = _db.getBoxSet().first();
		}

		logger.info(box.getBoxCode());

	}

}
