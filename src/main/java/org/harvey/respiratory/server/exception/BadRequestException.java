package org.harvey.respiratory.server.exception;

/**
 * 请求有误
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-02-01 14:09
 */
public class BadRequestException extends RuntimeException{
    public  int getCode() {
        return 400;
    }
    public BadRequestException(){
        super();
    }
    public BadRequestException(String message){
        super(message);
    }
    public BadRequestException(String message,Throwable cause){
        super(message,cause);
    }
    public BadRequestException(Throwable cause){
        super(cause);
    }
}
