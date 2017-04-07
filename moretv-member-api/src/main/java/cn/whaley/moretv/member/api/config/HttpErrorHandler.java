package cn.whaley.moretv.member.api.config;

import cn.whaley.moretv.member.base.res.ResultResponse;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Bob Jiang on 2017/4/6.
 */
@RestController
public class HttpErrorHandler extends AbstractErrorController {

    private static final String ERROR_PATH = "/error";

    public HttpErrorHandler(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return this.ERROR_PATH;
    }


    @RequestMapping(value = ERROR_PATH)
    public ResultResponse error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, false);
        HttpStatus status = getStatus(request);
        String message = body.get("message").toString();

        if (status.equals(HttpStatus.BAD_REQUEST)) {
            List<ObjectError> errors = (List<ObjectError>) body.get("errors");
            message = errors.get(0).getDefaultMessage();
        }

        return ResultResponse.define(status.value(), message);
    }
}
