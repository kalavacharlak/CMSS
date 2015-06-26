package com.broadridge.cmss;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broadridge.cmss.domain.Response;

@Controller
@RequestMapping("/achin")
public class ACHInController {
	
	private static final Logger logger = Logger.getLogger(ACHInController.class);
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public @ResponseBody Response createACHIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Response res = new Response();
		res.setMessage("SUCCESS");
		logger.debug("RETURNING RESPOSE == " + res);
		return res;
	}
}
