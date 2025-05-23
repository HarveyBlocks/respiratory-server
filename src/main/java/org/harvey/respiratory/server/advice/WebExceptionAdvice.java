package org.harvey.respiratory.server.advice;


import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.exception.*;
import org.harvey.respiratory.server.pojo.vo.NullPlaceholder;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;

/**
 * 异常处理增强
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-06-01 17:31
 */
@Slf4j
@RestControllerAdvice
public class WebExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public Result<NullPlaceholder> handleRuntimeException(RuntimeException e) {
        log.error("[未被登记的异常]: " + e.toString(), e);
        return new Result<>(502, "服务器异常,请稍后再试");
    }

    @ExceptionHandler(UnfinishedException.class)
    public Result<NullPlaceholder> handleUnfinishedException(UnfinishedException e) {
        log.error(e.toString(), e);
        return new Result<>(501, "未实现功能, 请耐心等待.");
    }

    @ExceptionHandler(ServerException.class)
    public Result<NullPlaceholder> handleServerException(ServerException e) {
        log.error(e.toString(), e);
        return new Result<>(500, "服务器异常, 请稍后重试.");
    }

    @ExceptionHandler(DaoException.class)
    public Result<NullPlaceholder> handleDaoException(DaoException e) {
        log.error(e.toString(), e);
        return new Result<>(406, "未能成功的写操作");
    }


    @ExceptionHandler(BadRequestException.class)
    public Result<NullPlaceholder> handleBadRequestException(BadRequestException bre) {
        return new Result<>(bre.getCode(), bre.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public Result<NullPlaceholder> handleForbiddenException(ForbiddenException e) {
        return new Result<>(405, e.getMessage());
    }

    @ExceptionHandler(ResourceNotFountException.class)
    public Result<NullPlaceholder> handleResourceNotFountException(ResourceNotFountException e) {
        return new Result<>(404, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<NullPlaceholder> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new Result<>(403, "请求方式错误或URL参数格式不符合要求");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Result<NullPlaceholder> handleUnauthorizedException(UnauthorizedException e) {
        return new Result<>(401, e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result<NullPlaceholder> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        Throwable t = e;
        while (t != null && !(t instanceof SQLException)) {
            t = t.getCause();
        }
        if (t == null) {
            t = e;
        }
        log.error("SQL错误, 依赖错误(外键)? 完整性异常(没给完整值)?: " + t.getMessage(), t);
        return new Result<>(403, t.getMessage());

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<NullPlaceholder> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("Json格式错误: " + e.toString(), e);
        return new Result<>(403, e.getMessage());
    }
}
