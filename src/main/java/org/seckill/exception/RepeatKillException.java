package org.seckill.exception;

/**
 * 重复秒杀异常 （运行期异常）
 * @author zyl65
 *
 */
public class RepeatKillException extends SeckillException{

	public RepeatKillException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RepeatKillException(String arg0) {
		super(arg0);
	}
	
	
}
