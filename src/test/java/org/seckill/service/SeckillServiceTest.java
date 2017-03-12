package org.seckill.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-dao.xml",
	"classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}",list);
	}

	@Test
	public void testGetById() {
		long id = 1001;
		Seckill seckill = seckillService.getById(id);
		logger.info("seckill={}",seckill);
	}

	/**
	 *集成 测试代码完整逻辑，注意可重复执行
	 */
	@Test
	public void testSeckillLogic() {
		long id = 1000;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()) {
			logger.info("exposer={}",exposer);
			long phone = 13245076585L;
			String md5 = exposer.getMd5();
			try{			
				SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
				logger.info("result={}",execution);
			}catch(RepeatKillException e){
				logger.error(e.getMessage());
			}catch(SeckillCloseException e) {
				logger.error(e.getMessage());
			}
		}
		else {
			//秒杀未开启
			logger.warn("exposer={}",exposer);
		}
	}
	
	@Test
	public void testExportSeckillUrl() {
		long id = 1001;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		logger.info("exposer={}",exposer);
	}

	@Test
	public void testExecuteSeckill() {
		long id = 1000;
		long phone = 13245076585l;
		String md5 = "67719770800c11eab69939eea7986759";
		try{			
			SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
			logger.info("result={}",execution);
		}catch(RepeatKillException e){
			logger.error(e.getMessage());
		}catch(SeckillCloseException e) {
			logger.error(e.getMessage());
		}
	}

}
