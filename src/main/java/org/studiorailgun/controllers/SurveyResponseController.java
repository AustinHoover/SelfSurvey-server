package org.studiorailgun.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.studiorailgun.dtos.response.Response;
import org.studiorailgun.dtos.response.ResponseRepository;
import org.studiorailgun.dtos.response.ResponseValue;
import org.studiorailgun.dtos.response.ResponseValuesRepository;
import org.studiorailgun.services.ResponseValueService;

@Controller
public class SurveyResponseController {

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private ResponseValuesRepository responseValuesRepository;

    @Autowired
    private ResponseValueService responseValueService;

    @GetMapping("/surveyresponse/add/{surveyId}")
    @ResponseBody
    public String add(@PathVariable("surveyId") int surveyId) {
        Response rVal = responseRepository.save(new Response(Instant.now(),surveyId));
        return rVal.getId() + "";
    }

    @GetMapping("/surveyresponse/delete/{responseId}")
    @ResponseBody
    public String surveyResponseDelete(@PathVariable("responseId") int responseId){
        responseRepository.deleteById(responseId);
        return "200";
    }

    @GetMapping("/surveyresponse/list")
    @ResponseBody
    public Iterable<Response> listResponses() {
        return responseRepository.findAll();
    }

    @GetMapping("/surveyresponse/value/list")
    @ResponseBody
    public Iterable<ResponseValue> listResponseValues() {
        return responseValuesRepository.findAll();
    }

    @PostMapping("/surveyresponse/value/add/{responseId}")
    @ResponseBody
    public String addValue(@PathVariable("responseId") int responseId, @RequestBody ResponseValue value) {
        // try {
		// 	responseValueService.queueResponseValue(value, responseId);
		// } catch (Exception e) {
		// 	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Response " + e.getMessage() + " not found");
		// }
        ResponseValue newValue = responseValuesRepository.save(value);
        responseRepository.findById(responseId).ifPresentOrElse(
        (response) -> {
            try {
                newValue.setResponse(response);
                response.addResponseValue(newValue);
                responseRepository.save(response);
                responseValuesRepository.save(newValue);
            } catch (Exception ex){
                ex.printStackTrace();
            }
            // System.out.println(newValue.getResponse().getId() + "~");
        }, 
        () -> {
            System.out.println(responseId + " not found");
        });
        // ResponseValue newValue = responseValuesRepository.save(value);
        //  //add value to response and save if found, else throw 404
        // responseRepository.findById(responseId).ifPresentOrElse(
        // (response) -> {
        //     newValue.setResponse(response);
        //     responseRepository.save(response);
        //     response.addResponseValue(newValue);
        //     responseValuesRepository.save(newValue);
        // }, 
        // () -> {
        //     throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Response " + responseId + " not found");
        // });
        return value.getId() + "";
    }

    @GetMapping("/surveyresponse/value/remove/{responseId}/{valueId}")
    @ResponseBody
    public String removeValue(@PathVariable("responseId") int responseId, @PathVariable("valueId") int valueId) {
        responseValuesRepository.deleteById(valueId);
        //remove value from response and save if found, else throw 404
        responseRepository.findById(responseId).ifPresentOrElse(
        (response) -> {
            response.getResponseValues().removeIf((value) -> value.getId() == valueId);
            responseRepository.save(response);
        }, 
        () -> {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Response " + responseId + " not found"
            );
        });
        return "200";
    }
    
}
