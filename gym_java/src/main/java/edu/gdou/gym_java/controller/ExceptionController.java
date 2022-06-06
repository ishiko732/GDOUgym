package edu.gdou.gym_java.controller;

import edu.gdou.gym_java.entity.bean.ResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {
    @GetMapping("/401/{msg}")
    public ResponseBean error401(@PathVariable("msg") String msg) {
        return new ResponseBean(401, msg, null);
    }
    @GetMapping("/403/{msg}")
    public ResponseBean error403(@PathVariable("msg") String msg) {
        return new ResponseBean(403, msg, null);
    }
}
