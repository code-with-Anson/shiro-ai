package com.alice.shiroai.handler;


import com.alice.shiroai.constant.MessageConstant;
import com.alice.shiroai.exception.BaseException;
import com.alice.shiroai.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        return processResponse(e);
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
