package com.alice.shiroai.handler;

import com.alice.shiroai.constant.MessageConstant;
import com.alice.shiroai.exception.BaseException;
import com.alice.shiroai.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Object handleBadRequestException(BaseException e) {
        log.error("自定义异常 -> {} , 异常原因：{}  ", e.getClass().getName(), e.getMessage());
        log.debug("详细异常信息", e);
        return R.failure(e);
    }

    private ResponseEntity<R<Void>> processResponse(BaseException e) {
        return ResponseEntity.status(e.getCode()).body(R.failure(e));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<String> HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("自定义异常 -> {} , 异常原因：{}  ", e.getClass().getName(), e.getMessage());
        log.debug("详细异常信息", e);
        return R.failure(MessageConstant.ARGS_LOCK);
    }

    /**
     * 处理资源未找到异常（如favicon.ico）
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResourceFoundException(NoResourceFoundException e) {
        // 可以选择性地记录日志，但使用debug级别而不是error
        if (e.getMessage().contains("favicon.ico") || e.getMessage().contains("静态资源")) {
            // 对于网站图标或其他静态资源，静默处理或使用debug级别
            log.debug("静态资源未找到: {}", e.getMessage());
        } else {
            // 其他资源未找到可能需要关注
            log.warn("资源未找到: {}", e.getMessage());
        }
        // 返回404状态码但不带消息体
        return ResponseEntity.notFound().build();
    }

    /**
     * 处理业务中的未定义异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<R<Void>> handleOtherException(Exception ex) {
        log.error("系统未定义异常，异常类型：{}，异常信息：{}", ex.getClass().getName(), ex.getMessage(), ex);
        return ResponseEntity.status(500).body(R.failure(MessageConstant.UNKNOWN_ERROR));
    }
}