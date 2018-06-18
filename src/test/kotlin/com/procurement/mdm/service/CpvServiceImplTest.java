package com.procurement.mdm.service;

//import com.procurement.mdm.model.entity.Cpv;
//import com.procurement.mdm.model.entity.Language;
//import com.procurement.mdm.repository.CpvRepository;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(SpringExtension.class)
class CpvServiceImplTest {
//
//    private static CpvService cpvService;
//
//    private static Cpv cpv;
//
//    private static Language language;
//
//    @BeforeAll
//    static void setUp() {
//        List<Cpv> cpvs = new ArrayList<>();
//        language = new Language();
//        language.setName("Indo-European");
//        language.setFamily("English");
//        language.setIso6391("en");
//        language.setDescription("test language");
//        cpv = new Cpv();
//        cpv.setCode("03000000-1");
//        cpv.setName("Test cpv");
//        cpv.setGroup(1);
//        cpv.setParent("03000000-1");
//        cpv.setLanguage(language);
//        cpvs.add(cpv);
//        CpvRepository cpvRepository = mock(CpvRepository.class);
//        when(cpvRepository.findCpvsByLanguage_Iso6391(language.getIso6391())).thenReturn(cpvs);
//        when(cpvRepository.findCpvsByLanguage_Iso6391AndGroup(language.getIso6391(), cpv.getGroup())).thenReturn(cpvs);
//        when(cpvRepository.findCpvsByLanguage_Iso6391AndParent(language.getIso6391(), cpv.getParent())).thenReturn(cpvs);
//        when(cpvRepository.findCpvsByLanguage_Iso6391AndGroupAndParent(language.getIso6391(), cpv.getGroup(), cpv.getParent())).thenReturn(cpvs);
//        cpvService = new CpvServiceImpl(cpvRepository);
//    }
//
//    @Test
//    void getLanguage() {
//        assertTrue(language.getIso6391().equals("en"));
//        assertTrue(language.getName().equals("Indo-European"));
//        assertTrue(language.getFamily().equals("English"));
//        assertTrue(language.getIso6391().equals("en"));
//        assertTrue(language.getDescription().equals("test language"));
//    }
//
//    @Test
//    void getCpvByLanguageCode() {
//        List<Cpv> cpvs = cpvService.getCpvByLanguageCode(cpv.getLanguage().getIso6391());
//        assertTrue(cpvs.get(0).getCode().equals(cpv.getCode()));
//    }
//
//    @Test
//    void getCpvByParamLanguageCode() {
//        List<Cpv> cpvs = cpvService.getCpvByParam(language.getIso6391());
//        assertTrue(cpvs.get(0).getCode().equals(cpv.getCode()));
//    }
//
//    @Test
//    void getCpvByParamsLanguageCodeAndGroup() {
//        List<Cpv> cpvs = cpvService.getCpvByParam(language.getIso6391(), cpv.getGroup());
//        assertTrue(cpvs.get(0).getCode().equals(cpv.getCode()));
//    }
//
//    @Test
//    void getCpvByParamsLanguageCodeAndParent() {
//        List<Cpv> cpvs = cpvService.getCpvByParam(language.getIso6391(), cpv.getParent());
//        assertTrue(cpvs.get(0).getCode().equals(cpv.getCode()));
//    }
//
//    @Test
//    void getCpvByParamsLanguageCodeAndGroupAndParent() {
//        List<Cpv> cpvs = cpvService.getCpvByParam(language.getIso6391(), cpv.getGroup(), cpv.getParent());
//        assertTrue(cpvs.get(0).getCode().equals(cpv.getCode()));
//    }
}