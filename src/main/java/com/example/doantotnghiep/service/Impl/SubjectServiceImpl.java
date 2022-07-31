package com.example.doantotnghiep.service.Impl;

import com.example.doantotnghiep.model.Subject;
import com.example.doantotnghiep.repository.SubjectRepository;
import com.example.doantotnghiep.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public void save(Subject subject){subjectRepository.save(subject);}

    @Override
    public void delete(Subject subject){subjectRepository.delete(subject);}
    @Override
    public Subject findById(int id) {
        return subjectRepository.findById(id);
    }

    @Override
    public Iterable<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Page<Subject> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    @Override
    public Page<Subject> findAllByNameSubjectContaining(String name, Pageable pageable){
        return subjectRepository.findAllByNameContaining(name, pageable);
    }

    @Override
    public boolean subjectExists(String name){
        return subjectRepository.findByName(name).isPresent();
    }
}
