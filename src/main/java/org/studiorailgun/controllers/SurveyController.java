package org.studiorailgun.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.studiorailgun.dtos.survey.Question;
import org.studiorailgun.dtos.survey.QuestionRepository;
import org.studiorailgun.dtos.survey.Survey;
import org.studiorailgun.dtos.survey.SurveyRepository;

// @CrossOrigin(origins = "http://localhost:8000")
@Controller
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/survey/add")
    @ResponseBody
    public String add() {
        Survey newSurvey = new Survey();
        newSurvey.setName("A new survey");
        Survey rVal = surveyRepository.save(newSurvey);
        return rVal.getId() + "";
    }

    @GetMapping("/survey/delete/{surveyId}")
    @ResponseBody
    public String surveyDelete(@PathVariable("surveyId") int surveyId){
        surveyRepository.deleteById(surveyId);
        return "200";
    }

    @GetMapping("/survey/list")
    @ResponseBody
    public Iterable<Survey> listSurvey() {
        return surveyRepository.findAll();
    }

    @GetMapping("/question/list")
    @ResponseBody
    public Iterable<Question> listQuestion() {
        return questionRepository.findAll();
    }

    @PostMapping("/survey/addQuestion/{surveyId}")
    @ResponseBody
    public String addQuestion(@PathVariable("surveyId") int surveyId, @RequestBody Question question) {
        Question newQuestion = questionRepository.save(question);
        //add question to survey and save if found, else throw 404
        surveyRepository.findById(surveyId).ifPresentOrElse(
        (survey) -> {
            newQuestion.setSurvey(survey);
            questionRepository.save(newQuestion);
            survey.addQuestion(newQuestion);
            surveyRepository.save(survey);
        }, 
        () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey " + surveyId + " not found");
        });
        return question.getId() + "";
    }

    @PostMapping("/survey/updateQuestion/{surveyId}/{questionId}")
    @ResponseBody
    public String addQuestion(@PathVariable("surveyId") int surveyId, @PathVariable("questionId") int questionId, @RequestBody Question question) {
        Question foundQuestion = questionRepository.findById(questionId).get();
        foundQuestion.setPrompt(question.getPrompt());
        Question updatedQuestion = questionRepository.save(foundQuestion);
        return updatedQuestion.getId() + "";
    }

    @GetMapping("/survey/removeQuestion/{surveyId}/{questionId}")
    @ResponseBody
    public String removeQuestion(@PathVariable("surveyId") int surveyId, @PathVariable("questionId") int questionId) {
        questionRepository.deleteById(questionId);
        //remove question from survey and save if found, else throw 404
        surveyRepository.findById(surveyId).ifPresentOrElse(
        (survey) -> {
            survey.getQuestions().removeIf((question) -> question.getId() == questionId);
            surveyRepository.save(survey);
        }, 
        () -> {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Survey " + surveyId + " not found"
            );
        });
        return "200";
    }
    
}
