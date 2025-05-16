package org.harvey.respiratory.server.advice;


import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.exception.*;
import org.harvey.respiratory.server.pojo.vo.NullPlaceholder;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
        return new Result<>(500, "服务器异常,请稍后再试");
    }

    @ExceptionHandler(ServerExceptionException.class)
    public Result<NullPlaceholder> handleUnauthorizedExceptionException(ServerExceptionException e) {
        log.error(e.toString(), e);
        return new Result<>(401, "服务器异常, 请稍后重试.");
    }

    @ExceptionHandler(UnfinishedException.class)
    public Result<NullPlaceholder> handleUnauthorizedExceptionException(UnfinishedException e) {
        log.error(e.toString(), e);
        return new Result<>(401, "未实现功能, 请耐心等待.");
    }

    @ExceptionHandler(BadRequestException.class)
    public Result<NullPlaceholder> handleBadRequestException(BadRequestException bre) {
        return new Result<>(bre.getCode(), bre.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public Result<NullPlaceholder> handleBadRequestException(ForbiddenException e) {
        return new Result<>(405, e.getMessage());
    }

    @ExceptionHandler(ResourceNotFountException.class)
    public Result<NullPlaceholder> handleBadRequestException(ResourceNotFountException e) {
        return new Result<>(404, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<NullPlaceholder> handleBadRequestException(MethodArgumentTypeMismatchException e) {
        return new Result<>(403, "请求方式错误或URL参数格式不符合要求");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Result<NullPlaceholder> handleUnauthorizedExceptionException(UnauthorizedException e) {
        return new Result<>(401, e.getMessage());
    }

}
