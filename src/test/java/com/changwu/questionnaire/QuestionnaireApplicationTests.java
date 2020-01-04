package com.changwu.questionnaire;

import com.changwu.questionnaire.bean.Paper;
import com.changwu.questionnaire.bean.User;
import com.changwu.questionnaire.repository.PaperRepository;
import com.changwu.questionnaire.repository.UserRepository;
import com.changwu.questionnaire.repository.projection.UserProjection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionnaireApplicationTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PaperRepository paperRepository;

    @Test
    @Transactional
    public void contextLoads() {
        Optional<User> byId = userRepository.findById(1);
        if (byId.isPresent()) {
            User user = byId.get();
            System.out.println(user);
            System.out.println(user.getRoles());
            System.out.println("================");
        }

    }

    @Test
    @Transactional
    public void contextLoads1() {

        List<User> all = userRepository.findAll();
        System.out.println(all);
    }


    @Test
    @Transactional
    public void contextLoads2() {

        List<Paper> all1 = paperRepository.findAll();

        System.out.println(all1);

    }


    @Test
    @Transactional
    public void contextLoads3() {

     /*   Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = userRepository.findAll(pageable);
        List<User> content = page.getContent();
*/
 /*     Pageable pageable = PageRequest.of(0, 10);
        Page<User> page= userRepository.findAll(new Specification() {
            String name = "ad";
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicateList = new ArrayList<>();
                if (name != null) {
                    Path labelname = root.get("username");
                    Predicate like = criteriaBuilder.like(labelname.as(String.class), "%" + name + "%");
                    predicateList.add(like);
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                predicateList.toArray(predicates);
                // 参数位置支持可变参数, 但是我们要尽量传递进去有效的条件
                return criteriaBuilder.and(predicates);
            }
        }, pageable);
        List<User> content = page.getContent();
        content.forEach(item->{
           *//* for (Paper paper : item.getPapers()) {
                System.err.println("--------------");
                System.out.println(paper.getTitle());
            }*//*
        });*/
       /* System.out.println(content);
        System.out.println(content);*/

    }


}
