package com.example.doantotnghiep.service.Impl;

import com.example.doantotnghiep.model.Exam;
import com.example.doantotnghiep.model.Question;
import com.example.doantotnghiep.model.QuestionForm;
import com.example.doantotnghiep.model.Result;
import com.example.doantotnghiep.repository.ExamRepository;
import com.example.doantotnghiep.repository.QuestionRepository;
import com.example.doantotnghiep.repository.ResultRepository;
import com.example.doantotnghiep.service.ExamService;
import com.example.doantotnghiep.service.QuestionService;
import com.example.doantotnghiep.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    Question question;

    @Autowired
    QuestionForm qForm;

    @Autowired
    QuestionService questionService;

    @Autowired
    Result result;

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    ExamService examService;

    @Override
    public QuestionForm getQuestions(int id) {
        List<Question> allQues = questionService.findAllByExams(id);
        List<Question> qList = new ArrayList<Question>();
        Exam exam = examService.findById(id);

        int fag = allQues.size();
        Random random = new Random();
        if (exam.getNumberQues() < allQues.size()) {
            fag = exam.getNumberQues();
        }
        for (int i = 0; i < fag; i++) {
            int rand = random.nextInt(allQues.size());
            qList.add(allQues.get(rand));
            allQues.remove(rand);
        }

        qForm.setQuestions(qList);
        return qForm;
    }

    @Override
    public double getResult(QuestionForm qForm) {
        double convert = 0;
        double correct = 0;
        double score;
        String[] choose;

        for (Question q : qForm.getQuestions()) {
            double tempScore = 0;
            System.out.println("Cau ...");
            System.out.println(q.getChose());
            String temp = q.getChose();
            temp = temp.replaceAll(" ", "");
            choose = temp.split(",");

            String[] ans = {q.getAns(), q.getAns1(), q.getAns2()};
            List<String> answer = new ArrayList<>();
            for(String s : ans) {
                if(s != null && s.length() > 0) {
                    answer.add(s);
                }
            }

            for (String c : choose){
                if (q.getAns1().equals("") && q.getAns2().equals("")){
                    score = 1;
                    if (c.equals(q.getAns())){
                        tempScore += score;
                    }
                }

                if (!q.getAns1().equals("") && q.getAns2().equals("") || q.getAns1().equals("") && !q.getAns2().equals("")){
                    score = 0.5;

                    if (answer.contains(c)){
                        tempScore += score;
                    }
                    else {
                        tempScore -= score;
                    }
                }

                if (!q.getAns1().equals("") && !q.getAns2().equals("")){
                    score = 1.0/3.0;

                    if (answer.contains(c)){
                        tempScore += score;
                    }
                    else {
                        tempScore -= score;
                    }
                }
            }
            correct += tempScore;
            DecimalFormat f = new DecimalFormat("##.00");
            convert = Double.parseDouble(f.format(correct));
        }
        return convert;
    }

    @Override
    public void saveScore(Result result) {
        result.setUsername(result.getUsername());
        result.setTotalCorrect(result.getTotalCorrect());
        resultRepository.save(result);
    }

    @Override
    public List<Result> getTopScore() {
        List<Result> sList = resultRepository.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
        return sList;
    }

    @Override
    public List<Result> getTopScoreByExam(int idExam) {
        List<Result> sList = resultRepository.findTopByExam(idExam);
        return sList;
    }
}
