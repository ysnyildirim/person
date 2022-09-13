package com.yil.person.config;

import com.yil.person.model.GenderType;
import com.yil.person.repository.GenderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SetupDataLoader implements ApplicationListener<ContextStartedEvent> {


    @Autowired
    private GenderDao genderDao;

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        System.out.println("Start Up Events");
        System.out.println(new Date(event.getTimestamp()));
        System.out.println("----------------------");

        initGenderTypes();
    }

    private void initGenderTypes() {
        genderDao.save(GenderType.builder().id(1).name("Erkek").build());
        genderDao.save(GenderType.builder().id(2).name("Kadın").build());
    }

}
