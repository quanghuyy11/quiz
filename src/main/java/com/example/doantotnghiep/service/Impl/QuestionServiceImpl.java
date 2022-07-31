package com.example.doantotnghiep.service.Impl;

import com.example.doantotnghiep.model.Question;
import com.example.doantotnghiep.model.Subject;
import com.example.doantotnghiep.repository.QuestionRepository;
import com.example.doantotnghiep.repository.SubjectRepository;
import com.example.doantotnghiep.service.QuestionService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question findById(Integer id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    @Override
    public Page<Question> findAllByTitleContaining(String title, Pageable pageable) {
        return questionRepository.findAllByTitleContaining(title, pageable);
    }

    @Override
    public Page<Question> findAllBySubject(Optional<Integer> id, Pageable pageable) {
        return questionRepository.findAllBySubject(id, pageable);
    }

    @Override
    public List<Question> findAllByExams(int id) {
        return questionRepository.findAllByExams(id);
    }

    @Override
    public void importFile(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Question tempQuestion = new Question();
            XSSFRow row = worksheet.getRow(i);

            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();

                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case 0:
                        //Set Subject
                        String subject = (String) getCellValue(row.getCell(0));
                        List<Subject> subjects = subjectRepository.findAll();
                        for (Subject s : subjects) {
                            if (subject.equals(s.getName())) {
                                tempQuestion.setSubject(s);
                            }
                        }
                        break;
                    case 1:
                        tempQuestion.setTitle((String) getCellValue(row.getCell(1)));
                        break;
                    //Set Option
                    case 2:
                        tempQuestion.setOptionA(removeLastChar(String.valueOf(getCellValue(row.getCell(2)))));
                        break;
                    case 3:
                        tempQuestion.setOptionB(removeLastChar(String.valueOf(getCellValue(row.getCell(3)))));
                        break;
                    case 4:
                        tempQuestion.setOptionC(removeLastChar(String.valueOf(getCellValue(row.getCell(4)))));
                        break;
                    case 5:
                        tempQuestion.setOptionD(removeLastChar(String.valueOf(getCellValue(row.getCell(5)))));
                        break;
                    case 6:
                        if (getCellValue(row.getCell(6)) == null){
                            tempQuestion.setOptionE(null);
                        } else {
                            String optionE = String.valueOf(getCellValue(row.getCell(6)));
                            tempQuestion.setOptionE(removeLastChar(optionE));
                        }
                        break;
                    case 7:
                        if (getCellValue(row.getCell(7)) == null){
                            tempQuestion.setOptionF(null);
                        } else {
                            String optionF = String.valueOf(getCellValue(row.getCell(7)));
                            tempQuestion.setOptionF(removeLastChar(optionF));
                        }
                        break;
                    case 8:
                        tempQuestion.setAns(removeLastChar(String.valueOf(getCellValue(row.getCell(8)))));
                        break;
                    case 9:
                        if (getCellValue(row.getCell(9)) == null){
                            tempQuestion.setAns1(null);
                        } else {
                            String ans1 = String.valueOf(getCellValue(row.getCell(9)));
                            tempQuestion.setAns1(removeLastChar(ans1));
                        }
                        break;
                    case 10:
                        if (getCellValue(row.getCell(10)) == null){
                            tempQuestion.setAns2(null);
                        } else {
                            String ans2 = String.valueOf(getCellValue(row.getCell(10)));
                            tempQuestion.setAns2(removeLastChar(ans2));
                        }
                        break;
                }
            }
            if ((tempQuestion.getAns1() == null && tempQuestion.getAns2() != null) || (tempQuestion.getAns2() == null && tempQuestion.getAns1() != null)){
                tempQuestion.setAnswerNumb(2);
            }
            if (tempQuestion.getAns2() == null && tempQuestion.getAns1() == null){
                tempQuestion.setAnswerNumb(1);
            }
            if (tempQuestion.getAns2() != null && tempQuestion.getAns1() != null){
                tempQuestion.setAnswerNumb(3);
            }
            questionRepository.save(tempQuestion);
        }
    }

    private static Object getCellValue(Cell cell) {
        Object cellValue = null;
        if (cell == null){
            return cellValue;
        } else {
            CellType cellType = cell.getCellTypeEnum();
            switch (cellType) {
                case NUMERIC:
                    cellValue = cell.getNumericCellValue();
                    break;
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case _NONE:
                case BLANK:
                case ERROR:
                    break;
                default:
                    break;
            }
        }
        return cellValue;
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 2);
    }
}
